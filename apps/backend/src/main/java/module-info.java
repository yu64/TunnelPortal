module app {
  
  // Java
  requires java.base;

  requires transitive com.fasterxml.jackson.databind;
  requires transitive com.fasterxml.jackson.dataformat.yaml;
  requires transitive info.picocli;

  // 公開
  exports app;

  // Picocli
  opens app to info.picocli;
  opens app.presentation to info.picocli;
}
