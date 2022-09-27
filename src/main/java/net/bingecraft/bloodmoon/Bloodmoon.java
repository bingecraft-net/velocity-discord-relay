package net.bingecraft.bloodmoon;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bloodmoon extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        Runnable task = new BloodmoonRepeatingTask(
                new BloodmoonClock(),
                getServer().getWorld("world")
        );
        getServer()
                .getScheduler()
                .scheduleSyncRepeatingTask(this, task, 0L, 20L);
    }
}
