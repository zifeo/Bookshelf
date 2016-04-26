SELECT A.name, COUNT(*) AS count
FROM PUBLICATIONS_AUTHORS P
INNER JOIN AUTHORS A ON P.author_id = A.id
GROUP BY A.name
ORDER BY count DESC
LIMIT 10;

SELECT DATE_PART('year', P.date_pub) AS year, COUNT(*) AS count
FROM PUBLICATIONS P
GROUP BY DATE_PART('year', P.date_pub)
ORDER BY year ASC;

SELECT A.name
FROM AUTHORS A
WHERE A.birth_date = (
SELECT MAX(A.birth_date) AS max
FROM PUBLICATIONS_AUTHORS PA
JOIN AUTHORS A ON PA.author_id = A.ID
WHERE PA.publication_id IN (
SELECT PUB.id
FROM PUBLICATIONS PUB
WHERE DATE_PART('year', PUB.date_pub) = '2010'
)) OR
A.birth_date = (
SELECT MIN(A.birth_date) AS max
FROM PUBLICATIONS_AUTHORS PA
JOIN AUTHORS A ON PA.author_id = A.ID
WHERE PA.publication_id IN (
SELECT PUB.id
FROM PUBLICATIONS PUB
WHERE DATE_PART('year', PUB.date_pub) = '2010'
));

SELECT a.name
FROM authors a
JOIN (
SELECT a.name AS n, COUNT(*) AS count_sf
FROM authors a
JOIN publications_authors pa   ON a.id = pa.author_id
JOIN publications p            ON pa.publication_id = p.id
JOIN publications_contents pc  ON pc.publication_id = p.id
JOIN titles t         ON pc.title_id = t.id
JOIN titles_tags tt      ON t.id = tt.title_id
JOIN tags           ON tt.tag_id = tags.id

WHERE tags.name LIKE '%sf%'
GROUP BY a.name
ORDER BY count_sf DESC
LIMIT 1)
C ON C.n = a.name;

SELECT COUNT(*)
FROM titles t
JOIN publications_contents AS pc ON t.id = pc.title_id
JOIN publications AS p ON pc.publication_id = p.id
WHERE t.graphic = 'YES'
AND p.Pages < 50;


SELECT COUNT(*)
FROM titles t
JOIN publications_contents AS pc ON t.id = pc.title_id
JOIN publications AS p ON pc.publication_id = p.id
WHERE t.graphic = 'YES'
AND p.Pages < 100;

SELECT COUNT(*)
FROM titles t
JOIN publications_contents AS pc ON t.id = pc.title_id
JOIN publications AS p ON pc.publication_id = p.id
WHERE t.graphic = 'YES'
AND p.Pages >= 100;


SELECT pr.name, AVG(p.price)
FROM publications p
JOIN publishers pr ON p.publisher_id = pr.id
WHERE p.currency = '$'
GROUP BY pr.name;

SELECT a.name
FROM authors a
JOIN (
SELECT a.name AS n, COUNT(*) AS count_sf
FROM authors a
JOIN publications_authors pa ON a.id = pa.author_id
JOIN publications p ON pa.publication_id = p.id
JOIN publications_contents pc ON pc.publication_id = p.id
JOIN titles t ON pc.title_id = t.id
JOIN titles_tags tt ON t.id = tt.title_id
JOIN tags ON tt.tag_id = tags.id
WHERE tags.name LIKE '%sf%'
OR tags.name LIKE '%science-fiction%'
GROUP BY a.name
ORDER BY count_sf DESC
LIMIT 1) c ON c.n = a.name;

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
LIMIT 3;
