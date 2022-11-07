package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class VelocityEventRelay {
  private final TextChannel relayChannel;

  public VelocityEventRelay(JDA jda, Configuration configuration) {
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
    relayChannel.sendMessage(event.getPlayer().getUsername() + " left the game").queue();
  }

  @Subscribe
  public void onPlayerChat(PlayerChatEvent event) {
    String message = String.format("%s: %s", event.getPlayer().getUsername(), event.getMessage());
    relayChannel.sendMessage(message).queue();
  }
}
