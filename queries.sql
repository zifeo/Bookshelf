
---- Deliverable 2

-- 1. For every year, output the year and the number of publications for said year.

SELECT
  DATE_PART('year', p.date_pub) AS year,
  COUNT(p.id) AS count
FROM publications p
GROUP BY year;

-- 2. Output the names of the ten authors with most publications.

SELECT a.name
FROM authors a
WHERE a.id IN (
  SELECT p.author_id
  FROM publications_authors p
  GROUP BY p.author_id
  ORDER BY COUNT(p.author_id) DESC
  LIMIT 10
);

-- 3. What are the names of the youngest and oldest authors to publish something in 2010

SELECT
  a.name,
  a.birth_date
FROM authors a
WHERE
  a.birth_date = (
    SELECT MAX(a.birth_date) AS max
    FROM publications_authors pa
      JOIN authors a ON pa.author_id = a.ID
    WHERE pa.publication_id IN (
      SELECT p.id
      FROM publications p
      WHERE DATE_PART('year', p.date_pub) = '2010'
    )
  )
  OR a.birth_date = (
    SELECT MIN(a.birth_date) AS min
    FROM publications_authors pa
      JOIN authors a ON pa.author_id = a.ID
    WHERE pa.publication_id IN (
      SELECT p.id
      FROM publications p
      WHERE DATE_PART('year', p.date_pub) = '2010'
    )
  );

-- 4. How many comics (graphic titles) have publications with less than 50 pages

SELECT COUNT(t.id)
FROM titles t
  INNER JOIN publications_contents pc ON t.id = pc.title_id
  INNER JOIN publications p ON pc.publication_id = p.id
WHERE
  t.graphic = 'YES'
  AND p.pages < 50;

-- 5. How many comics (graphic titles) have publications with less than 100 pages

SELECT COUNT(t.id)
FROM titles t
  INNER JOIN publications_contents pc ON t.id = pc.title_id
  JOIN publications p ON pc.publication_id = p.id
WHERE
  t.graphic = 'YES'
  AND p.pages < 100;

-- 6. How many comics (graphic titles) have publications with more (or equal) than 100 pages

SELECT COUNT(t.id)
FROM titles t
  JOIN publications_contents pc ON t.id = pc.title_id
  JOIN publications p ON pc.publication_id = p.id
WHERE
  t.graphic = 'YES'
  AND p.pages >= 100;

-- 7. For every publisher, calculate the average price of its published novels (the ones that have a dollar price).

SELECT
  pr.name,
  AVG(p.price)
FROM publications p
  INNER JOIN publishers pr ON p.publisher_id = pr.id
WHERE
  p.currency = '$'
  AND p.type = 'novel'
GROUP BY pr.name;

-- 8. What is the name of the author with the highest number of titles that are tagged as “science fiction”

SELECT a.name
FROM authors a
  INNER JOIN publications_authors pa ON a.id = pa.author_id
  INNER JOIN publications_contents pc ON pc.publication_id = pa.publication_id
  INNER JOIN titles_tags tt ON pc.title_id = tt.title_id
  INNER JOIN tags ON tt.tag_id = tags.id
WHERE tags.name LIKE '%science fiction%'
GROUP BY a.name
ORDER BY COUNT(DISTINCT pc.title_id) DESC
LIMIT 1;

-- 9. List the three most popular titles (i.e., the ones with the most awards and reviews).

SELECT t.title, count_awards, count_reviews
FROM titles t
  JOIN (
         SELECT ta.title_id, COUNT(ta.award_id) AS count_awards
         FROM titles_awards ta
         GROUP BY ta.title_id
       ) c ON c.title_id = t.id
  JOIN (
         SELECT r.title_id, COUNT(r.review_id) AS count_reviews
         FROM reviews r
         GROUP BY r.title_id
       ) d ON d.title_id = t.id
ORDER BY count_awards + count_reviews DESC
LIMIT 3;
-- JOIN, NULL cases

---- Deliverable 3

-- 10. Compute the average price per currency of the publications of the most popular title (i.e, the title with most
-- publications overall).

SELECT
  AVG(p.price),
  p.currency
FROM publications p
  JOIN publications_contents pc ON p.id = pc.publication_id
WHERE
  pc.title_id = (
    SELECT pc.title_id
    FROM publications_contents pc
    GROUP BY pc.title_id
    ORDER BY COUNT(pc.publication_id) DESC
    LIMIT 1
  )
  AND p.currency IS NOT NULL
GROUP BY p.currency;

-- 11. Output the names of the top ten title series with most awards.

SELECT ts.title
FROM titles t
  JOIN titles_awards ta ON t.id = ta.title_id
  JOIN titles_series ts ON t.series_id = ts.id
WHERE t.series_id IS NOT NULL
GROUP BY ts.title
ORDER BY COUNT(ta.award_id) DESC
LIMIT 10;

-- 12. Output the name of the author who has received the most awards after his/her death.

SELECT a.name
FROM authors a
  JOIN publications_authors pa ON a.id = pa.author_id
  JOIN publications_contents pc ON pa.publication_id = pc.publication_id
  JOIN titles_awards ta ON pc.title_id = ta.title_id
  JOIN awards aw ON ta.award_id = aw.id
WHERE
  a.death_date IS NOT NULL
  AND aw.date > a.death_date
GROUP BY a.name
ORDER BY COUNT(ta.award_id) DESC
LIMIT 1;

-- 13. For a given year, output the three publishers that published the most publications.

SELECT p.name
FROM publishers p
  JOIN publications pu ON p.id = pu.publisher_id
WHERE DATE_PART('year', pu.date_pub) = ?
GROUP BY p.name
ORDER BY COUNT(pu.id) DESC
LIMIT 3;

-- 14. Given an author, compute his/her most reviewed title(s).

SELECT t.title
FROM authors a
  INNER JOIN publications_authors pa ON pa.author_id = a.id
  INNER JOIN publications_contents pc ON pc.publication_id = pa.publication_id
  INNER JOIN titles t ON t.id = pc.title_id
  INNER JOIN reviews r ON r.title_id = t.id
WHERE a.id = ?
GROUP BY t.id
ORDER BY COUNT(DISTINCT r.review_id) DESC;

-- 15. For every language, find the top three title types with most translations.

SELECT
  r.name,
  r.type
FROM (
       SELECT
         l.name,
         t.type,
         ROW_NUMBER() OVER (PARTITION BY l.id ORDER BY COUNT(t.id) DESC) as row
       FROM languages l
         INNER JOIN titles_translators tt ON tt.language_id = l.id
         INNER JOIN titles t ON tt.title_id = t.id
       GROUP BY t.type, l.name, l.id
       ORDER BY COUNT(t.id) DESC
     ) r
WHERE r.row <= 3;

-- 16. For each year, compute the average number of authors per publisher.

SELECT DISTINCT
  pr.name,
  DATE_PART('year', p.date_pub) AS year,
  AVG(pa.author_id) AS avg
FROM publications p
  INNER JOIN publications_authors pa ON pa.publication_id = p.id
  INNER JOIN publishers pr ON pr.id = p.publisher_id
GROUP BY year, pr.id, pr.name;

-- 17. Find the publication series with most titles that have been given awards of “World Fantasy Award” type.

SELECT ps.name
FROM publications_series ps
  INNER JOIN publications p ON p.pub_series_id = ps.id
  INNER JOIN publications_contents pc ON pc.publication_id = p.id
  INNER JOIN titles_awards ta ON  ta.title_id = pc.title_id
  INNER JOIN awards a ON a.id = ta.award_id
  INNER JOIN awards_types at ON at.id = a.type_id
WHERE at.name = 'World Fantasy Award'
GROUP BY ps.id
ORDER BY COUNT(pc.title_id) DESC;

-- 18. For every award category, list the names of the three most awarded authors.

SELECT
  r.name,
  r.cat_name
FROM (
       SELECT
         a.name,
         ac.name AS cat_name,
         COUNT(DISTINCT aw.id) as count,
         ROW_NUMBER() OVER (PARTITION BY ac.id ORDER BY COUNT(DISTINCT aw.id) DESC) AS row
       FROM awards_categories ac
         INNER JOIN awards aw ON aw.category_id = ac.id
         INNER JOIN titles_awards ta ON ta.award_id = aw.id
         INNER JOIN publications_contents pc ON pc.title_id = ta.title_id
         INNER JOIN publications_authors pa ON pa.publication_id = pc.publication_id
         INNER JOIN authors a ON a.id = pa.author_id
       GROUP BY a.name, cat_name, ac.id, a.id
       ORDER BY count DESC
     ) r
WHERE r.row <= 3;

-- 19. Output the names of all living authors that have published at least one anthology from youngest to oldest.

SELECT DISTINCT a.name, a.birth_date
FROM authors a
  INNER JOIN publications_authors pa ON pa.author_id = a.id
  INNER JOIN publications p ON p.id = pa.publication_id
  INNER JOIN publications_contents pc ON pc.publication_id = p.id
  INNER JOIN titles t ON t.id = pc.title_id
WHERE
  t.type = 'anthology'
  AND a.death_date IS NULL
  AND a.birth_date IS NOT NULL
ORDER BY a.birth_date DESC;

-- 20. Compute the average number of publications per publication series (single result/number expected).

SELECT AVG(count.ncount)
FROM (
       SELECT COUNT(ps.name) as ncount
       FROM publications p
         INNER JOIN publications_series ps ON ps.id = p.pub_series_id
       GROUP BY ps.name
     ) count;

-- 21. Find the author who has reviewed the most titles.

SELECT a.name
FROM authors a
WHERE a.id = (
  SELECT pa.author_id
  FROM publications p
    INNER JOIN publications_authors pa ON pa.publication_id = p.id
    INNER JOIN publications_contents pc ON pc.publication_id = p.id
    INNER JOIN (
                 SELECT *
                 FROM titles t
                 WHERE t.type = 'review'
               ) AS rev ON rev.id = pc.title_id
  GROUP BY pa.author_id
  ORDER BY COUNT(*) DESC
  LIMIT 1
);
-- TODO rewrite

-- 22. For every language, list the three authors with the most translated titles of “novel” type.

SELECT
  r.name,
  r.auth_name
FROM (
       SELECT DISTINCT
         l.name,
         l.id,
         a.name AS auth_name,
         COUNT(DISTINCT t.id) AS count,
         ROW_NUMBER() OVER (PARTITION BY l.id ORDER BY COUNT(DISTINCT t.id) DESC) as row
       FROM languages l
         INNER JOIN titles_translators tt ON tt.language_id = l.id
         INNER JOIN titles t ON tt.title_id = t.id
         INNER JOIN publications_contents pc ON pc.title_id = t.id
         INNER JOIN publications_authors pa ON pa.publication_id = pc.publication_id
         INNER JOIN authors a ON a.id = pa.author_id
       WHERE t.type = 'novel'
       GROUP BY l.id, l.name, a.id, a.name
       ORDER BY count DESC
     ) r
WHERE r.row <= 3;

-- 23. Order the top ten authors whose publications have the largest pages per dollar ratio (considering all
-- publications of an author that have a dollar price).

SELECT a.name
FROM authors a
  INNER JOIN (
               SELECT pa.author_id
               FROM publications p
                 INNER JOIN publications_authors pa ON pa.publication_id = p.id
               WHERE
                 p.currency = '$'
                 AND p.pages != 0
                 AND p.price != 0
               GROUP BY pa.author_id, p.price, p.pages
               ORDER BY p.pages / p.price DESC
               LIMIT 10
             ) AS paa ON paa.author_id = a.id;
-- TODO rewrite

-- 24. For publications that have been awarded the Nebula award, find the top 10 with the most extensive web presence
-- (i.e, the highest number of author websites, publisher websites, publication series websites, and title series
-- websites in total)

SELECT e.title, COUNT(e.author_id) + COUNT(e.publisher_id) + COUNT(e.publications_series_id) + COUNT(e.title_series_id)
FROM webpages w
  INNER JOIN (
               SELECT DISTINCT
                 pc.publication_id AS publication_id,
                 ps.id AS publications_series_id,
                 p2.id AS publisher_id,
                 pa.author_id AS author_id,
                 ts.id AS title_series_id,
                 p.title AS title
               FROM publications_contents pc
                 INNER JOIN publications p ON p.id = pc.publication_id
                 INNER JOIN publishers p2 ON p2.id = p.publisher_id
                 INNER JOIN titles t ON t.id = pc.title_id
                 INNER JOIN titles_series ts ON ts.id = t.series_id
                 INNER JOIN publications_series ps ON ps.id = p.pub_series_id
                 INNER JOIN publications_authors pa ON pa.publication_id = pc.publication_id
                 INNER JOIN titles_awards ta ON ta.title_id = pc.title_id
                 INNER JOIN awards a ON a.id = ta.award_id
                 INNER JOIN awards_types at ON at.id = a.type_id
               WHERE at.name = 'Nebula Award'
             ) AS e ON e.author_id = w.author_id
                       OR e.publisher_id = w.publisher_id
                       OR e.publications_series_id = w.publications_series_id
                       OR e.title_series_id = w.title_series_id
GROUP BY (e.publication_id, e.title)
ORDER BY COUNT(e.author_id) + COUNT(e.publisher_id) + COUNT(e.publications_series_id) + COUNT(e.title_series_id) DESC
LIMIT 10;

-- COUNT DISTINCT
