package net.bingecraft.velocity_discord_relay;

import com.moandjiezana.toml.Toml;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigurationReader {
  private final Path configurationPath;

  public ConfigurationReader(Path dataDirectory) {
    this.configurationPath = dataDirectory.resolve("configuration.toml");
    if (!Files.exists(configurationPath)) writeDefaultConfiguration();
  }

  public Configuration build() {
    return new Toml().read(configurationPath.toFile()).to(Configuration.class);
  }


  private void writeDefaultConfiguration() {
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(configurationPath)) {
      bufferedWriter.write("relayChannelId = 00000000000");
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
