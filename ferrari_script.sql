CREATE TABLE IF NOT EXISTS Car (
  id INTEGER PRIMARY KEY AUTOINCREMENT, -- svarer til stelnummer
  model VARCHAR(255) NOT NULL,
  year INTEGER NOT NULL,
  price NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS Customer (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  phone_number VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  cpr VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Employee (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  phone_number VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  max_loan NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS Loan (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  car_id INTEGER NOT NULL,
  customer_id INTEGER NOT NULL,
  employee_id INTEGER NOT NULL,
  loan_size NUMERIC NOT NULL,
  down_payment NUMERIC NOT NULL,
  interest_rate NUMERIC NOT NULL,
  start_date DATE NOT NULL, -- sættes tilpas ude i fremtiden så det kan godkendes
  end_date DATE NOT NULL,
  status INTEGER NOT NULL,
  FOREIGN KEY (car_id) REFERENCES Car(id),
  FOREIGN KEY (customer_id) REFERENCES Customer(id),
  FOREIGN KEY (employee_id) REFERENCES Employee(id)
);

INSERT INTO Car (model, year, price) VALUES ('F8 Tributo', 2022, 1650000.00);
INSERT INTO Car (model, year, price) VALUES ('812 Superfast', 2021, 2000000.00);
INSERT INTO Car (model, year, price) VALUES ('Portofino M', 2023, 1450000.00);
INSERT INTO Car (model, year, price) VALUES ('SF90 Stradale', 2022, 3000000.00);
INSERT INTO Car (model, year, price) VALUES ('Roma', 2021, 1325000.00);
INSERT INTO Car (model, year, price) VALUES ('GTC4Lusso', 2020, 1800000.00);
INSERT INTO Car (model, year, price) VALUES ('488 Pista', 2019, 2100000.00);
INSERT INTO Car (model, year, price) VALUES ('458 Italia', 2014, 1300000.00);
INSERT INTO Car (model, year, price) VALUES ('812 GTS', 2021, 2150000.00);
INSERT INTO Car (model, year, price) VALUES ('488 GTB', 2018, 1550000.00);
INSERT INTO Car (model, year, price) VALUES ('California T', 2016, 1200000.00);
INSERT INTO Car (model, year, price) VALUES ('F12 Berlinetta', 2015, 1800000.00);
INSERT INTO Car (model, year, price) VALUES ('LaFerrari', 2014, 9500000.00);
INSERT INTO Car (model, year, price) VALUES ('599 GTO', 2011, 3700000.00);
INSERT INTO Car (model, year, price) VALUES ('FF', 2012, 1250000.00);
INSERT INTO Car (model, year, price) VALUES ('F430 Scuderia', 2009, 1000000.00);
INSERT INTO Car (model, year, price) VALUES ('612 Scaglietti', 2008, 750000.00);
INSERT INTO Car (model, year, price) VALUES ('Enzo', 2002, 12000000.00);
INSERT INTO Car (model, year, price) VALUES ('360 Modena', 2001, 650000.00);
INSERT INTO Car (model, year, price) VALUES ('575M Maranello', 2003, 850000.00);
INSERT INTO Car (model, year, price) VALUES ('550 Barchetta', 2000, 1100000.00);
INSERT INTO Car (model, year, price) VALUES ('F50', 1995, 8000000.00);
INSERT INTO Car (model, year, price) VALUES ('456 GT', 1994, 550000.00);
INSERT INTO Car (model, year, price) VALUES ('348 Spider', 1993, 450000.00);
INSERT INTO Car (model, year, price) VALUES ('F40', 1989, 10000000.00);
INSERT INTO Car (model, year, price) VALUES ('328 GTS', 1988, 400000.00);
INSERT INTO Car (model, year, price) VALUES ('Testarossa', 1986, 850000.00);
INSERT INTO Car (model, year, price) VALUES ('512 BB', 1983, 750000.00);

INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Mads', 'Nielsen', '55123456', 'mads.nielsen@email.dk', 'Strandvejen 11, 2920 Charlottenlund', '0101755431');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Morten', 'Jensen', '66123456', 'morten.jensen@email.dk', 'Bredgade 25, 7400 Herning', '2205831236');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Christian', 'Larsen', '77123456', 'christian.larsen@email.dk', 'Rungsted Strandvej 70, 2960 Rungsted', '0507797655');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Søren', 'Pedersen', '88123456', 'soren.pedersen@email.dk', 'Elme Alle 6, 7400 Herning', '1702688971');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Henrik', 'Andersen', '99123456', 'henrik.andersen@email.dk', 'Ordrupvej 45, 2920 Charlottenlund', '3001743213');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Jonas', 'Christensen', '10123456', 'jonas.christensen@email.dk', 'Østergade 34, 7400 Herning', '2105765436');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Lasse', 'Rasmussen', '21123456', 'lasse.rasmussen@email.dk', 'Gammel Kongevej 120, 1850 Frederiksberg', '0806717651');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Mikkel', 'Sørensen', '32123456', 'mikkel.sorensen@email.dk', 'Søndergade 12, 6950 Ringkøbing', '1605789877');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Daniel', 'Hansen', '43123456', 'daniel.hansen@email.dk', 'Tingvej 1, 7400 Herning', '0505743215');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Simon', 'Nielsen', '54123456', 'simon.nielsen@email.dk', 'Skodsborg Strandvej 30, 2942 Skodsborg', '2503735437');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Mathias', 'Petersen', '65123456', 'mathias.petersen@email.dk', 'Lyngby Hovedgade 80, 2800 Kongens Lyngby', '0905697651');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Casper', 'Johansen', '76123456', 'casper.johansen@email.dk', 'Nørregade 18, 7400 Herning', '2903729871');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Frederik', 'Andreasen', '87123456', 'frederik.andreasen@email.dk', 'Tuborg Havnevej 22, 2900 Hellerup', '1102683215');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Marius', 'Christiansen', '98123456', 'marius.christiansen@email.dk', 'Vestergade 5, 7400 Herning', '2405775439');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Jesper', 'Lund', '31234567', 'jesper.lund@email.dk', 'Egegårdsvej 12, 7400 Herning', '1903847657');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Stine', 'Simonsen', '42345678', 'stine.simonsen@email.dk', 'Kongevejen 45, 2830 Virum', '1507820248');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Claus', 'Frandsen', '53456789', 'claus.frandsen@email.dk', 'Aaskovvej 6, 7400 Herning', '2701759871');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Charlotte', 'Dam', '64567890', 'charlotte.dam@email.dk', 'Skovbrynet 8, 2930 Klampenborg', '0310808266');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Stefan', 'Kristensen', '75678901', 'stefan.kristensen@email.dk', 'Dalgasgade 25, 7400 Herning', '1404695437');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Camilla', 'Berg', '86789012', 'camilla.berg@email.dk', 'Dronninggårds Alle 34, 2840 Holte', '2603771644');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Ole', 'Dahl', '97890123', 'ole.dahl@email.dk', 'Solsikkevej 15, 7400 Herning', '1802727659');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Katrine', 'Madsen', '08901234', 'katrine.madsen@email.dk', 'Strandvejen 54, 2900 Hellerup', '0510782040');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Rasmus', 'Laursen', '19012345', 'rasmus.laursen@email.dk', 'Lundgårdsvej 4, 7400 Herning', '3105759873');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Mette', 'Vang', '20123456', 'mette.vang@email.dk', 'Tuborg Boulevard 14, 2900 Hellerup', '2906784646');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Emil', 'Eriksen', '31234567', 'emil.eriksen@email.dk', 'Vejlevej 9, 7400 Herning', '0603745439');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Louise', 'Kjær', '42345678', 'louise.kjaer@email.dk', 'Bredevej 22, 2830 Virum', '1704808248');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Jakob', 'Holm', '53456789', 'jakob.holm@email.dk', 'Grønnegade 7, 7400 Herning', '2303699875');
INSERT INTO Customer (first_name, last_name, phone_number, email, address, cpr) VALUES ('Lene', 'Dinesen', '64567890', 'lene.dinesen@email.dk', 'Øresundsvej 12, 2930 Klampenborg', '1105762464');

INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Jonas', 'Johnson', '12345678', 'admin@ferrari.dk', 'admin', -1); -- Sales Manager
INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Morten', 'Hansen', '12345678', 'morten.hansen@ferrari.dk', 'password1', -1); -- Sales Manager
INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Uranus', 'Saturnis', '12345678', 'employee@ferrari.dk', 'employee', 2400000);
INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Laura', 'Andersen', '23456789', 'laura.andersen@ferrari.dk', 'password2', 2200000);
INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Peter', 'Jensen', '34567890', 'peter.jensen@ferrari.dk', 'password3', 2350000);
INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Julie', 'Nielsen', '45678901', 'julie.nielsen@ferrari.dk', 'password4', 2400000);
INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Michael', 'Christensen', '56789012', 'michael.christensen@ferrari.dk', 'password5', 2250000);
INSERT INTO Employee (first_name, last_name, phone_number, email, password, max_loan) VALUES ('Sofie', 'Larsen', '67890123', 'sofie.larsen@ferrari.dk', 'password6', 2300000);

INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (1, 1, 2, 750000.00, 750000.00, 7.50, '2023-06-01', '2026-06-01', 0); -- PENDING_APPROVAL
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (2, 2, 3, 1350000.00, 450000.00, 9.50, '2023-07-01', '2028-07-01', 1); -- APPROVED
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (3, 3, 4, 1500000.00, 500000.00, 8.00, '2023-08-01', '2027-08-01', 2); -- REJECTED
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (4, 4, 5, 1125000.00, 375000.00, 10.00, '2023-05-01', '2028-05-01', 3); -- ACTIVE
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (5, 5, 6, 800000.00, 200000.00, 6.00, '2018-05-01', '2023-05-01', 4); -- COMPLETED
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (6, 6, 2, 1875000.00, 625000.00, 9.00, '2023-09-01', '2028-09-01', 0); -- PENDING_APPROVAL
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (7, 1, 5, 1050000.00, 350000.00, 7.50, '2023-10-01', '2026-10-01', 1); -- APPROVED
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (8, 2, 4, 1275000.00, 425000.00, 8.50, '2023-11-01', '2027-11-01', 2); -- REJECTED
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (9, 3, 2, 1500000.00, 300000.00, 12.00, '2023-05-01', '2028-05-01', 3); -- ACTIVE
INSERT INTO Loan (car_id, customer_id, employee_id, loan_size, down_payment, interest_rate, start_date, end_date, status) VALUES (10, 4, 3, 975000.00, 325000.00, 6.50, '2018-06-01', '2023-06-01', 4); -- COMPLETED
