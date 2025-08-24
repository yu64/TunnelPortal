package app.infrastructure;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

import app.domain.config.Config;
import app.util.Scala2Util;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

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
    try(
      ScanResult scanResult = new ClassGraph()
        .enableClassInfo()
        .scan()
    )
    {
      ClassInfo parentInfo = scanResult.getClassInfo(base.getName());
      if(parentInfo == null) return Scala2Util.toScalaList(List.of());
      
      return Scala2Util.toScalaList(
        parentInfo.getClassesImplementing()
          .stream()
          .filter(info -> !info.isAbstract() && !info.isInterface())
          .map(info -> info.loadClass())
          .collect(Collectors.toList())
      );
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return Scala2Util.toScalaList(List.of());
    }
  }

}
