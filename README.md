# TainOracleJdbc
Oracle Jdbc Samples

```
----------------------------------
select
    table_name
    , column_name
from
    all_tab_columns
where 1=1
    and column_name like '%APPROVAL%'
;
--------------------------------------
select * from all_all_tables
;
--------------------------------------
select * from dba_tables
;
--------------------------------------
select * from all_objects where object_type = 'TABLE'
;
--------------------------------------
-- connected account
select * from tabs
;
--------------------------------------
select * from user_objects where object_type = 'TABLE'
;
--------------------------------------
select * from user_tables
;
--------------------------------------
select * from all_tab_comments where 1=1
;
--------------------------------------
select * from user_tab_comments
;
--------------------------------------
select * from cols where 1=1
;
--------------------------------------
select * from all_tab_columns where 1=1
;
--------------------------------------
select * from user_tab_columns
;
--------------------------------------
select * from user_col_comments where 1=1
;
--------------------------------------
--------------------------------------
--------------------------------------
select
    table_name
    , column_name
from
    all_tab_columns
where 1=1
    and table_name = 'ICS_PROJECT'
    and column_name like '%' || upper('pl_User_Name') || '%'
;

```

```
?
```
