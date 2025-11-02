package app.presentation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import app.infrastructure.Logger;
import io.javalin.websocket.WsContext;

public class WsEventBus<T>
{
  private Logger logger;
  private Set<WsContext> ctxSet = Collections.synchronizedSet(new HashSet<>());

  public WsEventBus(
    Logger logger
  )
  {
    this.logger = logger;
  }

  /** WsContextを追加し、Eventの供給を開始する。*/
  public void addContext(WsContext ctx)
  {
    this.ctxSet.add(ctx);
  }

  /** WsContextを削除し、Eventの供給を停止する。 */
  public void removeContext(WsContext ctx)
  {
    if(!this.ctxSet.remove(ctx))
    {
      this.logger.warn(WsEventBus.class, "failed remove WsContext.");
    }
  }

  /** WsContextにイベントを通知する */
  public void notify(T value)
  {
    this.ctxSet.forEach(ctx -> ctx.send(value));
  }

}
