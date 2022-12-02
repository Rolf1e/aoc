package aoccore

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

object Day {

  def run(days: Seq[Day]): Unit = {
    for ((day, i) <- days.zipWithIndex) {
      println(s"=== Day: ${i + 1} === ")
      println("=> Part 1")
      day.partOne()
      println("=> Part 2")
      day.partTwo()
    }
  }

}