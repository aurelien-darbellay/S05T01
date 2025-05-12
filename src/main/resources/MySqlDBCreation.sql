
create database S05T01;
use S05T01;
drop table player;
CREATE TABLE player (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    points BIGINT DEFAULT 0
); 