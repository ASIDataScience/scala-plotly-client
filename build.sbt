name := "plotly"

version := "0.3.1-SNAPSHOT"

organization := "co.theasi"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "org.json4s" %% "json4s-native" % "3.7.0-M1",
  "org.scalatest" %% "scalatest" % "3.2.0" % "test"
)

initialCommands := """
  |import co.theasi.plotly._
""".stripMargin

publishMavenStyle := true

// Publishing
publishTo := {
  val nexus = "https://maven.pkg.github.com/tnyz"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

// Testing
parallelExecution in Test := false

logBuffered in Test := false

// Documentation
enablePlugins(SiteScaladocPlugin)

git.remoteRepo := "git@github.com:tnyz/scala-plotly-client.git"
