package app.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SingleRunner
{
  /** 識別名称 */
  public String getName();
  
  /** トンネルを起動し、管理下のTunnelItemを返す */
  CompletableFuture<List<TunnelItem>> start(TunnelItem tunnel);

  /** トンネルを停止する。成功すればtrue */
  boolean stop();

  /** トンネルの現在の状態を取得（オプション） */
  CompletableFuture<List<TunnelItem>> getStatus();
}
