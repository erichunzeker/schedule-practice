SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS front_desk_staff;
DROP TABLE IF EXISTS visit;
DROP TABLE IF EXISTS schedule;

CREATE TABLE doctor (
  doc_id   INT PRIMARY KEY AUTO_INCREMENT,
  doc_name VARCHAR(30) NOT NULL
);

CREATE TABLE front_desk_staff (
  front_desk_staff_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name          VARCHAR(25),
  last_name           VARCHAR(40)
);

CREATE TABLE patient (
  pat_id INT PRIMARY KEY AUTO_INCREMENT,
  name   VARCHAR(30) NOT NULL,
  doc_id INT,
  CONSTRAINT `fk_doc` FOREIGN KEY (doc_id) REFERENCES `doctor` (doc_id)
);

CREATE TABLE contact (
  contact_id   INT PRIMARY KEY AUTO_INCREMENT,
  contact_name VARCHAR(30) NOT NULL,
  pat_id       INT,
  CONSTRAINT `fk_pat` FOREIGN KEY (pat_id) REFERENCES `patient` (pat_id)
);

CREATE TABLE schedule (
  schedule_id         INT PRIMARY KEY AUTO_INCREMENT,
  front_desk_staff_id INT,
  doc_id              INT,
  CONSTRAINT `fk_front_desk_staff` FOREIGN KEY (front_desk_staff_id) REFERENCES `front_desk_staff` (front_desk_staff_id),
  CONSTRAINT `fk_doc_id` FOREIGN KEY (doc_id) REFERENCES `doctor` (doc_id)
);

CREATE TABLE visit (
  visit_id    INT PRIMARY KEY AUTO_INCREMENT,
  pat_id      INT,
  schedule_id INT,
  CONSTRAINT `fk_visit` FOREIGN KEY (schedule_id) REFERENCES `schedule` (schedule_id)
);


SET FOREIGN_KEY_CHECKS = 1;


