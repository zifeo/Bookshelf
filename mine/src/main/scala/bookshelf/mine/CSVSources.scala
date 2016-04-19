package bookshelf.mine

import bookshelf.mine.schema._

import scala.io.{Codec, Source}
import scala.util.Try

private[mine] object CSVSources {

  /**
    * Returns the given file as lists of lists.
    *
    * @param name file name
    * @return rows of columns
    */
  def getDataset(name: String): List[List[String]] =
    Source.fromFile(s"./datasets/$name")(Codec.ISO8859).getLines().toList.map { line =>
      line.replace("\\\t", "").split('\t').map(_.trim).toList
    }

  lazy val authors = getDataset("authors.csv").map(Authors.parseCols)
  lazy val awards = getDataset("awards.csv").map(Awards.parseCols)
  lazy val awardsCategories = getDataset("award_categories.csv").map(AwardsCategories.parseCols)
  lazy val awardsTypes = getDataset("award_types.csv").map(AwardsTypes.parseCols)
  lazy val languages = getDataset("languages.csv").map(Languages.parseCols)
  lazy val notes = getDataset("notes.csv").map(Notes.parseCols)
  lazy val publications = getDataset("publications.csv").map(Publications.parseCols)
  lazy val publicationsAuthors = getDataset("publications_authors.csv").map(PublicationsAuthors.parseCols)
  lazy val publicationsContents = getDataset("publications_content.csv").map(PublicationsContents.parseCols)
  lazy val publicationsSeries = getDataset("publications_series.csv").map(PublicationsSeries.parseCols)
  lazy val publishers = getDataset("publishers.csv").map(Publishers.parseCols)
  lazy val reviews = getDataset("reviews.csv").map(Reviews.parseCols)
  lazy val tags = getDataset("tags.csv").map(Tags.parseCols)
  lazy val titlesData = getDataset("titles.csv")
  lazy val titles = titlesData.map(Titles.parseCols)
  lazy val translators = titlesData.flatMap(Translators.parseCols(_).toOption).flatten.distinct.map(Try(_))
  lazy val titleTranslators = titlesData.flatMap(TitlesTranslators.parseCols(_).toOption).flatten.distinct.map(Try(_))
  lazy val titlesAwards = getDataset("titles_awards.csv").map(TitlesAwards.parseCols)
  lazy val titlesSeries = getDataset("titles_series.csv").map(TitlesSeries.parseCols)
  lazy val titlesTags = getDataset("titles_tag.csv").map(TitlesTags.parseCols)
  lazy val webpages = getDataset("webpages.csv").map(Webpages.parseCols)

}
