package net.bingecraft.velocity_discord_console;

import com.moandjiezana.toml.Toml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigurationBuilder {
  private final Path dataDirectory;
  private final Path tokenPath;
  private final Path configurationPath;

  public ConfigurationBuilder(Path dataDirectory) {
    this.dataDirectory = dataDirectory;
    this.tokenPath = dataDirectory.resolve("token");
    this.configurationPath = dataDirectory.resolve("configuration.txt");
    if (!Files.exists(dataDirectory)) createDataDirectory();
    if (!Files.exists(tokenPath)) writeDefaultToken();
    if (!Files.exists(configurationPath)) writeDefaultConfiguration();
  }

  public Configuration build() {
    return new Configuration(
      readToken(),
      new Toml().read(configurationPath.toFile()).getLong("consoleChannelId")
    );
  }

  private void createDataDirectory() {
    try {
      Files.createDirectory(dataDirectory);
    }
    catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  private void writeDefaultToken() {
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tokenPath)) {
      bufferedWriter.write("default-token");
    }
    catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  private void writeDefaultConfiguration() {
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(configurationPath)) {
      bufferedWriter.write("consoleChannelId = 00000000000");
    }
    catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  private String readToken() {
    try (BufferedReader bufferedReader = Files.newBufferedReader(tokenPath)) {
      return bufferedReader.readLine();
    }
    catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
