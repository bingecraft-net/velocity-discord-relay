package net.bingecraft.velocity_discord_relay;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class RelayChannelBuilder {
  private final Configuration configuration;
  private final String token;

  public RelayChannelBuilder(Configuration configuration, String token) {
    this.configuration = configuration;
    this.token = token;
  }

  public TextChannel create() throws InterruptedException {
    JDA jda = net.dv8tion.jda.api.JDABuilder.createDefault(token)
      .enableIntents(GatewayIntent.MESSAGE_CONTENT)
      .build()
      .awaitReady();

    TextChannel channel = jda.getTextChannelById(configuration.relayChannelId);
    if (channel == null) {
      throw new RuntimeException("Could not find relay channel with id: " + configuration.relayChannelId);
    }
    return channel;
  }
}
