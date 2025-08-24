package app.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * サービスやリソースから定義されているトンネルを検出するクラス
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.SIMPLE_NAME, property = "type")
public interface Detector<C>
{
  /** 識別名称 */
  public String getName();

  /** 設定 */
  public C getConfig();

  /** 現在のトンネルを検出します */
  public CompletableFuture<List<TunnelItem>> detect();
}
