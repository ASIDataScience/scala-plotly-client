package ai.faculty.plotly.writer

/** Control how an object is written to Plotly.
  *
  * @param overwrite If true, silently overwrite an existing file
  *   with the same name; if false, throws a [[PlotlyException]]
  */
case class FileOptions(overwrite: Boolean = true)
