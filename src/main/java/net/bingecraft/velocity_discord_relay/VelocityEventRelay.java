package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.scheduler.Scheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VelocityEventRelay {
  private final Plugin plugin;
  private final ProxyServer proxyServer;
  private final Scheduler scheduler;
  private final Configuration configuration;

  private final AvatarURLFactory avatarURLFactory;
  private final TextChannel relayChannel;
  private final Map<UUID, Long> quitMessageIdByPlayerId = new ConcurrentHashMap<>();

  public VelocityEventRelay(
    Plugin plugin,
    ProxyServer proxyServer,
    Scheduler scheduler,
    Configuration configuration,
    TextChannel relayChannel,
    AvatarURLFactory avatarURLFactory
  ) {
    this.plugin = plugin;
    this.proxyServer = proxyServer;
    this.scheduler = scheduler;
    this.configuration = configuration;
    this.relayChannel = relayChannel;
    this.avatarURLFactory = avatarURLFactory;
  }

  @Subscribe
  public void onServerConnected(ServerConnectedEvent event) {
    if (event.getPreviousServer().isPresent()) return;

    Player player = event.getPlayer();
    UUID playerId = player.getUniqueId();

    Long quitMessageId = quitMessageIdByPlayerId.get(playerId);
    if (quitMessageId != null) {
      relayChannel.deleteMessageById(quitMessageId).queue();
    }
    else {
      String avatarUrl = avatarURLFactory.getAvatarUrl(playerId);
      String message = String.format("%s joined the game", player.getUsername());
      MessageEmbed embed = new EmbedBuilder().setAuthor(message, avatarUrl, avatarUrl).build();

      relayChannel.sendMessageEmbeds(embed).queue();
      sendMessage(Component.text(message, NamedTextColor.YELLOW));
    }
  }

  @Subscribe
  public void onDisconnect(DisconnectEvent event) {
    if (event.getLoginStatus() != DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN) return;

    Player player = event.getPlayer();
    String username = player.getUsername();
    UUID playerId = player.getUniqueId();

    String messageText = String.format("%s left the game", username);
    String avatarUrl = avatarURLFactory.getAvatarUrl(playerId);
    MessageEmbed embed = new EmbedBuilder().setAuthor(messageText, avatarUrl, avatarUrl).build();

    relayChannel.sendMessageEmbeds(embed).queue(
      message -> {
        long messageId = message.getIdLong();
        quitMessageIdByPlayerId.put(playerId, messageId);
        scheduleForgetQuitMessage(playerId, messageId);
      }
    );
    sendMessage(Component.text(messageText, NamedTextColor.YELLOW));
  }

  private void scheduleForgetQuitMessage(UUID playerId, long messageId) {
    scheduler
      .buildTask(plugin, () -> {
        if (quitMessageIdByPlayerId.get(playerId) == messageId)
          quitMessageIdByPlayerId.remove(playerId);
      })
      .delay(Duration.ofSeconds(configuration.quitMessageMinAgeSeconds))
      .schedule();
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
