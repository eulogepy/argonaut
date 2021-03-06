import sbt._
import Keys._
import org.ensime.EnsimeKeys._

object ScalaSettings {
  type Sett = Def.Setting[_]

  private[this] val unusedWarnings = Def.setting {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 11)) =>
        Seq("-Ywarn-unused-import")
      case _ =>
        Seq("-Ywarn-unused:imports")
    }
  }

  def Scala211 = "2.11.12"

  lazy val all: Seq[Sett] = Def.settings(
    scalaVersion := Scala211
  , crossScalaVersions := Seq(Scala211, "2.12.8", "2.13.0-M5")
  , ensimeScalaVersion := Scala211
  , fork in test := true
  , scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:_", "-Xlint", "-Xfuture")
  , scalacOptions ++= unusedWarnings.value
  ) ++ Seq(Compile, Test).flatMap(c =>
    scalacOptions in (c, console) --= unusedWarnings.value
  )
}
