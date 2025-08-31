package app.infrastructure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import app.Main;
import app.domain.Detector;
import app.domain.GroupRunner;
import app.domain.SingleRunner;
import app.domain.config.Config;
import app.util.ClassUtil;

public class ConfigParser
{
  public ConfigParser()
  {
    // なし
  }

  public Config parse(Path path)
  {
    try
    {
      String yaml = Files.readString(path);
      ObjectMapper m = new ObjectMapper(new YAMLFactory());
      Set<Class<?>> subclasses = ClassUtil.findSubclasses(
        List.of(Main.class.getPackage()),
        List.of(
          Detector.class,
          GroupRunner.class,
          SingleRunner.class
        )
      );
      m.registerSubtypes(subclasses);
      Config config = m.readValue(yaml, Config.class);
      return config;
    }
    catch(Exception ex)
    {
      System.err.println(ex);
      return null;
    }
  }

}
