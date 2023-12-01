import aoccore.Day

object DayOne extends Day {
  override def number: Int = 1

  override def fileName: String = "inputs/dayone.txt"

  override def partOne(): Unit = {
    val lines = loadFromResource()
    var sum = 0
    for (line <- lines) {
      val n = findFirstAndLastDigit(line)
      sum += n.toInt
    }
    println(sum)
  }

  def findFirstAndLastDigit(line: String): String = {
    var endFirst, endLast = false
    var i = 0
    var first, last = ""
    while (!endFirst || !endLast) {
      val left = line(i)
      if (!endFirst && left.isDigit) {
        first = left.toString
        endFirst = true
      }

      val right = line(line.length - 1 - i)
      if (!endLast && right.isDigit) {
        last = right.toString
        endLast = true
      }

      i += 1
    }

    first + last
  }

  val words = Array("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

  def findFirstAndLastDigitAlsoWithWord(line: String): String = {
    var endFirst, endLast = false
    var i = 0
    var first, last = ""
    while (!endFirst || !endLast) {
      val left = line(i)
      if (!endFirst && left.isDigit) {
        first = left.toString
        endFirst = true
      } else if (!endFirst) {
        for ((w, j) <- words.zipWithIndex) {
          if (left != w.head) {
            /* continue */
          }
          else {
            val possible = line.slice(i, i + w.length)
            if (possible == w) {
              first = (j + 1).toString
              endFirst = true
            }
          }
        }

      }

      val rightIdx = line.length - 1 - i
      val right = line(rightIdx)
      if (!endLast && right.isDigit) {
        last = right.toString
        endLast = true
      } else if (!endLast) {
        for ((w, j) <- words.zipWithIndex) {
          if (right != w.head) {
            /* continue */
          }
          else {
            val possible = line.slice(rightIdx, rightIdx + w.length)
            if (possible == w) {
              last = (j + 1).toString
              endLast = true
            }
          }
        }

      }

      i += 1
    }

    first + last
  }

  override def partTwo(): Unit = {

    val lines = loadFromResource()
    var sum = 0
    for (line <- lines) {
      val n = findFirstAndLastDigitAlsoWithWord(line)
      sum += n.toInt

    }
    println(sum)
  }
}
