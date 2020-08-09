package co.theasi.plotly


sealed trait Series {
  type Self <: Series
  type OptionType <: SeriesOptions

  val options: OptionType

  def options(newOptions: OptionType): Self
}

sealed trait CartesianSeries extends Series {
  type Self <: CartesianSeries

  def xsAs[T: Readable]: Iterable[T] = {
    this match {
      case s: CartesianSeries1D[_] =>
        s.xs.map {
          implicitly[Readable[T]].fromPType(_)
        }
      case s: CartesianSeries2D[_, _] =>
        s.xs.map {
          implicitly[Readable[T]].fromPType(_)
        }
    }
  }

  def ysAs[T: Readable]: Iterable[T] = {
    this match {
      case s: CartesianSeries2D[_, _] =>
        s.ys.map {
          implicitly[Readable[T]].fromPType(_)
        }
      case _ =>
        throw new IllegalArgumentException(
          "Cannot extract ys from 1D series")
    }
  }
}

sealed abstract class CartesianSeries1D[X <: PType]
  extends CartesianSeries {
  val xs: Iterable[X]
}

sealed abstract class CartesianSeries2D[X <: PType, Y <: PType]
  extends CartesianSeries {
  val xs: Iterable[X]
  val ys: Iterable[Y]
  val options: OptionType
}

case class Box[X <: PType](
                            val xs: Iterable[X],
                            override val options: BoxOptions)
  extends CartesianSeries1D[X] {
  type Self = Box[X]
  type OptionType = BoxOptions

  override def options(newOptions: BoxOptions): Box[X] =
    copy(options = newOptions)
}

case class Scatter[X <: PType, Y <: PType](
                                            val xs: Iterable[X],
                                            val ys: Iterable[Y],
                                            override val options: ScatterOptions)
  extends CartesianSeries2D[X, Y] {
  type Self = Scatter[X, Y]
  type OptionType = ScatterOptions

  override def options(newOptions: ScatterOptions): Scatter[X, Y] =
    copy(options = newOptions)
}

case class ScatterMapbox[X <: PType, Y <: PType](
                                                  val xs: Iterable[X],
                                                  val ys: Iterable[Y],
                                                  override val options: ScatterOptions)
  extends CartesianSeries2D[X, Y] {
  type Self = ScatterMapbox[X, Y]
  type OptionType = ScatterOptions

  override def options(newOptions: ScatterOptions): ScatterMapbox[X, Y] =
    copy(options = newOptions)
}

case class Scatter3D[X <: PType, Y <: PType, Z <: PType](
                                                          val xs: Iterable[X],
                                                          val ys: Iterable[Y],
                                                          val zs: Iterable[Z],
                                                          override val options: ScatterOptions)
  extends ThreeDSeries {
  type Self = Scatter3D[X, Y, Z]
  type OptionType = ScatterOptions

  override def options(newOptions: ScatterOptions): Scatter3D[X, Y, Z] =
    copy(options = newOptions)
}

case class Bar[X <: PType, Y <: PType](
                                        val xs: Iterable[X],
                                        val ys: Iterable[Y],
                                        override val options: BarOptions)
  extends CartesianSeries2D[X, Y] {
  type Self = Bar[X, Y]
  type OptionType = BarOptions

  override def options(newOptions: BarOptions): Bar[X, Y] =
    copy(options = newOptions)
}


sealed trait ThreeDSeries extends Series {
  type Self <: ThreeDSeries
}

case class SurfaceZ[Z <: PType](
                                 val zs: Iterable[Iterable[Z]],
                                 val options: SurfaceOptions
                               ) extends ThreeDSeries {
  type Self = SurfaceZ[Z]
  type OptionType = SurfaceOptions

  def options(newOptions: SurfaceOptions): SurfaceZ[Z] =
    copy(options = newOptions)
}

case class SurfaceXYZ[X <: PType, Y <: PType, Z <: PType](
                                                           val xs: Iterable[X],
                                                           val ys: Iterable[Y],
                                                           val zs: Iterable[Iterable[Z]],
                                                           val options: SurfaceOptions
                                                         ) extends ThreeDSeries {
  type Self = SurfaceXYZ[X, Y, Z]
  type OptionType = SurfaceOptions

  def options(newOptions: SurfaceOptions): SurfaceXYZ[X, Y, Z] =
    copy(options = newOptions)
}
