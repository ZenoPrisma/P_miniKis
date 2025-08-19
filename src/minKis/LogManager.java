package minKis;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {

    private static final String LOG_FILE_PATH = "logs"; // Relativer Pfad zum Logverzeichnis

    private static LogManager instance;
    private Logger logger;

    private LogManager() {
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

    public static synchronized LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    // Information Loggen
    public void logInfo(String message) {
        logger.info(message);
    }

    // Warning Loggen
    public void logWarning(String message) {
        logger.warning(message);
    }

    // Error Loggen
    public void logError(String message, Throwable throwable) {
        logger.severe(message);
        if (throwable != null) {
            logger.severe(throwable.getMessage());
        }
    }
}

