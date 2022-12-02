package aoc2022

object Main extends App {
  private val days = Seq[Day](DayOne, DayTwo)
  for ((day, i) <- days.zipWithIndex) {
    println(s"=== Day: ${i + 1} === ")
    println("=> Part 1")
    day.partOne()
    println("=> Part 2")
    day.partTwo()
  }
}

trait Day {

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
