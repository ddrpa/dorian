-- liquibase formatted sql

-- changeset yufan:20221223_1607
-- comment 建立样例表
create table tbl_ship
(
    id                  int auto_increment
        primary key,
    last_known_location varchar(127) not null,
    last_report_time    datetime(6)  not null,
    name                varchar(63)  not null,
    owner               varchar(63)  not null,
    status              tinyint      not null,
    type                varchar(31)  not null
);
create table tbl_crew
(
    id      int auto_increment
        primary key,
    name    varchar(63) not null,
    ship_id int         not null
);
-- rollback drop table tbl_ship tbl_crew;