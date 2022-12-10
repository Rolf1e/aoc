import aoccore.Day

import scala.collection.mutable

object DaySeven extends Day {
  override def number: Int = 7

  override def fileName: String = "inputs/dayseven.txt"

  private val cwd = mutable.Stack("/")
  private val fileSystem = FileSystem()

  override def partOne(): Unit = {
    val lines = loadFromResource()
    for (line <- lines) {

      line match {
        case s"$$ cd $dir" => {
          if (dir == "..") cwd.pop()
          else if (dir != "/") cwd.push(dir)
        }
        case s"$$ ls" => {}
        case s"dir $dir" => {
          val fullPath = pwd + s"/$dir"
          fileSystem.mkdir(fullPath)
        }
        case s"$size $filename" => {
          fileSystem.touch(pwd, File(filename, size.toInt))
        }
      }
      //      fileSystem.ls()
    }

    val threshold = 100000
    val directories = fileSystem.findSubdirWithAtMost(threshold)
    println(directories.map(_.size).sum)
  }

  private def pwd: String = cwd.reverse.mkString("/")


  override def partTwo(): Unit = {
    val diskTotalSpace = 70000000
    val updateRequireSpace = 30000000

    val freeSpace = diskTotalSpace - fileSystem.occupiedSpace
    val toSmall = fileSystem.findSubdirWithAtMost(updateRequireSpace - freeSpace)
    val toBeDelete = fileSystem.findAllSubdir()
      .filter(node => !toSmall.contains(node))
      .minBy(_.size)

    println(s"You should delete $toBeDelete of ${toBeDelete.size}")
  }

  private sealed trait Node {

    def name: String

    def size: Int

    var children: Map[String, Node]
  }

  private case class File(name: String, size: Int) extends Node {
    override var children: Map[String, Node] = Map.empty
  }


  private case class Dir(name: String, var children: Map[String, Node] = Map.empty) extends Node {
    override def size: Int = children
      .view
      .map { case (_, node) => node.size }
      .sum
  }

  private class FileSystem private(root: Node) {
    def occupiedSpace: Int = root.size

    def findAllSubdir(root: Node = root): Seq[Node] = {
      root.children
        .view
        .flatMap { case (_, node) => node match {
          case File(_, _) => Seq[Node]()
          case Dir(_, _) => findAllSubdir(node) ++ Seq(node)
        }
        }
        .toSeq
    }

    def findSubdirWithAtMost(sizeThreshold: Int, root: Node = root): Seq[Node] = {
      root.children
        .view
        .flatMap { case (_, node) =>
          findSubdirWithAtMost(sizeThreshold, node) ++ (if (node.size < sizeThreshold) Seq(node) else Seq())
        }
        .filter(node => node match {
          case File(_, _) => false
          case Dir(_, _) => true
        })
        .toSeq
    }

    def ls(): Unit = {
      println(root)
    }

    def mkdir(fullPath: String): Unit = {
      var current = root
      for (dir <- fullPath.split("/") if dir.nonEmpty) {
        current = current.children.get(dir) match {
          case Some(v) => v
          case None => {
            val newDir = Dir(dir)
            current.children = current.children + (dir -> newDir)
            newDir
          }
        }
      }
    }

    def touch(fullPath: String, file: File): Unit = {
      var current = root
      if (fullPath != "/") {
        for (dir <- fullPath.split("/") if dir.nonEmpty) {
          current.children.get(dir) match {
            case Some(value) => current = value
            case None => throw new IllegalArgumentException(s"Can not touch $fullPath $file")
          }
        }
      }
      current.children = current.children + (file.name -> File(file.name, file.size))
    }

  }

  private object FileSystem {
    def apply(): FileSystem = {
      new FileSystem(Dir("/"))
    }
  }

}
