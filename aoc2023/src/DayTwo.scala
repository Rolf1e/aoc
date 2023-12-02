import aoccore.Day

object DayTwo extends Day {
  override def number: Int = 2

  override def fileName: String = "inputs/daytwo.txt"

  case class Color(r: Int, g: Int, b: Int) {
    def product: Int = r * g * b
  }

  def parseGame(line: String): (Int, Seq[Seq[Color]]) = {
    line match {
      case s"Game $id:$rest" => {
        val sets = rest.split(";")
          .map(set => {
            set.split(",")
              .map {
                case s" $n green" => Color(0, n.toInt, 0)
                case s" $n red" => Color(n.toInt, 0, 0)
                case s" $n blue" => Color(0, 0, n.toInt)
              }.toSeq
          })

        (id.toInt, sets.toSeq)
      }
    }
  }


  override def partOne(): Unit = {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14
    val lines = loadFromResource()

    val sum = lines
      .map(line => parseGame(line))
      .map { case (id, colors) => (id, colors.flatten) }
      .filter { case (_, colors) => colors.forall(c => c.r <= maxRed && c.g <= maxGreen && c.b <= maxBlue) }
      .map(_._1)
      .sum

    println(sum)

  }

  def maxColor(colors: Seq[Color]): Color = {
    var r, g, b = 0
    for (color <- colors) {
      r = r.max(color.r)
      b = b.max(color.b)
      g = g.max(color.g)
    }
    Color(r, g, b)
  }

  override def partTwo(): Unit = {
    val lines = loadFromResource()

    val sum = lines
      .map(line => parseGame(line))
      .map { case (_, colors) => maxColor(colors.flatten).product }
      .sum

    println(sum)
  }

}
