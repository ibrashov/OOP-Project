package university.service;

import university.model.users.User;

public class AuthenticationService {
    private static AuthenticationService instance;
    private User currentUser; 

    private AuthenticationService() {} 

    public static AuthenticationService getInstance() {
        if (instance == null) { instance = new AuthenticationService(); }
        return instance;
    }

    public boolean login(String credential, String passwordHash) {
        if (credential == null || passwordHash == null) return false;
        UniversitySystem system = UniversitySystem.getInstance();
        User user = system.findByEmail(credential);
        
        if (user == null) { user = system.findById(credential); }

        if (user != null && user.getPasswordHash().equals(passwordHash) && user.isActive()) {
            this.currentUser = user;
            return true;
        }
        return false; 
    }

    public void logout() { this.currentUser = null; }
    public User getCurrentUser() { return currentUser; }
}
