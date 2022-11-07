package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class EventRelay {
  private final TextChannel textChannel;

  public EventRelay(JDA jda, Configuration configuration) {
    textChannel = jda.getTextChannelById(configuration.consoleChannelId);
  }

  @Subscribe
  public void onServerConnected(ServerConnectedEvent event) {
    textChannel.sendMessage(event.getPlayer().getUsername() + " joined the game").queue();
  }

  @Subscribe
  public void onDisconnect(DisconnectEvent event) {
    textChannel.sendMessage(event.getPlayer().getUsername() + " left the game").queue();
  }
}
