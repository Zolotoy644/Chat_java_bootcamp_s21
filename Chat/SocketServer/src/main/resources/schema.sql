DROP SCHEMA IF EXISTS chat CASCADE;
DROP TABLE IF EXISTS chat.user, chat.message, chat.chatroom;

CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE IF NOT EXISTS chat.user
(
    id          serial primary key,
    username    varchar(50) NOT NULL UNIQUE,
    password    varchar(70) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.chatroom
(
    id          serial primary key,
    name   varchar(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS chat.message
(
    id          serial primary key,
    text        text NOT NULL,
    roomId      integer NOT NULL REFERENCES chat.chatroom(id),		
    sender      varchar(50) NOT NULL REFERENCES chat.user(username),
    time   timestamp DEFAULT CURRENT_TIMESTAMP
);
