package minKis;

import java.sql.Date;

public interface IPerson /* Interface */{
    int getID();
    String getName();
    String getVorname();
    Date getGeburtsdatum(); 
    
   void setID(int _id); 
   void setName(String _name);
   void setVorname(String _vorname);
   void setGeburtsdatum(Date _geburtsdatum); 
}
