package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VelocityEventRelay {
  private final ProxyServer proxyServer;
  private final TextChannel relayChannel;
  private final Map<UUID, QuitMessageDetails> quitMessageDetailsByPlayerId = new ConcurrentHashMap<>();

  private static class QuitMessageDetails {
    public long timeMillis;
    public long messageId;

    public QuitMessageDetails(long timeMillis, long messageId) {
      this.timeMillis = timeMillis;
      this.messageId = messageId;
    }
  }

  public VelocityEventRelay(JDA jda, ProxyServer proxyServer, Configuration configuration) {
    this.proxyServer = proxyServer;

    relayChannel = jda.getTextChannelById(configuration.relayChannelId);
    if (relayChannel == null) {
      throw new RuntimeException("Could not find relay channel with id: " + configuration.relayChannelId);
    }
  }

  @Subscribe
  public void onServerConnected(ServerConnectedEvent event) {
    if (event.getPreviousServer().isPresent()) return;

    UUID id = event.getPlayer().getUniqueId();
    QuitMessageDetails quitMessageDetails = quitMessageDetailsByPlayerId.get(id);
    if (quitMessageDetails != null && System.currentTimeMillis() - quitMessageDetails.timeMillis < 60_000) {
      relayChannel.deleteMessageById(quitMessageDetails.messageId).queue();
    } else {
      String username = event.getPlayer().getUsername();
      String message = String.format("%s joined the game", username);

      relayChannel.sendMessage(message).queue();
      sendMessage(Component.text(message, NamedTextColor.YELLOW));
    }
  }

  @Subscribe
  public void onDisconnect(DisconnectEvent event) {
    if (event.getLoginStatus() != DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN) return;

    String username = event.getPlayer().getUsername();
    String messageText = String.format("%s left the game", username);

    relayChannel.sendMessage(messageText).queue(
      (message) -> quitMessageDetailsByPlayerId.put(
        event.getPlayer().getUniqueId(),
        new QuitMessageDetails(System.currentTimeMillis(), message.getIdLong())
      )
    );
    sendMessage(Component.text(messageText, NamedTextColor.YELLOW));
  }

  @Subscribe
  public void onPlayerChat(PlayerChatEvent event) {
    String username = event.getPlayer().getUsername();

    String discordMessage = String.format("%s: %s", username, event.getMessage());
    relayChannel.sendMessage(discordMessage).queue();

    String gameMessage = String.format("<%s> %s", username, event.getMessage());
    sendMessage(Component.text(gameMessage));
  }

  private void sendMessage(Component message) {
    for (RegisteredServer server : proxyServer.getAllServers()) {
      server.sendMessage(message);
    }
  }
}
