/************************
 * Entities
 ************************/

DROP TABLE IF EXISTS authors CASCADE;
CREATE TABLE authors
(
  id          INT PRIMARY KEY NOT NULL,
  name        VARCHAR(256)    NOT NULL,
  legal_name  VARCHAR(128),
  last_name   VARCHAR(128),
  pseudonym   INT, -- fk
  birth_place VARCHAR(128),
  birth_date  DATE,
  death_date  DATE,
  email       VARCHAR(128),
  image       VARCHAR(256),
  language_id INT, -- fk
  note_id     INT -- fk
);

DROP TABLE IF EXISTS publications CASCADE;
CREATE TABLE publications
(
  id             INT PRIMARY KEY  NOT NULL,
  title          VARCHAR(2048)     NOT NULL,
  date_pub       DATE             NOT NULL,
  publisher_id   INT, -- fk
  pages          INT,
  preface_pages  INT,
  packaging_type VARCHAR(32),
  type           VARCHAR(32),
  isbn           BIGINT,
  cover          VARCHAR(256),
  price          FLOAT,
  currency       VARCHAR(32),
  pub_series_id  INT, -- fk
  pub_series_num INT,
  note_id        INT -- fk
);

DROP TABLE IF EXISTS titles CASCADE;
CREATE TABLE titles
(
  id           INT PRIMARY KEY NOT NULL,
  title        VARCHAR(2048)    NOT NULL,
  synopsis     INT, -- fk
  note_id      INT, -- fk
  series_id    INT, -- fk
  series_num   INT,
  story_length VARCHAR(2048),
  type         VARCHAR(32),
  parent       INT, -- fk
  language_id  INT, -- fk
  graphic      BOOLEAN
);

DROP TABLE IF EXISTS translators CASCADE;
CREATE TABLE translators
(
  id          INT PRIMARY KEY  NOT NULL,
  name        VARCHAR(128)     NOT NULL
);

DROP TABLE IF EXISTS languages CASCADE;
CREATE TABLE languages
(
  id     INT PRIMARY KEY NOT NULL,
  name   VARCHAR(32)    NOT NULL,
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
  name VARCHAR(32)    NOT NULL
);

DROP TABLE IF EXISTS titles_series CASCADE;
CREATE TABLE titles_series
(
  id      INT PRIMARY KEY NOT NULL,
  title   VARCHAR(256)    NOT NULL,
  parent  INT, -- fk
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
  name    VARCHAR(128)    NOT NULL,
  type_id INT             NOT NULL, -- fk
  ordr    INT,
  note_id INT, -- fk
  PRIMARY KEY (id, type_id)
);

DROP TABLE IF EXISTS awards_types CASCADE;
CREATE TABLE awards_types
(
  id          INT PRIMARY KEY NOT NULL,
  code        CHAR(2),
  name        VARCHAR(128)    NOT NULL,
  note_id     INT, -- fk
  awarded_by  VARCHAR(256)    NOT NULL,
  awarded_for VARCHAR(256)    NOT NULL,
  short_name  VARCHAR(32)    NOT NULL UNIQUE,
  poll        BOOLEAN         NOT NULL,
  non_genre   BOOLEAN         NOT NULL
);

DROP TABLE IF EXISTS publishers CASCADE;
CREATE TABLE publishers
(
  id      INT PRIMARY KEY NOT NULL,
  name    VARCHAR(512)    NOT NULL,
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

DROP TABLE IF EXISTS titles_translators CASCADE;
CREATE TABLE titles_translators
(
  title_id           INT NOT NULL, -- fk
  translator_id      INT NOT NULL, -- fk
  year               INT NOT NULL,
  language           VARCHAR(64) NOT NULL,
  CONSTRAINT pk_titles_translators PRIMARY KEY (title_id, translator_id, year, language)
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
 * Dead foreign keys values
 ************************/

UPDATE authors SET pseudonym = NULL
WHERE id IN (SELECT a1.id
             FROM authors a1
               LEFT JOIN authors a2
                 ON a1.pseudonym = a2.id
             WHERE a2.id IS NULL
                   AND a1.pseudonym IS NOT NULL);

UPDATE authors SET note_id = NULL
WHERE id IN (SELECT a1.id
             FROM authors a1
               LEFT JOIN notes a2
                 ON a1.note_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.note_id IS NOT NULL);

UPDATE publications SET note_id = NULL
WHERE id IN (SELECT a1.id
             FROM publications a1
               LEFT JOIN notes a2
                 ON a1.note_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.note_id IS NOT NULL);

DELETE FROM publications_authors
WHERE author_id IN (SELECT a1.author_id
                    FROM publications_authors a1
                      LEFT JOIN authors a2
                        ON a1.author_id = a2.id
                    WHERE a2.id IS NULL
                          AND a1.author_id IS NOT NULL);

DELETE FROM publications_authors
WHERE publication_id IN (SELECT a1.publication_id
                         FROM publications_authors a1
                           LEFT JOIN publications a2
                             ON a1.publication_id = a2.id
                         WHERE a2.id IS NULL
                               AND a1.publication_id IS NOT NULL);

DELETE FROM publications_contents
WHERE title_id IN (SELECT a1.title_id
                   FROM publications_contents a1
                     LEFT JOIN titles a2
                       ON a1.title_id = a2.id
                   WHERE a2.id IS NULL
                         AND a1.title_id IS NOT NULL);

DELETE FROM publications_contents
WHERE publication_id IN (SELECT a1.publication_id
                         FROM publications_contents a1
                           LEFT JOIN publications a2
                             ON a1.publication_id = a2.id
                         WHERE a2.id IS NULL
                               AND a1.publication_id IS NOT NULL);

UPDATE publishers SET note_id = NULL
WHERE id IN (SELECT a1.id
             FROM publishers a1
               LEFT JOIN notes a2
                 ON a1.note_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.note_id IS NOT NULL);

UPDATE titles SET note_id = NULL
WHERE id IN (SELECT a1.id
             FROM titles a1
               LEFT JOIN notes a2
                 ON a1.note_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.note_id IS NOT NULL);

UPDATE titles SET synopsis = NULL
WHERE id IN (SELECT a1.synopsis
             FROM titles a1
               LEFT JOIN notes a2
                 ON a1.synopsis = a2.id
             WHERE a2.id IS NULL
                   AND a1.synopsis IS NOT NULL);

UPDATE titles SET synopsis = NULL
WHERE id IN (SELECT a1.synopsis
             FROM titles a1
               LEFT JOIN notes a2
                 ON a1.synopsis = a2.id
             WHERE a2.id IS NULL
                   AND a1.synopsis IS NOT NULL);

UPDATE titles SET synopsis = NULL
WHERE id IN (SELECT a1.id
             FROM titles a1
               LEFT JOIN notes a2
                 ON a1.synopsis = a2.id
             WHERE a2.id IS NULL
                   AND a1.synopsis IS NOT NULL);

UPDATE titles SET parent = NULL
WHERE id IN (SELECT a1.id
             FROM titles a1
               LEFT JOIN titles a2
                 ON a1.parent = a2.id
             WHERE a2.id IS NULL
                   AND a1.parent IS NOT NULL);

UPDATE titles SET language_id = NULL
WHERE id IN (SELECT a1.id
             FROM titles a1
               LEFT JOIN languages a2
                 ON a1.language_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.language_id IS NOT NULL);

DELETE FROM webpages
WHERE id IN (SELECT a1.id
             FROM webpages a1
               LEFT JOIN publishers a2
                 ON a1.publisher_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.publisher_id IS NOT NULL);

DELETE FROM webpages
WHERE id IN (SELECT a1.id
             FROM webpages a1
               LEFT JOIN titles a2
                 ON a1.title_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.title_id IS NOT NULL);

DELETE FROM webpages
WHERE id IN (SELECT a1.id
             FROM webpages a1
               LEFT JOIN publications_series a2
                 ON a1.publications_series_id = a2.id
             WHERE a2.id IS NULL
                   AND a1.publications_series_id IS NOT NULL);

DELETE FROM titles_awards
WHERE award_id IN (SELECT a1.award_id
                   FROM titles_awards a1
                     LEFT JOIN awards a2
                       ON a1.award_id = a2.id
                   WHERE a2.id IS NULL
                         AND a1.award_id IS NOT NULL);

DELETE FROM titles_tags
WHERE tag_id IN (SELECT a1.tag_id
                 FROM titles_tags a1
                   LEFT JOIN tags a2
                     ON a1.tag_id = a2.id
                 WHERE a2.id IS NULL
                       AND a1.tag_id IS NOT NULL);

DELETE FROM titles_tags
WHERE title_id IN (SELECT a1.title_id
                   FROM titles_tags a1
                     LEFT JOIN titles a2
                       ON a1.title_id = a2.id
                   WHERE a2.id IS NULL
                         AND a1.title_id IS NOT NULL);

UPDATE titles_series SET parent = NULL
WHERE parent IN (SELECT a1.parent
                 FROM titles_series a1
                   LEFT JOIN titles a2
                     ON a1.parent = a2.id
                 WHERE a2.id IS NULL
                       AND a1.parent IS NOT NULL);

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
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (language_id)
REFERENCES languages (id)
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (note_id)
REFERENCES notes (id)
ON DELETE SET NULL;

/* Translators */
ALTER TABLE titles_translators
ADD FOREIGN KEY (title_id)
REFERENCES titles (id)
ON DELETE CASCADE;

ALTER TABLE titles_translators
ADD FOREIGN KEY (translator_id)
REFERENCES translators (id)
ON DELETE CASCADE;

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
ON DELETE SET NULL;

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
 * Futher constraints
 ************************/

ALTER TABLE webpages
ADD CONSTRAINT webpages_no_full_null CHECK (
  author_id IS NOT NULL OR
  publisher_id IS NOT NULL OR
  title_id IS NOT NULL OR
  publications_series_id IS NOT NULL OR
  award_type_id IS NOT NULL OR
  title_series_id IS NOT NULL OR
  award_category_id IS NOT NULL
);

CREATE SEQUENCE titles_id_seq;
SELECT setval('titles_id_seq', (SELECT id FROM titles ORDER BY id DESC LIMIT 1));
ALTER TABLE titles ALTER COLUMN id SET DEFAULT nextval('titles_id_seq');
ALTER SEQUENCE titles_id_seq OWNED BY titles.id;

CREATE SEQUENCE notes_id_seq;
SELECT setval('notes_id_seq', (SELECT id FROM notes ORDER BY id DESC LIMIT 1));
ALTER TABLE notes ALTER COLUMN id SET DEFAULT nextval('notes_id_seq');
ALTER SEQUENCE notes_id_seq OWNED BY notes.id;
