package net.bingecraft.bloodmoon;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class BloodmoonRepeatingTask implements Runnable {
    private Plugin plugin;

    BloodmoonRepeatingTask(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        World world = plugin.getServer().getWorld("world");

        long fullTime = world.getFullTime();
        long time = world.getTime();
        boolean isBloodMoon = ((fullTime / 24000) % 3) > 1;
        boolean isNight = time > 12000 && time < 23000;
        if (isBloodMoon && isNight && world.getDifficulty() != Difficulty.HARD) {
            world.setDifficulty(Difficulty.HARD);
            Bukkit.getConsoleSender().sendMessage("difficulty set to hard");
            for (Player player : world.getPlayers()) {
                player.sendMessage("the blood moon is rising");
            }
        } else if (world.getDifficulty() == Difficulty.HARD && (!isBloodMoon || !isNight)) {
            world.setDifficulty(Difficulty.PEACEFUL);
            Bukkit.getConsoleSender().sendMessage("difficulty set to peaceful");
        }
    }
}
