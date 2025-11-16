package app.application;

import java.util.List;
import java.util.concurrent.TimeUnit;

import app.domain.TunnelItem;
import app.domain.config.Config;
import app.infrastructure.Logger;
import app.presentation.WsEventBus;

public class DetectionWorker
{
  private Config config;
  private DetectUsecase usecase;
  private WsEventBus<List<TunnelItem>> bus;
  private Logger logger;
  private Thread worker;

  public DetectionWorker(
    Config config,
    DetectUsecase usecase,
    WsEventBus<List<TunnelItem>> bus,
    Logger logger
  )
  {
    this.config = config;
    this.usecase = usecase;
    this.bus = bus;
    this.logger = logger;
    this.worker = new Thread(this::run);
  }

  public void start()
  {
    this.worker.start();
  }

  public void stop()
  {
    this.worker.interrupt();
  }

  private void run()
  {
    try
    {
      while(true)
      {
        if(this.worker.isInterrupted()) return;
        
        this.logger.info(DetectionWorker.class, "Running Worker!");
        TimeUnit.MINUTES.sleep(1);
      }
    }
    catch(Exception e)
    {
      return;
    }
  }
}
