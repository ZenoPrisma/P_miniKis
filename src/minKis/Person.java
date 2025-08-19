package minKis;

import java.sql.Date;

public class Person /* Superklasse */ {
    private int id;
    private String name;
    private String vorname;
    private Date geburtsdatum;

    // Standardkonstruktor
    public Person() {
    }

    // Konstruktor mit allen Attributen
    public Person(int id, String name, String vorname, Date geburtsdatum) {
        this.id = id;
        this.name = name;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum; 
    }

    // Getter und Setter für Person Attribute
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
}

