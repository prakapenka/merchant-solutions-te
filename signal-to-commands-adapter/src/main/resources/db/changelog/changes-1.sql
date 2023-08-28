/* For signal 1: */
INSERT into signal (id)
VALUES (1);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (1, 'SETUP', 1);
INSERT INTO command (signal_id, operation, payload, ordinal)
VALUES (1, 'SET_ALGO_PARAM', '{"first":1,"second":60}', 2);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (1, 'PERFORM_CALC', 3);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (1, 'SUBMIT_TO_MARKET', 4);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (1, 'DO_ALGO', 5);

/* For signal 2: */
INSERT into signal (id)
VALUES (2);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (2, 'REVERSE', 1);
INSERT INTO command (signal_id, operation, payload, ordinal)
VALUES (2, 'SET_ALGO_PARAM', '{"first":1,"second":80}', 2);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (2, 'SUBMIT_TO_MARKET', 3);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (2, 'DO_ALGO', 4);

/* For signal 3: */
INSERT into signal (id)
VALUES (3);
INSERT INTO command (signal_id, operation, payload, ordinal)
VALUES (3, 'SET_ALGO_PARAM', '{"first":1,"second":90}', 1);
INSERT INTO command (signal_id, operation, payload, ordinal)
VALUES (3, 'SET_ALGO_PARAM', '{"first":2,"second":15}', 2);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (3, 'PERFORM_CALC', 3);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (3, 'SUBMIT_TO_MARKET', 4);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (3, 'DO_ALGO', 5);

/* default MIN long value */
INSERT INTO signal (id)
VALUES (-9223372036854775808);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (-9223372036854775808, 'CANCEL_THREADS', 1);
INSERT INTO command (signal_id, operation, ordinal)
VALUES (-9223372036854775808, 'DO_ALGO', 2);






