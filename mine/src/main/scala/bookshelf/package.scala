import io.getquill._
import io.getquill.sources.async.AsyncSource
import org.joda.time.DateTime
import scala.concurrent.ExecutionContext.Implicits.global

package object bookshelf {

  implicit val encodeJoda = mappedEncoding[DateTime, java.util.Date](_.toDate)
  implicit val decodeJoda = mappedEncoding[java.util.Date, DateTime](new DateTime(_))

  def truncate(table: String)(implicit db: AsyncSource[_, _, _]): Unit =
    db.execute(s"TRUNCATE TABLE $table")


}
