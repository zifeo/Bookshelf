package bookshelf.mine.schema

case class TitlesTypes(
                      id: Int,
                      name: String
                      )

object TitlesTypes {

  val values = List("ANTHOLOGY", "BACKCOVERART", "COLLECTION", "COVERART", "INTERIORART", "EDITOR", "ESSAY", "INTERVIEW",
    "NOVEL", "NONFICTION", "OMNIBUS", "POEM", "REVIEW", "SERIAL", "SHORTFICTION", "CHAPBOOK").map(_.toLowerCase)

  val all = values.zipWithIndex.map { case (name, id) =>
    TitlesTypes(id, name)
  }

}