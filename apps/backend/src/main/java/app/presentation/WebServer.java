package app.presentation;

import java.util.List;

import app.domain.TunnelItem;
import app.domain.config.Config;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class WebServer
{
  private WsEventBus<List<TunnelItem>> bus;

  public WebServer(
    WsEventBus<List<TunnelItem>> bus
  )
  {
    this.bus = bus;
  }


  public void start(Config config)
  {
    Javalin app = Javalin.create(c -> {

      // クラスパス上の/publicディレクトリを静的リソースとして設定
      c.staticFiles.add("/public", Location.CLASSPATH);

    }).start(config.server().port());

    // WebSocketをEventBusに接続
    app.ws("/app/detect/all/ws", ws -> {
      ws.onConnect(ctx -> { this.bus.addContext(ctx); });
      ws.onClose(ctx -> { this.bus.removeContext(ctx); });
    });

    app.get("/api/detect/all", ctx -> {
      
    });

    app.get("/api/detect/{id}", ctx -> {
      
    });

    app.post("/api/group/start/{id}", ctx -> {

    });
    
    app.post("/api/group/stop/{id}", ctx -> {

    });

    app.post("/api/single/start/{id}", ctx -> {

    });

    app.post("/api/single/stop/{id}", ctx -> {

    });


  }
}
