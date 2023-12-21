package aoccore

import scala.language.implicitConversions

trait Day {
  def number: Int

  def fileName: String

  def partOne(): Unit

  def partTwo(): Unit

  final def loadFromResource(): Seq[String] = {
    val source = io.Source.fromResource(fileName)
    try {
      source.getLines().toSeq
    } finally source.close()
  }

}

object Day {

  implicit class RichInput(s: String) {
    def toInts: Seq[Int] = toInts()

    def toInts(sep: String = " "): Seq[Int] = s.split(sep)
      .filter(_.nonEmpty)
      .map(_.toInt)
  }

  case class Coord(x: Int, y: Int)

  object Coord {
    implicit def fromTuple(t: (Int, Int)): Coord = Coord(t._1, t._2)

    def manhattanDistance(a: Coord, b: Coord): Int = (a.x - b.x).abs + (a.y - b.y).abs
  }

  def run(days: Seq[Day]): Unit = {
    for (day <- days) {
      println(s"=== Day: ${day.number} === ")
      println("=> Part 1")
      day.partOne()
      println("=> Part 2")
      day.partTwo()
    }
  }

}
