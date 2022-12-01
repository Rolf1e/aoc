// build.sc
import mill._, scalalib._, scalafmt._

object aoc2022 extends ScalaModule with ScalafmtModule {
  val globalScalaVersion = "2.13.8"
  def scalaVersion = globalScalaVersion
}
