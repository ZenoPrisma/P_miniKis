# Design Patterns in miniKis

## Implemented Patterns
- **Singleton**: `LogManager` garantiert eine einzige Logging-Instanz und kapselt die Konfiguration.
- **Factory**: `PersonFactory` erstellt `Patient`- und `Employees`-Objekte zentral, sodass der Aufrufer nicht die konkreten Klassen kennen muss.

## Weitere mögliche Muster
- **Observer**: Koennte eingesetzt werden, um z.B. UI-Komponenten oder andere Services automatisch zu informieren, wenn sich Patientendaten aendern.
- **Facade**: Eine Fassade fuer Datenbankoperationen koennte den Zugriff auf das komplexe Subsystem aus SQL-Anfragen vereinfachen.
- **Singleton fuer Datenbankverbindung**: Eine zentrale Verbindung wuerde Mehrfachverbindungen vermeiden und den Zugriff vereinheitlichen.
