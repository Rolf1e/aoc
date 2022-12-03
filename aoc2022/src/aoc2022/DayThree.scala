package aoc2022

import aoccore.Day

object DayThree extends Day {

  override def fileName: String = "inputs/daythree.txt"

  private val alphabet = ('a' to 'z') concat ('A' to 'Z')

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val priorities = for (line <- lines) yield {
      val (firstHalf, secondHalf) = line splitAt (line.length / 2)
      val common = firstHalf intersect secondHalf
      findPriority(common)
    }
    println(s"You have a sum of total priorities ${priorities.sum}")
  }

  private def findPriority(s: String): Int = {
    alphabet.indexOf(s(0)) + 1
  }

  override def partTwo(): Unit = ???
}
