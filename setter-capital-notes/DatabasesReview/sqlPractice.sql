-- CREATE DATABASE myDB;
-- USE myDB;
-- DROP DATABASE myDB;
-- ALTER DATABASE myDB READ ONLY = 1;

/*

-- Creation --

CREATE TABLE drivers(
    driver_number INT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    salary DECIMAL(10,2),
    team VARCHAR(50)
);


-- Modification --

ALTER TABLE drivers
ADD years_active INT;

ALTER TABLE drivers
MODIFY salary DECIMAL(10)
AFTER years_active;


-- Populate data -- 

INSERT INTO drivers (driver_number, first_name, last_name, years_active, salary, team)
VALUES
(4, 'Lando', 'Norris', 7, 25000000, 'McLaren'),
(81, 'Oscar', 'Piastri', 4, 10000000, 'McLaren'),

(63, 'George', 'Russell', 8, 15000000, 'Mercedes'),
(12, 'Kimi', 'Antonelli', 1, 5000000, 'Mercedes'),

(1, 'Max', 'Verstappen', 11, 60000000, 'Red Bull Racing'),
(6, 'Isack', 'Hadjar', 1, 3000000, 'Red Bull Racing'),

(16, 'Charles', 'Leclerc', 9, 30000000, 'Ferrari'),
(44, 'Lewis', 'Hamilton', 20, 50000000, 'Ferrari'),

(55, 'Carlos', 'Sainz', 11, 18000000, 'Williams'),
(23, 'Alex', 'Albon', 8, 8000000, 'Williams'),

(30, 'Liam', 'Lawson', 3, 4000000, 'Racing Bulls'),
(41, 'Arvid', 'Lindblad', 1, 2000000, 'Racing Bulls'),

(14, 'Fernando', 'Alonso', 23, 20000000, 'Aston Martin'),
(18, 'Lance', 'Stroll', 9, 10000000, 'Aston Martin'),

(31, 'Esteban', 'Ocon', 9, 10000000, 'Haas'),
(87, 'Oliver', 'Bearman', 2, 5000000, 'Haas'),

(27, 'Nico', 'Hulkenberg', 14, 8000000, 'Audi'),
(5, 'Gabriel', 'Bortoleto', 1, 3000000, 'Audi'),

(10, 'Pierre', 'Gasly', 9, 10000000, 'Alpine'),
(43, 'Franco', 'Colapinto', 1, 3000000, 'Alpine'),

(11, 'Sergio', 'Perez', 14, 12000000, 'Cadillac'),
(77, 'Valtteri', 'Bottas', 14, 10000000, 'Cadillac');


-- Conditionals --

SELECT * 
FROM drivers
WHERE years_active < 2;

SELECT *
FROM drivers
WHERE team = "Mercedes";


-- Updating values --

UPDATE drivers
SET years_active = 0
WHERE driver_number = 41;


-- Committing. Set to off to allow manual commits and rollbacks. --

SET AUTOCOMMIT = OFF;
COMMIT;

DELETE FROM drivers
WHERE driver_number = 41;

ROLLBACK;


-- Built in time functions --

-- DATE, TIME, DATETIME
-- CURRENT_DATE(), CURRENT_TIME(), NOW())


-- Constraints --

ALTER TABLE drivers
ADD CONSTRAINT
UNIQUE(driver_number);

-- Returns null because duplicate driver number
INSERT INTO drivers
VALUES(1, "Jenson", "Button", "McLaren", 15, 20000000);


-- Not Null --

ALTER TABLE drivers
MODIFY first_name VARCHAR(50) NOT NULL;

ALTER TABLE drivers
ADD CONSTRAINT check_salary CHECK (salary > 1000000);

ALTER TABLE drivers
DROP CHECK check_salary;


-- Default --

ALTER TABLE drivers
ALTER years_active SET DEFAULT 0;


-- Primary Key to uniquely identify a row. Each table can only have on PK --

ALTER TABLE drivers
ADD CONSTRAINT
PRIMARY KEY (driver_number);


-- Auto increment  --

CREATE TABLE transactions(
	transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);


ALTER TABLE x
AUTO_INCREMENT = 1000;
    
Now, each additional transaction will automatically increment id from 1000


-- Select statements -- 

SELECT first_name, last_name
FROM drivers
WHERE driver_number = 1;


-- Foriegn Key --

Link between two tables. Must reference a Primary Key or Unique index. For example, customers and orders.
CREATE TABLE orders(
	order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    FOREIGN KEY(customer_id) REFERENCES customers
);

CREATE TABLE customers(
	customer_id INT PRIMARY KEY,
    name VARCHAR(50),
    address VARCHAR(50)
);

If table already exists -> 
ALTER TABLE orders
ADD CONSTRAINT fk_customer_id
FOREIGN KEY(customer_id) REFERENCES customer(customer_id)


-- Join Examples --

CREATE TABLE teams (
    team_id INT PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(50),
    base_country VARCHAR(50),
    principal VARCHAR(50)
);

INSERT INTO teams (team_name, base_country, principal)
VALUES
('McLaren', 'United Kingdom', 'Andrea Stella'),
('Mercedes', 'United Kingdom', 'Toto Wolff'),
('Red Bull Racing', 'Austria', 'Christian Horner'),
('Ferrari', 'Italy', 'Frédéric Vasseur'),
('Williams', 'United Kingdom', 'James Vowles'),
('Racing Bulls', 'Italy', 'Laurent Mekies'),
('Aston Martin', 'United Kingdom', 'Mike Krack'),
('Haas', 'United States', 'Ayao Komatsu'),
('Audi', 'Germany', 'Mattia Binotto'),
('Alpine', 'France', 'Bruno Famin'),
('Cadillac', 'United States', 'Graeme Lowdon');

ALTER TABLE drivers
ADD CONSTRAINT fk_team
FOREIGN KEY (team)
REFERENCES teams(team_name);

SELECT d.first_name, d.last_name, d.team, t.base_country
FROM drivers d INNER JOIN teams t
ON d.team = t.team_name;

SELECT t.team_name, COUNT(d.driver_number) AS num_drivers
FROM teams t LEFT JOIN drivers d
ON t.team_name = d.team
GROUP BY t.team_name;

CREATE TABLE race_points(
	driver_number INT,
    race_name VARCHAR(100),
    points INT,
    FOREIGN KEY(driver_number) REFERENCES drivers(driver_number)
);

INSERT INTO race_points (driver_number, race_name, points)
VALUES
(1, 'Bahrain GP', 25),  -- Max Verstappen
(16, 'Bahrain GP', 18), -- Charles Leclerc
(44, 'Bahrain GP', 15), -- Lewis Hamilton
(63, 'Bahrain GP', 12), -- George Russell
(4, 'Bahrain GP', 10),  -- Lando Norris
(81, 'Bahrain GP', 8),  -- Oscar Piastri
(55, 'Bahrain GP', 6),  -- Carlos Sainz
(23, 'Bahrain GP', 4),  -- Alex Albon
(14, 'Bahrain GP', 2),  -- Fernando Alonso
(18, 'Bahrain GP', 1);  -- Lance Stroll


-- Inner and Left joins --

SELECT CONCAT(d.first_name, " ", d.last_name) AS driver_names, r.race_name
FROM drivers d
INNER JOIN race_points r ON d.driver_number = r.driver_number;

SELECT CONCAT(d.first_name, " ", d.last_name) AS driver_names, r.points
FROM drivers d
LEFT JOIN race_points r ON d.driver_number = r.driver_number
ORDER BY r.points DESC;


-- Right Join examples -- 

INSERT INTO race_points (race_name, points)
VALUES
('Barcelona GP', 25), 
('Barcelona GP', 18), 
('Barcelona GP', 15), 
('Barcelona GP', 12), 
('Barcelona GP', 10), 
('Barcelona GP', 8), 
('Barcelona GP', 6), 
('Barcelona GP', 4),
('Barcelona GP', 2),  
('Barcelona GP', 1);  

SELECT CONCAT(d.first_name, " ", d.last_name) AS driver_names, r.points, r.race_name
FROM drivers d
RIGHT JOIN race_points r ON d.driver_number = r.driver_number;


-- Self Join --

SELECT d1.first_name AS driver1, d2.first_name as driver2, d1.team
FROM drivers d1
JOIN drivers d2
ON d1.team = d2.team
AND d1.driver_number <> d2.driver_number;


-- Functions --

SELECT CONCAT(d.first_name, " ", d.last_name) AS driver_name, r.points
FROM race_points r
INNER JOIN drivers d ON d.driver_number = r.driver_number
WHERE r.points =(
	SELECT MAX(points) FROM race_points
); -- Subquery


-- AND, OR --

SELECT CONCAT(first_name, " ", last_name) as driver_name
FROM drivers
WHERE team = "Mercedes" AND years_active > 2;

-- Between to choose between start and end point (ie: DateTime)
-- IN to find items that are IN a specific condition


-- Wildcard -> Searches patterns --

SELECT * FROM drivers
WHERE first_name LIKE "_A%" -- _ Skips first letter, looks for second letter A, with any amount of letters after it
LIMIT 5; 


-- Union-- 

SELECT CONCAT(first_name, " ", last_name)
FROM drivers
UNION
SELECT principal
FROM teams;


-- Views -- Up to date so when we modify in drivers, the view gets updated too. Its useful for ease of use and quick reference

CREATE VIEW driver_name AS
SELECT first_name, last_name
FROM drivers;


-- Indexes-- Are BTrees and are used to find values within specific column quicker. Updating takes more time, so there is a balance

CREATE INDEX first_Name_last_Name_idx ON drivers(first_name, last_name);

SELECT * FROM drivers
WHERE last_name = "Verstappen";


-- Sub Query

SELECT first_name, last_name
FROM drivers
WHERE driver_number IN (
	SELECT driver_number
    FROM race_points
    WHERE points > 1
);


-- Group By

SELECT d.first_name, d.last_name, r.points
FROM drivers d
JOIN race_points r ON d.driver_number = r.driver_number
WHERE d.driver_number IN (
	SELECT d.driver_number
    FROM race_points
    GROUP BY r.points
);

SELECT d.first_name, d.last_name, SUM(r.points) AS total_points
FROM drivers d
JOIN race_points r 
ON d.driver_number = r.driver_number
GROUP BY d.driver_number
HAVING total_points > 10;

-- Rollup is an extension of Group By clause. It produces another row and shows the grand total (super-aggregate value) -> WITH ROLLUP

-- On delete SET NULL -> When foreign key is deleted, replace FK with null
-- On delete CASCADE -> When foreign key is deleted, delete the row

-- Stored procedure is prepared SQL code that you can save if there is a query that you write often -> CALL getCustomers(); Works like a function since we can pass value into it
-- Delimeter gives us a replacement for ; -> Can change it to // or $$, etc.

CREATE PROCEDURE find_customer(IN f_name VARCHAR(50), IN l_name VARCHAR(50))
	
DELIMETER $$
BEGIN 
	SELECT * 
	FROM customers
	WHERE first_name = f_name AND last_name = l_name;
END;
DELIMETER ;

*/

-- Triggers -> 
