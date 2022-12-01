package aoc2022

object Main extends App {
  val days = Seq[Day](DayOne)
  for (day <- days) {
    day.partOne()
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
