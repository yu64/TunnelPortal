package app;

import app.infrastructure.ConfigParser;
import app.presentation.SetupCommand;
import picocli.CommandLine;

public class Main
{
    public static void main(String[] args)
    {
        ConfigParser configParser = new ConfigParser();
        SetupCommand cmd = new SetupCommand(configParser);
        CommandLine cmdLine = new CommandLine(cmd);
        System.exit(cmdLine.execute(args));
    }
}
