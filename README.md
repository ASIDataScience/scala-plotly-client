[![Build Status](https://travis-ci.org/facultyai/scala-plotly-client.svg)](https://travis-ci.org/facultyai/scala-plotly-client)

# Plotly client

## Installation

To add the plotly client to your code, add the following lines to your
`build.sbt`:

```scala
libraryDependencies += "ai.faculty" %% "plotly" % "0.2.0"
```

To install the bleeding edge version, add this instead:

```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "ai.faculty" %% "plotly" % "0.2.1-SNAPSHOT"
```

## Documentation

- Tutorials will be included as part of the [Plotly
  documentation](https://plot.ly/api/) when the interface stabilises.
- [API
  documentation](https://facultyai.github.io/scala-plotly-client/latest/api/#ai.faculty.plotly.package).

## Authentication

To create a graph on Plotly, start by opening an account with the web UI. Then
create an API key by clicking on your username in the top right hand corner of
the screen and selecting *SETTINGS > API KEYS*. Create the file
`~/.plotly/.credentials` in your home directory. The file should look like:

```json
{
  "username": "pbugnion",
  "api_key": "l233fgfdsjk"
}
```

Note that if you have already used another Plotly client, you probably do not
need to do this.

## Your first graph

To create a graph on the Plotly servers, start by importing the client:

```scala
import ai.faculty.plotly._
```

Then, just pass the *x*, *y* series that you want to plot:

```scala
scala> val x = Vector(1.0, 2.0, 3.0)

scala> val y = Vector(1.0, 4.0, 9.0)

scala> val p = Plot().withScatter(x, y)

scala> draw(p, "hello-plotly")
PlotFile = PlotFile(pbugnion:264,hello-plotly)
```

This will create a graph called `hello-plotly` in your account!

## Using custom credentials

Sometimes, creating a `~/.plotly/.credentials` file isn't practical. In that
case, you can pass credentials to Plotly programatically by defining a custom
server.

```scala
import ai.faculty.plotly._

implicit val server = new writer.Server {
  val credentials = writer.Credentials("<username>", "<api_key>")
  val url = "https://api.plot.ly/v2/"
}
```

You can then use Plotly commands normally:

```scala
scala> val p = Plot().withScatter(Vector(1, 2, 3), Vector(1, 4, 9))

scala> draw(p, "custom-credentials")
PlotFile = PlotFile(pbugnion:268,custom-credentials)
```
