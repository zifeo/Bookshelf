package bookshelf.mine

private[mine] object Failures extends App {

  def prettyPrint[T](data: Iterable[T]): Unit = {
    val elements = data.size
    println(s"elements: $elements")
    println(data.take(2000).mkString("\n"))
  }

  import CSVSources._

  println("\n\n===== Authors =====")
  prettyPrint(authors.filter(_.isFailure))

  //println("\n\n===== Publication =====")
  //prettyPrint(publications.filter(_.isFailure))

  //println("\n\n===== Publication authors =====")
  //prettyPrint(publicationsAuthors.filter(_.isFailure))

  //println("\n\n===== Publication content =====")
  //prettyPrint(publicationsContents.filter(_.isFailure))

  //println("\n\n===== Publication series =====")
  //prettyPrint(publicationsSeries.filter(_.isFailure))

  //println("\n\n===== Publishers =====")
  //prettyPrint(publishers.filter(_.isFailure))

  //println("\n\n===== Awards =====")
  //prettyPrint(awards.filter(_.isFailure))

  //println("\n\n===== Awards categories =====")
  //prettyPrint(awardsCategories.filter(_.isFailure))

  //println("\n\n===== Awards types =====")
  //prettyPrint(awardsTypes.filter(_.isFailure))

  //println("\n\n===== Tags =====")
  //prettyPrint(tags.filter(_.isFailure))

  //println("\n\n===== Languages =====")
  //prettyPrint(languages.filter(_.isFailure))

  //println("\n\n===== Webpages =====")
  //prettyPrint(webpages.filter(_.isFailure))

  //println("\n\n===== Notes =====")
  //prettyPrint(notes.filter(_.isFailure))

  //println("\n\n===== Titles =====")
  //prettyPrint(titles.filter(_.isFailure))

  //println("\n\n===== Titles award =====")
  //prettyPrint(titlesAwards.filter(_.isFailure))

  //println("\n\n===== Titles series =====")
  //prettyPrint(titlesSeries.filter(_.isFailure))

  //println("\n\n===== Titles tag =====")
  //prettyPrint(titlesTags.filter(_.isFailure))

  //println("\n\n===== reviews =====")
  //prettyPrint(reviews.filter(_.isFailure))

  println("\n==============\n===== DONE =====\n==============")

}
