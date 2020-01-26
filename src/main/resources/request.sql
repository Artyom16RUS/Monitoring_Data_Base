--Create table USERS
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users(
                      id INTEGER PRIMARY KEY,
                      name TEXT NOT NULL,
                      age INTEGER NOT NULL,
                      address TEXT NOT NULL
);

--SQL command
INSERT INTO users (name, age, address) VALUES ('Ola', 18, 'www');
UPDATE users SET name = 'null', age = 0, address = '0000' WHERE id = 1;
DELETE FROM users WHERE id = 3;

--Create table USER_LOG
DROP TABLE IF EXISTS user_log;
CREATE TABLE IF NOT EXISTS user_log (
                          new_id INTEGER,
                          new_name TEXT NOT NULL,
                          new_age INTEGER NOT NULL,
                          new_address TEXT NOT NULL,
                          date TEXT NOT NULL,
                          operation TEXT NOT NULL
);

--TRIGGER INSERT
DROP TRIGGER IF EXISTS after_insert;
CREATE TRIGGER IF NOT EXISTS after_insert AFTER INSERT
    ON users
BEGIN
    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)
    VALUES (NEW.id, NEW.name, NEW.age, NEW.address, datetime('now'), 'ins');
END;

--TRIGGER UPDATE
CREATE TRIGGER IF NOT EXISTS after_update AFTER UPDATE
    ON users
BEGIN
    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)
    VALUES (OLD.id, OLD.name, OLD.age, OLD.address, datetime('now'), 'upd');
END;

--TRIGGER DELETE
CREATE TRIGGER IF NOT EXISTS after_delete AFTER DELETE
    ON users
BEGIN
    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)
    VALUES (OLD.id, OLD.name, OLD.age, OLD.address, datetime('now'), 'del');
END;

--VIEW
SELECT * FROM users;
SELECT * FROM user_log;

