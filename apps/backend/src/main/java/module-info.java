module app {
  
  // Java
  requires java.base;

  requires transitive com.fasterxml.jackson.annotation;
  requires transitive com.fasterxml.jackson.databind;
  requires transitive com.fasterxml.jackson.dataformat.yaml;
  requires transitive mbknor.jackson.jsonschema;
  requires transitive info.picocli;
  requires transitive scala.library;
  requires transitive io.github.classgraph;

  // 公開
  exports app;
  exports app.domain to com.fasterxml.jackson.databind;
  exports app.application.detector to com.fasterxml.jackson.databind;
  exports app.domain.config;

  // リフレクションアクセス
  opens app to info.picocli;
  opens app.presentation to info.picocli;
  
}
