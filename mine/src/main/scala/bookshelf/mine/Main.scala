package bookshelf.mine

private[mine] object Main extends App {

  def prettyPrint[T](data: List[T]): Unit =
    println(data.mkString("\n"))

  /*
  println("\n\n===== Authors =====")
  prettyPrint(Author.all.filter(_.isFailure))

  println("\n\n===== Publications =====")
  prettyPrint(Publications.all.filter(_.isFailure))

  println("\n\n===== Publication authors =====")
  prettyPrint(Publication_author.all.filter(_.isFailure))

  println("\n\n===== Publication content =====")
  prettyPrint(Publication_content.all.filter(_.isFailure))

  println("\n\n===== Publication series =====")
  prettyPrint(Publication_serie.all.filter(_.isFailure))

  println("\n\n===== Publishers =====")
  prettyPrint(Publisher.all.filter(_.isFailure))

  println("\n\n===== Awards =====")
  prettyPrint(Award.all.filter(_.isFailure))

  println("\n\n===== Awards categories =====")
  prettyPrint(Award_category.all.filter(_.isFailure))

  println("\n\n===== Awards types =====")
  prettyPrint(Award_type.all.filter(_.isFailure))

  println("\n\n===== Tags =====")
  prettyPrint(Tag.all.filter(_.isFailure))

  println("\n\n===== Languages =====")
  prettyPrint(Language.all.filter(_.isFailure))

  println("\n\n===== Webpages =====")
  prettyPrint(Webpage.all.filter(_.isFailure))

  println("\n\n===== Notes =====")
  prettyPrint(Note.all.filter(_.isFailure))

  println("\n\n===== Titles =====")
  prettyPrint(Title.all.filter(_.isFailure))

  println("\n\n===== Titles award =====")
  prettyPrint(Titles_awards.all.filter(_.isFailure))

  println("\n\n===== Titles series =====")
  prettyPrint(Titles_series.all.filter(_.isFailure))

  println("\n\n===== Titles tag =====")
  prettyPrint(Titles_tag.all.filter(_.isFailure))

  println("\n\n===== reviews =====")
  prettyPrint(Reviews.all.filter(_.isFailure))
  */

  println("\n==============\n===== DONE =====\n==============")

}
