package app.application.os;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import app.domain.Detector;
import app.domain.TunnelItem;
import app.domain.value.DetectorName;
import app.domain.value.LocalUrl;
import app.domain.value.TunnelName;
import app.domain.value.TunnelStatus;

public record WindowsDetector(
  String name,
  WindowsDetectorConfig config
) implements Detector<WindowsDetectorConfig>
{

  @Override
  public String getName()
  {
    return this.name();
  }

  @Override
  public WindowsDetectorConfig getConfig()
  {
    return this.config();
  }

  @Override
  public CompletableFuture<List<TunnelItem>> detect()
  {
    return this.execNetStat()
      .thenApply(entries -> entries.stream()
        .map(entry -> {

          return new TunnelItem(
            new LocalUrl(
              entry.proto.toLowerCase()
              + "://"
              + entry.localAddress
            ),
            Set.of(new DetectorName(this.getName())),
            TunnelStatus.Unknown,
            Set.of(new TunnelName("PID: " + entry.pid)),
            Optional.empty(),
            Optional.empty(),
            Map.of(
              "pid", entry.pid
            )
          );
        })
        .filter(item -> item != null)
        .toList()
      );
  }

  private CompletableFuture<List<NetStatEntry>> execNetStat()
  {
    return CompletableFuture.supplyAsync(() ->
    {
      ProcessBuilder pb = new ProcessBuilder(
        "cmd.exe",
        "/c",
        "netstat",
        "-ano",
        "|",
        "findstr",
        "LISTENING",
        "|",
        "findstr",
        "127.0.0.1"
      );
      pb.redirectErrorStream(true);

      // netstat stdout sample
      //TCP         127.0.0.1:8080         0.0.0.0:0              LISTENING       26224
      //TCP         127.0.0.1:80           0.0.0.0:0              LISTENING       6296

      Process p = null;
      try
      {
        p = pb.start();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.defaultCharset())))
        {
          return br.lines()
            .map(line -> line.trim().replaceAll("\\s+", " "))
            .map(line -> line.split(" "))
            .filter(parts -> parts.length >= 5)
            .map(parts -> new NetStatEntry(
              parts[0],
              parts[1],
              parts[2],
              parts[3],
              parts[4]
            ))
            .toList();
        }
      }
      catch(Exception e)
      {
        if(p != null) p.destroy();
        return List.of();
      }
    });
  }

  private record NetStatEntry(
    String proto,
    String localAddress,
    String foreignAddress,
    String state,
    String pid
  ) {}



}