package co.theasi.plotly

sealed trait Series {
  type Self <: Series
  type OptionType <: SeriesOptions

  val plotType: PlotType.Value
  val options: OptionType
}

sealed abstract class CartesianSeries1D[X <: PType] extends Series {
  val xs: Iterable[X]

  def xsAs[T: Readable]: Iterable[T] = xs.map(p => implicitly[Readable[T]].fromPType(p))

}

sealed abstract class CartesianSeries2D[X <: PType, Y <: PType] extends Series {
  val xs: Iterable[X]
  val ys: Iterable[Y]
  val options: OptionType

  def xsAs[T: Readable]: Iterable[T] = xs.map(p => implicitly[Readable[T]].fromPType(p))

  def ysAs[T: Readable]: Iterable[T] = ys.map(p => implicitly[Readable[T]].fromPType(p))
}

sealed abstract class CartesianSeriesGeo[X <: PType, Y <: PType] extends Series {
  val lons: Iterable[X]
  val lats: Iterable[Y]
  val options: OptionType

  def xsAs[T: Readable]: Iterable[T] = lons.map(p => implicitly[Readable[T]].fromPType(p))

  def ysAs[T: Readable]: Iterable[T] = lats.map(p => implicitly[Readable[T]].fromPType(p))
}

case class Box[X <: PType](xs: Iterable[X], override val options: BoxOptions) extends CartesianSeries1D[X] {
  type Self = Box[X]
  type OptionType = BoxOptions
  override val plotType: PlotType.Value = PlotType.BOX
}

case class Scatter[X <: PType, Y <: PType](xs: Iterable[X], ys: Iterable[Y], override val options: ScatterOptions)
  extends CartesianSeries2D[X, Y] {
  type Self = Scatter[X, Y]
  type OptionType = ScatterOptions
  override val plotType: PlotType.Value = PlotType.SCATTER
}

case class ScatterMapbox[X <: PType, Y <: PType](lons: Iterable[X], lats: Iterable[Y], override val options: ScatterOptions)
  extends CartesianSeriesGeo[X, Y] {
  type Self = ScatterMapbox[X, Y]
  type OptionType = ScatterOptions
  override val plotType: PlotType.Value = PlotType.SCATTERMAPBOX
}

case class Scatter3D[X <: PType, Y <: PType, Z <: PType](xs: Iterable[X], ys: Iterable[Y], zs: Iterable[Z],
                                                         override val options: ScatterOptions)
  extends ThreeDSeries {
  type Self = Scatter3D[X, Y, Z]
  type OptionType = ScatterOptions
  override val plotType: PlotType.Value = PlotType.SCATTER3D
}

case class Bar[X <: PType, Y <: PType](xs: Iterable[X], ys: Iterable[Y], override val options: BarOptions)
  extends CartesianSeries2D[X, Y] {
  type Self = Bar[X, Y]
  type OptionType = BarOptions
  override val plotType: PlotType.Value = PlotType.BAR
}

sealed trait ThreeDSeries extends Series {
  type Self <: ThreeDSeries
}

case class SurfaceZ[Z <: PType](zs: Iterable[Iterable[Z]], options: SurfaceOptions) extends ThreeDSeries {
  type Self = SurfaceZ[Z]
  type OptionType = SurfaceOptions
  override val plotType: PlotType.Value = PlotType.SURFACE
}

case class SurfaceXYZ[X <: PType, Y <: PType, Z <: PType](xs: Iterable[X], ys: Iterable[Y],
                                                          zs: Iterable[Iterable[Z]], options: SurfaceOptions) extends ThreeDSeries {
  type Self = SurfaceXYZ[X, Y, Z]
  type OptionType = SurfaceOptions
  override val plotType: PlotType.Value = PlotType.SURFACEXYZ
}

object PlotType extends Enumeration {
  type label = PlotType.Value
  val SCATTER, SCATTER3D, SURFACE, SURFACEXYZ, BAR, BOX, SCATTERMAPBOX = Value
}
