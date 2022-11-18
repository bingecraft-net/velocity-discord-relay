package net.bingecraft.velocity_discord_relay;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.function.Consumer;

public class NotificationRelay implements Consumer<Notification> {
  private final AvatarURLFactory avatarURLFactory;
  private final TextChannel relayChannel;

  public NotificationRelay(Configuration configuration, JDA jda, AvatarURLFactory avatarURLFactory) {
    this.avatarURLFactory = avatarURLFactory;

    relayChannel = jda.getTextChannelById(configuration.relayChannelId);
    if (relayChannel == null) {
      throw new RuntimeException("Could not find relay channel with id: " + configuration.relayChannelId);
    }
  }

  @Override
  public void accept(Notification notification) {
    String avatarUrl = avatarURLFactory.getAvatarUrl(notification.playerID);
    MessageEmbed embed = new EmbedBuilder().setAuthor(notification.message, avatarUrl, avatarUrl).build();

    relayChannel.sendMessageEmbeds(embed).queue();
  }
}
