package aoc2022

import aoccore.Day

object DayTwo extends Day {

  override def number: Int = 2

  override def fileName: String = "inputs/daytwo.txt"

  override def partOne(): Unit = {

    /**
     * Winner is returned
     */
    def battle(me: Shape, opponent: Shape): Int = {
      (me, opponent) match {
        case (Rock(), Paper()) => Rock().factor + Loss.point
        case (Rock(), Rock()) => Rock().factor + Draw.point
        case (Rock(), Scissors()) => Rock().factor + Win.point

        case (Paper(), Scissors()) => Paper().factor + Loss.point
        case (Paper(), Paper()) => Paper().factor + Draw.point
        case (Paper(), Rock()) => Paper().factor + Win.point

        case (Scissors(), Rock()) => Scissors().factor + Loss.point
        case (Scissors(), Scissors()) => Scissors().factor + Draw.point
        case (Scissors(), Paper()) => Scissors().factor + Win.point
      }
    }

    val lines = loadFromResource()
    val score = for (line <- lines) yield {
      line match {
        case s"$opponent $me" => {
          val shapeOpponent = Shape.from(opponent(0))
          val shapeMe = Shape.from(me(0))
          battle(shapeMe, shapeOpponent)
        }
      }
    }

    println(s"You score is ${score.sum}.")
  }

  override def partTwo(): Unit = {

    def battle(target: Outcome, opponent: Shape): Int = {
      (target, opponent) match {
        case (Loss, Paper()) => Rock().factor + Loss.point
        case (Draw, Rock()) => Rock().factor + Draw.point
        case (Win, Scissors()) => Rock().factor + Win.point

        case (Loss, Scissors()) => Paper().factor + Loss.point
        case (Draw, Paper()) => Paper().factor + Draw.point
        case (Win, Rock()) => Paper().factor + Win.point

        case (Loss, Rock()) => Scissors().factor + Loss.point
        case (Draw, Scissors()) => Scissors().factor + Draw.point
        case (Win, Paper()) => Scissors().factor + Win.point
      }
    }

    val lines = loadFromResource()
    val score = for (line <- lines) yield {
      line match {
        case s"$opponent $outcome" => {
          val shapeOpponent = Shape.from(opponent(0))
          val outCome = Outcome.from(outcome(0))
          battle(outCome, shapeOpponent)
        }
      }
    }

    println(s"You score is ${score.sum}.")

  }

}

object Outcome {

  def from(char: Char): Outcome = {
    char match {
      case 'X' => Loss
      case 'Y' => Draw
      case 'Z' => Win
      case _ => throw new IllegalArgumentException(char.toString)
    }
  }
}

sealed trait Outcome {
  def point: Int
}

object Draw extends Outcome {
  override def point: Int = 3
}

object Loss extends Outcome {
  override def point: Int = 0
}

object Win extends Outcome {
  override def point: Int = 6
}

sealed trait Shape {
  def factor: Int
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
  override def factor: Int = 1
}

case class Paper() extends Shape {
  override def factor: Int = 2
}

case class Scissors() extends Shape {
  override def factor: Int = 3
}
