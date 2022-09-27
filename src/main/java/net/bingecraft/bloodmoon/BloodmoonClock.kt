package net.bingecraft.bloodmoon;

import org.bukkit.Difficulty

class BloodmoonClock(
  private val intervalTicks: Long,
  private val startTicks: Long,
  private val endTicks: Long
) {
  fun getDifficulty(tick: Long): Difficulty {
    val offset = intervalTicks + intervalTicks - endTicks
    val isBloodmoon = (tick + offset) % intervalTicks >= (startTicks + offset) % intervalTicks
    return if (isBloodmoon) Difficulty.HARD else Difficulty.PEACEFUL
  }
}
