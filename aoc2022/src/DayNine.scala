import aoccore.Day

import scala.collection.mutable

object DayNine extends Day {
  case class Coo(x: Int, y: Int)

  override def number: Int = 9

  override def fileName: String = "inputs/ex2_daynine.txt"

  override def partOne(): Unit = {
    var head: Coo = Coo(0, 0)
    var tail: Coo = Coo(0, 0)
    val tailVisited = mutable.Set[Coo](tail)

    for (line <- loadFromResource()) {
      val (direction, repeat) = instruction(line)
      for (_ <- 0 until repeat) {
        head = moveHead(direction, head)
        tail = tailCatchUp(head, tail)
        tailVisited.add(tail)
      }
    }
    println(s"Tail visited ${tailVisited.size}")
  }

  private def moveHead(direction: Char, head: Coo): Coo = {
    direction match {
      case 'R' => Coo(head.x + 1, head.y)
      case 'L' => Coo(head.x - 1, head.y)
      case 'U' => Coo(head.x, head.y + 1)
      case 'D' => Coo(head.x, head.y - 1)
    }
  }

  private def sign(a: Int) = if (a == 0) 0 else if (a > 0) 1 else -1

  private def tailCatchUp(head: Coo, tail: Coo): Coo = {
    val max = math.max((tail.x - head.x).abs, (tail.y - head.y).abs)
    if (max <= 1) {
      tail
    } else {
      Coo(tail.x + sign(head.x - tail.x), tail.y + sign(head.y - tail.y))
    }
  }

  private def instruction(line: String): (Char, Int) = line match {
    case s"$d $i" => (d(0), i.toInt)
  }

  override def partTwo(): Unit = {
    val rope = Array.fill(10)(Coo(0, 0))
    val tailVisited = mutable.Set[Coo](rope.last)

//    def display(): Unit = {
//      for {
//        i <- 0 until 16
//        j <- 0 until 12
//      } {
//
//      }
//    }

    for (line <- loadFromResource()) {
      val (direction, repeat) = instruction(line)
      for (_ <- 0 until repeat) {
        rope(0) = moveHead(direction, rope(0))
        for (i <- 1 to 9) {
          rope(i) = tailCatchUp(rope(i), rope(i - 1))
        }
        tailVisited.add(rope.last)
      }
    }
    println(s"Tail visited ${tailVisited.size}")
  }

}
