CREATE TABLE IF NOT EXISTS pchat.<login1>__<login2>
(
  id     INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  time   LONG         NOT NULL,
  text   VARCHAR(500) NOT NULL,
  sender VARCHAR(30)  NOT NULL
);