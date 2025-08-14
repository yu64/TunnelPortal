package app;

import app.infrastructure.ConfigParser;
import app.presentation.CliCommand;
import picocli.CommandLine;

public class Main
{
    public static void main(String[] args)
    {
        ConfigParser configParser = new ConfigParser();
        CliCommand cmd = new CliCommand(configParser);
        CommandLine cmdLine = new CommandLine(cmd);
        System.exit(cmdLine.execute(args));
    }
}
