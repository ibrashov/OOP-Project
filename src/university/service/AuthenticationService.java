package university.service;

import university.model.users.User;
import java.util.List;

public class AuthenticationService {
    public static User login(List<User> allUsers, int inputId, String inputPassword) {
        for (User user : allUsers) {
            if (user.getId() == inputId && user.getPasswordHash().equals(inputPassword)) {
                System.out.println("Welcome, " + user.getFullname() + "!");
                return user;
            }
        }
        System.out.println("Error: Invalid ID or password.");
        return null; 
    }
}