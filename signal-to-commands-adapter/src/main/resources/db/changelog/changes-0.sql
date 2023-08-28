CREATE TABLE signal
(
    id BIGINT PRIMARY KEY
);

CREATE TABLE command
(
    id        SERIAL,
    signal_id BIGINT,
    FOREIGN KEY (signal_id) REFERENCES signal (id),
    operation VARCHAR,
    ordinal   INT,
    payload   VARCHAR
)