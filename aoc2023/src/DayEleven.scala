
import aoccore.Day
import aoccore.Day.Coord

import scala.collection.mutable

object DayEleven extends Day {
  def main(args: Array[String]): Unit = {
    DayEleven.partOne()
    DayEleven.partTwo()
  }

  def number: Int = 11

  def fileName: String = "inputs/dayeleven.txt"

  case class Key(i: Int, j: Int)

  def partOne(): Unit = {
    val (galaxies, xs, ys) = parse
    println(galaxies)
    println(xs)
    println(ys)

    val expandedGalaxies = correctWithExpands(galaxies, xs, ys)
    println(expandedGalaxies)

    var visited = Set[Key]()

    val distances = for {
      (galaxy, i) <- expandedGalaxies.zipWithIndex
      (other, j) <- expandedGalaxies.zipWithIndex if galaxy != other
    } yield {
      if (visited.contains(Key(i, j)) || visited.contains(Key(j, i))) 0
      else {
        val distance = Coord.manhattanDistance(galaxy, other)
        // println(s"Between galaxy ${i + 1} $galaxy and galaxy ${j + 1} $other: $distance")
        visited = visited + Key(i, j)
        distance
      }
    }
    println(distances.sum)
  }

  def displayGalaxy(galaxies: Seq[Coord], maxX: Int, maxY: Int) = {
    for {
      y <- 0 until maxY
      x <- 0 until maxX
    } {
      if (x == 0) {
        println
      }
      if (galaxies contains Coord(x, y)) {
        print("#")
      } else {
        print(".")
      }
    }

    println
  }

  def partTwo(): Unit = {
    val (galaxies, xs, ys) = parse
    println(galaxies)
    // displayGalaxy(galaxies, maxX, maxY)
    println(xs)
    println(ys)

    val expandedGalaxies = correctWithExpands(galaxies, xs, ys, 1_000_000 - 1)
    println(expandedGalaxies)
    // displayGalaxy(expandedGalaxies, 20, 20)

    var visited = Set[Key]()

    val distances = for {
      (galaxy, i) <- expandedGalaxies.zipWithIndex
      (other, j) <- expandedGalaxies.zipWithIndex if galaxy != other
    } yield {
      if (visited.contains(Key(i, j)) || visited.contains(Key(j, i))) {
        0
      } else {
        val distance = Coord.manhattanDistance(galaxy, other)
        // println(s"Between galaxy ${i + 1} $galaxy and galaxy ${j + 1} $other: $distance")
        visited = visited + Key(i, j)
        distance.toLong
      }
    }
    println(distances.sum)
  }

  def correctWithExpands(
      galaxies: Seq[Coord],
      xs: Seq[Boolean],
      ys: Seq[Boolean],
      offsetFactor: Int = 1
  ): Seq[Coord] = {
    for (Coord(x, y) <- galaxies) yield {
      val offsetX = xs.slice(0, x).count(_ == false) * offsetFactor
      val offsetY = ys.slice(0, y).count(_ == false) * offsetFactor
      Coord(x + offsetX, y + offsetY)
    }
  }

  def parse: (Seq[Coord], Seq[Boolean], Seq[Boolean]) = {
    val lines = loadFromResource()
    var galaxies = Seq[Coord]()
    val rowsWithGalaxy = mutable.Seq.fill(lines.head.length)(false)
    val columnsWithGalaxy = mutable.Seq.fill(lines.length)(false)

    for {
      (line, y) <- lines.zipWithIndex
      (c, x) <- line.zipWithIndex
    } {
      if (c == '#') {
        galaxies = galaxies :+ (x, y)
        rowsWithGalaxy(x) = true
        columnsWithGalaxy(y) = true
      }
    }

    (
      galaxies,
      rowsWithGalaxy.toSeq,
      columnsWithGalaxy.toSeq
    )
  }

}
