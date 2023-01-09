insert into tbl_ship (id, name, type, owner, status, last_report_time, last_known_location)
values (5422, 'Red Chamber', 'RESEARCH_VESSEL', 'Red Grant', 20, '8525-12-26
00:00:00', 'Lumen Ziggurat');

insert into tbl_crew (id, name, ship_id)
values (2, 'Edalune Grant', 11),
       (3, 'Remi Dorian', 11),
       (4, 'Jun Lee', 11),
       (5, 'Kay Volan', 11);
