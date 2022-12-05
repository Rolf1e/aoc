package aoc2022

import aoc2022.DayFive.Drawing
import aoccore.Day

import scala.collection.mutable

object DayFive extends Day {
  type Drawing = mutable.Map[Int, mutable.Stack[Char]]

  override def number: Int = 5

  override def fileName: String = "inputs/dayfive.txt"

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val drawing = mutable.Map[Int, mutable.Stack[Char]]()
    for (line <- lines) {
      if (line.contains('[')) {
        updateDrawing(drawing, line)
      } else if (line.startsWith("move")) {
        val instruction = Instruction.from(line)
        instruction.stackOneByOne(drawing)
      }
    }

    val topRow = findTopRow(drawing)
    println(s"Top row is: ${topRow.mkString}")
  }

  private def parseMapRow(line: String): Seq[(Char, Int)] = {
    line.grouped(4)
      .zipWithIndex
      .map { case (str, i) => (str(1), i + 1) }
      .filter { case (str, _) => str != ' ' }
      .toSeq
  }

  override def partTwo(): Unit = {
    val lines = loadFromResource()
    val drawing = mutable.Map[Int, mutable.Stack[Char]]()
    for (line <- lines) {
      if (line.contains('[')) {
        updateDrawing(drawing, line)
      } else if (line.startsWith("move")) {
        val instruction = Instruction.from(line)
        instruction.stackByRow(drawing)
      }
    }

    val topRow = findTopRow(drawing)
    println(s"Top row is: ${topRow.mkString}")
  }

  private def findTopRow(drawing: Drawing): Seq[Char] = {
    drawing.view
      .map { case (_, column) => column.pop() }
      .toSeq
  }

  private def updateDrawing(drawing: Drawing, line: String): Unit = {
    for ((letter, index) <- parseMapRow(line)) {
      drawing.updateWith(index) {
        case Some(value) => Some(value :+ letter)
        case None => Some(mutable.Stack(letter))
      }
    }
  }
}

case class Instruction(from: Int, to: Int, repeat: Int) {
  def stackOneByOne(drawing: Drawing): Unit = {
    for (_ <- 0 until repeat) {
      (drawing.get(from), drawing.get(to)) match {
        case (Some(from), Some(to)) => to.push(from.pop())
        case (Some(from), None) => drawing.put(to, mutable.Stack(from.pop()))
      }
    }
  }

  def stackByRow(drawing: Drawing): Unit = {
    val toBeMoved = mutable.Stack[Char]()
    for (_ <- 0 until repeat) {
      drawing.get(from) match {
        case Some(value) => toBeMoved.push(value.pop())
      }
    }
    drawing.get(to) match {
      case Some(value) => value.pushAll(toBeMoved)
      case None => drawing.put(to, toBeMoved)
    }
  }
}

object Instruction {
  def from(s: String): Instruction = s match {
    case s"move $repeat from $from to $to" => Instruction(from.toInt, to.toInt, repeat.toInt)
  }
}
