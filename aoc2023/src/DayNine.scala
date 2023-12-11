import aoccore.Day

object DayNine extends Day {
  override def number: Int = 9

  override def fileName: String = "inputs/ex_daynine.txt"

  type History = Seq[Int]


  def main(args: Array[String]): Unit = {
    DayNine.partOne()
    DayNine.partTwo()
  }

  def parse: Seq[History] = loadFromResource()
    .map(line => line.split(" ").map(_.toInt))

  override def partOne(): Unit = {
    val histories = parse
    val sum = histories
      .map(h => predict(h)._2)
      .sum
    println(sum)
  }


  def predict(history: History): (Int, Int) = {
    def inner(history: History): (Int, Int) = {
      val step = history
        .sliding(2)
        .map { pair => pair(1) - pair(0) }
        .toSeq

      if (step.forall(_ == 0)) {
        (0, 0)
      } else {
        val (left, right) = inner(step)
        (step.head - left, step.last + right)
      }
    }

    val (left, right) = inner(history)
    (history.head - left, history.last + right)
  }

  override def partTwo(): Unit = {
    val histories = parse
    val sum = histories
      .map(h => predict(h)._1)
      .sum
    println(sum)

  }
}
