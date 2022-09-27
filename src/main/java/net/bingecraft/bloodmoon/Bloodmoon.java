package net.bingecraft.bloodmoon;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bloodmoon extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        getServer()
                .getScheduler()
                .scheduleSyncRepeatingTask(this, new BloodmoonRepeatingTask(this), 0L, 20L);
    }
}
