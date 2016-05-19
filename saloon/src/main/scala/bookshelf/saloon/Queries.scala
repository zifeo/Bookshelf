package bookshelf.saloon

import bookshelf.mine.schema._
import io.getquill._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Queries {

  case class NewTitle(
                       title: String,
                       synopsis: Option[String],
                       noteId: Option[String],
                       seriesId: Option[Int],
                       seriesNum: Option[Int],
                       storyLength: Option[String],
                       `type`: Option[String],
                       parent: Option[Int],
                       languageId: Option[Int],
                       graphic: Option[Boolean]
                     ) {

    require(title.nonEmpty)

    require(TitlesLengths.all.exists(_.name == storyLength))
    require(TitlesTypes.all.exists(_.name == `type`))

    def gen: Titles =
      Titles(
        id = 0,
        title,
        synopsis = synopsis.map(x => Await.result(Queries.notesIns(Notes(0, x)), 10 seconds)),
        noteId = noteId.map(x => Await.result(Queries.notesIns(Notes(0, x)), 10 seconds)),
        seriesId,
        seriesNum,
        storyLength,
        `type`,
        parent,
        languageId,
        graphic
      )

  }

  private object Query {

    val authById = quote { id: Int =>
      query[Authors]
        .filter(_.id == id)
    }

    val pubById = quote { id: Int =>
      query[Publications]
        .filter(_.id == id)
    }

    val titlById = quote { id: Int =>
      query[Titles]
        .filter(_.id == id)
    }

    val authDel = quote { id: Int =>
      query[Authors]
        .filter(_.id == id)
        .delete
    }

    val pubDel = quote { id: Int =>
      query[Publications]
        .filter(_.id == id)
        .delete
    }

    val titlDel = quote { id: Int =>
      query[Titles]
        .filter(_.id == id)
        .delete
    }

    val titlIns = quote {
      query[Titles]
        .schema(_.generated(_.id))
        .insert
    }

    val notesIns = quote {
      query[Notes]
          .schema(_.generated(_.id))
        .insert
    }

  }

  def authors(id: Int): Future[Authors] =
    db.run(Query.authById)(id).map(_.head)

  def publications(id: Int): Future[Publications] =
    db.run(Query.pubById)(id).map(_.head)

  def titles(id: Int): Future[Titles] =
    db.run(Query.titlById)(id).map(_.head)

  def authorsDel(id: Int): Future[Long] =
    db.run(Query.authDel)(id)

  def publicationsDel(id: Int): Future[Long] =
    db.run(Query.pubDel)(id)

  def titlesDel(id: Int): Future[Long] =
    db.run(Query.titlDel)(id)

  def titleIns(t: NewTitle): Future[Long] =
    db.run(Query.titlIns)(t.gen)

  def notesIns(t: Notes): Future[Int] =
    db.run(Query.notesIns)(t).map(_.toInt)

}
