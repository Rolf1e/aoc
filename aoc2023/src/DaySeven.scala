import aoccore.Day

import scala.collection.mutable
import scala.util.Try

object DaySeven extends Day {
  override def number: Int = 7

  def main(args: Array[String]): Unit = {
    DaySeven.partTwo()
  }

  override def fileName: String = "inputs/dayseven.txt"

  override def partOne(): Unit = {
    val hands = loadFromResource()
      .map(l => parse(l, "B"))
      .sortBy { case (hand, _) => hand.score }

    val sum = hands.zipWithIndex
      .map { case ((_, bid), i) => bid * (i + 1) }
      .sum

    println(sum)
  }

  type Bid = Int
  type Hand = String

  implicit class RichHand(hand: Hand) {
    def findRank: Score = {
      val counts = hand.groupBy(identity)
        .view
        .mapValues(_.length)
        .values
        .toSeq
        .sorted
        .reverse

      (counts.headOption.getOrElse(0), Try(counts(1)).getOrElse(0)) match {
        case (5, _) => FiveOfAKind
        case (4, _) => FourOfAKind
        case (3, 2) => FullHouse
        case (3, _) => ThreeOfAKind
        case (2, 2) => TwoPair
        case (2, _) => OnePair
        case _ => HighCard
      }

    }

    def score: Int = findRank.rank + Integer.parseInt(hand, 16)

    def findRankWithJoker: Score = {
      val counts = hand.groupBy(identity)
        .view
        .mapValues(_.length)
        .to(mutable.Map)

      val jokers: Int = counts.remove('1').getOrElse(0)

      val c = counts.view
        .values
        .toSeq
        .sorted
        .reverse

      (c.headOption.getOrElse(0) + jokers, Try(c(1)).getOrElse(0)) match {
        case (5, _) => FiveOfAKind
        case (4, _) => FourOfAKind
        case (3, 2) => FullHouse
        case (3, _) => ThreeOfAKind
        case (2, 2) => TwoPair
        case (2, _) => OnePair
        case _ => HighCard
      }

    }

    def scoreWithJoker: Int = findRankWithJoker.rank + Integer.parseInt(hand, 16)

  }

  abstract class Score(r: Int) {
    def rank: Int = r
  }

  object FiveOfAKind extends Score(7_000_000)

  object FourOfAKind extends Score(6_000_000)

  object FullHouse extends Score(5_000_000)

  object ThreeOfAKind extends Score(4_000_000)

  object TwoPair extends Score(3_000_000)

  object OnePair extends Score(2_000_000)

  object HighCard extends Score(1_000_000)

  def parse(line: String, joker: String): (Hand, Bid) = {
    line match {
      case s"$hand $bid" => (toHexa(hand, joker), bid.toInt)
    }
  }


  def toHexa(line: String, joker: String): Hand = line
    .replace("A", "E")
    .replace("K", "D")
    .replace("Q", "C")
    .replace("J", joker)
    .replace("T", "A")

  override def partTwo(): Unit = {
    val hands = loadFromResource()
      .map(l => parse(l, "1"))
      .sortBy { case (hand, _) => hand.scoreWithJoker }

    val sum = hands.zipWithIndex
      .map { case ((_, bid), i) => bid * (i + 1) }
      .sum

    println(sum)
  }
}
