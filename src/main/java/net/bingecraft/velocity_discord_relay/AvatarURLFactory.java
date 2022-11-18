package net.bingecraft.velocity_discord_relay;

import java.util.UUID;

public class AvatarURLFactory {
  public String getAvatarUrl(UUID playerID) {
    return String.format("https://crafatar.com/avatars/%s", playerID);
  }
}
