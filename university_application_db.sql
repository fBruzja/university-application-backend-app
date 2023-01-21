drop database universityapplicationdb;
drop user universityapplication;

create user universityapplication with password 'password';
create database universityapplicationdb with template=template0 owner=universityapplication;
\connect universityapplicationdb
alter default privileges grant all on tables to universityapplication;
alter default privileges grant all on sequences to universityapplication;

create table ua_users(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null
);

create table ua_categories(
category_id integer primary key not null,
user_id integer not null,
title varchar(20) not null,
description varchar(50) not null
);
alter table ua_categories add constraint cat_users_fk
foreign key (user_id) references ua_users(user_id);

create sequence ua_users_seq increment 1 start 1;
create sequence ua_categories_seq increment 1 start 1;
