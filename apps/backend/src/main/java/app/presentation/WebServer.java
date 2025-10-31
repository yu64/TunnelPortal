package app.presentation;

import app.domain.config.Config;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class WebServer
{



  public WebServer(

  )
  {

  }


  public void start(Config config)
  {
    Javalin app = Javalin.create(c -> {

      // クラスパス上の/publicディレクトリを静的リソースとして設定
      c.staticFiles.add("/public", Location.CLASSPATH);

    }).start(config.server().port());


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
