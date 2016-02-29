package bookshelf.mining

object Main extends App {

  def prettyPrint[T](data: List[T]): Unit =
    println(data.mkString("\n"))

  println("===== Authors =====")
  prettyPrint(Author.all.filter(_.isFailure))

}
