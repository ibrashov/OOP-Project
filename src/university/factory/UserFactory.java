package university.factory;

import university.model.users.*;
import university.enums.*;

public class UserFactory {
    public static User createUser(String type, int id, String fullname, String email, String passwordHash) {
        if (type == null) return null;

        switch (type.toUpperCase()) {
            case "STUDENT":
                return new Student(id, fullname, email, passwordHash, true, "24B" + id, "Computer Science");

            case "TEACHER":
                return new Teacher(id, fullname, email, passwordHash, true,
                            "T-" + id, TeacherTitle.LECTURER, 250000.0, "EMP-" + id);

            case "ADMIN":
                return new Admin(id, fullname, email, passwordHash, true, 350000.0, "ADM-" + id);

            case "MANAGER":
                return new Manager(id, fullname, email, passwordHash, true, 200000.0, "MGR-" + id, ManagerType.OR);

            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}
