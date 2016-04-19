package bookshelf.mine.schema

case class TitlesLengths(
                        id: Int,
                        name: String
                        )

object TitlesLengths {

  val values = List("NOVELLA", "SHORTSTORY", "JUVENILE_FICTION", "NOVELIZATION", "SHORT_FICTION").map(_.toLowerCase)

  val all = values.zipWithIndex.map { case (name, id) =>
    TitlesLengths(id, name)
  }

}