INSERT INTO category(name)
VALUES ('Action and adventure'),
       ('Anthology'),
       ('Classic'),
       ('Comic and Graphic novel'),
       ('Drama'),
       ('Fable'),
       ('Fairy Tale'),
       ('Fan-Fiction'),
       ('Fantasy'),
       ('Historical Fiction'),
       ('Horror'),
       ('Humor'),
       ('Legend'),
       ('Magical Realism'),
       ('Mystery'),
       ('Mythology'),
       ('Realistic Fiction'),
       ('Romance'),
       ('Satire'),
       ('Science Fiction'),
       ('Short Story'),
       ('Suspense/Thriller'),
       ('Biography/Autobiography');
INSERT INTO role (name)
VALUES ('ROLE_ADMINISTRATOR'),
       ('ROLE_USER');
INSERT INTO users(email, password, username)
VALUES ('admin@admin.com', '$2a$10$lOI0pcl3xHTLw.eyKWq5SeFz0Ngnxlzlc6I9jU50WUqpw5GaDRAmC', 'admin');
INSERT INTO user_role(user_id, role_id)
VALUES (1, 1);