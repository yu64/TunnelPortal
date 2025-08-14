package app.presentation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;

import app.infrastructure.ConfigParser;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

@Command(
  name = "tunnel-portal",
  mixinStandardHelpOptions = true,
  version = "tunnel-portal 1.0",
  description = "Application Command"
)
public class CliCommand implements Callable<Integer>
{
  
  @Spec
  private CommandSpec spec;
  
  private final ConfigParser configParser;

  public CliCommand(ConfigParser configParser)
  {
    this.configParser = configParser;
  }

  @Override
  public Integer call() throws Exception
  {
    this.spec.commandLine().printVersionHelp(System.out);
    return 0;
  }

  
  @Command(
    name = "server",
    description = "Application Server Command"
  )
  public Integer server(
    @Option(
      names = {"-c", "--config"},
      description = "Select Config File Path",
      defaultValue = "./config.yaml"
    )
    String configPath
  )
  {
    try
    {
      configParser.parse(configPath);
      return 0;
    }
    catch(Exception e)
    {
      System.err.println("Error: " + e.getMessage());
      return 1;
    }
  }


  @Command(
    name = "init",
    description = "Create Template"
  )
  public Integer init(
    @Option(
      names = {"-o", "--output"},
      description = "Output Path",
      defaultValue = "."
    )
    Path output,

    @Option(
      names = {"-c", "--config"},
      description = "Create config.yaml Template",
      defaultValue = "false"
    )
    boolean isEnableConfig
  )
  {
    try
    {
      Files.writeString(output, "config", StandardOpenOption.CREATE_NEW);
      return 0;
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return 1;
    }
  }
  
}
