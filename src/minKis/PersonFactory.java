package minKis;

public class PersonFactory {
    public enum PersonType {
        PATIENT,
        EMPLOYEE
    }

    public static IPerson createPerson(PersonType type) {
        switch (type) {
            case PATIENT:
                return new Patient();
            case EMPLOYEE:
                return new Employees();
            default:
                throw new IllegalArgumentException("Unbekannter Personentyp: " + type);
        }
    }
}
