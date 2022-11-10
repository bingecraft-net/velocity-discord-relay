package net.bingecraft.velocity_discord_relay;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class JDABuilder {
  private final String token;

  public JDABuilder(String token) {
    this.token = token;
  }

  public JDA create() throws InterruptedException {
    return net.dv8tion.jda.api.JDABuilder.createDefault(token)
      .enableIntents(GatewayIntent.MESSAGE_CONTENT)
      .build()
      .awaitReady();
  }
}
