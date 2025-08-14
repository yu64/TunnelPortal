package app.domain.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import app.domain.Detector;
import app.domain.GroupRunner;
import app.domain.SingleRunner;

public record Config(
  ServerConfig server,
  List<DetectorConfig> detectors,
  List<GroupRunner> groupRunners,
  List<SingleRunner> singleRunners
)
{
  

}
