-- Sequences
CREATE SEQUENCE seq_adresse START WITH 4 INCREMENT BY 1; --START WITH normalerweiße 1 aber wegen den bereits eingetragenen Testdaten 4
CREATE SEQUENCE seq_patient START WITH 4 INCREMENT BY 1; -- -"-
CREATE SEQUENCE seq_patientadresse START WITH 4 INCREMENT BY 1; -- -"-
CREATE SEQUENCE seq_mitarbeiter START WITH 4 INCREMENT BY 1; -- -"-

-- Krankenhaus Tabelle
CREATE TABLE YA_Krankenhaus (
    ID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL
);

-- Standort Tabelle
CREATE TABLE YA_Standort (
    ID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Kis_ID INT,
    FOREIGN KEY (Kis_ID) REFERENCES YA_Krankenhaus(ID)
);

-- Station Tabelle
CREATE TABLE YA_Station (
    ID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Standort_ID INT,
    FOREIGN KEY (Standort_ID) REFERENCES YA_Standort(ID)
);

-- Abteilung Tabelle
CREATE TABLE YA_Abteilung (
    ID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Station_ID INT,
    FOREIGN KEY (Station_ID) REFERENCES YA_Station(ID)
);

-- Zimmer Tabelle
CREATE TABLE YA_Zimmer (
    ID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Abteilung_ID INT,
    FOREIGN KEY (Abteilung_ID) REFERENCES YA_Abteilung(ID)
);

-- Bett Tabelle
CREATE TABLE YA_Bett (
    ID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Zimmer_ID INT,
    FOREIGN KEY (Zimmer_ID) REFERENCES YA_Zimmer(ID)
);

-- YA_Adresse Tabelle
CREATE TABLE YA_Adresse (
    ID INT DEFAULT seq_adresse.NEXTVAL PRIMARY KEY,
    PLZ VARCHAR(10) NOT NULL,
    Stadt VARCHAR(50) NOT NULL,
    Land VARCHAR(50) NOT NULL,
    Strassenname VARCHAR(50) NOT NULL,
    Strassennummer VARCHAR(10) NOT NULL
);

-- YA_Patient Tabelle
CREATE TABLE YA_Patient (
    ID INT DEFAULT seq_patient.NEXTVAL PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Vorname VARCHAR(50) NOT NULL,
    Geburtsdatum DATE NOT NULL,
    Kis_ID INT, -- Krankenhaus ID
    FOREIGN KEY (Kis_ID) REFERENCES YA_Krankenhaus(ID)
);

-- YA_Mitarbeiter
CREATE TABLE YA_Mitarbeiter (
    ID INT DEFAULT seq_mitarbeiter.NEXTVAL PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Vorname VARCHAR(50) NOT NULL,
    Geburtsdatum DATE NOT NULL,
    Type VARCHAR(50) NOT NULL
);


-- YA_PatientAdress Tabelle
CREATE TABLE YA_PatientAdress (
    ID INT DEFAULT seq_patientadresse.NEXTVAL PRIMARY KEY,
    Patient_ID INT NOT NULL, --Patienten ID
    Adresse_ID INT NOT NULL, -- Adressen ID
    FOREIGN KEY (Patient_ID) REFERENCES YA_Patient(ID),
    FOREIGN KEY (Adresse_ID) REFERENCES YA_Adresse(ID)
);

--------------------------------------------------- Einfügen der Testdaten
-- Beispielwerte YA_Krankenhaus 
INSERT ALL
    INTO YA_Krankenhaus (ID, Name) VALUES (1, 'St. Alex Krankenhaus')
    INTO YA_Krankenhaus (ID, Name) VALUES (2, 'Klinikum Musterstadt')
    INTO YA_Krankenhaus (ID, Name) VALUES (3, 'Klinik Elias Maier')
SELECT * FROM dual;^^

--Löscht den Inhalt in den einzelnen Spalten
--DElETE FROM YA_Krankenhaus;

-- Commit durchführen um die Änderungen zu speichern
-- COMMIT;

-- Beispielwerte YA_Adresse 
INSERT ALL
    INTO YA_Adresse (ID, PLZ, Stadt, Land, Strassenname, Strassennummer) VALUES (1, 12345, 'Musterstadt', 'Deutschland', 'Musterstraße', 23)
    INTO YA_Adresse (ID, PLZ, Stadt, Land, Strassenname, Strassennummer) VALUES (2, 54321, 'Beispielstadt', 'Deutschland', 'Hauptweg', 56)
    INTO YA_Adresse (ID, PLZ, Stadt, Land, Strassenname, Strassennummer) VALUES (3, 98765, 'Teststadt', 'Deutschland', 'Nebengasse', 79)
SELECT * FROM dual;

--DElETE FROM YA_Adresse;

--COMMIT;

-- Beispielwerte YA_Patienten
INSERT ALL
    INTO YA_Patient (ID, Name, Vorname, Geburtsdatum, Kis_ID) VALUES (1, 'Mustermann', 'Max', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 1)
    INTO YA_Patient (ID, Name, Vorname, Geburtsdatum, Kis_ID) VALUES (2, 'Musterfrau', 'Maria', TO_DATE('1985-05-15', 'YYYY-MM-DD'), 1)
    INTO YA_Patient (ID, Name, Vorname, Geburtsdatum, Kis_ID) VALUES (3, 'Schmidt', 'Anna', TO_DATE('1982-11-30', 'YYYY-MM-DD'), 1)
SELECT * FROM dual;

--DElETE FROM YA_Patient;

--COMMIT;

--Beispielwerte YA_Mitarbeiter
INSERT ALL
    INTO YA_Mitarbeiter (ID, Name, Vorname, Geburtsdatum, Type) VALUES (1, 'Testdoktor', 'Antonio', TO_DATE('1998-12-12', 'YYYY-MM-DD'), 'DOCTOR')
    INTO YA_Mitarbeiter (ID, Name, Vorname, Geburtsdatum, Type) VALUES (2, 'Mustermarie', 'Justin', TO_DATE('1990-10-02', 'YYYY-MM-DD'), 'DOCTOR')
    INTO YA_Mitarbeiter (ID, Name, Vorname, Geburtsdatum, Type) VALUES (3, 'Kanal', 'Marlon', TO_DATE('2000-01-06', 'YYYY-MM-DD'), 'CARE')
SELECT * FROM dual;

--DELETE FROM YA_Mitarbeiter;

--COMMIT;

-- Beispielwerte YA_Standort
INSERT ALL
    INTO YA_Standort (ID, Name, Kis_ID) VALUES (1, 'Standort1', 1)
    INTO YA_Standort (ID, Name, Kis_ID) VALUES (2, 'Standort2', 1)
SELECT * FROM dual;

--DELETE FROM YA_Standort

--COMMIT;

-- Beispielwerte YA_Station 
INSERT ALL
    INTO YA_Station (ID, Name, Standort_ID) VALUES (1, 'Station1', 1)
    INTO YA_Station (ID, Name, Standort_ID) VALUES (2, 'Station2', 1)
    INTO YA_Station (ID, Name, Standort_ID) VALUES (3, 'Station3', 1)
SELECT * FROM dual;

--DELETE FROM YA_Station

--COMMIT;

-- Beispielwerte YA_Abteilung 
INSERT ALL
    INTO YA_Abteilung (ID, Name, Station_ID) VALUES (1, 'Abteilung1', 1)
    INTO YA_Abteilung (ID, Name, Station_ID) VALUES (2, 'Abteilung2', 1)
SELECT * FROM dual;

--DELETE FROM YA_Abteilung

--COMMIT;

-- Beispielwerte YA_Zimmer 
INSERT ALL
    INTO YA_Zimmer (ID, Name, Abteilung_ID) VALUES (1, 'Zimmer1', 1)
    INTO YA_Zimmer (ID, Name, Abteilung_ID) VALUES (2, 'Zimmer2', 1)
    INTO YA_Zimmer (ID, Name, Abteilung_ID) VALUES (3, 'Zimmer3', 1)
SELECT * FROM dual;

--DELETE FROM YA_Zimmer

--COMMIT;

-- Beispielwerte YA_Bett 
INSERT ALL
    INTO YA_Bett (ID, Name, Zimmer_ID) VALUES (1, 'Bett1', 1)
    INTO YA_Bett (ID, Name, Zimmer_ID) VALUES (2, 'Bett2', 1)
SELECT * FROM dual;

--DELETE FROM YA_Bett

--COMMIT;

-- Beispielwerte YA_PatientAdress
INSERT ALL
    INTO YA_PatientAdress (ID, Patient_ID, Adresse_ID) VALUES (1, 1, 1)
    INTO YA_PatientAdress (ID, Patient_ID, Adresse_ID) VALUES (2, 2, 2)
    INTO YA_PatientAdress (ID, Patient_ID, Adresse_ID) VALUES (3, 3, 3)
SELECT * FROM dual;

--DELETE FROM YA_PatientAdress

--COMMIT;

-------------------------------------------------- Tabellen Löschen

--DROP SEQUENCE seq_adresse;
--DROP SEQUENCE seq_patient;
--DROP SEQUENCE seq_patientadresse;
--DROP SEQUENCE seq_mitarbeiter;
--DROP TABLE YA_PatientAdress;
--DROP TABLE YA_Krankenhaus;
--DROP TABLE YA_Standort;
--DROP TABLE YA_Station;
--DROP TABLE YA_Abteilung;
--DROP TABLE YA_Zimmer;
--DROP TABLE YA_Bett;
--DROP TABLE YA_Patient;
--DROP TABLE YA_Adresse;
--DROP TABLE YA_Mitarbeiter;

--COMMIT;

