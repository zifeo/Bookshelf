package bookshelf.mine.schema

case class TitlesLengths(
                        id: Int,
                        name: String
                        )

object TitlesLengths {

  val values = Map(
    "nv" -> "novella",
    "ss" -> "shortstory",
    "jvn" -> "juvenile_fiction",
    "nvz" -> "novelization",
    "sf" -> "short_fiction"
  )

  val all = values.values.zipWithIndex.map { case (name, id) =>
    TitlesLengths(id, name)
  }

}