package minKis;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
    
    private static final String LOG_FILE_PATH = "E:\\Git\\EDUCATION.DEV\\miniKis\\logs"; // Pfad anpassen
    
    private static Logger logger;

    static {
        try {
            // Konfiguration des Loggers
            logger = Logger.getLogger("KIS-Logger"); // Name des Loggers
            logger.setLevel(Level.INFO); // Level des Loggers
            
            FileHandler filehandler = new FileHandler(LOG_FILE_PATH + "/logfile.log", true); 
            filehandler.setFormatter(new SimpleFormatter()); // Einfacher Formatter
            logger.addHandler(filehandler); // Handler zum Logger hinzufügen
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Information Loggen
    public static void logInfo(String message) {
        logger.info(message);        
    }

    // Warning Loggen
    public static void logWarning(String message) {
        logger.warning(message);
    }

    // Error Loggen
    public static void logError(String message, Throwable throwable) {
        logger.severe(message);
        if (throwable != null) {
            logger.severe(throwable.getMessage());
        }
    }
}

