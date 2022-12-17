import aoccore.Day

object DayThirteen extends Day {
  override def number: Int = 13

  override def fileName: String = "inputs/daythirteen.txt"

  override def partOne(): Unit = {
    val orderedPairs = loadFromResource()
      .filter(_.nonEmpty)
      .grouped(2)
      .zipWithIndex
      .map { case (Seq(left, right), i) =>
        val isOrdered = ordered(Pair.from(left).get, Pair.from(right).get).get
        //        println(s"$left, $right => $isOrdered")
        (isOrdered, i + 1)
      }
      .filter(_._1)
      .map(_._2)
      .sum

    println(s"Sum of ordered pairs \n$orderedPairs")
  }

  override def partTwo(): Unit = {
    val packet1 = "[[2]]"
    val packet2 = "[[6]]"
    val orderedPackets = (loadFromResource() ++ Seq(packet1, packet2))
      .filter(_.nonEmpty)
      .map(Pair.from(_).get)
      .sortWith((a, b) => ordered(a, b).get)

    val decoderKey = (orderedPackets.indexOf(Pair.from(packet1).get) + 1) * (orderedPackets.indexOf(Pair.from(packet2).get) + 1)
    println(s"Decoder key: $decoderKey")
  }

  private def ordered(left: Pair, right: Pair): Option[Boolean] = {
    (left, right) match {
      case (Value(a), Value(b)) => if (a == b) None else Some(a < b)
      case (PairList(hL :: tailL), PairList(hR :: tailR)) => ordered(hL, hR) orElse ordered(PairList(tailL), PairList(tailR))
      case (PairList(_ :: _), PairList(Nil)) => Some(false)
      case (PairList(Nil), PairList(_ :: _)) => Some(true)
      case (PairList(Nil), PairList(Nil)) => None
      case (Value(a), PairList(elements)) => ordered(PairList(List(Value(a))), PairList(elements))
      case (PairList(elements), Value(a)) => ordered(PairList(elements), PairList(List(Value(a))))
    }
  }

  sealed trait Pair

  case class PairList(values: List[Pair]) extends Pair

  case class Value(value: Int) extends Pair

  object Pair {

    def from(s: String): Option[Pair] = {
      if (s.contains('[')) {
        val subList = s.substring(1, s.lastIndexOf(']'))
        Some(PairList(findList(subList).flatMap(from)))
      } else if (s.nonEmpty) {
        Some(Value(s.toInt))
      } else {
        None
      }
    }

    private def findList(s: String): List[String] = {
      val (_, commasIndexes) = s.zipWithIndex
        .foldLeft(0 -> Seq.empty[Int]) { case ((level, freeCommasIndex), (c, idx)) =>
          (level, c) match {
            case (0, ',') => 0 -> (freeCommasIndex :+ idx)
            case (_, ']') => (level - 1) -> freeCommasIndex
            case (_, '[') => (level + 1) -> freeCommasIndex
            case _ => level -> freeCommasIndex
          }
        }
      val ss = s.toCharArray
      commasIndexes.foreach(idx => ss(idx) = ';')
      ss.mkString.split(";").toList
    }
  }


}
