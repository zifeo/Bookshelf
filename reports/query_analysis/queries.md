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
This query basically links the tables `authors` and `awards` by doing multiple joins with tables in between. Then it simply check if the author is dead and filter to keep only the awards that has been received after his death. Finally we order the final results on the number of awards that weren't filtered and keep only the first result, the one with most award.

### Indexes
Without any indexes the query runs in approximatly 700ms.


### Plan
The plan for query 12 is simple to understand. We see that it first does the join between `authors` and `publications_authors` then joins the resulting table with `publications_contents`, same thing for `titles_awards` and finally `awards`. At this point we still have 3987 rows. The aggregate operation is simply the `COUNT`, then the sort is on `a.name` and the limit keeps only one result.
![](query12.png)

## Query 16
### Description

### Indexes

### Plan
![](query16.png)

## Query 21
### Description

### Indexes

### Plan
![](query21.png)