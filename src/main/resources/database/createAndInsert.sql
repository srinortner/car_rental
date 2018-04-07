DROP TABLE IF EXISTS vehicle_license_requirement;
DROP TABLE IF EXISTS vehicle_booking;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS booking;

DROP SEQUENCE IF EXISTS invoice_number;

CREATE TABLE IF NOT EXISTS vehicle (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(288) NOT NULL,
  buildyear YEAR NOT NULL,
  description TEXT,
  seats NUMERIC(2,0),
  licenseplate VARCHAR(7),
  type VARCHAR(6) NOT NULL CHECK(type IN ('ENGINE', 'MUSCLE')),
  power NUMERIC(7,3),
  hourlyrate NUMERIC(5,0),
  picture VARCHAR(100),
  createtime TIMESTAMP NOT NULL,
  uuid_for_editing VARCHAR(288) NOT NULL,
  edittime TIMESTAMP NOT NULL,
  deleted BOOLEAN DEFAULT FALSE,
);

INSERT INTO vehicle
SELECT * FROM (
SELECT * FROM vehicle WHERE FALSE

UNION SELECT 1, 'Seat', 2001, 'Car', 4, 'AB12345','ENGINE', 100, 120, NULL, '2018-10-01 10:22:00', 'cardefault1','2018-10-01 10:22:00', False
UNION SELECT 2, 'Honda', 2005, 'Another Car', 3, 'AB22334', 'ENGINE', 100, 120, NULL, '2018-10-01 10:23:00', 'cardefault2','2018-10-01 10:23:00', False
UNION SELECT 3, 'Mountainbike', 2011, 'Bike', 1, NULL, 'MUSCLE', 20, 90, NULL, '2018-10-01 10:24:00', 'bikedefault1','2018-10-01 10:24:00', false
);

CREATE TABLE vehicle_license_requirement (
  vehicle_id BIGINT NOT NULL,
  license VARCHAR(1) CHECK(license IN ('A', 'B', 'C')),
  FOREIGN KEY (vehicle_id) references vehicle(id),
);

INSERT INTO vehicle_license_requirement
SELECT * FROM (
SELECT * FROM vehicle_license_requirement WHERE FALSE

UNION SELECT 1, 'B'
UNION SELECT 2, 'C'
);

CREATE SEQUENCE IF NOT EXISTS invoice_number START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS booking (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(288) NOT NULL,
  paymenttype VARCHAR(10) NOT NULL CHECK (paymenttype IN ('IBAN','CREDITCARD')),
  paymentnumber VARCHAR(288) NOT NULL,
  startdate TIMESTAMP NOT NULL,
  enddate TIMESTAMP NOT NULL,
  vehicles VARCHAR(288) NOT NULL,
  total_price NUMERIC(100,0) NOT NULL,
  type VARCHAR(8) NOT NULL CHECK(type IN ('BOOKED', 'PAID', 'CANCELED')),
  createtime TIMESTAMP NOT NULL,
  paidtime TIMESTAMP DEFAULT NULL,
  ivoice_number_booking INTEGER DEFAULT NULL

);

INSERT INTO booking
SELECT * FROM (
SELECT * FROM booking WHERE FALSE

UNION SELECT 1, 'Ash Ketchum','CREDITCARD', '4532642579652817', '2018-04-04 10:22:00', '2018-04-05 10:25:00', '[Seat ]', 2000, 'BOOKED', '2018-10-01 10:29:00', NULL, NULL
UNION SELECT 2, 'Misty', 'CREDITCARD', '4532642579652817', '2018-10-06 10:22:00', '2018-10-07 10:22:00', '[Seat ]', 2000, 'BOOKED', '2018-10-01 10:22:00', NULL, NULL
);

CREATE TABLE IF NOT EXISTS vehicle_booking (
  vehicle_id BIGINT NOT NULL,
  booking_id BIGINT NOT NULL,
  FOREIGN KEY (vehicle_id) references vehicle(id),
  FOREIGN KEY (booking_id) references booking(id),
  license VARCHAR(1) CHECK(license IN ('A', 'B', 'C')),
  license_number TEXT,
  license_date DATE,
);

INSERT INTO vehicle_booking
SELECT * FROM (
SELECT * FROM vehicle_booking WHERE FALSE

UNION SELECT 1, 1, 'B', 12345, '2015-10-04'
UNION SELECT 1, 2, 'C',12344, '2014-10-04'
UNION SELECT 1, 2, 'B', 12345, '2015-10-04'
);