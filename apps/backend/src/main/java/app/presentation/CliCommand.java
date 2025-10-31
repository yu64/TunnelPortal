package app.presentation;

import java.nio.file.Path;
import java.util.concurrent.Callable;

import app.domain.config.Config;
import app.infrastructure.ConfigParser;
import app.infrastructure.TemplateWriter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

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
  
  private final TemplateWriter writer;
  private final ConfigParser configParser;
  private final WebServer server;

  public CliCommand(
    ConfigParser configParser,
    TemplateWriter writer,
    WebServer server
  )
  {
    this.configParser = configParser;
    this.writer = writer;
    this.server = server;
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
    Path configPath
  )
  {
    try
    {
      Config config = configParser.parse(configPath);
      this.server.start(config);
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
      defaultValue = "./schema.json"
    )
    Path output,

    @Option(
      names = {"-c", "--config"},
      description = "Create config.yaml Template",
      defaultValue = "false"
    )
    boolean isEnableConfig,

    @Option(
      names = {"-s", "--config-schema"},
      description = "Create config.yaml Schema",
      defaultValue = "false"
    )
    boolean isEnableConfigSchema
  )
  {
    try
    {
      if(isEnableConfig) this.writer.saveConfig(output);
      if(isEnableConfigSchema) this.writer.saveConfigSchema(output);
      return 0;
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return 1;
    }
  }
  
}
