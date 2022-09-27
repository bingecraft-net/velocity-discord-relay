package net.bingecraft.bloodmoon

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.bukkit.Difficulty

fun ticks(days: Long, ticks: Long) = days * 24000 + ticks

class BloodmoonClockTest : FunSpec({
  context("three day interval, bloodmoon rises third sunset and ends 2000 ticks after fourth sunrise") {
    val intervalTicks = 72000L
    val startTicks = 60000L
    val endTicks = 73000L
    val clock = BloodmoonClock(
      intervalTicks, startTicks, endTicks
    )
    test("should be hard end of 0th blood moon") {
      for (time in 0L until ticks(0, 1000L)) {
        clock.getDifficulty(time) shouldBe Difficulty.HARD
      }
    }
    test("should be peaceful until start of 1st blood moon") {
      for (time in ticks(0, 1000L) until ticks(2, 12000L)) {
        clock.getDifficulty(time) shouldBe Difficulty.PEACEFUL
      }
    }
    test("should be hard until end of 1st bloodmoon") {
      for (time in ticks(2, 12000L) until ticks(3, 1000L)) {
        clock.getDifficulty(time) shouldBe Difficulty.HARD
      }
    }
    test("should be peaceful until start of 2nd bloodmoon") {
      for (tick in ticks(3, 1000L) until ticks(5, 12000L)) {
        clock.getDifficulty(tick) shouldBe Difficulty.PEACEFUL
      }
    }
    test("should be hard until end of 2nd bloodmoon") {
      for (time in ticks(5, 12000L) until ticks(6, 1000L)) {
        clock.getDifficulty(time) shouldBe Difficulty.HARD
      }
    }
  }
  context("two day interval, bloodmoon rises second sunset and ends fourth sunrise") {
    val intervalTicks = 48000L
    val startTicks = 36000L
    val endTicks = 47000L
    val clock = BloodmoonClock(
      intervalTicks, startTicks, endTicks
    )
    test("should be peaceful until start of 1st blood moon") {
      for (time in ticks(0, 0L) until ticks(1, 12000L)) {
        clock.getDifficulty(time) shouldBe Difficulty.PEACEFUL
      }
    }
    test("should be hard until end of 1st bloodmoon") {
      for (time in ticks(1, 12000L) until ticks(1, 23000L)) {
        clock.getDifficulty(time) shouldBe Difficulty.HARD
      }
    }
    test("should be peaceful until start of 2nd bloodmoon") {
      for (tick in ticks(1, 23000L) until ticks(3, 12000L)) {
        clock.getDifficulty(tick) shouldBe Difficulty.PEACEFUL
      }
    }
    test("should be hard until end of 2nd bloodmoon") {
      for (time in ticks(3, 12000L) until ticks(3, 23000L)) {
        clock.getDifficulty(time) shouldBe Difficulty.HARD
      }
    }
  }
})
