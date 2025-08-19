package minKis;

import java.sql.*;
import java.util.*;

public class KISController {
	Scanner sc = new Scanner(System.in);
    private Connection conn;

    public KISController(Connection conn) {
        this.conn = conn;
    }
    
    //------------------------------------------------------------------------ Auflisten
    //Alle Patienten auflisten
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM YA_Patient ORDER BY ID"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient patient = (Patient) PersonFactory.createPerson(PersonFactory.PersonType.PATIENT);
                patient.setID(rs.getInt("ID"));
                patient.setName(rs.getString("Name"));
                patient.setVorname(rs.getString("Vorname"));
                patient.setGeburtsdatum(rs.getDate("Geburtsdatum"));
                patient.setInsID(rs.getInt("Kis_ID"));

                patients.add(patient);
            }
        } 
        return patients;      
    }
    
    public List<Adresse> getAdressFromPatients(int patientID, List<Adresse> adressList) throws SQLException {
    	List<Adresse> result = new ArrayList<Adresse>();
        String sql = "SELECT Adresse_ID FROM YA_PatientAdress WHERE Patient_ID = ? ORDER BY ID"; 
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, patientID);
        List<Integer> adressIDs = new ArrayList<Integer>();
        
        ResultSet rs = stmt.executeQuery(); 
        while (rs.next()) {
        	adressIDs.add(rs.getInt("Adresse_ID"));
        }        
        for (int adresseID : adressIDs) {
			for (Adresse adresse : adressList) {
				if (adresse.getId() == adresseID) {
					result.add(adresse);
				}
			}
		}       
    	return result;
    } 

    //Alle Adressen auflisten
    public List<Adresse> getAllAdress() throws SQLException { 
        List<Adresse> adressen = new ArrayList<>();
        String sql = "SELECT * FROM YA_Adresse ORDER BY ID"; 
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { 
        	
            while (rs.next()) {
                Adresse adresse = new Adresse();
                adresse.setId(rs.getInt("ID"));
                adresse.setPlz(rs.getInt("PLZ"));
                adresse.setStadt(rs.getString("Stadt"));
                adresse.setLand(rs.getString("Land"));
                adresse.setStrassenname(rs.getString("Strassenname"));
                adresse.setStrassennummer(rs.getInt("Strassennummer"));

                adressen.add(adresse);
            }
        }        
		return adressen;
    }   
    
    //Alle Mitarbeiter auflisten
    public List<Employees> getAllMitarbeiter() throws SQLException {
        List<Employees> mitarbeitern = new ArrayList<>();
        String sql = "SELECT * FROM YA_Mitarbeiter ORDER BY ID"; 
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
        	
            while (rs.next()) {
                Employees mitarbeiter = (Employees) PersonFactory.createPerson(PersonFactory.PersonType.EMPLOYEE);
                mitarbeiter.setID(rs.getInt("ID"));
                mitarbeiter.setName(rs.getString("Name"));
                mitarbeiter.setVorname(rs.getString("Vorname"));
                mitarbeiter.setGeburtsdatum(rs.getDate("Geburtsdatum"));
                mitarbeiter.setType(rs.getString("Type"));                

                mitarbeitern.add(mitarbeiter);
            }
        }        
		return mitarbeitern;
    }
    
    //Alle Mitarbeiter auflisten (Interfaces) 
    public List<IPerson> getAllIMitarbeiter() throws SQLException {
    	List<IPerson> personenListe = new ArrayList<>();
    	String sql = "SELECT * FROM YA_Mitarbeiter ORDER BY ID";
    	
    	try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { 
           	
               while (rs.next()) {
                   IPerson mitarbeiter = PersonFactory.createPerson(PersonFactory.PersonType.EMPLOYEE);
                   mitarbeiter.setID(rs.getInt("ID"));
                   mitarbeiter.setName(rs.getString("Name"));
                   mitarbeiter.setVorname(rs.getString("Vorname"));
                   mitarbeiter.setGeburtsdatum(rs.getDate("Geburtsdatum"));
                   
                   personenListe.add(mitarbeiter);
              }         
         }      
    	return personenListe;
    }
   

    //------------------------------------------------------------------------ Hinzufügen
    
    //Hinzufügen Patient
    public Patient addPatient(Scanner sc) throws SQLException {
        System.out.println("Bitte geben Sie die Daten fuer den neuen Patienten ein:");
              
        //ErrorHandling
        //Patientenname
        String[] nameHolder = new String[1];
        if (!errorName(sc, nameHolder)) {
            return null;
        }
        String Name = nameHolder[0];
        
        //Patientenvorname
        String[] vornameHolder = new String[1];
        if (!errorVorname(sc, vornameHolder)) {
            return null;
        }
        String Vorname = vornameHolder[0];

        //Patientengeburtstag
        java.sql.Date[] dateHolder = new java.sql.Date[1];
        if (!errorGeburtsdatum(sc, dateHolder)) {
            return null;
        }
        java.sql.Date Geburtsdatum = dateHolder[0];

        Patient newPatient = (Patient) PersonFactory.createPerson(PersonFactory.PersonType.PATIENT);
        newPatient.setName(Name);
        newPatient.setVorname(Vorname);
        newPatient.setGeburtsdatum(Geburtsdatum);
        
        //1. Auswahl eines Krankenhauses
        System.out.println("Verfuegbare Krankenhaeuser:");
        listKrankenhaeuser(); 
        System.out.println("Bitte waehlen Sie ein Krankenhaus (ID): ");
        int ins_ID = sc.nextInt();

        //2. Standort abfragen
        System.out.println("Verfuegbare Standorte fuer Krankenhaus " + ins_ID + ":");
        listStandorte(ins_ID);
        System.out.println("Bitte waehlen Sie einen Standort (ID): ");
        int standort_ID = sc.nextInt();

        //3. Station abfragen
        System.out.println("Verfuegbare Stationen fuer Standort " + standort_ID + ":");
        listStationen(standort_ID);
        System.out.println("Bitte waehlen Sie eine Station (ID): ");
        int station_ID = sc.nextInt();

        //4. Abteilung abfragen
        System.out.println("Verfuegbare Abteilungen fuer Station " + station_ID + ":");
        listAbteilungen(station_ID); 
        System.out.println("Bitte waehlen Sie eine Abteilung (ID): ");
        int abteilung_ID = sc.nextInt();

        //5. Zimmer abfragen
        System.out.println("Verfuegbare Zimmer fuer Abteilung " + abteilung_ID + ":");
        listZimmer(abteilung_ID); 
        System.out.println("Bitte waehlen Sie ein Zimmer (ID): ");
        int zimmer_ID = sc.nextInt();

        //6. Bett abfragen
        System.out.println("Verfuegbare Betten fuer Zimmer " + zimmer_ID + ":");
        listBetten(zimmer_ID); 
        System.out.println("Bitte waehlen Sie ein Bett (ID): ");
        sc.nextInt(); 

        //7. Auswahl einer Adresse bzw. Erstellung einer neuen Adresse
        System.out.println("Moechten Sie eine vorhandene Adresse auswaehlen oder eine neue Adresse erstellen?");
        System.out.println("1: Vorhandene Adresse auswaehlen");
        System.out.println("2: Neue Adresse erstellen");
        
        int choice = sc.nextInt();
        int generatedPatientID;  
                       
        if (choice == 1) {
            // Auswahl einer vorhandenen Adresse
            System.out.println("Verfuegbare Adressen: ");
        	List<Adresse> allAdressen = getAllAdress();
            for (Adresse adresse : allAdressen) {
            	System.out.println(adresse);
			}
            System.out.print("Bitte waehlen Sie eine Adresse (ID): ");
            int adresseID = sc.nextInt();
            generatedPatientID = insertNewPatient(conn, newPatient, ins_ID); 
            connPatientAdresse(conn, generatedPatientID, adresseID);
            
        } else if (choice == 2) {
        	
            //Postleitzahl  
        	int[] plzHolder = new int[1];
            if (!errorPlz(sc, plzHolder)) {
                return null;
            }
            int Plz = plzHolder[0];
            
            //Stadtname
            String[] stadtHolder = new String[1];
            if (!errorStadt(sc, stadtHolder)) {
            	return null;
            }
            String Stadt = stadtHolder[0];
             
            //Landname
            String[] landHolder = new String[1];
            if (!errorLand(sc, landHolder)) {
            	return null;
            }
            String Land = landHolder[0]; 
               
            //Strassenname
            String[] strassennameHolder = new String[1];
            if (!errorStrassenname(sc, strassennameHolder)) {
            	return null;
            }
            String Strassenname = strassennameHolder[0];
            
            //Strassennummer
            int[] strassennummerHolder = new int[1];
            if (!errorStrassenummer(sc, strassennummerHolder)) {
            	return null;
            }
            int Strassennummer = strassennummerHolder[0];
            
            Adresse adresse = new Adresse();
            adresse.setPlz(Plz);
            adresse.setStadt(Stadt);
            adresse.setLand(Land);
            adresse.setStrassenname(Strassenname);
            adresse.setStrassennummer(Strassennummer);
            
            int generatedAdressID = insertNewAdresse(conn, adresse);
            generatedPatientID = insertNewPatient(conn, newPatient, ins_ID);
            connPatientAdresse(conn, generatedPatientID, generatedAdressID);
        } else {
            System.out.println("Ungueltige Auswahl.");
            System.out.println("");
        }
        return newPatient; 
    }
  
    //Hinzufügen Mitarbeiter 
    public Employees addMitarbeiter(Scanner sc) throws SQLException {
    	System.out.println("Bitte geben Sie die Daten fuer den neuen Mitarbeiter ein:");
    	
    	//ErrorHandling 
        //Patientenname
        String[] nameHolder = new String[1];
        if (!errorName(sc, nameHolder)) {
            return null;
        }
        String Name = nameHolder[0];
        
        //Patientenvorname
        String[] vornameHolder = new String[1];
        if (!errorVorname(sc, vornameHolder)) {
            return null;
        }
        String Vorname = vornameHolder[0];

        //Patientengeburtstag
        java.sql.Date[] dateHolder = new java.sql.Date[1];
        if (!errorGeburtsdatum(sc, dateHolder)) {
            return null;
        }
        java.sql.Date Geburtsdatum = dateHolder[0];
        
        System.out.println("Arzt oder Pflege?");
        System.out.println("1. Arzt");
        System.out.println("2. Pflege");
        int auswahl = sc.nextInt();
        String type;
        
        if (auswahl == 1) {
			type = "DOCTOR";
		} else if(auswahl == 2) {
			type = "CARE";
		} else {
			System.out.println("Ungueltige Auswahl.");
            System.out.println("");
			return null;
		} 

        Employees newMitarbeiter = (Employees) PersonFactory.createPerson(PersonFactory.PersonType.EMPLOYEE);
        newMitarbeiter.setName(Name);
        newMitarbeiter.setVorname(Vorname);
        newMitarbeiter.setGeburtsdatum(Geburtsdatum);
        newMitarbeiter.setType(type); 
        
        insertNewMitarbeiter(conn, newMitarbeiter, type);
    	return newMitarbeiter;
    }

    //------------------------------------------------------------------------ Einfügen 

    //Einfügen des neuen Patienten
    public int insertNewPatient(Connection conn, Patient patient, int ins_ID) throws SQLException {
        String sql = "INSERT INTO YA_Patient (ID, Name, Vorname, Geburtsdatum, Kis_ID) VALUES (seq_patient.NEXTVAL, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getVorname());
            stmt.setDate(3, patient.getGeburtsdatum());
            stmt.setInt(4, ins_ID);

            int rowsAffected = stmt.executeUpdate();
            int generatedPatientID = 0;
            
            if (rowsAffected > 0) {
                System.out.println("Patient erfolgreich eingefuegt!");
                LogManager.getInstance().logInfo("Ein Patient wurde eingefuegt.");
                //Abrufen des letzten generierten Werts aus der Sequence
                try (Statement sequenceStmt = conn.createStatement()) {
                    ResultSet resultSet = sequenceStmt.executeQuery("SELECT seq_patient.CURRVAL FROM dual");
                    if (resultSet.next()) {
                        generatedPatientID = resultSet.getInt(1); 
                    }
                }
            } else {
                System.out.println("Fehler beim Einfuegen des Patienten.");
                LogManager.getInstance().logError("Fehler beim Einfuegen eines Patienten.", null);
            }
            return generatedPatientID;
        }
    }
    
    //Einfügen der neuen Adresse
    public int insertNewAdresse(Connection conn, Adresse adresse) throws SQLException {
        String sql = "INSERT INTO YA_Adresse (ID, PLZ, Stadt, Land, Strassenname, Strassennummer) VALUES (seq_adresse.NEXTVAL, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adresse.getPlz());
            stmt.setString(2, adresse.getStadt());
            stmt.setString(3, adresse.getLand());
            stmt.setString(4, adresse.getStrassenname());
            stmt.setInt(5, adresse.getStrassennummer());

            int rowsAffected = stmt.executeUpdate();
            int generatedAdressID = 0;
            
            if (rowsAffected > 0) {
                System.out.println("Adresse erfolgreich eingefuegt!");
                LogManager.getInstance().logInfo("Eine Adresse wurde hinzugefuegt.");
                //Abrufen des letzten generierten Werts aus der Sequence
                try (Statement sequenceStmt = conn.createStatement()) {
                    ResultSet resultSet = sequenceStmt.executeQuery("SELECT seq_adresse.CURRVAL FROM dual");
                    if (resultSet.next()) {
                        generatedAdressID = resultSet.getInt(1);
                    }
                }
            } else {
                System.out.println("Fehler beim Einfuegen der Adresse.");
                LogManager.getInstance().logError("Fehler beim Einfuegen der Adresse.", null);
            }
            return generatedAdressID;
        }
    }
    
    //Relation zwischen Patient & Adresse in YA_PatientAdress, so können z.B Patienten mehrere Adressen haben.
    public void connPatientAdresse(Connection conn, int generatedPatientID, int generatedAdressID) throws SQLException {
        String sql = "INSERT INTO YA_PatientAdress (Patient_ID, Adresse_ID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, generatedPatientID);
            stmt.setInt(2, generatedAdressID);
            stmt.executeUpdate();
        }
    }
   
    //Einfügen eines neuen Mitarbeiters
    public int insertNewMitarbeiter(Connection conn, Employees mitarbeiter, String type) throws SQLException {
        String sql = "INSERT INTO YA_Mitarbeiter (ID, Name, Vorname, Geburtsdatum, Type) VALUES (seq_patient.NEXTVAL, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mitarbeiter.getName());
            stmt.setString(2, mitarbeiter.getVorname());
            stmt.setDate(3, mitarbeiter.getGeburtsdatum());
            stmt.setString(4, type);

            int rowsAffected = stmt.executeUpdate();
            int generatedMitarbeiterID = 0;

            if (rowsAffected > 0) {
                System.out.println("Mitarbeiter erfolgreich eingefuegt!");               
                //Abrufen des letzten generierten Werts aus der Sequence
                try (Statement sequenceStmt = conn.createStatement()) {
                    ResultSet resultSet = sequenceStmt.executeQuery("SELECT seq_patient.CURRVAL FROM dual");
                    if (resultSet.next()) {
                        generatedMitarbeiterID = resultSet.getInt(1);
                    }
                }
            } else {
                System.out.println("Fehler beim Einfuegen des Mitarbeiters.");
                LogManager.getInstance().logError("Fehler beim Einfuegen des Mitarbeiters.", null);
            }
            return generatedMitarbeiterID;
        }
    }

    //------------------------------------------------------------------------ Löschen 
    
    //-----------Patienten löschen
    public void deletePatient(Scanner sc, Connection conn) throws SQLException {
    	List<Patient> allPatienten = getAllPatients();
    	for (Patient patient : allPatienten) {
			System.out.println(patient);
		}
        System.out.print("Bitte geben Sie die ID des Patienten an, den Sie loeschen moechten: ");
        int patientIDToDelete = sc.nextInt(); 
        deleteRelationPatient(conn, patientIDToDelete);
        deletePatientById(conn, patientIDToDelete);
        System.out.println("");
        System.out.println("Patient erfolgreich geloescht!");
        System.out.println(""); 
    }
    
    //Relation zu YA_PatientAdress
    public void deleteRelationPatient(Connection conn, int patientID) throws SQLException {
    	String deleteRelationSql = "DELETE FROM YA_PatientAdress WHERE Patient_ID = ?";
    	try (PreparedStatement stmt = conn.prepareStatement(deleteRelationSql)) {
            stmt.setInt(1, patientID);
            stmt.executeUpdate();
        }
    }
    
    //Patienten Löschen
    public void deletePatientById(Connection conn, int patientID) throws SQLException {
        String deletePatientSql = "DELETE FROM YA_Patient WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deletePatientSql)) {
            stmt.setInt(1, patientID);
            stmt.executeUpdate();
        }
    }

    //-----------Adresse Löschen
    public void deleteAddress(Scanner sc, Connection conn) throws SQLException {
    	List<Adresse> allAdressen = getAllAdress();
        for (Adresse adresse : allAdressen) {
        	System.out.println(adresse);
		}
        System.out.print("Bitte geben Sie die ID der Adresse an, die Sie loeschen moechten: ");
        int adressIDToDelete = sc.nextInt(); 
        deleteRelationAdress(conn, adressIDToDelete);
        deleteAdressByID(conn, adressIDToDelete);
        System.out.println("");
        System.out.println("Adresse erfolgreich geloescht!");
        System.out.println(""); 
    }
    
    //Relation zu YA_PatientAdress 
    public void deleteRelationAdress(Connection conn, int adresseID) throws SQLException {
    	String deleteRelationSql = "DELETE FROM YA_PatientAdress WHERE Adresse_ID = ?";
    	try (PreparedStatement stmt = conn.prepareStatement(deleteRelationSql)) {
            stmt.setInt(1, adresseID);
            stmt.executeUpdate();
        }
    }
    
    //Adresse Löschen
    public void deleteAdressByID(Connection conn, int adressID) throws SQLException {
        String deletePatientSql = "DELETE FROM YA_Adresse WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deletePatientSql)) {
            stmt.setInt(1, adressID);
            stmt.executeUpdate();
        }
    } 
     
    //------------------------------------------------------------------------ Error-Handling
    
    //Name
    public boolean errorName(Scanner sc, String[] nameHolder) { 
    	//Testfall 1: Eingabe eines gültigen (Vor-)Namens (z.B. "Max" oder "Maximilian").
    	//Testfall 2: Eingabe eines ungültigen (Vor-)Namens (z.B. mit Zahlen "Max123" oder mit Sonderzeichen "Max!"). 
    	System.out.print("Name: ");
        nameHolder[0] = sc.next();

        if (nameHolder[0].length() <= 2 || nameHolder[0].length() > 20) {
            System.out.println("Fehler: Namen duerfen nicht kuerzer als 2 oder laenger als 20 Buchstaben sein.");
            return false;
        } else if (!nameHolder[0].matches("[a-zA-Z]+")) {
            System.out.println("Fehler: Der Name darf nur Buchstaben enthalten.");
            return false;
        }
        return true; // Name ist gültig
    } 
    
    //Vorname
    public boolean errorVorname(Scanner sc, String[] vornameHolder) {
        System.out.print("Vorname: ");
        vornameHolder[0] = sc.next();

        if (vornameHolder[0].length() <= 2 || vornameHolder[0].length() > 20) {
            System.out.println("Fehler: Vornamen duerfen nicht kuerzer als 2 oder laenger als 20 Buchstaben sein.");
            return false;
        } else if (!vornameHolder[0].matches("[a-zA-Z]+")) {
            System.out.println("Fehler: Der Vorname darf nur Buchstaben enthalten.");
            return false;
        } 
        return true; // Name ist gültig 
    } 
    
    //Geburtsdatum
    public boolean errorGeburtsdatum(Scanner sc, java.sql.Date[] dateHolder) {
    	//Testfall 1: Eingabe eines gültigen Datums im Format "YYYY-MM-DD" (z.B. "1990-05-20").
        //Testfall 2: Eingabe eines ungültigen Datums (z.B. "1990-20-05" oder "1990-05-").
    	System.out.print("Geburtsdatum (Format: YYYY-MM-DD): ");
        String geburtsdatumStr = sc.next();
        try {
            dateHolder[0] = java.sql.Date.valueOf(geburtsdatumStr);
            return true; // Datum ist gültig
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: Ungueltiges Datumsformat. Bitte verwenden Sie das Format YYYY-MM-DD.");
            return false;
        }
    } 
    
    //Postleitzahl
    public boolean errorPlz(Scanner sc, int[] plzHolder) {
    	System.out.println("Plz: ");
    	plzHolder[0] = sc.nextInt();
    	
    	String plzString = Integer.toString(plzHolder[0]);
    	
    	if (plzString.length() != 5) {
    		System.out.println("Fehler: Postleitzahlen muessen 5 Zahlen beeinhalten");
    		return false;
    	} else if (!plzString.matches("\\d+")) {
    		System.out.println("Fehler: Die Postleitzahl darf nur Zahlen enthalten");
    		return false;
    	}
    	return true;
    }
    
    //Stadtname
    public boolean errorStadt(Scanner sc, String[] stadtHolder) {
    	System.out.println("Stadt: ");
    	stadtHolder[0] = sc.next();
    	
    	if (stadtHolder[0].length() <= 2 || stadtHolder[0].length() > 20) {
            System.out.println("Fehler: Stadtnamen duerfen nicht kuerzer als 2 oder laenger als 20 Buchstaben sein.");
            return false;
        } else if (!stadtHolder[0].matches("[a-zA-Z]+")) {
            System.out.println("Fehler: Der Stadtname darf nur Buchstaben enthalten.");
            return false;
        }
        return true; 
    } 
    
    //Landname
    public boolean errorLand(Scanner sc, String[] landHolder) {
    	System.out.println("Land: ");
    	landHolder[0] = sc.next();
    	
    	if (landHolder[0].length() <= 2 || landHolder[0].length() > 20) {
            System.out.println("Fehler: Laendernamen duerfen nicht kuerzer als 2 oder laenger als 20 Buchstaben sein.");
            return false;
        } else if (!landHolder[0].matches("[a-zA-Z]+")) {
            System.out.println("Fehler: Die Laendernamen duerfen nur Buchstaben enthalten.");
            return false;
        } 
    	return true;
    } 
    
    //Strassenname
    public boolean errorStrassenname(Scanner sc, String[] strassennameHolder) {
    	System.out.print("Strassenname: ");
        strassennameHolder[0] = sc.next();
        
        if (strassennameHolder[0].length() <= 2 || strassennameHolder[0].length() > 20) {
            System.out.println("Fehler: Strassennamen duerfen nicht kuerzer als 2 oder laenger als 20 Buchstaben sein.");
            return false;
        } else if (!strassennameHolder[0].matches("[a-zA-Z]+")) { 
            System.out.println("Fehler: Der Strassennamen darf nur Buchstaben enthalten.");
            return false;
        }
    	return true;
    }
    
    //Strassennummer 
    public boolean errorStrassenummer(Scanner sc, int[] strassennummerHolder) {
    	System.out.print("Strassennummer: ");
        strassennummerHolder[0] = sc.nextInt();
        
        String strassennummerString = Integer.toString(strassennummerHolder[0]);
        
        if (strassennummerString.length() <= 0 || strassennummerString.length() > 100) {
    		System.out.println("Fehler: Strassennummern duerfen nicht kleiner als 0 oder größer als 100 Zahlen sein.");
    		return false;
    	} else if (!strassennummerString.matches("\\d+")) {
    		System.out.println("Fehler: Die Strassennummern duerfen nur Zahlen enthalten");
    		return false;
    	}
        return true;
    }
        
    //------------------------------------------------------------------------ Auflisten
       
	public void listKrankenhaeuser() throws SQLException { //Liste der verfügbaren Krankenhäuser
		String sql = "SELECT ID, Name FROM YA_Krankenhaus ORDER BY ID";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery()) {
         	while (rs.next()) {
         		System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("Name"));
         	}
     	}
 	}
	
    public void listStandorte(int Kis_ID) throws SQLException { //Liste der verfügbaren Standorte
        String sql = "SELECT ID, Name FROM YA_Standort WHERE Kis_ID = ? ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, Kis_ID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("Name"));
                }
            }
        }
    }
	
    public void listStationen(int standort_ID) throws SQLException { //Liste der verfügbaren Stationen
        String sql = "SELECT ID, Name FROM YA_Station WHERE Standort_ID = ? ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, standort_ID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("Name"));
                }
            }
        }
    }
    
    public void listAbteilungen(int station_ID) throws SQLException { //Liste der verfügbaren Abteilungen
        String sql = "SELECT ID, Name FROM YA_Abteilung WHERE Station_ID = ? ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, station_ID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("Name"));
                }
            }
        }
    }
    
    public void listZimmer(int abteilung_ID) throws SQLException { //Liste der verfügbaren Zimmer
        String sql = "SELECT ID, Name FROM YA_Zimmer WHERE Abteilung_ID = ? ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, abteilung_ID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("Name"));
                }
            }
        }
    }
    
    public void listBetten(int zimmer_ID) throws SQLException { //Liste der verfügbaren Betten
        String sql = "SELECT ID, Name FROM YA_Bett WHERE Zimmer_ID = ? ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, zimmer_ID); 
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("Name"));
                }
            }
        }
    }
    
    //------------------------------------------------------------------------ Relationstabelle YA_PatientAdress aufrufen
     
    public void listPatientAdress(Connection conn) throws SQLException { //Liste der Relations
        String sql = "SELECT * FROM YA_PatientAdress ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {        	
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") + ", Patient_ID: " + rs.getString("Patient_ID") 
                    					+ ", Adresse_ID: " + rs.getString("Adresse_ID"));
                }
            } 
        }
    }
    
    //------------------------------------------------------------------------ Institut Informationen 
    
    //Institut Informationen ausgeben
    public void getInstitutInformation() throws SQLException {
    	List<Krankenhaus> krankenhaeuser = loadListKrankenhaus();
        for (Krankenhaus krankenhaus : krankenhaeuser) {
            loadStandorte(krankenhaus);

            if (krankenhaus.getStandorte().isEmpty()) {
                continue;
            }

            System.out.println("Krankenhaus: " + krankenhaus.getName() + " ID: " + krankenhaus.getId());
            System.out.println("Standorte:");

            for (Standort standort : krankenhaus.getStandorte()) {
                loadStationen(standort);

                if (standort.getStationen().isEmpty()) {
                    continue;
                }

                System.out.println("  - Standort: " + standort.getName() + " ID: " + standort.getId());
                System.out.println("Stationen:");

                for (Station station : standort.getStationen()) {
                    loadAbteilungen(station);

                    if (station.getAbteilungen().isEmpty()) {
                        continue;
                    }

                    System.out.println("  - Station: " + station.getName() + " ID: " + station.getId());
                    System.out.println("Abteilungen:");

                    for (Abteilungen abteilung : station.getAbteilungen()) {
                        loadZimmer(abteilung);

                        if (abteilung.getZimmer().isEmpty()) {
                            continue;
                        }

                        System.out.println("  - Abteilung: " + abteilung.getName() + " ID: " + abteilung.getId());
                        System.out.println("Zimmer:");

                        for (Zimmer zimmer : abteilung.getZimmer()) {
                            loadBetten(zimmer);

                            if (zimmer.getBetten().isEmpty()) {
                                continue;
                            }

                            System.out.println("  - Zimmer: " + zimmer.getName() + " ID: " + zimmer.getId());
                            System.out.println("Betten:");

                            for (Bett bett : zimmer.getBetten()) {
                                System.out.println("  - ID = " + bett.getId() + ", Name = " + bett.getName());
                            }
                        }
                    }
                }
            }
        }
    }
    
    //--------------- Abfrage
    //Abfrage der Krankenhäuser
    public List<Krankenhaus> loadListKrankenhaus() throws SQLException {
        String sql = "SELECT ID, Name FROM YA_Krankenhaus ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
        	//Liste zum speichern der geladenen Daten (Krankenhäuser)
            List<Krankenhaus> krankenhaeuser = new ArrayList<>();
            while (rs.next()) {
            	//erstellen eines neuen Krankenhausobjekts mit den daten aus der aktuellen Zeile
                Krankenhaus krankenhaus = new Krankenhaus(rs.getInt("ID"), rs.getString("Name"));
                krankenhaeuser.add(krankenhaus); //Hinzufügen des Krankenhauses zur Liste
            }
            return krankenhaeuser;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            LogManager.getInstance().logError("Fehler beim Abrufen der Krankenhaeuser", e);
            throw new RuntimeException("Fehler beim Abrufen der Krankenhaeuser", e);           
        }
    }
    
    //Abfrage der Standorte
    public void loadStandorte(Krankenhaus krankenhaus) throws SQLException {
        String sql = "SELECT * FROM YA_Standort WHERE Kis_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, krankenhaus.getId()); 
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Standort standort = new Standort(rs.getInt("ID"), rs.getString("Name"));
                krankenhaus.addStandort(standort);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogManager.getInstance().logError("Fehler beim Laden der Standorte fuer Krankenhaus", e);
            throw new RuntimeException("Fehler beim Laden der Standorte fuer Krankenhaus " + krankenhaus.getId(), e);
        }
    }
    
    //Abfrage der Stationen
    public void loadStationen(Standort standort) throws SQLException {
        String sql = "SELECT ID, Name FROM YA_Station WHERE Standort_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, standort.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Station station = new Station(rs.getInt("ID"), rs.getString("Name"));
                standort.addStation(station);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogManager.getInstance().logError("Fehler beim Laden der Stationen fuer Standort", e);
            throw new RuntimeException("Fehler beim Laden der Stationen fuer Standort " + standort.getId(), e);
        }
    }
    
    //Abfrage der Abteilungen	
    public void loadAbteilungen(Station station) throws SQLException {
        String sql = "SELECT ID, Name FROM YA_Abteilung WHERE Station_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, station.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Abteilungen abteilung = new Abteilungen(rs.getInt("ID"), rs.getString("Name"));
                station.addAbteilung(abteilung);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogManager.getInstance().logError("Fehler beim Laden der Abteilungen fuer Station", e);
            throw new RuntimeException("Fehler beim Laden der Abteilungen fuer Station " + station.getId(), e);
        }
    }

    //Abfrage der Zimmer
    public void loadZimmer(Abteilungen abteilung) throws SQLException {
        String sql = "SELECT ID, Name FROM YA_Zimmer WHERE Abteilung_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, abteilung.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Zimmer zimmer = new Zimmer(rs.getInt("ID"), rs.getString("Name"));
                abteilung.addZimmer(zimmer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogManager.getInstance().logError("Fehler beim Laden der Zimmer fuer Abteilungen", e);
            throw new RuntimeException("Fehler beim Laden der Zimmer fuer Abteilungen " + abteilung.getId(), e);
        }
    }
    
    //Abfrage der Betten
    public void loadBetten(Zimmer zimmer) throws SQLException {
        String sql = "SELECT ID, Name FROM YA_Bett WHERE Zimmer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, zimmer.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bett bett = new Bett(rs.getInt("ID"), rs.getString("Name"));
                zimmer.addBett(bett);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            LogManager.getInstance().logError("Fehler beim Laden der Betten fuer Zimmer", e);
            throw new RuntimeException("Fehler beim Laden der Betten fuer Zimmer " + zimmer.getId(), e);
        }
    }
}

