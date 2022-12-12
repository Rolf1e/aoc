import aoccore.Day

object DayFour extends Day {
  override def number: Int = 4

  override def fileName: String = "inputs/dayfour.txt"

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val count = lines.count(line => {
      val assignments = Assignments.from(line)
      assignments.left.fullyContains(assignments.right)
    })
    println(s"$count assignments overlap")
  }

  override def partTwo(): Unit = {
    val lines = loadFromResource()
    val count = lines.count(line => {
      val assignments = Assignments.from(line)
      assignments.left.overlapAll(assignments.right)
    })
    println(s"$count assignments overlap at all")
  }

  case class Assignments(left: Range, right: Range)

  case class Range(low: Int, high: Int) {
    def fullyContains(r: Range): Boolean = {
      (low <= r.low && high >= r.high) || (r.low <= low && r.high >= high)
    }

    def overlapAll(r: Range): Boolean = {
      high >= r.low && r.high >= low // check is they are disjoint
    }
  }

  object Assignments {
    def from(s: String): Assignments = {
      s match {
        case s"$lowL-$highL,$lowR-$highR" => Assignments(Range(lowL.toInt, highL.toInt), Range(lowR.toInt, highR.toInt))
      }
    }
  }

}

