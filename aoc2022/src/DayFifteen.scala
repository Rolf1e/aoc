import aoccore.Day

import scala.collection.mutable


object DayFifteen extends Day {
  override def number: Int = 15

  override def fileName: String = "inputs/dayfifteen.txt"

  override def partOne(): Unit = {
    val lines = loadFromResource()
    val sensors = Sensor.from(lines)

    val y = 2000000

    var distances = mutable.Set[Int]()
    var distancesToBeacons = mutable.Set[Int]()
    for (sensor <- sensors) yield {
      val distance = sensor.distanceToBeacon()
      if ((sensor.y - y).abs <= distance) {
        val a = distance - (sensor.y - y).abs
        distances = distances ++ (sensor.x - a to sensor.x + a)
      }

      val beacon = sensor.beacon
      if (beacon.y == y) {
        distancesToBeacons = distancesToBeacons + beacon.x
      }
    }
    println(s"${distances.size - distancesToBeacons.size}")
  }

  override def partTwo(): Unit = {}

  case class Sensor(x: Int, y: Int, beacon: Beacon) {
    def distanceToBeacon(): Int = ManhattanGeometry.distance(this, beacon)
  }

  case class Beacon(x: Int, y: Int)

  object Sensor {
    def from(lines: Seq[String]): Seq[Sensor] = for (line <- lines) yield {
      line match {
        case s"Sensor at x=$sx, y=$sy: closest beacon is at x=$bx, y=$by" =>
          Sensor(sx.toInt, sy.toInt, Beacon(bx.toInt, by.toInt))
      }
    }
  }

  object ManhattanGeometry {
    def distance(sensor: Sensor, beacon: Beacon): Int = {
      (sensor.x - beacon.x).abs + (sensor.y - beacon.y).abs
    }
  }
}
