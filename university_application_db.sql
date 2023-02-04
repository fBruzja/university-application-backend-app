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
password text not null,
major varchar(30) not null,
minor varchar(30) not null,
courses text[],
profile_picture varchar(10000),
notifications boolean,
friends integer[]
);

create table ua_categories(
category_id integer primary key not null,
user_id integer not null,
title varchar(20) not null,
description varchar(50) not null,
course_time varchar(50) not null,
location varchar(50) not null,
attendees text[] not null,
);

CREATE TABLE IF NOT EXISTS ua_comments
(
    id integer primary key not null,
    content character varying(6000),
    author character varying(6000),
    likes integer,
    course integer,
    likedby integer[]
)

CREATE TABLE IF NOT EXISTS friendship
(
    friendship_id integer primary key NOT NULL,
    user_id integer,
    friend_id integer,
    status character varying(100),
    notified boolean
);

create sequence ua_users_seq increment 1 start 1;
create sequence ua_categories_seq increment 1 start 1;
create sequence ua_comments_seq increment 1 start 1;
create sequence friendship_seq increment 1 start 1;
