package bookshelf.saloon

object Inserts {

  case class NewTitle(
                       title: String,
                       synopsis: Option[String],
                       noteId: Option[String],
                       seriesId: Option[Int],
                       seriesNum: Option[Int],
                       storyLength: Option[String],
                       `type`: Option[String],
                       parent: Option[Int],
                       languageId: Option[Int],
                       graphic: Option[Boolean],
                       review: Option[Int],
                       tags: Option[String],
                       awards: Option[String]
                     ) {

    require(title.nonEmpty)

  }


  def title(t: NewTitle): String = {
    println(t)
    "1"
  }

}
