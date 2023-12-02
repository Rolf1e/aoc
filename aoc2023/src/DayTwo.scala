import aoccore.Day

object DayTwo extends Day {
  override def number: Int = 2

  override def fileName: String = "inputs/daytwo.txt"

  def main(args: Array[String]): Unit = {
    DayTwo.partTwo()
  }


  override def partOne(): Unit = {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14
    val lines = loadFromResource()

    val sum = lines
      .map(line => parseGame(line))
      .filter(_.isValid(maxRed, maxGreen, maxBlue))
      .map(_.id)
      .sum

    println(sum)

  }

  override def partTwo(): Unit = {
    val lines = loadFromResource()

    val sum = lines
      .view
      .map(line => parseGame(line).sets)
      .map(sets => sets.flatMap(s => s.draws).groupBy(_.color))
      .map(game => game.map { case (_, draws) => draws.map(_.n).max })
      .map(l => l.product)
      .sum

    println(sum)
  }

  def parseGame(line: String): Game = {
    line match {
      case s"Game $id:$rest" => {
        val sets = rest.split(";")
          .map(set => {
            val draws = set.split(",")
              .map {
                case s" $n green" => Draw(n.toInt, "green")
                case s" $n red" => Draw(n.toInt, "red")
                case s" $n blue" => Draw(n.toInt, "blue")
              }

            Set(draws)
          })

        Game(id.toInt, sets)
      }
    }


  }

  case class Game(id: Int, sets: Seq[Set]) {
    def isValid(red: Int, green: Int, blue: Int): Boolean =
      sets.forall(_.isValid(red, green, blue))
  }

  case class Set(draws: Seq[Draw]) {
    def isValid(red: Int, green: Int, blue: Int): Boolean =
      draws.forall(_.isValid(red, green, blue))
  }

  case class Draw(n: Int, color: String) {

    def isValid(red: Int, green: Int, blue: Int): Boolean = {
      color match {
        case "red" => n <= red
        case "blue" => n <= blue
        case "green" => n <= green
      }
    }
  }


}
