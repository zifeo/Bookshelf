package bookshelf

import scala.io.{Codec, Source}
import scala.util.Try

package object mining {

  /**
    * Returns the given file as lists of lists.
    *
    * @param name file name
    * @return rows of columns
    */
  def getDataset(name: String): List[List[String]] =
    Source.fromFile(s"../datasets/$name")(Codec.ISO8859).getLines().map(_.split('\t').toList).toList

  /**
    * Parse an integer as an option.
    * @param raw number
    * @return some of integer or none
    */
  def intOrNone(raw: String): Option[Int] =
    Try(raw.toInt).toOption

}
