package net.bingecraft.velocity_discord_relay;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.function.Consumer;

public class NotificationRelay implements Consumer<Notification> {
  private final TextChannel relayChannel;
  private final AvatarURLFactory avatarURLFactory;

  public NotificationRelay(TextChannel relayChannel, AvatarURLFactory avatarURLFactory) {
    this.avatarURLFactory = avatarURLFactory;
    this.relayChannel = relayChannel;
  }

  @Override
  public void accept(Notification notification) {
    String avatarUrl = avatarURLFactory.getAvatarUrl(notification.playerID);
    MessageEmbed embed = new EmbedBuilder().setAuthor(notification.message, avatarUrl, avatarUrl).build();

    relayChannel.sendMessageEmbeds(embed).queue();
  }
}
