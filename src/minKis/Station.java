package minKis;

import java.util.*;

public class Station {
	private int id;
	private String name;
	private List<Abteilungen> abteilung;
	
	//Standardkonstruktor
	public Station() {
	}
	
	//Konstruktor mit allen Attributen
	public Station(int id, String name) {
		this.id = id;
		this.name = name;
		this.abteilung = new ArrayList<Abteilungen>();
	}
	
	public void addAbteilung(Abteilungen abteilungen) {
		abteilung.add(abteilungen);
	}
	
	public int removeAbteilungen(int id) {
		int result = 0;
		for (Abteilungen abteilungen : abteilung) {
			if (abteilungen.getId() == id) {
				boolean cDel = abteilung.remove(abteilungen);
				if (!cDel) {
					result = -1;
				} else {
					result = 1;
				}
			}
		}
		return result;
	}
	
	//Getter und Setter
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	Abteilungen abteilungen = new Abteilungen();
	
	public String toString() {
	    String result = "";
	    
	    // Stationsinformationen
	    result += "Station: " + name;
	    result += " ID: " + id + "\n";
	    result += "Abteilungen:\n";
	    
	    // Einrückung für Abteilungen
	    for (Abteilungen abteilungen : abteilung) {
	        result += "  - " + abteilungen.toString() + "\n";
	    }
	    
	    return result;
	}

	public List<Abteilungen> getAbteilungen() {
	    return abteilung;
	}
}
