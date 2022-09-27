package net.bingecraft.bloodmoon;

import org.bukkit.Difficulty;

public final class BloodmoonClock {
    public Difficulty getDifficulty(long tick) {
        return (tick + 71000L) % 72000L < 59000L
                ? Difficulty.PEACEFUL
                : Difficulty.HARD;
    }
}
