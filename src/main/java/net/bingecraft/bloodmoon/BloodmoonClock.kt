package net.bingecraft.bloodmoon;

import org.bukkit.Difficulty

class BloodmoonClock {
  fun getDifficulty(tick: Long): Difficulty {
    return if ((tick + 71000L) % 72000L < 59000L) Difficulty.PEACEFUL
    else Difficulty.HARD
  }
}
