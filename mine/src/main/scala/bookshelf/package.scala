import io.getquill._
import io.getquill.sources.jdbc.JdbcSource
import org.joda.time.DateTime

package object bookshelf {

  implicit val encodeJoda = mappedEncoding[DateTime, java.util.Date](_.toDate)
  implicit val decodeJoda = mappedEncoding[java.util.Date, DateTime](new DateTime(_))

  def truncate(table: String)(implicit db: JdbcSource[_, _]): Unit =
    db.execute(s"TRUNCATE TABLE $table", None)


}
