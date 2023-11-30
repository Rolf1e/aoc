import aoccore.Day

import scala.collection.mutable

object DayFour extends Day {
  override def number: Int = 4

  override def fileName: String = "inputs/dayfour.txt"

  def main(args: Array[String]): Unit = {
    DayFour.partOne()
  }

  override def partOne(): Unit = {
    val lines = loadFromResource()

    var randomNumbers = Array[Int]()
    var boards = Seq[Board]()
    var currentCells = Seq[Seq[Cell]]()
    var currentSumUnMarked = 0

    for ((line, idx) <- lines.zipWithIndex) {
      if (idx == 0) {
        randomNumbers = parseRandomNumbers(line)
      } else if (line.isEmpty) {
        boards = boards :+ Board(currentCells, currentSumUnMarked)
        currentCells = Seq()
        currentSumUnMarked = 0
      } else {
        val row = line.split(' ')
          .filterNot(_.isEmpty)
          .map(v => {
            currentSumUnMarked += v.toInt
            Cell(v.toInt)
          })

        currentCells = currentCells :+ row
      }

    }

    boards = boards :+ Board(currentCells, currentSumUnMarked)
    currentCells = Seq()
    currentSumUnMarked = 0

    boards = boards.drop(1)
    println(boards)
    for {
      randomNumber <- randomNumbers
      (board, i) <- boards.zipWithIndex
    } {
      board.mark(randomNumber)
      if (board.isWon) {
        println(s"Found winner ${i}: ${board.sumUnMarked} * ${randomNumber} = ${board.sumUnMarked * randomNumber}")
        return
      }
    }

  }

  def parseRandomNumbers(line: String): Array[Int] =
    line.split(',').map(_.toInt)


  override def partTwo(): Unit = ???

  case class Cell(value: Int, var marked: Boolean = false)

  case class Board(cells: Seq[Seq[Cell]], var sumUnMarked: Int) {

    def mark(value: Int): Unit = {
      for {
        row <- cells
        cell <- row
      } {
        if (cell.value == value) {
          cell.marked = true
          sumUnMarked -= cell.value
        }
      }
    }

    def isWon: Boolean = {
      for (row <- cells) {
        if (row.count(_.marked) == row.size) {
          return true
        }
      }

      for {i <- cells.head.indices} {
        var column = Seq[Cell]()
        for (j <- cells.indices) {
          column = column :+ cells(i)(j)
        }
        if (column.count(_.marked) == column.size) {
          return true
        }
      }

      false
    }

  }
}