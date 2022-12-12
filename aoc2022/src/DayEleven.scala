import aoccore.Day

import scala.collection.immutable.TreeMap
import scala.collection.{View, mutable}

object DayEleven extends Day {
  type MonkeyId = Int
  type WorryLevel = BigInt
  type Operation = WorryLevel => WorryLevel
  type ThrowTo = WorryLevel => MonkeyId

  override def number: Int = 11

  override def fileName: String = "inputs/dayeleven.txt"

  override def partOne(): Unit = {
    val monkeys = loadFromResource()
      .view
      .grouped(7)
      .map(Monkey(_))
      .to(TreeMap)

    for {
      _ <- 1 to 20
      monkey <- monkeys.values
      move <- monkey.inspects((worry: WorryLevel) => worry / 3)
    } {
      executeMove(monkeys, move)
    }

    val (first, second) = business(monkeys)
    println(s"Business is ${first.business * second.business}")
  }

  private def business(monkeys: Map[Int, Monkey]): (Monkey, Monkey) = {
    val Seq(first, second) = monkeys
      .values
      .toSeq
      .sortBy(_.business)
      .reverse
      .take(2)
    (first, second)
  }

  private def executeMove(monkeys: Map[Int, Monkey], move: Move): Unit = {
    monkeys(move.from).clearItems()
    monkeys(move.to) add move.worry
  }

  override def partTwo(): Unit = {
    val monkeys = loadFromResource()
      .view
      .grouped(7)
      .map(Monkey(_))
      .to(TreeMap)

    val productModulos = monkeys.values.map(_.divisibleBy).product
    for {
      _ <- 1 to 10000
      monkey <- monkeys.values
      move <- monkey.inspects((worry: WorryLevel) => worry % productModulos)
    } {
      executeMove(monkeys, move)
    }

    val (first, second) = business(monkeys)
    println(s"Business is ${first.business * second.business}")
    println(monkeys.mkString("\n"))
  }

  case class Move(from: MonkeyId, to: MonkeyId, worry: WorryLevel)

  case class Monkey private(
                             id: Int,
                             items: mutable.Buffer[WorryLevel],
                             operation: Operation,
                             nextMonkeyId: ThrowTo,
                             divisibleBy: Int,
                             var inspectsItems: BigInt = 0
                           ) {

    override def toString: String = s"business $business items ${items.mkString("[", ",", "]")}"

    def business: BigInt = inspectsItems

    def clearItems(): Unit = items.clear()

    def inspects(manageWorry: Operation): Seq[Move] = {
      for {
        worry <- items.toSeq
      } yield {
        inspectsItems += 1
        val newWorry = manageWorry(operation(worry))
        Move(from = id, to = nextMonkeyId(newWorry), newWorry)
      }
    }

    def add(toAdd: WorryLevel): Unit = {
      items append toAdd
    }

  }

  object Monkey {
    def apply(instructions: View[String]): (Int, Monkey) = {
      var id = -1
      var items = mutable.Stack.empty[WorryLevel]
      var operation: Operation = (i: WorryLevel) => i
      var divisibleBy = -1
      var success = -1
      var failure = -1
      for (instruction <- instructions) {
        instruction match {
          case s"Monkey $rawId:" => id = rawId.toInt
          case s"  Starting items: $rawItems" => items = rawItems.split(", ").map(BigInt(_)).to(mutable.Stack)
          case s"  Operation: new = old * old" => operation = (old: WorryLevel) => old * old
          case s"  Operation: new = old * $factor" => operation = (old: WorryLevel) => old * factor.toLong
          case s"  Operation: new = old + $addition" => operation = (old: WorryLevel) => old + addition.toLong
          case s"  Test: divisible by $rawDivisibleBy" => divisibleBy = rawDivisibleBy.toInt
          case s"    If true: throw to monkey $rawSuccess" => success = rawSuccess.toInt
          case s"    If false: throw to monkey $rawFailure" => failure = rawFailure.toInt
          case "" => {}
        }
      }
      (id, Monkey(id, items, operation, (worry: WorryLevel) => if (worry % divisibleBy == 0) success else failure, divisibleBy))
    }
  }
}
