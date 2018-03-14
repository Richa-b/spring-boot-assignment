/**
 * CREATE Script for init of DB
 */

 -- Create drivers in user table  --Decoded Password for all users is Test@123
INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (1, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver01',now());
INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (2, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver02',now());
INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (3, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver03',now());


INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (4, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver04',now());
INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (5, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver05',now());
INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (6, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver06',now());
INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (7, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver07',now());
INSERT INTO user (id,date_created, deleted, password, username,last_updated) VALUES (8, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'driver08',now());

-- Create 3 admin users
insert into user (id, date_created, deleted, password, username,last_updated) values (9, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'admin01',now());
insert into user (id, date_created, deleted, password, username,last_updated) values (10, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'admin02',now());
insert into user (id, date_created, deleted, password, username,last_updated) values (11, now(), false, '$2a$10$Yx3wNJnXw8dv7B7mSvgw7uKbzI4wKbzIsOMBtwF3mU5S7JHgxiXwS', 'admin03',now());


-- Create 3 roles
insert into role (id, date_created, deleted, last_updated,authority) values (1, now(), false, now(),'ROLE_ADMIN');
insert into role (id, date_created, deleted,  last_updated,authority) values (2, now(), false, now(),'ROLE_DRIVER');
insert into role (id, date_created, deleted, last_updated, authority) values (3, now(), false,now(), 'ROLE_USER');


--- insert Into User Role Table

--admin role user mapping
INSERT  INTO user_role (user_id,role_id) VALUES (9,1);
INSERT  INTO user_role (user_id,role_id) VALUES (10,1);
INSERT  INTO user_role (user_id,role_id) VALUES (11,1);

--Driver role user Mapping
INSERT  INTO user_role (user_id,role_id) VALUES (1,2);
INSERT  INTO user_role (user_id,role_id) VALUES (2,2);
INSERT  INTO user_role (user_id,role_id) VALUES (3,2);
INSERT  INTO user_role (user_id,role_id) VALUES (4,2);
INSERT  INTO user_role (user_id,role_id) VALUES (5,2);
INSERT  INTO user_role (user_id,role_id) VALUES (6,2);
INSERT  INTO user_role (user_id,role_id) VALUES (7,2);
INSERT  INTO user_role (user_id,role_id) VALUES (8,2);


-- Create 3 OFFLINE drivers

insert into driver (id, online_status) values (1,  'OFFLINE');
insert into driver (id, online_status) values (2,  'OFFLINE');
insert into driver (id, online_status) values (3, 'OFFLINE');

-- Create 3 ONLINE drivers

insert into driver (id, online_status) values (4, 'ONLINE');
insert into driver (id, online_status) values (5,  'ONLINE');
insert into driver (id, online_status) values (6, 'ONLINE');



-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, online_status)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127',now(), 'OFFLINE');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, online_status)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127',now(),'ONLINE');


-- Create 3 Manufacturers
INSERT INTO manufacturer(id,name,date_created, deleted,created_by,last_updated) VALUES (1,'Hyundai',now(),FALSE ,'DUMMY_USER',now());
INSERT INTO manufacturer(id,name,date_created, deleted,created_by,last_updated) VALUES (2,'Mahindra',now(),FALSE ,'DUMMY_USER',now());
INSERT INTO manufacturer(id,name,date_created, deleted,created_by,last_updated) VALUES (3,'Suzuki',now(),FALSE ,'DUMMY_USER',now());


-- Create 3 Cars
INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated) VALUES
(1,'Swift','Black','MH-15-AB-1234' , 5,false,3,'PETROL','MANUAL','SMALL',3,now(),FALSE ,'DUMMY_USER',now());

INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated) VALUES
(2,'i30','White','AP-19-AK-2804',6,TRUE ,4,'DIESEL','AUTOMATIC','MEDIUM' ,1,now(),FALSE ,'DUMMY_USER',now());

INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated) VALUES
(3,'Scorpio','White','MH-41-UB-8192',8,false,5,'DIESEL','MANUAL','SUV' ,2,now(),FALSE ,'DUMMY_USER',now());


-- Cars With Driver Associated
INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated,driver_id) VALUES
(4,'Swift','Black','MH-15-AB-1568' , 5,false,3,'ELECTRIC','AUTOMATIC','SMALL',1,now(),FALSE ,'DUMMY_USER',now(),4);

INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated,driver_id) VALUES
(5,'i30','White','AP-19-AK-1234',6,TRUE ,4,'DIESEL','AUTOMATIC','MEDIUM' ,1,now(),FALSE ,'DUMMY_USER',now(),5);

INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated,driver_id) VALUES
(6,'Scorpio','White','MH-41-UB-9808',8,false,5,'DIESEL','MANUAL','SUV' ,2,now(),FALSE ,'DUMMY_USER',now(),6);
