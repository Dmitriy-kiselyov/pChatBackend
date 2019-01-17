CREATE TABLE pchat.users
(
  login         VARCHAR(30) NOT NULL PRIMARY KEY,
  password      VARCHAR(100) NOT NULL,
  token         VARCHAR(300) NOT NULL
);