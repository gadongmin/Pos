-- ----------------------------------------------------
-- pos 
-- ----------------------------------------------------
drop table category;
drop table menu;
drop table orders;

show databases;

-- 테이블 리스트 조회 -------------------------------------------
show tables;


-- 테이블 만들기
create table category(
	category_id 	int 			primary key 		auto_increment
    ,emoji			varchar(10) 	
    ,name			varchar(100)	
    ,explanation	varchar(500)
);


create table menu(
	menu_id			int				primary key 		auto_increment
    ,name	 		varchar(100)	
    ,price			int
	,category_id 	int
	,constraint menu_fk foreign key(category_id)
    references category(category_id)
);

create table orders(
	order_id 		int				primary key 		auto_increment
    ,quantity		int 		
    ,table_num 		int
    ,ispaid 		boolean
    ,order_date 	date
    ,menu_id		int		
	,constraint orders_fk foreign key(menu_id)
    references menu(menu_id)
    
);

select * from category;
select * from menu;
select * from orders;


select *
from orders o, menu m, category c
where o.menu_id = m.menu_id
and m.category_id = c.category_id
;