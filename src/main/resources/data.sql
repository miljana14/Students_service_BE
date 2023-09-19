INSERT INTO `users` (username, email, firstname, lastname, password, role, new_user) VALUES ('admin','andricmiljana92@gmail.com','Miljana','Andric','miljana1411','ROLE_STUDENT',0),('anmiljana','miljana@gmail.com','Miljana','Andric','miljana1411','ROLE_ADMIN',1),('ema04','ema@gmail.com','Emilija','Ristic','ema04','ROLE_STUDENT',0),('gavriloandric','gavrilo@gmail.com','Gavrilo','Andric','andric31','ROLE_STUDENT',0),('jelenamilutinovic','jelena@gmail.com','Jelena','Milutinovic','jelenam','ROLE_PROFESSOR',0),('markoandric','marko@gmail.com','Marko','Andric','marko90','ROLE_STUDENT',0),('mmm','mmm@gmail.com','mmm','mmm','mmm123','ROLE_PROFESSOR',1),('nenadkojic','nenad@gmail.com','Nenad','Kojic','nenad5','ROLE_PROFESSOR',0),('pera','andricmiljana922@gmail.com','Miljana','Andric','vvv','ROLE_STUDENT',1),('stefanr','stefan@gmail.com','Stefan','Ristic','stefan95','ROLE_STUDENT',0),('vladetapetrovic','vladeta@gmail.com','Vladeta','Petrovic','vladeta','ROLE_PROFESSOR',1),('zaka','zaka@gmail.com','Zaklina','Ristic','zaka','ROLE_STUDENT',0);

INSERT INTO `city` (postalcode, name) VALUES ('11060','Belgrade'),('15307','Loznica'),('17501','Vranje'),('18000','Nis'),('21000','Novi Sad');

INSERT INTO `title` (id, title) VALUES (2,'ASSISTANT_PROFESSOR'),(3,'ASSOCIATE_PROFESSOR'),(1,'PROFESSOR'),(4,'TEACHING_ASSISTANT');

INSERT INTO `subject` (id, description, name, noofesp,semester, yearofstudy) VALUES (2,'racunanje, geometrija','Matematika',6,'WINTER',4),(3,'gramatika, knjizevnost','Engleski',5,'SUMMER',4),(18,'gramatika, knjizevnost','Italijanski',3,'SUMMER',3),(20,'pevanje','Muzicko',3,'SUMMER',2),(22,'proslost','Istorija',4,'SUMMER',4),(32,'gramatika, knjizevnost','Francuski',3,'WINTER',1),(37,'drustvo','Sociologija',2,'WINTER',2),(46,'svet','Geografija',5,'WINTER',3);

INSERT INTO `student` (indexnumber, indexyear, address, currentyearofstudy, email, firstname, lastname, postalcode) VALUES ('0016',2020,'Lesnica',4,'gavrilo@gmail.com','Gavrilo','Andric','15307'),('0123',2020,'Lesnica',4,'marko@gmail.com','Marko','Andric','15307'),('0753',2023,'Masuricka',2,'zaka@gmail.com','Zaklina','Ristic','17501'),('2222',2019,'Belgrade',4,'andricmiljana922@gmail.com','Miljana','Andric','21000'),('3600',2023,'Belgrade',3,'ema@gmail.com','Emilija','Ristic','11060'),('5496',2020,'Vranje',2,'stefan@gmail.com','Stefan','Ristic','17501');

INSERT INTO `subject_student` (indexnumber, indexyear, subject) VALUES ('0016',2020,2),('0123',2020,2),('2222',2019,2),('0016',2020,3),('0123',2020,3),('2222',2019,3),('0016',2020,18),('0123',2020,18),('2222',2019,18),('3600',2023,18),('0016',2020,20),('0123',2020,20),('0753',2023,20),('2222',2019,20),('3600',2023,20),('5496',2020,20),('0016',2020,22),('0123',2020,22),('2222',2019,22),('0016',2020,32),('0123',2020,32),('0753',2023,32),('2222',2019,32),('3600',2023,32),('5496',2020,32),('0016',2020,37),('0123',2020,37),('0753',2023,37),('2222',2019,37),('3600',2023,37),('5496',2020,37),('0016',2020,46),('0123',2020,46),('2222',2019,46),('3600',2023,46);

INSERT INTO `professor` (id, address, email, firstname, lastname, phone, reelectiondate, postalcode, title) VALUES (1,'Palilula','mmm@gmail.com','mmm','mmm','536596236','2025-01-15','17501',2),(8,'Palilula','vladeta@gmail.com','Vladeta','Petrovic','0648539621','2024-12-15','11060',2),(11,'Telep','nenad@gmail.com','Nenad','Kojic','0349312653','2024-09-11','21000',1),(40,'Karaburma','jelena@gmail.com','Jelena','Milutinovic','0635522670','2026-09-29','15307',4);

INSERT INTO `professor_subject` (subject_id, professor_id) VALUES (2,8),(32,8),(37,8),(46,8),(2,11),(3,11),(22,11),(32,11),(3,40),(18,40),(20,40),(32,40);

INSERT INTO `examination_period` (id, begindate, enddate, name, active) VALUES (15,'2023-09-04','2023-09-23','septembar','F'),(16,'2023-10-01','2023-10-15','oktobar','F'),(20,'2023-08-01','2023-08-18','avgust','T'),(21,'2024-05-17','2024-05-31','maj','F'),(23,'2024-07-26','2024-08-23','cheesecake','F');

INSERT INTO `exam` (id, examdate, examinationperiod, professor, subject) VALUES (6,'2024-05-25',21,40,18),(7,'2023-08-18',20,11,32),(9,'2023-08-10',20,8,2),(10,'2023-08-11',20,11,3),(11,'2023-08-13',20,11,22),(12,'2023-08-17',20,40,20),(13,'2023-08-10',20,8,37),(14,'2023-09-17',15,8,2),(15,'2023-09-14',15,8,37);

INSERT INTO `exam_application` (id, applicationdate, indexnumber, indexyear) VALUES (24,'2023-07-25','2222',2019),(25,'2023-07-26','0016',2020),(26,'2023-07-26','0123',2020),(27,'2023-07-26','0123',2020);

INSERT INTO `exam_application_exams` (exam_application_entity_id, exams_id) VALUES (24,7),(25,7),(25,9),(27,10),(26,12),(26,13);

INSERT INTO `mark` (id, mark, exam_id, indexnumber, indexyear) VALUES (38,9,7,'0016',2020),(39,8,7,'2222',2019),(40,6,12,'0123',2020),(41,10,10,'0123',2020);