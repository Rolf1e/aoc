import DayFourteen.Grid.{abyss, sand, sandStartCoo, void}
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
          shouldStop = grid.sandFlows()
        }
//        println(s"STEP $step $shouldStop")
//        show()
        step += 1
      }
    }

    pourSand()
    show()
    println(grid.getRestedSand)
  }

  override def partTwo(): Unit = {}

  case class Grid(paths: Array[Array[Char]]) {
    private var restedSand: Int = 0

    def getRestedSand: Int = restedSand - 1

    private var movingSand: Coo = sandStartCoo

    def popSand(): Unit = {
      movingSand = sandStartCoo
      paths(sandStartCoo.y)(sandStartCoo.x) = sand
      restedSand += 1
    }

    /**
     * @return whether we should stop
     */
    def sandFlows(): Boolean = {
      for (destination <- nextSandDestinations(movingSand)) {
        if (paths(destination.y)(destination.x) == abyss) {
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
        .exists(destination => paths(destination.y)(destination.x) == void || paths(destination.y)(destination.x) == abyss)
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
    val abyss = 'x'
    val sandStartCoo = Coo(500, 0)

    def from(lines: Seq[String], xMax: Int, yMax: Int): Grid = {
      val paths = Array.fill(yMax, xMax)(void)
      paths(sandStartCoo.y)(sandStartCoo.x) = sandStart // sand source (500, 0)
      for (x <- 0 until xMax) paths(yMax - 1)(x) = abyss

      def drawRock(from: Coo, to: Coo): Unit = {
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

      Grid(paths)
    }

    private def toCoo(coo: String): Coo = {
      val Array(x, y) = coo.split(",")
      Coo(x.trim.toInt, y.trim.toInt)
    }

  }
}
