import aoccore.Day

object DayFour extends Day {
  override def number: Int = 4

  override def fileName: String = "inputs/dayfour.txt"

  def main(args: Array[String]): Unit = {
    DayFour.partOne()
    DayFour.partTwo()
  }

  override def partOne(): Unit = {
    val sum = parseCards
      .map(_.worth)
      .sum
    println(sum)
  }

  def parseCards: Seq[Card] = loadFromResource()
    .map {
      case s"Card $_: $draws | $winners" => Card(
        draws.split(" ").filter(_.nonEmpty).map(_.toInt),
        winners.split(" ").filter(_.nonEmpty).map(_.toInt),
      )
    }

  case class Card(draws: Seq[Int], winners: Seq[Int]) {
    def worth: Int = (draws intersect winners)
      .foldLeft(0)((a, _) => if (a == 0) 1 else a * 2)
  }

  override def partTwo(): Unit = {
    val cards = parseCards
      .map(card => (card.draws intersect card.winners).size)

    val cache = Array.fill(cards.size)(1)

    cards.zipWithIndex
      .foreach {
        case (cnt, idx) =>
          if (cnt > 0) (1 to cnt)
            .foreach(i => cache(i + idx) += 1 * cache(idx))
      }

    println(cache.sum)
  }
}
