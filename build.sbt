name := "plotly"

version := "0.2.0"

organization := "co.theasi"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.10.6")

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.2.1",
  "org.json4s" %% "json4s-native" % "3.4.1",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

initialCommands := """
  |import co.theasi.plotly._
""".stripMargin

publishMavenStyle := true

// Publishing
publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

// Testing
parallelExecution in Test := false

logBuffered in Test := false

// Documentation
enablePlugins(SiteScaladocPlugin)

ghpages.settings

git.remoteRepo := "git@github.com:ASIDataScience/scala-plotly-client.git"
