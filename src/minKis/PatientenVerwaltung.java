package minKis;

import java.sql.*;

import java.util.*;

public class PatientenVerwaltung {
    private Connection conn;
    
    public static void main(String[] args) { //Verbindungsdaten Datenbank
        final String url = "jdbc:oracle:thin:@//localhost:1521/MF19";
        final String user = "mf_manager";
        final String password = "mf_manager";
        
        try {
            PatientenVerwaltung patientenVerwaltung = new PatientenVerwaltung(url, user, password);
            patientenVerwaltung.startPatientenverwaltung();                     

        } catch (SQLException e) {
        	System.out.println("Verbindung zur Datenbank konnte nicht hergestellt werden: " + e.getMessage());
            LogManager.getInstance().logError("Verbindung zur Datenbank fehlgeschlagen!", e);
            return;
        }
    }
    
    ///Konstruktor, der die Verbindung zur Datenbank initialisiert
    public PatientenVerwaltung(String url, String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
        new KISController(conn);
        System.out.println("Verbunden!");
        System.out.println("Willkommen zur Patientenverwaltung!");
        LogManager.getInstance().logInfo("Eine Verbindung zur Datenbank wurde Hergestellt.");
    }

    public void startPatientenverwaltung() {
        Scanner sc = new Scanner(System.in);
        KISController patientController;
      
        try {
        	patientController = new KISController(conn);
            while (true) {
            	System.out.println("");
                System.out.println("Was möchten Sie tun?");
                System.out.println("1. Patienten anzeigen");
                System.out.println("2. Patienten hinzufügen");
                System.out.println("3. Patienten löschen");
                System.out.println("4. Adressen anzeigen");
                System.out.println("5. Adressen löschen");
                System.out.println("6. Mitarbeiter anzeigen");
                System.out.println("7. Mitarbeiter hinzufügen");
                System.out.println("8. Relationstabelle anzeigen");
                System.out.println("9. Institut Informationen");
                System.out.println("10. Mitarbeiter anzeigen (Interfaces)");
                System.out.println("123. Patientenverwaltung herunterfahren.");
                System.out.println("");
                
                int choice = sc.nextInt();
                
                if (choice == 123) {
                    System.out.println("Patientenverwaltung wird heruntergefahren...");
                    LogManager.getInstance().logInfo("Eine Verbindung zur Datenbank wurde geschlossen.");
                    break;
                } 

                switch (choice) {
                    case 1:
                    	List<Patient> patients = patientController.getAllPatients();
                    	List<Adresse> adressen = patientController.getAllAdress();
                    	for (Patient patient : patients) {
                    		List<Adresse> patientAdressen = patientController.getAdressFromPatients(patient.getID(), adressen);
                    		patient.addAdressen(patientAdressen);
                    		System.out.println(patient);
                		}
                        break;
                    case 2:
                    	patientController.addPatient(sc);	
                        break;
                    case 3:
                    	patientController.deletePatient(sc, conn);
                        break;
                    case 4: 
                    	List<Adresse> allAdressen = patientController.getAllAdress();
                        for (Adresse adresse : allAdressen) {
                        	System.out.println(adresse);
            			}
                    	break;
                    case 5:
                    	patientController.deleteAddress(sc, conn);
                    	break;
                    case 6:
                    	List<Employees> allMitarbeitern = patientController.getAllMitarbeiter();
                    	for (Employees mitarbeiter : allMitarbeitern) {
							System.out.println(mitarbeiter);
						}
                    	break; 
                    case 7:
                    	patientController.addMitarbeiter(sc);
                        break;
                    case 8:
                    	patientController.listPatientAdress(conn);
                    	break;
                    case 9:
                    	patientController.getInstitutInformation();
                    	break;
                    case 10: 
                    	List<IPerson> allIMitarbeiter = patientController.getAllIMitarbeiter();  
                    	for (IPerson imitarbeiter : allIMitarbeiter) {
                           	System.out.println(imitarbeiter.toString());
                        }
                    	break;
                    default:
                        System.out.println("Ungueltige Eingabe. Bitte erneut versuchen.");
                        break; 
                }
            }
        } catch (SQLException e) {
        	System.out.println("Ein Datenbankfehler ist aufgetreten: " + e.getMessage());
            LogManager.getInstance().logError("!Datenbank Error!", e);
		} finally {
            sc.close();
        }
    }
}




