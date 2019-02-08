package ai.faculty.plotly

sealed trait TextValue

case class StringText(value: String) extends TextValue
case class IterableText[T <: PType](value: Iterable[T]) extends TextValue
case class SrcText(value: String) extends TextValue
