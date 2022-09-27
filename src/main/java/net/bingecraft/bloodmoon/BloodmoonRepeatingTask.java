package net.bingecraft.bloodmoon;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class BloodmoonRepeatingTask implements Runnable {
    private BloodmoonClock bloodmoonClock;
    private World world;

    BloodmoonRepeatingTask(BloodmoonClock bloodmoonClock, World world) {
        this.bloodmoonClock = bloodmoonClock;
        this.world = world;
    }

    @Override
    public void run() {
        Difficulty have = world.getDifficulty();
        Difficulty want = bloodmoonClock.getDifficulty(world.getFullTime());
        if (have != want) {
            world.setDifficulty(want);
            Bukkit.getConsoleSender().sendMessage("difficulty set to " + want);
        }

        if (have != want && want == Difficulty.HARD) {
            for (Player player : world.getPlayers()) {
                player.sendMessage("the blood moon is rising");
            }
        }
    }
}
