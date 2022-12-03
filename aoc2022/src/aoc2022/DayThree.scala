package aoc2022

import aoccore.Day

object DayThree extends Day {

  override def number: Int = 3

  override def fileName: String = "inputs/daythree.txt"

  private val alphabet = ('a' to 'z') concat ('A' to 'Z')

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val priorities = for (line <- lines) yield {
      val (firstHalf, secondHalf) = line splitAt (line.length / 2)
      val common = firstHalf.find(c => secondHalf.contains(c))
      common match {
        case Some(value) => findPriority(value)
        case None => 0
      }
    }
    println(s"You have a sum of total priorities ${priorities.sum}")
  }

  private def findPriority(c: Char): Int = {
    alphabet.indexOf(c) + 1
  }

  override def partTwo(): Unit = {
    val lines = loadFromResource()
    val priorities = for (line <- lines.grouped(3)) yield {
      val common = line.head.find(c => line(1).contains(c) && line(2).contains(c))
      common match {
        case Some(value) => findPriority(value)
        case None => 0
      }
    }

    println(s"You have a sum of total priorities ${priorities.sum}")
  }
}
