package net.bingecraft.cheaper_netherite_repair;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
  @Override
  public void onEnable() {
    FileConfiguration config = getConfig();
    config.addDefault("durabilityPerNetherBrick", 0.5);
    config.options().copyDefaults(true);
    saveConfig();

    getServer().getPluginManager().registerEvents(
      new Listener(
        config.getDouble("durabilityPerNetherBrick"),
        (Runnable runnable) -> getServer().getScheduler().runTask(this, runnable)
      ),
      this
    );
  }
}
