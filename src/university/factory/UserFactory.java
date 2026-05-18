package university.factory;

import university.model.users.*;
import university.enums.*;
import university.service.UniversitySystem;
import java.util.Calendar;

public class UserFactory {

    public static User createUser(String type, String fullname, String email, String passwordHash) {
        if (type == null) return null;

        String finalId = generateUserId(type, null);
        boolean defaultActive = true;

        return switch (type.toUpperCase()) {
            case "TEACHER" -> new Teacher(finalId, fullname, email, passwordHash, defaultActive,
                    "TCH-" + finalId, TeacherTitle.LECTURER, 250000.0, "EMP-" + finalId);

            case "ADMIN" -> new Admin(finalId, fullname, email, passwordHash, defaultActive, 350000.0, "ADM-" + finalId);

            case "MANAGER" -> new Manager(finalId, fullname, email, passwordHash, defaultActive, 200000.0, "MGR-" + finalId, ManagerType.OR);

            default -> throw new IllegalArgumentException("Unknown staff type: " + type);
        };
    }

    public static Student createStudent(String fullname, String email, String passwordHash, DegreeType degreeType) {
        if (degreeType == null) {
            throw new IllegalArgumentException("У студента должна быть указана степень!");
        }

        String finalId = generateUserId("STUDENT", degreeType);
        return new Student(finalId, fullname, email, passwordHash, true, finalId, "Computer Science", degreeType);
    }

    private static String generateUserId(String type, DegreeType degreeType) { 
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String yearPart = String.format("%02d", currentYear % 100);

        String letterPart = switch (type.toUpperCase()) {
            case "ADMIN"   -> "A";
            case "TEACHER" -> "T";
            case "MANAGER" -> "M";
            default        -> "U"; 
        };

        if (type.equalsIgnoreCase("STUDENT")) {
            letterPart = switch (degreeType) {
                case BACHELOR -> "B";
                case MASTER -> "M";
                case PHD -> "D";
            };
        }

        int seqNumber = UniversitySystem.getInstance().getNextSequenceNumber();
        String seqPart = String.format("%06d", seqNumber);

        return yearPart + letterPart + seqPart;
    }
}
