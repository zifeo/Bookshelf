/************************
 * Entities
 ************************/

CREATE TABLE authors
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(70) NOT NULL,
  legal_name VARCHAR(70) NOT NULL,
  last_name VARCHAR(70),
  pseudonyme VARCHAR(70) NOT NULL,
  ------------------------ ?????????????
  birthplace VARCHAR(200),
  birthdate DATE,
  deathdate DATE,
  email VARCHAR(150) UNIQUE,
  image VARCHAR(200),
  language_id INT, -- fk
  note_id INT -- fk
);

CREATE TABLE publications
(
  id INT PRIMARY KEY NOT NULL,
  title VARCHAR(150) NOT NULL,
  date_pub DATE NOT NULL,
  publisher_id INT NOT NULL, -- fk
  pages INT,
  packaging_type VARCHAR(50) NOT NULL,
  type VARCHAR(30) NOT NULL CHECK (
    VALUE IN ('ANTHOLOGY', 'COLLECTION', 'MAGAZINE', 'NONFICTION', 'NOVEL', 'OMNIBUS', 'FANZINE', 'CHAPBOOK')
  ),
  isbn INT UNIQUE,
  cover VARCHAR(200),
  price FLOAT,
  currency VARCHAR(5) CHECK (
    VALUE IN ('€', '$', '£', 'CH')
  ),
  pub_series_id INT, -- fk
  pub_series_num INT,
  note_id INT -- fk
);

CREATE TABLE titles
(
  id INT PRIMARY KEY NOT NULL,
  title VARCHAR(150) NOT NULL,
  translator VARCHAR(100),
  synopsis INT, -- fk
  note_id INT, -- fk
  series_id INT, -- fk
  series_num INT,
  story_length VARCHAR(10) CHECK (
    VALUE IN ('NOVELLA', 'SHORTSTORY', 'JUVENILE_FICTION', 'NOVELIZATION', 'SHORT_FICTION')
  ),
  type VARCHAR(30) CHECK (
    VALUE IN ('ANTHOLOGY', 'BACKCOVERART', 'COLLECTION', 'COVERART', 'INTERIORART', 'EDITOR', 'ESSAY', 'INTERVIEW',
              'NOVEL', 'NONFICTION', 'OMNIBUS', 'POEM', 'REVIEW', 'SERIAL', 'SHORTFICTION', 'CHAPBOOK')
  ),
  parent INT NOT NULL DEFAULT 0, -- fk, 0 on not defined
  language_id INT, -- fk
  graphic BOOLEAN NOT NULL
);

CREATE TABLE languages
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL,
  code CHAR(3) NOT NULL UNIQUE,
  script BOOLEAN
);

CREATE TABLE notes
(
  id INT PRIMARY KEY NOT NULL,
  note TEXT NOT NULL -- None ?
);

CREATE TABLE webpages
(
  id INT PRIMARY KEY NOT NULL,
  author_id INT, -- fk
  publisher_id INT, -- fk
  title_id INT, -- fk
  url VARCHAR(300) NOT NULL UNIQUE,
  publications_series_id INT, -- fk
  award_type_id INT, -- fk
  title_series_id INT, -- fk
  award_category_id INT -- fk
);

CREATE TABLE tags
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE title_series
(
  id INT PRIMARY KEY NOT NULL,
  title VARCHAR(150) NOT NULL,
  parent INT DEFAULT 0, -- fk
  note_id INT -- fk
);

CREATE TABLE awards
(
  id INT PRIMARY KEY NOT NULL,
  title VARCHAR(100) NOT NULL,
  date_award DATE NOT NULL,
  category_id INT NOT NULL, -- fk
  note_id INT -- fk
);

CREATE TABLE awards_categories
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(200) NOT NULL,
  type_id INT NOT NULL, -- fk
  order INT,
  note_id INT -- fk
);

CREATE TABLE awards_types
(
  id INT PRIMARY KEY NOT NULL,
  code CHAR(2) UNIQUE,
  name VARCHAR(100) NOT NULL,
  note_id INT, -- fk
  awarded_by VARCHAR(250) NOT NULL,
  awarded_for INT NOT NULL, --- REFERENCES ?
  short_name VARCHAR(100) NOT NULL UNIQUE,
  poll BOOLEAN NOT NULL,
  non_genre BOOLEAN NOT NULL -- type ?
);

CREATE TABLE publishers
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(100) NOT NULL,
  note_id INT -- fk
);

CREATE TABLE publications_series
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(100) NOT NULL,
  note_id INT -- fk
);

/************************
 * Relations
 ************************/

CREATE TABLE publications_authors
(
  publication_id INT NOT NULL, -- fk
  author_id INT NOT NULL, -- fk
  CONSTRAINT PK PRIMARY KEY (publication_id, author_id)
);

CREATE TABLE titles_awards
(
  title_id INT NOT NULL, -- fk
  award_id INT NOT NULL, -- fk
  CONSTRAINT PK PRIMARY KEY (title_id, award_id)
);

CREATE TABLE titles_tags
(
  title_id INT NOT NULL, -- fk
  tag_id INT NOT NULL, -- fk
  CONSTRAINT PK PRIMARY KEY (title_id, tag_id)
);

CREATE TABLE reviews
(
  title_id INT NOT NULL, -- fk
  review_id INT NOT NULL, -- fk
  CONSTRAINT PK PRIMARY KEY (title_id, review_id)
);

CREATE TABLE publications_contents
(
  title_id INT NOT NULL, -- fk
  publication_id INT NOT NULL, -- fk
  CONSTRAINT PK PRIMARY KEY (title_id, publication_id)
);

/************************
 * Foreign keys
 ************************/

/* Authors */
ALTER TABLE authors
ADD FOREIGN KEY (language_id)
REFERENCES languages(id)
ON DELETE SET NULL;

ALTER TABLE authors
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Publications authors s*/
ALTER TABLE publications_authors
ADD FOREIGN KEY (publication_id)
REFERENCES publications(id)
ON DELETE CASCADE;

ALTER TABLE publications_authors
ADD FOREIGN KEY (author_id)
REFERENCES authors(id)
ON DELETE CASCADE;

/* Publications */
ALTER TABLE publications
ADD FOREIGN KEY (publisher_id)
REFERENCES publishers(id)
ON DELETE SET NULL;

ALTER TABLE publications
ADD FOREIGN KEY (publications_series_id)
REFERENCES publications_series(id)
ON DELETE SET NULL;

ALTER TABLE publications
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Publications contents */
ALTER TABLE publications_contents
ADD FOREIGN KEY (title_id)
REFERENCES titles(id)
ON DELETE CASCADE;

ALTER TABLE publications_contents
ADD FOREIGN KEY (publication_id)
REFERENCES publications(id)
ON DELETE CASCADE;

/* Publishers */
ALTER TABLE publishers
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Publications series */
ALTER TABLE publications_series
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Titles */
ALTER TABLE titles
ADD FOREIGN KEY (synopsis)
REFERENCES notes(id)
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (series_id)
REFERENCES title_series(id)
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (parent)
REFERENCES titles(id)
ON DELETE SET DEFAULT;

ALTER TABLE titles
ADD FOREIGN KEY (language_id)
REFERENCES languages(id)
ON DELETE SET NULL;

ALTER TABLE titles
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Reviews */
ALTER TABLE reviews
ADD FOREIGN KEY (title_id)
REFERENCES titles(id)
ON DELETE CASCADE;

ALTER TABLE reviews
ADD FOREIGN KEY (review_id)
REFERENCES titles(id)
ON DELETE CASCADE;

/* Webpages */
ALTER TABLE webpages
ADD FOREIGN KEY (author_id)
REFERENCES authors(id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (publisher_id)
REFERENCES publishers(id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (title_id)
REFERENCES titles(id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (publications_series_id)
REFERENCES publications_series(id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (award_type_id)
REFERENCES awards_types(id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (title_series_id)
REFERENCES title_series(id)
ON DELETE CASCADE;

ALTER TABLE webpages
ADD FOREIGN KEY (award_category_id)
REFERENCES awards_categories(id)
ON DELETE CASCADE;

/* Titles awards */
ALTER TABLE titles_awards
ADD FOREIGN KEY (title_id)
REFERENCES titles(id)
ON DELETE CASCADE;

ALTER TABLE titles_awards
ADD FOREIGN KEY (award_id)
REFERENCES awards(id)
ON DELETE CASCADE;

/* Titles tags */
ALTER TABLE titles_tags
ADD FOREIGN KEY (title_id)
REFERENCES titles(id)
ON DELETE CASCADE;

ALTER TABLE titles_tags
ADD FOREIGN KEY (tag_id)
REFERENCES tags(id)
ON DELETE CASCADE;

/* Titles series */
ALTER TABLE title_series
ADD FOREIGN KEY (parent)
REFERENCES title_series(id)
ON DELETE SET DEFAULT;

ALTER TABLE title_series
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Awards */
ALTER TABLE awards
ADD FOREIGN KEY (category_id)
REFERENCES awards_categories(id)
ON DELETE SET NULL;

ALTER TABLE awards
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Awards categories */
ALTER TABLE awards_categories
ADD FOREIGN KEY (type_id)
REFERENCES awards_types(id)
ON DELETE SET NULL;

ALTER TABLE awards_categories
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/* Awards type */
ALTER TABLE awards_types
ADD FOREIGN KEY (note_id)
REFERENCES notes(id)
ON DELETE SET NULL;

/************************
 * Indexes
 ************************/
