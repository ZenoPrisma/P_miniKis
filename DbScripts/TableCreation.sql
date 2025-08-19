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
    ID INT PRIMARY KEY,
    PLZ VARCHAR(10) NOT NULL,
    Stadt VARCHAR(50) NOT NULL,
    Land VARCHAR(50) NOT NULL,
    Strassenname VARCHAR(50) NOT NULL,
    Strassennummer VARCHAR(10) NOT NULL
);

-- YA_Patient Tabelle
CREATE TABLE YA_Patient (
    ID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Vorname VARCHAR(50) NOT NULL,
    Geburtsdatum DATE NOT NULL,
    Kis_ID INT, -- Krankenhaus ID
    Adresse_ID INT, -- Adressen ID
    FOREIGN KEY (Kis_ID) REFERENCES YA_Krankenhaus(ID),
    FOREIGN KEY (Adresse_ID) REFERENCES YA_Adresse(ID)
);

CREATE TABLE YA_PatientAdress (
    ID INT PRIMARY KEY,
    Patient_ID INT NOT NULL, --Patienten ID
    Adresse_ID INT NOT NULL, -- Adressen ID
    FOREIGN KEY (Patient_ID) REFERENCES YA_Patient(ID),
    FOREIGN KEY (Adresse_ID) REFERENCES YA_Adresse(ID)
);




