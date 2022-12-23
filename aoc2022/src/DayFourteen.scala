import DayFourteen.Grid.{floor, sand, sandStartCoo, void}
import aoccore.Day

object DayFourteen extends Day {

  override def number: Int = 14

  override def fileName: String = "inputs/dayfourteen.txt"

  override def partOne(): Unit = {
    val grid = Grid.from(loadFromResource(), 600, 200)

    def show(): Unit = {
      display(grid.paths, Coo(470, 0), Coo(550, 200))
    }

    def pourSand(): Unit = {
      var step = 1
      var shouldStop = false
      while (!shouldStop && step < 1000) {
        grid.popSand()
        while (!shouldStop && !grid.isResting) {
          shouldStop = grid.sandFlows(grid.untilPartOne)
        }
        step += 1
      }
    }

    pourSand()
    val restedSand = grid.paths.map(_.count(_ == sand)).sum
//    show()
    println(s"You can pour ${restedSand - 1} grain of sand")
  }

  override def partTwo(): Unit = {
    val grid = Grid.from(loadFromResource(), 1000, 200)

    def show(): Unit = {
      display(grid.paths, Coo(0, 0), Coo(1000, 200))
    }

    def pourSand(): Unit = {
      var step = 1
      var shouldStop = false
      while (!shouldStop) {
        grid.popSand()
        while (!shouldStop && !grid.isRestingTwo) {
          shouldStop = grid.sandFlows(_ => false)
        }

        shouldStop = if (grid.paths(0)(500) == sand) true else shouldStop
        step += 1
      }
    }

    pourSand()
//    show()
    val restedSand = grid.paths.map(_.count(_ == sand)).sum
    println(s" You can pour ${restedSand} grain of sand")
  }

  case class Grid(paths: Array[Array[Char]]) {
    private var movingSand: Coo = sandStartCoo

    def popSand(): Unit = {
      movingSand = sandStartCoo
      paths(sandStartCoo.y)(sandStartCoo.x) = sand
    }

    def untilPartOne(coo: Coo): Boolean = paths(coo.y)(coo.x) == floor

    /**
     * @return whether we should stop
     */
    def sandFlows(until: Coo => Boolean): Boolean = {
      for (destination <- nextSandDestinations(movingSand)) {
        if (until.apply(destination)) {
          return true
        }
        if (paths(destination.y)(destination.x) == void) {
          paths(destination.y)(destination.x) = sand
          paths(movingSand.y)(movingSand.x) = void // previous case to void
          movingSand = destination
          return false
        }
      }
      true
    }

    def isResting: Boolean = {
      !nextSandDestinations(movingSand)
        .exists(destination => paths(destination.y)(destination.x) == void || paths(destination.y)(destination.x) == floor)

    }

    def isRestingTwo: Boolean = {
      !nextSandDestinations(movingSand)
        .exists(destination => paths(destination.y)(destination.x) == void)
    }

    private def nextSandDestinations(coo: Coo): Seq[Coo] = {
      Seq(
        Coo(coo.x, coo.y + 1),
        Coo(coo.x - 1, coo.y + 1),
        Coo(coo.x + 1, coo.y + 1)
      )
    }
  }

  private def display(paths: Array[Array[Char]], start: Coo = Coo(494, 0), end: Coo = Coo(504, 10)): Unit = {
    println(paths.map(_.slice(start.x, end.x).mkString).slice(start.y, end.y).mkString("\n"))
  }

  case class Coo(x: Int, y: Int)

  object Grid {
    val rock = '#'
    val void = '.'
    val sandStart = '+'
    val sand = 'o'
    val floor = 'x'
    val sandStartCoo = Coo(500, 0)

    def from(lines: Seq[String], xMax: Int, yMax: Int): Grid = {
      val paths = Array.fill(yMax, xMax)(void)
      paths(sandStartCoo.y)(sandStartCoo.x) = sandStart // sand source (500, 0)
      var maxY = 0

      def drawRock(from: Coo, to: Coo): Unit = {
        maxY = maxY.max(from.y.max(to.y))
        if (from.x == to.x) for (y <- from.y.min(to.y) to from.y.max(to.y)) {
          paths(y)(from.x) = rock
        } else if (from.y == to.y) for (x <- from.x.min(to.x) to from.x.max(to.x)) {
          paths(from.y)(x) = rock
        }
      }


      for {
        line <- lines
        window = line.split("->").sliding(2) // .toSeq
        //        _ = println(window.map(_.mkString("->")).mkString("|"))
        Array(from, to) <- window
      } {
        drawRock(toCoo(from), toCoo(to))
      }

      val floorY = (yMax - 1).min(maxY + 2)
      for (x <- 0 until xMax) paths(floorY)(x) = floor

      Grid(paths)
    }

    private def toCoo(coo: String): Coo = {
      val Array(x, y) = coo.split(",")
      Coo(x.trim.toInt, y.trim.toInt)
    }

  }
}
