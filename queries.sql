
---- Deliverable 2

-- 1. For every year, output the year and the number of publications for said year.

SELECT DATE_PART('year', p.date_pub) AS year, COUNT(*) AS count
FROM publications p
GROUP BY year
ORDER BY year; -- TODO order

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

-- TODO : review
SELECT a.name
FROM authors a
WHERE a.birth_date = (
  SELECT MAX(a.birth_date) AS max
  FROM publications_authors pa
    JOIN authors A ON pa.author_id = a.ID
  WHERE pa.publication_id IN (
    SELECT PUB.id
    FROM publications PUB
    WHERE DATE_PART('year', PUB.date_pub) = '2010'
  )) OR
      a.birth_date = (
        SELECT MIN(a.birth_date) AS max
        FROM publications_authors PA
          JOIN authors A ON PA.author_id = a.ID
        WHERE PA.publication_id IN (
          SELECT PUB.id
          FROM publications PUB
          WHERE DATE_PART('year', PUB.date_pub) = '2010'
        ));

-- 4. How many comics (graphic titles) have publications with less than 50 pages

SELECT COUNT(*)
FROM titles t
  JOIN publications_contents AS pc ON t.id = pc.title_id
  JOIN publications AS p ON pc.publication_id = p.id
WHERE t.graphic = 'YES'
      AND p.Pages < 50;

-- 5. How many comics (graphic titles) have publications with less than 100 pages

SELECT COUNT(*)
FROM titles t
  JOIN publications_contents AS pc ON t.id = pc.title_id
  JOIN publications AS p ON pc.publication_id = p.id
WHERE t.graphic = 'YES'
      AND p.Pages < 100;

-- 6. How many comics (graphic titles) have publications with more (or equal) than 100 pages

SELECT COUNT(*)
FROM titles t
  JOIN publications_contents AS pc ON t.id = pc.title_id
  JOIN publications AS p ON pc.publication_id = p.id
WHERE t.graphic = 'YES'
      AND p.Pages >= 100;

-- 7. For every publisher, calculate the average price of its published novels (the ones that have a dollar price).

SELECT pr.name, AVG(p.price)
FROM publications p
  JOIN publishers pr ON p.publisher_id = pr.id
WHERE p.currency = '$'
GROUP BY pr.name;

-- 8. What is the name of the author with the highest number of titles that are tagged as “science fiction”

SELECT a.name AS name
FROM authors a
  INNER JOIN publications_authors pa ON a.id = pa.author_id
  INNER JOIN publications_contents pc ON pc.publication_id = pa.publication_id
  INNER JOIN titles_tags tt ON pc.title_id = tt.title_id
  INNER JOIN tags ON tt.tag_id = tags.id
WHERE tags.name LIKE '%science fiction%'
GROUP BY a.name
ORDER BY COUNT(*) DESC
LIMIT 1; -- TODO : join vs where clause, distinct titles

-- 9. List the three most popular titles (i.e., the ones with the most awards and reviews).

SELECT t.title, count_awards + count_reviews as total
FROM titles t
  JOIN(
        SELECT ta.title_id, COUNT(ta.award_id) AS count_awards
        FROM titles_awards ta
        GROUP BY ta.title_id) c ON c.title_id = t.id
  JOIN(
        SELECT r.title_id, COUNT(r.review_id) AS count_reviews
        FROM reviews r
        GROUP BY r.title_id) d ON d.title_id = t.id
ORDER BY count_awards + count_reviews DESC
LIMIT 3; -- TODO : Alllllllo  twice the wizard

---- Deliverable 3

-- 10. Compute the average price per currency of the publications of the most popular title (i.e, the title with most publications overall).

SELECT AVG(p.price), p.currency
FROM publications p
  JOIN publications_contents pc ON p.id = pc.publication_id
WHERE pc.title_id = (
  SELECT pc.title_id
  FROM publications_contents pc
  GROUP BY pc.title_id
  ORDER BY COUNT(pc.publication_id) DESC
  LIMIT 1
)
GROUP BY p.currency;

-- 11. Output the names of the top ten title series with most awards.

SELECT ts.title
FROM titles t
  JOIN titles_awards ta ON t.id = ta.title_id
  JOIN titles_series ts ON t.series_id = ts.id
WHERE t.series_id IS NOT NULL
GROUP BY ts.title
ORDER BY COUNT(*) DESC
LIMIT 10;

-- 12. Output the name of the author who has received the most awards after his/her death.

SELECT
  a.name
FROM authors a
  JOIN publications_authors pa
    ON a.id = pa.author_id
  JOIN publications_contents pc
    ON pa.publication_id = pc.publication_id
  JOIN titles_awards ta
    ON pc.title_id = ta.title_id
  JOIN awards aw
    ON ta.award_id = aw.id
WHERE
  a.death_date IS NOT NULL AND
  aw.date > a.death_date
GROUP BY
  a.name
ORDER BY
  COUNT(*) DESC;

-- 13. For a given year, output the three publishers that published the most publications.

SELECT
  p.name
FROM
  publishers p
  JOIN publications pu
    ON p.id = pu.publisher_id
WHERE
  DATE_PART('year', pu.date_pub) = 2000
GROUP BY
  p.name
ORDER BY
  COUNT(*) DESC
LIMIT
  3; -- TODO : given year

-- 14. Given an author, compute his/her most reviewed title(s).

SELECT
  *
FROM
  authors a
  INNER JOIN publications_authors pa
    ON pa.author_id = a.id
INNER JOIN publications p
    ON p.id = pa.publication_id
INNER JOIN publications_contents pc
    ON pc.publication_id = p.id
INNER JOIN titles t
  ON t.id = pc.publication_id
INNER JOIN reviews r
    ON r.title_id = t.id
GROUP BY
  t.id  ;

-- TODO : given year

-- 15. For every language, find the top three title types with most translations.

SELECT tt.language, COUNT(*)
FROM titles t
  INNER JOIN titles_translators tt
    ON tt.title_id = t.id
GROUP BY tt.language; -- TODO : for every language 

-- 16. For each year, compute the average number of authors per publisher.



-- 17. Find the publication series with most titles that have been given awards of “World Fantasy Award” type.



-- 18. For every award category, list the names of the three most awarded authors.



-- 19. Output the names of all living authors that have published at least one anthology from youngest to oldest.



-- 20. Compute the average number of publications per publication series (single result/number expected).



-- 21. Find the author who has reviewed the most titles.



-- 22. For every language, list the three authors with the most translated titles of “novel” type.



-- 23. Order the top ten authors whose publications have the largest pages per dollar ratio (considering all publications of an author that have a dollar price).



-- 24. For publications that have been awarded the Nebula award, find the top 10 with the most extensive web presence (i.e, the highest number of author websites, publication websites, publisher websites, publication series websites, and title series websites in total)



