package net.bingecraft.velocity_discord_relay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TokenReader {
  private final Path tokenPath;

  public TokenReader(Path dataDirectory) {
    this.tokenPath = dataDirectory.resolve("token");
  }

  public String read() {
    if (!Files.exists(tokenPath)) writeDefaultToken();
    try (BufferedReader bufferedReader = Files.newBufferedReader(tokenPath)) {
      return bufferedReader.readLine();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  private void writeDefaultToken() {
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tokenPath)) {
      bufferedWriter.write("default-token");
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
