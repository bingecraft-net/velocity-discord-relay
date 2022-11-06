package net.bingecraft.velocity_discord_console;

public class Configuration {
  public final String token;
  public final long consoleChannelId;

  public Configuration(String token, long consoleChannelId) {
    this.token = token;
    this.consoleChannelId = consoleChannelId;
  }
}
