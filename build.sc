// build.sc
import mill._, scalalib._, scalafmt._

object aoc2023 extends ScalaModule with ScalafmtModule {
  val globalScalaVersion = "2.13.8"
  def scalaVersion = globalScalaVersion
  override def moduleDeps = Seq(aoccore)
}

object aoc2022 extends ScalaModule with ScalafmtModule {
  val globalScalaVersion = "2.13.8"
  def scalaVersion = globalScalaVersion
  override def moduleDeps = Seq(aoccore)
}

object aoc2021 extends ScalaModule with ScalafmtModule {
  val globalScalaVersion = "2.13.8"
  def scalaVersion = globalScalaVersion
  override def moduleDeps = Seq(aoccore)
}

object aoc2015 extends ScalaModule with ScalafmtModule {
  val globalScalaVersion = "2.13.8"
  def scalaVersion = globalScalaVersion
  override def moduleDeps = Seq(aoccore)
}

object aoccore extends ScalaModule with ScalafmtModule {
  val globalScalaVersion = "2.13.8"
  def scalaVersion = globalScalaVersion
}
