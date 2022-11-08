package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class VelocityEventRelay {
  private final ProxyServer proxyServer;
  private final TextChannel relayChannel;

  public VelocityEventRelay(JDA jda, ProxyServer proxyServer, Configuration configuration) {
    this.proxyServer = proxyServer;
    relayChannel = jda.getTextChannelById(configuration.relayChannelId);
    if (relayChannel == null) {
      throw new RuntimeException("Could not find relay channel with id: " + configuration.relayChannelId);
    }
  }

  @Subscribe
  public void onServerConnected(ServerConnectedEvent event) {
    relayChannel.sendMessage(event.getPlayer().getUsername() + " joined the game").queue();
  }

  @Subscribe
  public void onDisconnect(DisconnectEvent event) {
    String username = event.getPlayer().getUsername();

    String discordMessage = String.format("%s left the game", username);
    relayChannel.sendMessage(discordMessage).queue();

    String gameMessage = String.format("%s left the game", username);
    sendMessage(event.getPlayer(), Component.text(gameMessage, NamedTextColor.YELLOW));
  }

  @Subscribe
  public void onPlayerChat(PlayerChatEvent event) {
    String username = event.getPlayer().getUsername();

    String discordMessage = String.format("%s: %s", username, event.getMessage());
    relayChannel.sendMessage(discordMessage).queue();

    String gameMessage = String.format("<%s> %s", username, event.getMessage());
    sendMessage(event.getPlayer(), Component.text(gameMessage));
  }

  private void sendMessage(Player source, Component message) {
    RegisteredServer originServer = getRegisteredServer(source);
    for (RegisteredServer server : proxyServer.getAllServers()) {
      if (server.equals(originServer)) continue;
      server.sendMessage(message);
    }
  }

  private RegisteredServer getRegisteredServer(Player player) {
    return player
      .getCurrentServer()
      .orElseThrow(() -> new PlayerNotConnectedException(player))
      .getServer();
  }

  private static class PlayerNotConnectedException extends RuntimeException {
    public PlayerNotConnectedException(Player player) {
      super(String.format("player %s is not connected to any server", player.getUsername()));
    }
  }
}
