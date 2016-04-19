package bookshelf.mine.schema

case class PublicationsTypes(
                              id: Int,
                              name: String
                            )

object PublicationsTypes {

  val values = List("ANTHOLOGY", "COLLECTION", "MAGAZINE", "NONFICTION", "NOVEL", "OMNIBUS", "FANZINE", "CHAPBOOK")
    .map(_.toLowerCase)

  val all = values.zipWithIndex.map { case (name, id) =>
    PublicationsTypes(id, name)
  }

}