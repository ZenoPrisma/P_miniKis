package minKis;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person implements IPerson {
    private int insID; // Institut-ID (ehemals Krankenhaus-ID)
    private List<Adresse> adressen; // Adresse-ID

    // Standardkonstruktor
    public Patient() {
        adressen = new ArrayList<>();
    }

    // Konstruktor mit allen Attributen
    public Patient(int id, String name, String vorname, Date geburtsdatum, int insID) {
        super(id, name, vorname, geburtsdatum);
        this.insID = insID;
        this.adressen = new ArrayList<>();
    }

    public void addAdresse(Adresse adresse) {
        adressen.add(adresse);
    }

    public int removeAdresse(int id) {
        int result = 0;
        for (Adresse adresse : adressen) {
            if (adresse.getId() == id) {
                boolean aDel = adressen.remove(adresse);
                if (!aDel) {
                    result = -1;
                } else {
                    result = 1;
                }
            }
        } 
        return result;
    }
    
    public void addAdressen(List<Adresse> adressen) {
        this.adressen.addAll(adressen);
    }

    public int getInsID() {
        return insID;
    }

    public void setInsID(int insID) {
        this.insID = insID;
    }

    // Override: Überschreibt die toString Methode für bessere Darstellung
    @Override
    public String toString() {
        return "ID = " + getID() +
               ", Name = " + getName() +
               ", Vorname = " + getVorname() +
               ", Geburtsdatum = " + getGeburtsdatum() +
               ", InsID = " + insID;
    }
}
