package net.bingecraft.velocity_discord_relay;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public final class ConfigurationReader {
  private final File file;

  public ConfigurationReader(Path dataDirectory) {
    file = dataDirectory.resolve("configuration.toml").toFile();
  }

  public Configuration read() {
    Toml defaults = new Toml()
      .read(getClass().getResourceAsStream("/default-configuration.toml"));
    Configuration configuration = new Toml(defaults)
      .read(file)
      .to(Configuration.class);
    try {
      new TomlWriter().write(configuration, file);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    return configuration;
  }
}
