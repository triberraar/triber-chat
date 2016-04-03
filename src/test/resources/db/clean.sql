SET REFERENTIAL_INTEGRITY FALSE
TRUNCATE TABLE triber_user
TRUNCATE TABLE user_entity_roles
TRUNCATE TABLE reset_password
TRUNCATE TABLE public_message

INSERT INTO triber_user(id, version,password, username, email, activated, validated) VALUES('1',0, '$2a$10$MENm5lVP/GPtt8tGievarO.3hvk7Ofao6aK45F6oD6CTEWj03ebvO', 'admin', 'admin@admin.com', true, true)
INSERT INTO user_entity_roles(user_entity_id, roles) VALUES(1, 'ROLE_USER')
INSERT INTO user_entity_roles(user_entity_id, roles) VALUES(1, 'ROLE_ADMIN')
INSERT INTO triber_user(id, version,password, username, email, activated, validated) VALUES('2',0, '$2a$10$0y5Pl/5Z8Qqafa8ukdvxCOgG5jLY4Xc0ZTU2zfTmKORqyoPaduViS', 'user', 'user2@user.com', true, true)
INSERT INTO user_entity_roles(user_entity_id, roles) VALUES(2, 'ROLE_USER')
INSERT INTO triber_user(id, version,password, username, email, activated, validated) VALUES('3',0, '$2a$10$MENm5lVP/GPtt8tGievarO.3hvk7Ofao6aK45F6oD6CTEWj03ebvO', 'unvalidated', 'unvalidated@user.com', true, false)
INSERT INTO user_entity_roles(user_entity_id, roles) VALUES(3, 'ROLE_USER')
SET REFERENTIAL_INTEGRITY true