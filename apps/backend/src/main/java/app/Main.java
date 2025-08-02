package app;

import app.presentation.SetupCommand;
import picocli.CommandLine;

public class Main
{
    public static void main(String[] args)
    {
        SetupCommand cmd = new SetupCommand();
        CommandLine cmdLine = new CommandLine(cmd);
        System.exit(cmdLine.execute(args));
    }
}
