package university.model.users;

<<<<<<< HEAD
import java.io.Serializable;

public abstract class User implements Cloneable, Serializable{
    private int id;
    private String fullname;
=======
public abstract class User {
    private String id;
    private String fullName;
>>>>>>> 889aaa7 (Add academic module)
    private String email;
    private String passwordHash;
    private boolean isActive;

<<<<<<< HEAD
    public User(int id, String fullname, String email, String passwordHash, boolean isActive) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean signIn(String email, String passwordHash) {
        return this.email.equals(email) && this.passwordHash.equals(passwordHash) && this.isActive;
    }

    public void signOut() {
        // Logic for signing out (e.g., clearing session data)
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
=======
    public User(String id, String fullName, String email, String passwordHash) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = true;
    }

    public abstract boolean signIn(String email, String password);
    public abstract void signOut();

    public void changePassword(String newPassword) {
        this.passwordHash = newPassword;
        System.out.println("Password changed.");
>>>>>>> 889aaa7 (Add academic module)
    }

    public void deleteAccount() {
        this.isActive = false;
<<<<<<< HEAD
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();   
    }   

    @Override
    public String toString() {
        return "User{" + "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                '}';
    }
=======
        System.out.println("Account deleted.");
    }

    // Getters
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isActive() { return isActive; }
>>>>>>> 889aaa7 (Add academic module)
}