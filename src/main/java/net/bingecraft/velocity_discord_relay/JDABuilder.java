package net.bingecraft.velocity_discord_relay;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class JDABuilder {
  private final Configuration configuration;

  public JDABuilder(Configuration configuration) {
    this.configuration = configuration;
  }

  public JDA create() throws InterruptedException {
    return net.dv8tion.jda.api.JDABuilder.createDefault(configuration.token)
      .enableIntents(GatewayIntent.MESSAGE_CONTENT)
      .build()
      .awaitReady();
  }
}
