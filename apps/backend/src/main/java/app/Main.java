package app;

import app.infrastructure.ConfigParser;
import app.infrastructure.TemplateWriter;
import app.presentation.CliCommand;
import app.presentation.WebServer;
import picocli.CommandLine;

public class Main
{
    public static void main(String[] args)
    {
        TemplateWriter writer = new TemplateWriter();
        ConfigParser configParser = new ConfigParser();
        WebServer server = new WebServer();

        CliCommand cmd = new CliCommand(
            configParser,
            writer,
            server
        );

        CommandLine cmdLine = new CommandLine(cmd);
        System.exit(cmdLine.execute(args));
    }
}
