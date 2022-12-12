import aoccore.Day

import scala.collection.{View, mutable}

object DayEleven extends Day {
  type MonkeyId = Int
  type WorryLevel = Long
  type BusinessTime = Long
  type Operation = WorryLevel => WorryLevel
  type ThrowTo = WorryLevel => MonkeyId

  override def number: Int = 11

  override def fileName: String = "inputs/dayeleven.txt"

  override def partOne(): Unit = {
    val monkeys = loadFromResource()
      .view
      .grouped(7)
      .map(Monkey(_))
      .toSeq

    for {
      _ <- 1 to 20
      monkey <- monkeys
      move <- monkey.inspects((worry: WorryLevel) => worry / 3)
    } {
      executeMove(monkeys, move)
    }

    val (first, second) = business(monkeys)
    println(s"Business is ${first.business * second.business}")
  }

  private def business(monkeys: Seq[Monkey]): (Monkey, Monkey) = {
    val Seq(first, second) = monkeys
      .sortBy(_.business)
      .reverse
      .take(2)
    (first, second)
  }

  private def executeMove(monkeys: Seq[Monkey], move: Move): Unit = {
    monkeys(move.from).clearItems()
    monkeys(move.to) add move.worry
  }

  override def partTwo(): Unit = {
    val monkeys = loadFromResource()
      .view
      .grouped(7)
      .map(Monkey(_))
      .toSeq

    val productModulos = monkeys.map(_.divisibleBy).product
    for {
      _ <- 1 to 10000
      monkey <- monkeys
      move <- monkey.inspects((worry: WorryLevel) => worry % productModulos)
    } {
      executeMove(monkeys, move)
    }

    val (first, second) = business(monkeys)
    println(s"Business is ${first.business * second.business}")
  }

  case class Move(from: MonkeyId, to: MonkeyId, worry: WorryLevel)

  case class Monkey private(
                             id: Int,
                             items: mutable.Buffer[WorryLevel],
                             operation: Operation,
                             nextMonkeyId: ThrowTo,
                             divisibleBy: Int,
                             var inspectsItems: BusinessTime = 0
                           ) {

    override def toString: String = s"business $business items ${items.mkString("[", ",", "]")}"

    def business: BusinessTime = inspectsItems

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
    def apply(instructions: View[String]): Monkey = {
      var id = -1
      var items = mutable.Stack.empty[WorryLevel]
      var operation: Operation = (i: WorryLevel) => i
      var divisibleBy = -1
      var success = -1
      var failure = -1
      for (instruction <- instructions) {
        instruction match {
          case s"Monkey $rawId:" => id = rawId.toInt
          case s"  Starting items: $rawItems" => items = rawItems.split(", ").map(_.toLong).to(mutable.Stack)
          case s"  Operation: new = old * old" => operation = (old: WorryLevel) => old * old
          case s"  Operation: new = old * $factor" => operation = (old: WorryLevel) => old * factor.toLong
          case s"  Operation: new = old + $addition" => operation = (old: WorryLevel) => old + addition.toLong
          case s"  Test: divisible by $rawDivisibleBy" => divisibleBy = rawDivisibleBy.toInt
          case s"    If true: throw to monkey $rawSuccess" => success = rawSuccess.toInt
          case s"    If false: throw to monkey $rawFailure" => failure = rawFailure.toInt
          case "" => {}
        }
      }
      Monkey(id, items, operation, (worry: WorryLevel) => if (worry % divisibleBy == 0) success else failure, divisibleBy)
    }
  }
}
