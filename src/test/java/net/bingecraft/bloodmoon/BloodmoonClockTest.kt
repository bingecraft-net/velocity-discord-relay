package net.bingecraft.bloodmoon

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.bukkit.Difficulty

fun ticks(days: Long, ticks: Long) = days * 24000 + ticks

class BloodmoonClockTest : FunSpec({
  val clock = BloodmoonClock()
  test("should be hard until 2000 ticks after first sunrise") {
    for (time in 0L until ticks(0, 1000L)) {
      clock.getDifficulty(time) shouldBe Difficulty.HARD
    }
  }
  test("should be peaceful between 2000 ticks after first sunrise and third sunset") {
    for (time in ticks(0, 1000L) until ticks(2, 12000L)) {
      clock.getDifficulty(time) shouldBe Difficulty.PEACEFUL
    }
  }
  test("should be hard between third sunset and 2000 ticks after fourth sunrise") {
    for (time in ticks(2, 12000L) until ticks(3, 1000L)) {
      clock.getDifficulty(time) shouldBe Difficulty.HARD
    }
  }
  test("should be peaceful between 2000 ticks after fourth sunrise and sixth sunset") {
    for (tick in ticks(3, 1000L) until ticks(5, 12000L)) {
      clock.getDifficulty(tick) shouldBe Difficulty.PEACEFUL
    }
  }
  test("should be hard between sixth sunset and 2000 ticks after seventh sunrise") {
    for (time in ticks(5, 12000L) until ticks(6, 1000L)) {
      clock.getDifficulty(time) shouldBe Difficulty.HARD
    }
  }
})
