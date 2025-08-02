package app.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import app.domain.value.DetectorName;
import app.domain.value.LocalUrl;
import app.domain.value.TunnelName;
import app.domain.value.TunnelProvider;
import app.domain.value.TunnelStatus;
import app.domain.value.TunnelUrl;
import app.util.StringUtil;

/**
 * あるトンネルを表すクラス
 * あるローカルURLと対応する公開URLを持つ。
 */
public record TunnelItem(
  LocalUrl localUrl,
  Set<DetectorName> sources,
  TunnelStatus status,
  Set<TunnelName> tunnelNames,
  Optional<TunnelProvider> tunnelProvider,
  Optional<TunnelUrl> tunnelUrl,
  Map<String, String> meta
)
{
  public TunnelItem
  {
    Objects.requireNonNull(localUrl, "localUrl must not be null");
    Objects.requireNonNull(sources, "sources set must not be null");
    Objects.requireNonNull(status, "status must not be null");
    Objects.requireNonNull(tunnelNames, "tunnelNames set must not be null");
    Objects.requireNonNull(tunnelProvider, "tunnelProvider Optional must not be null");
    Objects.requireNonNull(tunnelUrl, "tunnelUrl Optional must not be null");
    Objects.requireNonNull(meta, "meta map must not be null");

    // コレクションを不変にするための防御的コピー
    sources = Collections.unmodifiableSet(new HashSet<>(sources));
    tunnelNames = Collections.unmodifiableSet(new HashSet<>(tunnelNames));
    meta = Collections.unmodifiableMap(new HashMap<>(meta));
  }


  public String getId()
  {
    return StringUtil.sha256(String.join(
      "::",
      "$",
      this.localUrl.value(),
      this.tunnelUrl.map(v -> v.value()).orElse(""),
      this.tunnelProvider.map(v -> v.value()).orElse(""),
      "$"
    ));
  }

  @Override
  public boolean equals(Object o)
  {
      if(this == o) return true;
      if(o == null || getClass() != o.getClass()) return false;

      TunnelItem item = (TunnelItem) o;
      return Objects.equals(this.getId(), item.getId());
  }

  @Override
  public int hashCode()
  {
      return Objects.hash(getId());
  }

  
  public TunnelItem merge(TunnelItem other)
  {
    if(!this.getId().equals(other.getId()))
    {
      throw new IllegalArgumentException("Cannot merge TunnelItems with different IDs. This ID: " + this.getId() + ", Other ID: " + other.getId());
    }

    // 状態の優先順位に基づくメタデータのマージ
    Map<String, String> mergedMeta = new HashMap<>();
    if(this.status.isHigherPriorityThan(other.status))
    {
      // this 優先
      mergedMeta.putAll(other.meta);
      mergedMeta.putAll(this.meta);
    }
    else
    {
      // other 優先 (同等である場合も含む)
      mergedMeta.putAll(this.meta);
      mergedMeta.putAll(other.meta);
    }
    

    return new TunnelItem(
      this.localUrl,
      Stream.of(this.sources, other.sources)
        .flatMap(v -> v.stream())
        .collect(Collectors.toSet()),
      (
        this.status.isHigherPriorityThan(other.status)
        ? this.status
        : other.status
      ),
      Stream.of(this.tunnelNames, other.tunnelNames)
        .flatMap(v -> v.stream())
        .collect(Collectors.toSet()),
      this.tunnelProvider,
      this.tunnelUrl,
      mergedMeta
    );
  }
}
