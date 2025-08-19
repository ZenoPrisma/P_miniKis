
-- Testdaten 
-- Beispielwerte YA_Krankenhaus 
INSERT ALL
    INTO YA_Krankenhaus (ID, Name) VALUES (1, 'St. Alex Krankenhaus')
    INTO YA_Krankenhaus (ID, Name) VALUES (2, 'Klinikum Musterstadt')
    INTO YA_Krankenhaus (ID, Name) VALUES (3, 'Klinik Elias Maier')
SELECT * FROM dual;

--Löscht den Inhalt in den einzelnen Spalten
--DElETE FROM YA_Krankenhaus;

-- Commit durchführen um die Änderungen zu speichern
COMMIT;

-- Beispielwerte YA_Adresse 
INSERT ALL
    INTO YA_Adresse (ID, PLZ, Stadt, Land, Strassenname, Strassennummer) VALUES (1, 12345, 'Musterstadt', 'Deutschland', 'Musterstraße', 23)
    INTO YA_Adresse (ID, PLZ, Stadt, Land, Strassenname, Strassennummer) VALUES (2, 54321, 'Beispielstadt', 'Deutschland', 'Hauptweg', 56)
    INTO YA_Adresse (ID, PLZ, Stadt, Land, Strassenname, Strassennummer) VALUES (3, 98765, 'Teststadt', 'Deutschland', 'Nebengasse', 79)
SELECT * FROM dual;

--DElETE FROM YA_Adresse;

COMMIT;

-- Beispielwerte YA_Patienten
INSERT ALL
    INTO YA_Patient (ID, Name, Vorname, Geburtsdatum, Kis_ID, Adresse_ID) VALUES (1, 'Mustermann', 'Max', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 1, 1)
    INTO YA_Patient (ID, Name, Vorname, Geburtsdatum, Kis_ID, Adresse_ID) VALUES (2, 'Musterfrau', 'Maria', TO_DATE('1985-05-15', 'YYYY-MM-DD'), 1, 2)
    INTO YA_Patient (ID, Name, Vorname, Geburtsdatum, Kis_ID, Adresse_ID) VALUES (3, 'Schmidt', 'Anna', TO_DATE('1982-11-30', 'YYYY-MM-DD'), 1, 3)
SELECT * FROM dual;

INSERT ALL
    INTO YA_PatientAdress (ID, Patient_ID, Adresse_ID) VALUES (1, 1, 1) 
    INTO YA_PatientAdress (ID, Patient_ID, Adresse_ID) VALUES (2, 2, 2) 
    INTO YA_PatientAdress (ID, Patient_ID, Adresse_ID) VALUES (3, 3, 3) 
SELECT * FROM dual;

--DElETE FROM YA_Patient;

COMMIT;

-- Einfügen von Testdaten in die YA_Standort Tabelle
INSERT ALL
    INTO YA_Standort (ID, Name, Kis_ID) VALUES (1, 'Standort1', 1)
    INTO YA_Standort (ID, Name, Kis_ID) VALUES (2, 'Standort2', 1)
SELECT * FROM dual;

--DELETE FROM YA_Standort;

COMMIT;

-- Einfügen von Testdaten in die YA_Station Tabelle
INSERT ALL
    INTO YA_Station (ID, Name, Standort_ID) VALUES (1, 'Station1', 1)
    INTO YA_Station (ID, Name, Standort_ID) VALUES (2, 'Station2', 1)
    INTO YA_Station (ID, Name, Standort_ID) VALUES (3, 'Station3', 1)
SELECT * FROM dual;

--DELETE FROM YA_Station;

COMMIT;

-- Einfügen von Testdaten in die YA_Abteilung Tabelle
INSERT ALL
    INTO YA_Abteilung (ID, Name, Station_ID) VALUES (1, 'Abteilung1', 1)
    INTO YA_Abteilung (ID, Name, Station_ID) VALUES (2, 'Abteilung2', 1)
SELECT * FROM dual;

--DELETE FROM YA_Abteilung

COMMIT;

-- Einfügen von Testdaten in die YA_Zimmer Tabelle
INSERT ALL
    INTO YA_Zimmer (ID, Name, Abteilung_ID) VALUES (1, 'Zimmer1', 1)
    INTO YA_Zimmer (ID, Name, Abteilung_ID) VALUES (2, 'Zimmer2', 1)
    INTO YA_Zimmer (ID, Name, Abteilung_ID) VALUES (3, 'Zimmer3', 1)
SELECT * FROM dual;

--DELETE FROM YA_Zimmer

COMMIT;

-- Einfügen von Testdaten in die YA_Bett Tabelle
INSERT ALL
    INTO YA_Bett (ID, Name, Zimmer_ID) VALUES (1, 'Bett1', 1)
    INTO YA_Bett (ID, Name, Zimmer_ID) VALUES (2, 'Bett2', 1)
SELECT * FROM dual;

--DELETE FROM YA_Bett

COMMIT;