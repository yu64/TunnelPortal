package app.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface SingleRunner
{
  /** 識別名称 */
  @JsonIgnore
  public String getName();
  
  /** トンネルを起動し、管理下のTunnelItemを返す */
  CompletableFuture<List<TunnelItem>> start(TunnelItem tunnel);

  /** トンネルを停止する。成功すればtrue */
  boolean stop();

  /** トンネルの現在の状態を取得（オプション） */
  @JsonIgnore
  CompletableFuture<List<TunnelItem>> getStatus();
}
