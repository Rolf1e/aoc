package aoc2022

import aoccore.Day

import scala.collection.mutable

object DayFive extends Day {
  type Drawing = mutable.Map[Int, mutable.Stack[Char]]
  ()

  override def number: Int = 5

  override def fileName: String = "inputs/dayfive.txt"

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val (drawing, instructions) = parseInput(lines)
    for {
      instruction <- instructions
      _ <- 0 until instruction.repeat
    } {
      (drawing.get(instruction.from), drawing.get(instruction.to)) match {
        case (Some(from), Some(to)) => to.push(from.pop())
        case (Some(from), None) => drawing.put(instruction.to, mutable.Stack(from.pop()))
      }
    }

    val topRow = findTopRow(drawing)
    println(s"Top row is: ${topRow.mkString}")
  }

  private def parseInput(input: Seq[String]): (Drawing, Seq[Instruction]) = {
    var instructions = mutable.Seq[Instruction]()
    var processInstructions = false

    val drawing = mutable.Map[Int, mutable.Stack[Char]]()
    for (line <- input) {
      if (processInstructions) {
        instructions = instructions :+ Instruction.from(line)
      } else if (line.contains('[')) {
        for {(letter, index) <- parseMapRow(line)} {
          drawing.updateWith(index) {
            case Some(value) => Some(value :+ letter)
            case None => Some(mutable.Stack(letter))
          }
        }
      } else if (line.isEmpty) {
        processInstructions = true
      }
    }

    (drawing, instructions.toSeq)
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
    val (drawing, instructions) = parseInput(lines)

    for (instruction <- instructions) {
      val toBeMoved = mutable.Stack[Char]()

      for (_ <- 0 until instruction.repeat) {
        drawing.get(instruction.from) match {
          case Some(value) => toBeMoved.push(value.pop())
        }
      }

      drawing.get(instruction.to) match {
        case Some(value) => value.pushAll(toBeMoved)
        case None => drawing.put(instruction.to, toBeMoved)
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
}

case class Instruction(from: Int, to: Int, repeat: Int)

object Instruction {
  def from(s: String): Instruction = s match {
    case s"move $repeat from $from to $to" => Instruction(from.toInt, to.toInt, repeat.toInt)
  }
}
