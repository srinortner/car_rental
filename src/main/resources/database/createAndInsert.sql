CREATE TABLE IF NOT EXISTS vehicle (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(288) NOT NULL,
  buildyear YEAR NOT NULL,
  description TEXT,
  seats NUMERIC(2,0),
  licenseplate VARCHAR(7),
  type VARCHAR(6) NOT NULL CHECK(type IN ('ENGINE', 'MUSCLE')),
  power NUMERIC(3,2),
  hourlyrate NUMERIC(4,2),
  createtime TIMESTAMP AS CURRENT_TIMESTAMP NOT NULL,
  deleted BOOLEAN DEFAULT FALSE,
);

CREATE TABLE vehicle_license_requirement (
  vehicle_id BIGINT NOT NULL,
  license VARCHAR(1) NOT NULL CHECK(license IN ('A', 'B', 'C')),
  FOREIGN KEY (vehicle_id) references vehicle(id),
);

CREATE TABLE IF NOT EXISTS booking (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
);

CREATE TABLE IF NOT EXISTS vehicle_booking (
  vehicle_id BIGINT NOT NULL,
  booking_id BIGINT NOT NULL,
  FOREIGN KEY (vehicle_id) references vehicle(id),
  FOREIGN KEY (booking_id) references booking(id),
  license_number TEXT,
  license_date TIMESTAMP,
);