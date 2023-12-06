import aoccore.Day
import aoccore.Day.RichInput

object DaySix extends Day {
  override def number: Int = 6

  override def fileName: String = "inputs/daysix.txt"

  def main(args: Array[String]): Unit = {
    DaySix.partTwo()
  }

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val times = parse(lines.head)
    val distances = parse(lines(1))

    var product = 1
    for (i <- times.indices) {
      var count = 0
      val time = times(i)
      for (hold <- 1 until time) {
        val toBeat = distances(i)
        if (hold * (time - hold) > toBeat) {
          count += 1
        }
      }
      product = product * count
    }
    println(product)
  }

  def parse(line: String): Seq[Int] = {
    line match {
      case s"Time: $ts" => ts.toInts
      case s"Distance: $dist" => dist.toInts
      case _ => Seq.empty
    }
  }

  def parse2(line: String): Long = {
    line match {
      case s"Time: $ts" => ts.filter(_ != ' ').toLong
      case s"Distance: $dist" => dist.filter(_ != ' ').toLong
    }
  }

  override def partTwo(): Unit = {
    val lines = loadFromResource()
    val time = parse2(lines.head)
    val distance = parse2(lines(1))
    println(time)
    println(distance)

    var count = 0
    for (hold <- 1L until time) {
      val toBeat = distance
      if (hold * (time - hold) > toBeat) {
        count += 1
      }
    }
    println(count)
  }
}
