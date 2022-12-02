package aoc2022

object DayTwo extends Day {
  override def fileName: String = "inputs/daytwo.txt"

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val score = for (line <- lines) yield {
      line match {
        case s"$opponent $me" => {
          val shapeOpponent = Shape.from(opponent(0))
          val shapeMe = Shape.from(me(0))
          shapeMe.battle(shapeOpponent)
        }
      }
    }

    println(s"You score is ${score.sum}. Details($score)")

  }

  override def partTwo(): Unit = ???
}

sealed trait Shape {
  def symbol(me: Boolean): Char

  def factor: Int

  /**
   * Winner is returned
   */
  def battle(opponent: Shape): Int = {
    (this, opponent) match {
      case (Rock(), Paper()) => Rock().factor + 0
      case (Rock(), Rock()) => Rock().factor + 3
      case (Rock(), Scissors()) => Rock().factor + 6

      case (Paper(), Scissors()) => Paper().factor + 0
      case (Paper(), Paper()) => Paper().factor + 3
      case (Paper(), Rock()) => Paper().factor + 6

      case (Scissors(), Rock()) => Scissors().factor + 0
      case (Scissors(), Scissors()) => Scissors().factor + 3
      case (Scissors(), Paper()) => Scissors().factor + 6
    }
  }
}

object Shape {
  def from(char: Char): Shape = {
    char match {
      case 'X' | 'A' => Rock()
      case 'Y' | 'B' => Paper()
      case 'Z' | 'C' => Scissors()
      case _ => throw new IllegalArgumentException(char.toString)
    }
  }
}

case class Rock() extends Shape {
  override def symbol(me: Boolean): Char = if (me) 'X' else 'A'

  override def factor: Int = 1
}

case class Paper() extends Shape {
  override def symbol(me: Boolean): Char = if (me) 'Y' else 'B'

  override def factor: Int = 2
}

case class Scissors() extends Shape {
  override def symbol(me: Boolean): Char = if (me) 'Z' else 'C'

  override def factor: Int = 3
}
