package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.proxy.ProxyServer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;

public class JDAEventRelay extends ListenerAdapter {
  private final ProxyServer proxyServer;
  private final Configuration configuration;

  public JDAEventRelay(ProxyServer proxyServer, Configuration configuration) {
    this.proxyServer = proxyServer;
    this.configuration = configuration;
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    if (event.getChannel().getIdLong() != configuration.relayChannelId) return;
    String message = String.format(
      "[discord] %s: %s",
      event.getMember().getEffectiveName(),
      event.getMessage().getContentStripped()
    );
    proxyServer.sendMessage(Component.text(message));
  }
}
