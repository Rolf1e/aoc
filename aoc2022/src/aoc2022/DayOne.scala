package aoc2022

object DayOne extends Day {

  override def fileName = "inputs/dayone.txt"

  def partOne(): Unit = {
    val lines = loadFromResource()
    var max = 0
    var currentSum = 0
    for (line <- lines :+ "") {
      if (line == "") {
        max = Math.max(currentSum, max)
        currentSum = 0
      } else {
        currentSum += line.toInt
      }
    }

    println(s"max: $max")
  }

  def partTwo(): Unit = {
    val lines = loadFromResource()
    var sums = Seq[Int]()
    var currentSum = 0
    for (line <- lines :+ "") {
      if (line == "") {
        sums = sums :+ currentSum
        currentSum = 0
      } else {
        currentSum += line.toInt
      }
    }


    val top3 = sums.sorted.reverse.take(3)
    println(s"top3: ${top3}")
    println(s"sum top3: ${top3.sum}")
  }

}
