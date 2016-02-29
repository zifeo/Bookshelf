package bookshelf.mining

import bookshelf.mining.publication._

object Main extends App {

  def prettyPrint[T](data: List[T]): Unit =
    println(data.mkString("\n"))

  val PATTERN = "(\\d*)\\+([A-z]*)".r

  val abc = "1+a"

  /*
  println("\n\n===== Authors =====")
  prettyPrint(Author.all.filter(_.isFailure))
  println("\n\n===== Publications =====")
  val data = Publications.all
  prettyPrint(data.filter(_.isFailure))
    println("\n\n===== Publication authors =====")
    prettyPrint(Publication_author.all.filter(_.isFailure))

    println("\n\n===== Publication content =====")
    prettyPrint(Publication_content.all.filter(_.isFailure))

    println("\n\n===== Publication series =====")
    prettyPrint(Publication_serie.all.filter(_.isFailure))

    println("\n\n===== Publishers =====")
    prettyPrint(Publisher.all.filter(_.isFailure))
    */
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

  println("\n==============\n===== DONE =====\n==============")

}
