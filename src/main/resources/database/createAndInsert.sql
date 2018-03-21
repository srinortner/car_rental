DROP TABLE IF EXISTS vehicle_license_requirement;
DROP TABLE IF EXISTS vehicle_booking;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS booking;

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
UNION SELECT 2, 'B'
);

CREATE TABLE IF NOT EXISTS booking (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(288) NOT NULL,
  paymentnumber VARCHAR(288) NOT NULL,
  startdate DATE NOT NULL,
  enddate DATE NOT NULL,
  vehicles VARCHAR(288) NOT NULL,
  total_price NUMERIC(100,0) NOT NULL,
  type VARCHAR(6) NOT NULL CHECK(type IN ('BOOKED', 'PAID', 'CANCELED')),
  createtime TIMESTAMP NOT NULL,

);

CREATE TABLE IF NOT EXISTS vehicle_booking (
  vehicle_id BIGINT NOT NULL,
  booking_id BIGINT NOT NULL,
  FOREIGN KEY (vehicle_id) references vehicle(id),
  FOREIGN KEY (booking_id) references booking(id),
  vehicle_price NUMERIC(1000,0),
  license_number TEXT,
  license_date TIMESTAMP,
);