package app.infrastructure;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

public class TemplateWriter
{
  public void saveConfig(Path output)
  {
    try
    {
      InputStream is = this.getClass().getClassLoader().getResourceAsStream("config-sample.yaml");
      Files.copy(is, output, StandardCopyOption.REPLACE_EXISTING);
    }
    catch(Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public void saveConfigSchema(Path output)
  {
    try
    {
      ObjectMapper m = new ObjectMapper();
      JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(m);
      JsonSchema schema = schemaGen.generateSchema(SimpleBean.class);
    }
    catch(Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }
}
