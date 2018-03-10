/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');


-- Create 3 Manufacturers
INSERT INTO manufacturer(id,name,date_created, deleted,created_by,last_updated) VALUES (1,'Hyundai',now(),FALSE ,'DUMMY_USER',now());
INSERT INTO manufacturer(id,name,date_created, deleted,created_by,last_updated) VALUES (2,'Mahindra',now(),FALSE ,'DUMMY_USER',now());
INSERT INTO manufacturer(id,name,date_created, deleted,created_by,last_updated) VALUES (3,'Suzuki',now(),FALSE ,'DUMMY_USER',now());


-- Create 3 Cars
INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated) VALUES
(1,'Swift','Black','MH-15-AB-1234' , 5,false,3,'PETROL','MANUAL','SMALL',3,now(),FALSE ,'DUMMY_USER',now());

INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated) VALUES
(2,'i30','White','AP-19-AK-2804',6,false,4,'DIESEL','AUTOMATIC','MEDIUM' ,1,now(),FALSE ,'DUMMY_USER',now());

INSERT INTO car (id,model,color,license_plate,seat_count,convertible,rating,engine_type,transmission,car_type,manufacturer_id,date_created, deleted,created_by,last_updated) VALUES
(3,'Scorpio','White','MH-41-UB-8192',8,false,5,'DIESEL','MANUAL','SUV' ,2,now(),FALSE ,'DUMMY_USER',now());