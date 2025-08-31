package app.infrastructure;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

import app.Main;
import app.domain.config.Config;
import app.util.ClassUtil;
import app.util.Scala2Util;

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
      JsonSchemaConfig config = JsonSchemaConfig.vanillaJsonSchemaDraft4()
        .withSubclassesResolver(this::resolveSubClass);

      ObjectMapper m = new ObjectMapper();
      JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(m, config);
      JsonNode schema = schemaGen.generateJsonSchema(Config.class);

      Path dir = output.getParent();
      if(!Files.exists(dir)) Files.createDirectory(dir);
      
      Files.writeString(
        output,
        schema.toPrettyString(),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
      );
    }
    catch(Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  
  private scala.collection.immutable.List<Class<?>> resolveSubClass(Class<?> base)
  {
    return Scala2Util.toScalaList(new ArrayList<>(ClassUtil.findSubclasses(
      List.of(Main.class.getPackage()),
      List.of(base))
    ));
  }

}
