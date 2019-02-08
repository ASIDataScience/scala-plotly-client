package ai.faculty.plotly

trait Readable[T] {
  def fromPType(x: PType): T
}

trait ReadableImplicits {
  implicit object ReadableDouble extends Readable[Double] {
    def fromPType(x: PType): Double = {
      x match {
        case PDouble(v) => v
        case PInt(v) => v.toDouble
        case _ => throw new ClassCastException(
          s"Cannot cast $x to double.")
      }
    }
  }
}
