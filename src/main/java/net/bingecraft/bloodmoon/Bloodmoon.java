package net.bingecraft.bloodmoon;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bloodmoon extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        World world = getServer().getWorld("world");
        Runnable task = new BloodmoonRepeatingTask(world);
        getServer()
                .getScheduler()
                .scheduleSyncRepeatingTask(this, task, 0L, 20L);
    }
}
