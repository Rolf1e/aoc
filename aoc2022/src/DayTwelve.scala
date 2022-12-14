import aoccore.Day

import scala.collection.immutable.TreeMap
import scala.collection.mutable

object DayTwelve extends Day {

  override def number: Int = 12

  override def fileName: String = "inputs/daytwelve.txt"

  private val startChar = 0
  private val endChar = 25

  type Element = (Int, Int)

  override def partOne(): Unit = {
    val (grid, start, end) = parseInput
    val paths = bfs(grid, start)
    println(s"Size ${paths(end)}")
  }

  private def bfs(grid: Array[Array[Int]], start: Element): Map[Element, Int] = {
    val h = grid.length
    val l = grid(0).length
    val queue = mutable.Queue(start)
    val visited = mutable.Set[Element](start)
    val distances = mutable.Map[Element, Int](start -> 0)

    def generateNeighbours(row: Int, col: Int): Seq[Element] = {
      Seq((row, col - 1), (row, col + 1), (row - 1, col), (row + 1, col))
        .view
        .filter { case (x, y) => 0 <= x && 0 <= y && x < h && y < l }
        .filterNot(visited)
        .filter { case (x, y) => isNextLetter((row, col), (x, y)) }
        .toSeq
    }

    def isNextLetter(left: Element, right: Element): Boolean = {
      val (row, col) = left
      val (nRow, nCol) = right
      grid(nRow)(nCol) <= grid(row)(col) + 1
    }

    while (queue.nonEmpty) {
      val (row, col) = queue.dequeue()
      for (neighbour <- generateNeighbours(row, col)) {
        visited.add(neighbour)
        val newDistance = distances((row, col)) + 1
        distances.put(neighbour, newDistance)
        queue.enqueue(neighbour)
      }
    }
    distances.to(TreeMap)
  }

  private def parseInput: (Array[Array[Int]], Element, Element) = {
    var start = (-1, -1)
    var end = (-1, -1)
    val grid = for ((row, x) <- loadFromResource().zipWithIndex) yield {
      for ((c, y) <- row.toCharArray.zipWithIndex) yield {
        c match {
          case 'S' =>
            start = (x, y)
            startChar
          case 'E' =>
            end = (x, y)
            endChar
          case _ => c - 97
        }
      }
    }
    (grid.toArray, start, end)
  }

  override def partTwo(): Unit = {
    val (grid, _, end) = parseInput

    val As = findLetter(grid)
    val distances = for (start <- As) yield {
      val paths = bfs(grid, start)
      if (paths.contains(end)) paths(end)
      else 1000000000
    }
    println(distances.min)
  }

  private def findLetter(grid: Array[Array[Int]]): Seq[Element] = {
    val cases = for {
      (row, x) <- grid.zipWithIndex
      (c, y) <- row.zipWithIndex if c == 0
    } yield {
      (x, y)
    }
    cases
  }
}
