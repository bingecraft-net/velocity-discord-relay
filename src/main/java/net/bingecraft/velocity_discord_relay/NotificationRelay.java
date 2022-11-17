package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.proxy.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.UUID;
import java.util.function.Consumer;

public class NotificationRelay implements Consumer<Notification> {
  private final TextChannel relayChannel;

  public NotificationRelay(Configuration configuration, JDA jda) {
    relayChannel = jda.getTextChannelById(configuration.relayChannelId);
    if (relayChannel == null) {
      throw new RuntimeException("Could not find relay channel with id: " + configuration.relayChannelId);
    }
  }

  @Override
  public void accept(Notification notification) {
    String avatarUrl = getAvatarUrl(notification.playerID);
    MessageEmbed embed = new EmbedBuilder().setAuthor(notification.message, avatarUrl, avatarUrl).build();

    relayChannel.sendMessageEmbeds(embed).queue();
  }

  private static String getAvatarUrl(UUID playerID) {
    return String.format("https://crafatar.com/avatars/%s", playerID);
  }
}
