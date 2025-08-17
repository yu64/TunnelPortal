package app;

import app.infrastructure.ConfigParser;
import app.infrastructure.TemplateWriter;
import app.presentation.CliCommand;
import picocli.CommandLine;

public class Main
{
    public static void main(String[] args)
    {
        TemplateWriter writer = new TemplateWriter();
        ConfigParser configParser = new ConfigParser();


        CliCommand cmd = new CliCommand(
            configParser,
            writer
        );

        CommandLine cmdLine = new CommandLine(cmd);
        System.exit(cmdLine.execute(args));
    }
}
