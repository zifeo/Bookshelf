/************************
 * Entities
 ************************/

DROP TABLE IF EXISTS authors CASCADE;
CREATE TABLE authors
(
  id          INT PRIMARY KEY NOT NULL,
  name        VARCHAR(512)    NOT NULL,
  legal_name  VARCHAR(512),
  last_name   VARCHAR(512),
  pseudonym   INT, -- fk
  birth_place VARCHAR(512),
  birth_date  DATE,
  death_date  DATE,
  email       VARCHAR(512),
  image       VARCHAR(512),
  language_id INT, -- fk
  note_id     INT -- fk
);

DROP TABLE IF EXISTS publications CASCADE;
CREATE TABLE publications
(
  id             INT PRIMARY KEY  NOT NULL,
  title          VARCHAR(512)     NOT NULL,
  date_pub       DATE             NOT NULL,
  publisher_id   INT              NOT NULL, -- fk
  pages          INT,
  preface_pages  INT,
  packaging_type VARCHAR(16),
  type           VARCHAR(256) NOT NULL,
  isbn           BIGINT,
  cover          VARCHAR(512),
  price          FLOAT,
  currency       VARCHAR(8),
  pub_series_id  INT, -- fk
  pub_series_num INT,
  note_id        INT -- fk
);

DROP TABLE IF EXISTS titles CASCADE;
CREATE TABLE titles
(
  id           INT PRIMARY KEY NOT NULL,
  title        VARCHAR(512)    NOT NULL,
  translator   VARCHAR(512),
  synopsis     INT, -- fk
  note_id      INT, -- fk
  series_id    INT, -- fk
  series_num   INT,
  story_length VARCHAR(512),
  type         VARCHAR(256),
  parent       INT             NOT NULL DEFAULT 0, -- fk
  language_id  INT, -- fk
  graphic      BOOLEAN
);

DROP TABLE IF EXISTS languages CASCADE;
CREATE TABLE languages
(
  id     INT PRIMARY KEY NOT NULL,
  name   VARCHAR(512)    NOT NULL,
  code   CHAR(3)         NOT NULL UNIQUE,
  script BOOLEAN
);

DROP TABLE IF EXISTS notes CASCADE;
CREATE TABLE notes
(
  id   INT PRIMARY KEY NOT NULL,
  note TEXT            NOT NULL -- None ?
);

DROP TABLE IF EXISTS webpages CASCADE;
CREATE TABLE webpages
(
  id                     INT PRIMARY KEY NOT NULL,
  author_id              INT, -- fk
  publisher_id           INT, -- fk
  title_id               INT, -- fk
  url                    VARCHAR(512)    NOT NULL,
  publications_series_id INT, -- fk
  award_type_id          INT, -- fk
  title_series_id        INT, -- fk
  award_category_id      INT -- fk
);

DROP TABLE IF EXISTS tags CASCADE;
CREATE TABLE tags
(
  id   INT PRIMARY KEY NOT NULL,
  name VARCHAR(512)    NOT NULL
);

DROP TABLE IF EXISTS titles_series CASCADE;
CREATE TABLE titles_series
(
  id      INT PRIMARY KEY NOT NULL,
  title   VARCHAR(512)    NOT NULL,
  parent  INT DEFAULT 0, -- fk
  note_id INT -- fk
);

DROP TABLE IF EXISTS awards CASCADE;
CREATE TABLE awards
(
  id          INT PRIMARY KEY NOT NULL,
  title       VARCHAR(512)    NOT NULL,
  date        DATE            NOT NULL,
  category_id INT             NOT NULL, -- fk
  type_id     INT             NOT NULL, -- fk
  note_id     INT -- fk
);

DROP TABLE IF EXISTS awards_categories CASCADE;
CREATE TABLE awards_categories -- weak entity of award_type
(
  id      INT             NOT NULL,
  name    VARCHAR(512)    NOT NULL,
  type_id INT             NOT NULL, -- fk
  ordr    INT,
  note_id INT, -- fk
  PRIMARY KEY (id, type_id)
);

DROP TABLE IF EXISTS awards_types CASCADE;
CREATE TABLE awards_types
(
  id          INT PRIMARY KEY NOT NULL,
  code        CHAR(2) UNIQUE,
  name        VARCHAR(512)    NOT NULL,
  note_id     INT, -- fk
  awarded_by  VARCHAR(512)    NOT NULL,
  awarded_for VARCHAR(512)    NOT NULL,
  short_name  VARCHAR(512)    NOT NULL UNIQUE,
  poll        BOOLEAN         NOT NULL,
  non_genre   BOOLEAN         NOT NULL
);

DROP TABLE IF EXISTS publishers CASCADE;
CREATE TABLE publishers
(
  id      INT PRIMARY KEY NOT NULL,
  name    VARCHAR(1024)    NOT NULL,
  note_id INT -- fk
);

DROP TABLE IF EXISTS publications_series CASCADE;
CREATE TABLE publications_series
(
  id      INT PRIMARY KEY NOT NULL,
  name    VARCHAR(1024)    NOT NULL,
  note_id INT -- fk
);

/************************
 * Relations
 ************************/

DROP TABLE IF EXISTS publications_authors CASCADE;
CREATE TABLE publications_authors
(
  publication_id INT NOT NULL, -- fk
  author_id      INT NOT NULL, -- fk
  CONSTRAINT pk_publications_authors PRIMARY KEY (publication_id, author_id)
);

DROP TABLE IF EXISTS titles_awards CASCADE;
CREATE TABLE titles_awards
(
  title_id INT NOT NULL, -- fk
  award_id INT NOT NULL, -- fk
  CONSTRAINT pk_titles_awards PRIMARY KEY (title_id, award_id)
);

DROP TABLE IF EXISTS titles_tags CASCADE;
CREATE TABLE titles_tags
(
  title_id INT NOT NULL, -- fk
  tag_id   INT NOT NULL, -- fk
  CONSTRAINT pk_titles_tags PRIMARY KEY (title_id, tag_id)
);

DROP TABLE IF EXISTS reviews CASCADE;
CREATE TABLE reviews
(
  title_id  INT NOT NULL, -- fk
  review_id INT NOT NULL, -- fk
  CONSTRAINT pk_reviews PRIMARY KEY (title_id, review_id)
);

DROP TABLE IF EXISTS publications_contents CASCADE;
CREATE TABLE publications_contents
(
  title_id       INT NOT NULL, -- fk
  publication_id INT NOT NULL, -- fk
  CONSTRAINT pk_publications_contents PRIMARY KEY (title_id, publication_id)
);

/************************
 * Foreign keys
 ************************/

/* Authors */
ALTER TABLE authors
ADD FOREIGN KEY (language_id)
REFERENCES languages (id)
ON DELETE SET NULL;

ALTER TABLE authors
ADD FOREIGN KEY (pseudonym)
REFERENCES authors (id)
ON DELETE CASCADE;

ALTER TABLE authors
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Publications authors s*/
ALTER TABLE publications_authors
ADD FOREIGN KEY (publication_id)
REFERENCES publications (id)
ON DELETE CASCADE;

ALTER TABLE publications_authors
ADD FOREIGN KEY (author_id)
REFERENCES authors (id)
ON DELETE CASCADE;

/* Publications */
ALTER TABLE publications
ADD FOREIGN KEY (publisher_id)
REFERENCES publishers (id)
ON DELETE SET NULL;

ALTER TABLE publications
ADD FOREIGN KEY (pub_series_id)
REFERENCES publications_series (id)
ON DELETE SET NULL;

ALTER TABLE publications
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Publications contents */
ALTER TABLE publications_contents
ADD FOREIGN KEY (title_id)
REFERENCES titles (id)
ON DELETE CASCADE;

ALTER TABLE publications_contents
ADD FOREIGN KEY (publication_id)
REFERENCES publications (id)
ON DELETE CASCADE;

/* Publishers */
ALTER TABLE publishers
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Publications series */
ALTER TABLE publications_series
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Titles */
ALTER TABLE titles
ADD FOREIGN KEY (synopsis)
REFERENCES notes (id)
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (series_id)
REFERENCES titles_series (id)
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (parent)
REFERENCES titles (id)
ON DELETE SET DEFAULT;

ALTER TABLE titles
ADD FOREIGN KEY (language_id)
REFERENCES languages (id)
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Reviews */
ALTER TABLE reviews
ADD FOREIGN KEY (title_id)
REFERENCES titles (id)
ON DELETE CASCADE;

ALTER TABLE reviews
ADD FOREIGN KEY (review_id)
REFERENCES titles (id)
ON DELETE CASCADE;

/* Webpages */
ALTER TABLE webpages
ADD FOREIGN KEY (author_id)
REFERENCES authors (id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (publisher_id)
REFERENCES publishers (id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (title_id)
REFERENCES titles (id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (publications_series_id)
REFERENCES publications_series (id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (award_type_id)
REFERENCES awards_types (id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (title_series_id)
REFERENCES titles_series (id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (award_category_id, award_type_id)
REFERENCES awards_categories (id, type_id)
ON DELETE CASCADE;

/* Titles awards */
ALTER TABLE titles_awards
ADD FOREIGN KEY (title_id)
REFERENCES titles (id)
ON DELETE CASCADE;

ALTER TABLE titles_awards
ADD FOREIGN KEY (award_id)
REFERENCES awards (id)
ON DELETE CASCADE;

/* Titles tags */
ALTER TABLE titles_tags
ADD FOREIGN KEY (title_id)
REFERENCES titles (id)
ON DELETE CASCADE;

ALTER TABLE titles_tags
ADD FOREIGN KEY (tag_id)
REFERENCES tags (id)
ON DELETE CASCADE;

/* Titles series */
ALTER TABLE titles_series
ADD FOREIGN KEY (parent)
REFERENCES titles_series (id)
ON DELETE SET DEFAULT;

ALTER TABLE titles_series
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Awards */
ALTER TABLE awards
ADD FOREIGN KEY (category_id, type_id)
REFERENCES awards_categories (id, type_id)
ON DELETE SET NULL;

ALTER TABLE awards
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Awards categories */
ALTER TABLE awards_categories
ADD FOREIGN KEY (type_id)
REFERENCES awards_types (id)
ON DELETE CASCADE;

ALTER TABLE awards_categories
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Awards type */
ALTER TABLE awards_types
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/************************
 * Indexes
 ************************/
