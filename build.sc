// import Mill dependency
import mill._
import mill.scalalib._
import mill.scalalib.scalafmt.ScalafmtModule
import mill.scalalib.TestModule.Utest
// support BSP
import mill.bsp._


val defaultVersions = Map(
  "scala"             -> "2.12.13",
  "chisel3"           -> "3.4.3",
  "chisel3-plugin"    -> "3.4.3",
  "paradise"          -> "2.1.1",
  "utest"             -> "0.7.10",
  "chisel-iotesters"  -> "1.5.3",
  "chiseltest"        -> "0.3.3",
  "scalatest"         -> "3.2.7",
  "organize-imports"  -> "0.5.0",
  "semanticdb-scalac" -> "4.4.12"
  )

object fastvdma extends ScalaModule with ScalafmtModule { m =>
  override def scalaVersion = defaultVersions("scala")
  override def scalacOptions = Seq(
    "-Xsource:2.11",
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
    // Enables autoclonetype2 in 3.4.x (on by default in 3.5)
    "-P:chiselplugin:useBundlePlugin"
  )
  override def ivyDeps = Agg(
    ivy"edu.berkeley.cs::chisel3:${defaultVersions("chisel3")}",
  )
  override def scalacPluginIvyDeps = Agg(
    ivy"edu.berkeley.cs:::chisel3-plugin:${defaultVersions("chisel3-plugin")}",
    ivy"org.scalamacros:::paradise:${defaultVersions("paradise")}"
  )
  object test extends Tests {
    override def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:${defaultVersions("scalatest")}",
      ivy"edu.berkeley.cs::chiseltest:${defaultVersions("chiseltest")}",
      ivy"edu.berkeley.cs::chisel-iotesters:${defaultVersions("chisel-iotesters")}",
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}
