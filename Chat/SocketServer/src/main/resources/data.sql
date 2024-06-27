INSERT INTO chat.user (username, password) VALUES ('Alex666', '$2a$10$3b3EEehviczXNeG47sYkHufRnqTzDZz/6VgMU4eucIyOyJpcJ.bIq');
INSERT INTO chat.user (username, password) VALUES ('Ivan', '$2a$10$3b3EEehviczXNeG47sYkHufRnqTzDZz/6VgMU4eucIyOyJpcJ.bIq');
INSERT INTO chat.user (username, password) VALUES ('Petr', '$2a$10$3b3EEehviczXNeG47sYkHufRnqTzDZz/6VgMU4eucIyOyJpcJ.bIq');
INSERT INTO chat.user (username, password) VALUES ('Vasya', '$2a$10$3b3EEehviczXNeG47sYkHufRnqTzDZz/6VgMU4eucIyOyJpcJ.bIq');
INSERT INTO chat.user (username, password) VALUES ('Inokentiy', '$2a$10$3b3EEehviczXNeG47sYkHufRnqTzDZz/6VgMU4eucIyOyJpcJ.bIq');
INSERT INTO chat.user (username, password) VALUES ('Anna', '$2a$10$3b3EEehviczXNeG47sYkHufRnqTzDZz/6VgMU4eucIyOyJpcJ.bIq');
INSERT INTO chat.user (username, password) VALUES ('Irina', '$2a$10$3b3EEehviczXNeG47sYkHufRnqTzDZz/6VgMU4eucIyOyJpcJ.bIq');

INSERT INTO chat.chatroom (name) VALUES ('Memes');
INSERT INTO chat.chatroom (name) VALUES ('Sport');
INSERT INTO chat.chatroom (name) VALUES ('Cars');
INSERT INTO chat.chatroom (name) VALUES ('Engineering');
INSERT INTO chat.chatroom (name) VALUES ('War');

INSERT INTO chat.message (text, sender, roomId, time) VALUES ('Hello!', 'Alex666', 1, to_timestamp('2024/04/10 06:57:13', 'YYYY/MM/DD HH:MI:SS'));
INSERT INTO chat.message (text, sender, roomId, time) VALUES ('Hi there!', 'Irina', 1, to_timestamp('2024/04/10 06:58:22', 'YYYY/MM/DD HH:MI:SS'));
INSERT INTO chat.message (text, sender, roomId, time) VALUES ('Wazzup!', 'Anna', 5, to_timestamp('2024/04/10 06:59:14', 'YYYY/MM/DD HH:MI:SS'));
INSERT INTO chat.message (text, sender, roomId, time) VALUES ('Hi', 'Anna', 5, to_timestamp('2024/04/10 07:00:56', 'YYYY/MM/DD HH:MI:SS'));
INSERT INTO chat.message (text, sender, roomId, time) VALUES ('qqqqq', 'Petr', 5, to_timestamp('2024/04/10 07:01:32', 'YYYY/MM/DD HH:MI:SS'));
