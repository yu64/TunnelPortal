package app.application;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import app.domain.Detector;
import app.domain.TunnelItem;
import app.domain.config.Config;

public class DetectUsecase
{
  private Config config;
  private Map<String, Detector> detectorDict;

  public DetectUsecase(
    Config config
  )
  {
    this.config = config;
    this.detectorDict = this.config
      .detectors()
      .stream()
      .collect(Collectors.toMap(v -> v.getId(), v -> v))
      ;
  }


  private CompletableFuture<List<TunnelItem>> detect(Detector detector)
  {
    return detector.detect();
  }


  
}
