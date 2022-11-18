package net.bingecraft.velocity_discord_relay;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class RelayChannelConnector {
  private final Configuration configuration;
  private final JDA jda;

  public RelayChannelConnector(Configuration configuration, JDA jda) {
    this.configuration = configuration;
    this.jda = jda;
  }

  public TextChannel connect() {
    TextChannel channel = jda.getTextChannelById(configuration.relayChannelId);
    if (channel == null) {
      throw new RuntimeException("Could not find relay channel with id: " + configuration.relayChannelId);
    }
    return channel;
  }
}
