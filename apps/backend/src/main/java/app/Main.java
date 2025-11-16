package app;

import java.util.List;

import app.domain.TunnelItem;
import app.infrastructure.ConfigParser;
import app.infrastructure.Logger;
import app.infrastructure.Logger.Level;
import app.infrastructure.TemplateWriter;
import app.presentation.CliCommand;
import app.presentation.WebServer;
import app.presentation.WsEventBus;
import picocli.CommandLine;

public class Main
{
    public static void main(String[] args)
    {
        Logger logger = new Logger(Level.Debug);
        WsEventBus<List<TunnelItem>> bus = new WsEventBus<>(logger);


        WebServer server = new WebServer(bus);
        TemplateWriter writer = new TemplateWriter();
        ConfigParser configParser = new ConfigParser();

        CliCommand cmd = new CliCommand(
            configParser,
            writer,
            server
        );

        CommandLine cmdLine = new CommandLine(cmd);
        System.exit(cmdLine.execute(args));
    }
}
