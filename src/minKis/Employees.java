package minKis;

import java.sql.Date;

public class Employees implements IPerson {
    private int id;
    private String name;
    private String vorname;
    private Date geburtsdatum;
    //Nicht in IPerson implementiert
    private String type; // z.B., DOCTOR, CARE
    
    // Standardkonstruktor
    public Employees() {
    }
    
    // Konstruktor mit allen Attributen
    public Employees(int id, String name, String vorname, Date geburtsdatum, String type) {
    	this.id = id; //1. Art es zu machen
    	setName(name); //2. Art es zu machen
    	setVorname(vorname);
    	setGeburtsdatum(geburtsdatum);
        this.type = type;
    }
    
    // Getter und Setter für Mitarbeiter Attribute
	@Override
    public int getID() {
        return id;
    }
		
	@Override
    public void setID(int id) {
        this.id = id;
    }
	
	@Override
    public String getName() {
        return name;
    }
	
	@Override
    public void setName(String name) {
        this.name = name;
    }
	
	@Override
    public String getVorname() {
        return vorname;
    }

	@Override
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

	@Override
    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

	@Override
    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

	// Getter und Setter für spezifische Mitarbeiter Attribute (type)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    } 

	// Override: Überschreibt die toString Methode für bessere Darstellung
    @Override
    public String toString() {
    	if (type == null) { //Interface ausgabe (ohne type)
    		return "ID = " + getID() +
 	               ", Name = " + getName() +
 	               ", Vorname = " + getVorname() +
 	               ", Geburtsdatum = " + getGeburtsdatum();
    	} else { //Vollständige ausgabe (mit type)
    		return "ID = " + getID() +
    	               ", Name = " + getName() +
    	               ", Vorname = " + getVorname() +
    	               ", Geburtsdatum = " + getGeburtsdatum() +
    	               ", Type = " + type;
    	}      
    }
} 

