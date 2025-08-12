package app.presentation;

import java.util.concurrent.Callable;

import app.infrastructure.ConfigParser;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
  name = "tunnel-portal",
  mixinStandardHelpOptions = true,
  version = "tunnel-portal 1.0",
  description = "Application Start Command"
)
public class SetupCommand implements Callable<Integer>
{
  @Option(
    names = {"-c", "--config"},
    description = "Select Config File Path"
  )
  private String configPath = "./config.yaml";

  private final ConfigParser configParser;

  public SetupCommand(ConfigParser configParser)
  {
    this.configParser = configParser;
  }

  @Override
  public Integer call() throws Exception
  {
    try
    {
      System.out.println(configPath);
      // ConfigParserの呼び出し例（仮）
      // configParser.parse(configPath);
      return 0;
    }
    catch(Exception e)
    {
      System.err.println("Error: " + e.getMessage());
      return 1;
    }
  }
}
