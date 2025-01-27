INSERT INTO userdb.roles (name) VALUES ('ROLE_USER');
INSERT INTO userdb.roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO userdb.users (age, email, name, password) VALUES (21, 'user@user.com', 'user', '$2a$12$r/UgXbidDftGfwuZlXVd4eECHJcp1bviAVvcsQPAp0HVtwEBFU3sq');
INSERT INTO userdb.users (age, email, name, password) VALUES (25, 'admin@admin.com', 'admin', '$2a$12$VtePqSbEBG0gNKa99GxTJOZxqetVIvuf/vKxHkk7tblicWhLljW5K');


INSERT INTO userdb.users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO userdb.users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO userdb.users_roles (user_id, role_id) VALUES (2, 2);