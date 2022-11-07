package net.bingecraft.velocity_discord_relay;

public class Configuration {
  public final String token;
  public final long relayChannelId;

  public Configuration(String token, long relayChannelId) {
    this.token = token;
    this.relayChannelId = relayChannelId;
  }
}
