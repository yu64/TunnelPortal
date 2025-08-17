package app.infrastructure;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import app.domain.config.Config;

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
