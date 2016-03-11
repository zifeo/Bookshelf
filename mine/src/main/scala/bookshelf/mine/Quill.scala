package bookshelf.mine

import io.getquill._
import io.getquill.naming.SnakeCase

object Quill extends App {

  val db = source(new PostgresAsyncSourceConfig[SnakeCase]("db"))

  case class TitleA(id: Int, a: String, note_id: Int)

  val t = TitleA(3, "test quill", 3)

  val ts = quote {
    query[TitleA].map(t => t.id)
  }

  val res = db.probe("SELECT * FROM title")

  println(db.run(ts))

}

/*
object Test extends App {

  import io.getquill._
  lazy val db = source(new SqlMirrorSourceConfig("testSource"))

  case class Circle(radius: Float)

  val q: Quoted[Query[Circle]] = quote {
    query[Circle].filter(c => c.radius > 10)
  }

  //db.run(q) // Dynamic query


}
*/
