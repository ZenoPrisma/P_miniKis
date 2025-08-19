package minKis;

public class Adresse {
    private int id;
    private int plz;
    private String stadt;
    private String land;
    private String strassenname;
    private int strassennummer;

    // Standardkonstruktor
    public Adresse() {
    }

    // Konstruktor mit allen Attributen
    public Adresse(int id, int plz, String stadt, String land, String strassenname, int strassennummer) {
        this.id = id;
        this.plz = plz;
        this.stadt = stadt;
        this.land = land;
        this.strassenname = strassenname;
        this.strassennummer = strassennummer;
    }

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getStrassenname() {
        return strassenname;
    }

    public void setStrassenname(String strassenname) {
        this.strassenname = strassenname;
    }

    public int getStrassennummer() {
        return strassennummer;
    }

    public void setStrassennummer(int strassennummer) {
        this.strassennummer = strassennummer;
    }

    //Override: Überschreibt die toString Methode für bessere Darstellung
    @Override
    public String toString() {
        return  "ID = " + id +
                ", PLZ = " + plz + 
                ", Stadt = " + stadt +
                ", Land = " + land + 
                ", Strassenname = " + strassenname + 
                ", Strassennummer = " + strassennummer;
    }	
}
