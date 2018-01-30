name := "plotly"

version := "0.2.2-SNAPSHOT"

organization := "co.theasi"

scalaVersion := "2.12.4"

def scalacOptionsForVersion(version: String) =
  CrossVersion.partialVersion(version) match {
    case Some((2, major)) if major >= 11 => "-Ywarn-unused-import"
    case _ => ""
  }

scalacOptions += scalacOptionsForVersion(scalaVersion.value)

crossScalaVersions := Seq("2.11.8", "2.12.4")

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.json4s" %% "json4s-native" % "3.5.3",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

initialCommands := """
  |import co.theasi.plotly._
""".stripMargin

publishMavenStyle := true

// Publishing
publishTo := {
  val nexus = "https://oss.sonatype.org/"
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

git.remoteRepo := "git@github.com:ASIDataScience/scala-plotly-client.git"
