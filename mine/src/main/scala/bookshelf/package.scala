import io.getquill._
import org.joda.time.DateTime

package object bookshelf {

  implicit val encodeJoda = mappedEncoding[DateTime, java.util.Date](_.toDate)
  implicit val decodeJoda = mappedEncoding[java.util.Date, DateTime](new DateTime(_))

}
