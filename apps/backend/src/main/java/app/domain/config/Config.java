package app.domain.config;

import java.util.List;
import java.util.Objects;

import app.domain.Detector;
import app.domain.GroupRunner;
import app.domain.SingleRunner;

public record Config(
  ServerConfig server,
  List<Detector> detectors,
  List<GroupRunner> groupRunners,
  List<SingleRunner> singleRunners
)
{
  public Config
  {
    Objects.requireNonNull(server);
    if(detectors == null) detectors = List.of();
    if(groupRunners == null) groupRunners = List.of();
    if(singleRunners == null) singleRunners = List.of();
  }

}
