import aoccore.Day

import scala.collection.mutable

object DayTen extends Day {
  override def number: Int = 10

  override def fileName: String = "inputs/dayten.txt"

  private val indexes = Seq[Int](20, 60, 100, 140, 180, 220)

  override def partOne(): Unit = {
    val lines = loadFromResource()
    var cycle = 1
    var cpu = 1
    var signalStrength = Seq[Int]()

    def computeSignalStrength: Int = if (indexes.contains(cycle)) cpu * cycle else 0

    for (line <- lines) {
      signalStrength = signalStrength :+ computeSignalStrength
      Instruction.from(line) match {
        case Noop => // Nothing to do
        case AddX(x) => {
          cycle += 1
          signalStrength = signalStrength :+ computeSignalStrength
          cpu += x
        }
      }
      cycle += 1
    }
    println(s"signal strength ${signalStrength.sum} ${signalStrength.filter(_ != 0).mkString("[", ",", "]")}")
  }


  override def partTwo(): Unit = {
    val lines = loadFromResource()
    var cycle = 1
    var cpu = 1
    val crt = mutable.Stack[Char]()

    def charToDraw(): Char = {
      val pos = (cycle - 1) % 40
      if ((pos - cpu).abs < 2) '#' else '.'
    }

    def printDraw(): Unit = {
      for ((e, i) <- crt.zipWithIndex.reverse) {
        print(e)
        if (i != 0 && i % 40 == 0) {
          println()
        }
      }
    }

    printDraw()

    for (line <- lines) {
      crt push charToDraw()
      Instruction.from(line) match {
        case Noop => // Nothing to do
        case AddX(x) => {
          cycle += 1
          crt push charToDraw()
          cpu += x
        }
      }
      cycle += 1
    }
    printDraw()


  }


  sealed trait Instruction

  object Instruction {
    def from(line: String): Instruction = {
      line match {
        case s"addx $v" => AddX(v.toInt)
        case "noop" => Noop
      }
    }
  }

  object Noop extends Instruction

  case class AddX(x: Int) extends Instruction

}

