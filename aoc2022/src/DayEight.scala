import aoccore.Day

import scala.collection.mutable

object DayEight extends Day {

  override def number: Int = 8

  override def fileName: String = "inputs/dayeight.txt"

  private var grid = mutable.Seq[mutable.Seq[Int]]()

  override def partOne(): Unit = {
    val lines = loadFromResource()
    for (line <- lines) {
      grid = grid :+ line.toCharArray.map({
        _.toString.toInt
      })
    }

    val size = grid.size
    val visible = Array.ofDim[Boolean](size, size)

    def updateMap(i: Int, j: Int, max: Int): Int = {
      if (grid(i)(j) > max) {
        if (!visible(i)(j)) {
          visible(i)(j) = true
        }
        grid(i)(j)
      } else max
    }


    for {i <- 0 until size} {
      visible.head(i) = true
      visible(size - 1)(i) = true
      visible(i)(0) = true
      visible(i)(size - 1) = true
    }

    for (i <- 1 until size - 1) {
      var up = grid.head(i)
      var down = grid(size - 1)(i)
      var left = grid(i).head
      var right = grid(i)(size - 1)

      for (j <- 1 until size - 1) {
        up = updateMap(j, i, up)
        down = updateMap(size - 1 - j, i, down)
        left = updateMap(i, j, left);
        right = updateMap(i, size - 1 - j, right)
      }
    }

    val visibleTrees = visible
      .map(v => v.map(if (_) 1 else 0).sum)
      .sum

    println(s"$visibleTrees trees are visible")
  }


  override def partTwo(): Unit = {
    val size = grid.size
    var highestScore = -1

    for {
      i <- 1 until size - 1
      j <- 1 until size - 1
    } {
      val tree = grid(i)(j)
      val up = exploreRow(i, j, North(), tree)
      val down = exploreRow(i, j, South(), tree)
      val left = exploreRow(i, j, West(), tree)
      val right = exploreRow(i, j, East(), tree)
      highestScore = highestScore.max(up * down * left * right)
    }

    println(s"The highest scenic score is $highestScore")
  }

  private def exploreRow(x: Int, y: Int, direction: Direction, original: Int): Int = {
    direction.next(x, y) match {
      case Some((x2, y2)) =>
        if (original > grid(x2)(y2)) {
          1 + exploreRow(x2, y2, direction, original)
        } else {
          1
        }
      case None => 0 // edges are 0
    }
  }

  private sealed trait Direction {
    def next(x: Int, y: Int): Option[(Int, Int)]
  }

  private case class North() extends Direction {
    override def next(x: Int, y: Int): Option[(Int, Int)] = if (x >= 1) Some((x - 1, y)) else None
  }

  private case class South() extends Direction {
    override def next(x: Int, y: Int): Option[(Int, Int)] = if (x <= grid.size - 2) Some((x + 1, y)) else None
  }

  private case class West() extends Direction {
    override def next(x: Int, y: Int): Option[(Int, Int)] = if (y >= 1) Some((x, y - 1)) else None
  }

  private case class East() extends Direction {
    override def next(x: Int, y: Int): Option[(Int, Int)] = if (y <= grid.size - 2) Some((x, y + 1)) else None
  }
}