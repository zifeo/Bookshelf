## Query 12
### Description
*Output the name of the author who has received the most awards after his/her death.*

```
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
```
This query basically links the tables `authors` and `awards` by doing multiple joins with tables in between. Then it simply checks if the author is dead and filter to keep only the awards that has been received after his death. Finally we order the final results on the number of awards that weren't filtered and keep only the first result, the one with most award.

### Running time & optimization
Without any indexes the query runs in approximatly 700ms. This time is mainly distributed between the full scans of tables and joins. We already spent 99.96% of the time after all the joins are done. So even if we create an index on fields used in the where clauses or aggregate actions, we won't see any improvement.


### Plan
The plan for query 12 is simple to understand. We see that it first does the join between `authors` and `publications_authors` then joins the resulting table with `publications_contents`, same thing for `titles_awards` and finally `awards`. At this point we still have 3987 rows. The aggregate operation is simply the `COUNT`, then the sort is on `a.name` and the limit keeps only one result.
We can see that the time after the joins is 59997 and at the end of the execution 60017. The time of the joins highly depends on the number of processed rows.
![](query12.png)

## Query 16
### Description
*For each year, compute the average number of authors per publisher.*

```
SELECT
  r.name,
  r.year,
  AVG(r.count)
FROM (
       SELECT DISTINCT
         pr.name,
         DATE_PART('year', p.date_pub) AS year,
         COUNT(DISTINCT pa.author_id) as count
       FROM publications p
         INNER JOIN publications_authors pa ON pa.publication_id = p.id
         INNER JOIN publishers pr ON pr.id = p.publisher_id
       GROUP BY pr.id, p.date_pub
     ) r
GROUP BY r.year, r.name;
```
This query uses a subquery for it's FROM clause to select for each year and for each publisher, the number of authors per publisher. Then the main query simply group by the year and the publisher and compute the average number of author.
### Running time & optimization
A few runs of this query showed an average running time of 2,2 seconds. The larger part of the time is spent in the subquery.


### Plan
![](query16.png)

## Query 21
### Description

### Running time & optimization

### Plan
![](query21.png)