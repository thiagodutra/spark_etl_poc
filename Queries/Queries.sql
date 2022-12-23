select directors.name as name,
    sum(revenue) as total_revenue,
    round(avg(vote_average), 2) as rating
from  movies as mv
join directors
    on mv.director_id = directors.id
group by name
order by total_revenue desc;

select * from movies;