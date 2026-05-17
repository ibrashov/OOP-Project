package university.service;

import university.model.users.User;
import java.util.List;

public class AuthenticationService {
    public static User login(List<User> allUsers, int inputId, String inputPassword) {
        if (allUsers == null) {
            System.out.println("Error: User list is empty.");
            return null;
        }

        for (User user : allUsers) {
            if (user != null
                    && user.getId() == inputId
                    && user.isActive()
                    && user.getPasswordHash() != null
                    && user.getPasswordHash().equals(inputPassword)) {
                System.out.println("Welcome, " + user.getFullname() + "!");
                return user;
            }
        }
        System.out.println("Error: Invalid ID or password.");
        return null; 
    }
}
