package ai.faculty.plotly.writer

import ai.faculty.plotly.{SeriesOptions, ScatterOptions,
  BarOptions, BoxOptions, SurfaceOptions}

sealed trait SeriesWriteInfo {
  val srcs: List[String]
  val options: SeriesOptions
}

case class ScatterWriteInfo(
  srcs: List[String],
  axisIndex: Int,
  options: ScatterOptions
) extends SeriesWriteInfo

case class Scatter3DWriteInfo(
  srcs: List[String],
  axisIndex: Int,
  options: ScatterOptions
) extends SeriesWriteInfo

case class BarWriteInfo(
  srcs: List[String],
  axisIndex: Int,
  options: BarOptions
) extends SeriesWriteInfo

case class BoxWriteInfo(
  srcs: List[String],
  axisIndex: Int,
  options: BoxOptions
) extends SeriesWriteInfo

case class SurfaceZWriteInfo(
  srcs: List[String],
  sceneIndex: Int,
  options: SurfaceOptions
) extends SeriesWriteInfo

case class SurfaceXYZWriteInfo(
  srcs: List[String],
  sceneIndex: Int,
  options: SurfaceOptions
) extends SeriesWriteInfo
