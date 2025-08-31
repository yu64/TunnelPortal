package app.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * サービスやリソースから定義されているトンネルを検出するクラス
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.SIMPLE_NAME, property = "type")
public interface Detector
{
  /** 識別名称 */
  @JsonProperty(required = true)
  public String getName();

  /** 現在のトンネルを検出します */
  @JsonIgnore
  public CompletableFuture<List<TunnelItem>> detect();
}
