package app.domain;

import java.util.List;

/**
 * サービスやリソースから定義されているトンネルを検出するクラス
 */
public interface Detector<C>
{
  /** 識別名称 */
  public String getName();

  /** 現在のトンネルを検出します */
  public List<TunnelItem> detect(C config);
}
