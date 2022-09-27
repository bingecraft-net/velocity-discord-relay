package net.bingecraft.bloodmoon;

import org.bukkit.Difficulty;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements CommandExecutor {
  @Override
  public void onEnable() {
    FileConfiguration config = getConfig();
    config.addDefault("intervalTicks", 72000L);
    config.addDefault("startTicks", 60000L);
    config.addDefault("endTicks", 73000L);
    config.options().copyDefaults(true);
    saveConfig();

    Runnable task = new BloodmoonRepeatingTask(
      new BloodmoonClock(
        config.getLong("intervalTicks"),
        config.getLong("startTicks"),
        config.getLong("endTicks")
      ),
      getServer().getWorld("world")
    );
    getServer()
      .getScheduler()
      .scheduleSyncRepeatingTask(this, task, 0L, 20L);
  }

  @Override
  public void onDisable() {
    getServer().getWorld("world").setDifficulty(Difficulty.HARD);
  }
}
