SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO patient (pat_id, name, doc_id) VALUES (1, 'Patient1', 1);
INSERT INTO patient (pat_id, name, doc_id) VALUES (2, 'Patient2', 1);
INSERT INTO patient (pat_id, name, doc_id) VALUES (3, 'Patient3', 2);
INSERT INTO patient (pat_id, name, doc_id) VALUES (4, 'Patient4', 3);
INSERT INTO patient (pat_id, name, doc_id) VALUES (5, 'Patient5', 3);
INSERT INTO patient (pat_id, name, doc_id) VALUES (6, 'Patient6', 3);

INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (1, 'Contact1', 1);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (2, 'Contact1', 2);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (3, 'Contact1', 3);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (4, 'Contact2', 3);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (5, 'Contact2', 4);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (6, 'Contact3', 4);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (7, 'Contact4', 4);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (8, 'Contact4', 5);
INSERT INTO contact (contact_id, contact_name, pat_id) VALUES (9, 'Contact5', 6);

INSERT INTO doctor (doc_id, doc_name) VALUES (1, 'Doctor1');
INSERT INTO doctor (doc_id, doc_name) VALUES (2, 'Doctor2');
INSERT INTO doctor (doc_id, doc_name) VALUES (3, 'Doctor3');

INSERT INTO front_desk_staff (front_desk_staff_id, first_name, last_name) VALUES (1, 'Anabelle', 'Jackson');
INSERT INTO front_desk_staff (front_desk_staff_id, first_name, last_name) VALUES (2, 'Joan', 'Smith');
INSERT INTO front_desk_staff (front_desk_staff_id, first_name, last_name) VALUES (3, 'Eric', 'Rubert');
INSERT INTO front_desk_staff (front_desk_staff_id, first_name, last_name) VALUES (4, 'Bill', 'Lendeal');
INSERT INTO front_desk_staff (front_desk_staff_id, first_name, last_name) VALUES (5, 'Beth', 'Oneal');
INSERT INTO front_desk_staff (front_desk_staff_id, first_name, last_name) VALUES (6, 'Jill', 'Tyson');

INSERT INTO schedule (schedule_id, front_desk_staff_id, doc_id) VALUES (1, 1, 1);
INSERT INTO schedule (schedule_id, front_desk_staff_id, doc_id) VALUES (2, 2, 2);
INSERT INTO schedule (schedule_id, front_desk_staff_id, doc_id) VALUES (3, 3, 3);
INSERT INTO schedule (schedule_id, front_desk_staff_id, doc_id) VALUES (4, 4, 1);
INSERT INTO schedule (schedule_id, front_desk_staff_id, doc_id) VALUES (5, 5, 2);
INSERT INTO schedule (schedule_id, front_desk_staff_id, doc_id) VALUES (6, 6, 3);

INSERT INTO visit (visit_id, pat_id, schedule_id) VALUES (1, 1, 1);
INSERT INTO visit (visit_id, pat_id, schedule_id) VALUES (2, 2, 2);
INSERT INTO visit (visit_id, pat_id, schedule_id) VALUES (3, 3, 3);
INSERT INTO visit (visit_id, pat_id, schedule_id) VALUES (4, 4, 4);
INSERT INTO visit (visit_id, pat_id, schedule_id) VALUES (5, 5, 5);


SET FOREIGN_KEY_CHECKS = 1;
