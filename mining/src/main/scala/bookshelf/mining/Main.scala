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
  */
  println("\n\n===== Publications =====")
  val data = Publications.all
  //prettyPrint(data.filter(_.isFailure))
  /*
    println("\n\n===== Publication authors =====")
    prettyPrint(Publication_author.all.filter(_.isFailure))

    println("\n\n===== Publication content =====")
    prettyPrint(Publication_content.all.filter(_.isFailure))

    println("\n\n===== Publication series =====")
    prettyPrint(Publication_serie.all.filter(_.isFailure))

    println("\n\n===== Publishers =====")
    prettyPrint(Publisher.all.filter(_.isFailure))
    */
  println("\n==============\n===== DONE =====\n==============")

}
