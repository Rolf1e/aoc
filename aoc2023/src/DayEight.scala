import aoccore.Day

import scala.annotation.tailrec
import scala.collection.mutable

object DayEight extends Day {
  override def number: Int = 8

  override def fileName: String = "inputs/ex3_dayeight.txt"

  def main(args: Array[String]): Unit = {
    DayEight.partTwo()
  }

  override def partOne(): Unit = {
    val (directions, nodes) = parse
    var current = start
    var steps = 0
    var i = 0
    while (current != end) {
      val neighbours = nodes(current)
      val direction = directions(i)
      current = if (direction == 'L') neighbours.left else neighbours.right

      steps += 1
      i = if (i == directions.length - 1) 0 else i + 1
    }

    println(steps)

  }

  implicit class RichTuple(t: Next) {
    def left: Node = t._1

    def right: Node = t._2
  }

  type Directions = String
  type Node = String
  type Next = (String, String)

  val start = "AAA"
  val end = "ZZZ"

  def parse: (Directions, Map[Node, Next]) = {
    val lines = loadFromResource()
    var instructions = ""
    val nodes = mutable.Map[Node, Next]()

    for (line <- lines) {
      line match {
        case s"$node = ($left, $right)" => nodes.update(node, (left, right))
        case "" => {}
        case insts => instructions = insts
      }

    }


    (instructions, nodes.toMap)
  }

  @tailrec
  def gcd(a: Int, b: Int): Int = if (b == 0) a.abs else gcd(b, a % b)

  def lcm(a: Int, b: Int): Int = (a / gcd(a, b)) * b

  def computeCycle(node: Node, directions: Directions, nodes: Map[Node, Next]): Int = {
    var steps = 0
    var current = node

    for (d <- directions) {
      steps += 1
      val neighbours = nodes(current)
      current = if (d == 'L') neighbours.left else neighbours.right
      if (current.endsWith("Z")) {
        return steps
      }
    }
    steps
  }

  override def partTwo(): Unit = {
    val (directions, nodes) = parse
    val starts = nodes
      .keys
      .filter(_.startsWith("A"))
      .toArray

    var result = 1
    for (start <- starts) {
      val cycle = computeCycle(start, directions, nodes)
      result = lcm(result, cycle)
    }
    println(result)
  }
}
