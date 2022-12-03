package aoc2015

import aoc2015.Matrix.{off, on}
import aoccore.Day

import scala.collection.mutable

object DaySix extends Day {

  override def number: Int = 6

  override def fileName: String = "inputs/daysix.txt"

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val matrix = Matrix()
    for (line <- lines) yield {
      val action = Action.from(line)
      matrix.updateStateArea(action)
    }
    println(s"You have ${matrix.computeLightsOn} lit lights.")
  }

  override def partTwo(): Unit = {
    val lines = loadFromResource()
    val matrix = Matrix()
    for (line <- lines) yield {
      val action = Action.from(line)
      matrix.updateBrightnessArea(action)
    }
    println(s"You have total brightness of ${matrix.computeTotalBrightness}")
  }

}

class Matrix private(grid: mutable.Seq[mutable.Seq[Int]]) {

  def updateStateArea(action: Action): Unit = {
    for {
      x <- action.low.x to action.high.x
      y <- action.low.y to action.high.y
    } {
      grid(x)(y) = action.lightAction match {
        case TurnOn => on
        case TurnOff => off
        case Toggle => if (grid(x)(y) == on) off else on
      }
    }
  }

  def computeLightsOn: Int = grid.map(_.count(_ == on)).sum

  def computeTotalBrightness: Int = grid.map(_.sum).sum

  def updateBrightnessArea(action: Action): Unit = {
    for {
      x <- action.low.x to action.high.x
      y <- action.low.y to action.high.y
    } {
      val incr = action.lightAction match {
        case TurnOn => 1
        case TurnOff => if (grid(x)(y) == off) off else -1
        case Toggle => 2
      }
      grid(x)(y) += incr
    }
  }

}

object Matrix {
  val off = 0
  val on = 1

  def apply(): Matrix = {
    new Matrix(mutable.Seq.fill(1000)(mutable.Seq.fill(1000)(off)))
  }

}


case class Action(low: Coordinate, high: Coordinate, lightAction: LightAction)

case class Coordinate(x: Int, y: Int)

object Action {

  def from(s: String): Action = {
    s match {
      case s"turn on $low through $high" => Action(intFromString(low), intFromString(high), TurnOn)
      case s"turn off $low through $high" => Action(intFromString(low), intFromString(high), TurnOff)
      case s"toggle $low through $high" => Action(intFromString(low), intFromString(high), Toggle)
      case _ => throw new IllegalArgumentException(s)
    }
  }

  private def intFromString(s: String): Coordinate = s match {
    case s"$x,$y" => Coordinate(x.toInt, y.toInt)
    case _ => throw new IllegalArgumentException(s)
  }
}

sealed trait LightAction

object Toggle extends LightAction

object TurnOn extends LightAction

object TurnOff extends LightAction
