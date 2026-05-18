package university.model.users;

import java.io.Serializable;

public abstract class User implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String fullname;
    private String email;
    private String passwordHash;
    private boolean isActive;

    public User(String id, String fullname, String email, String passwordHash, boolean isActive) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
    }

    public boolean signIn(String email, String passwordHash) {
        return this.email.equals(email) && this.passwordHash.equals(passwordHash) && this.isActive;
    }

    public void signOut() {
        // AuthenticationService owns the active session; User keeps account data only.
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
    }

    public void deleteAccount() {
        this.isActive = false;
    }

    public abstract String getRole();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", fullname='" + fullname + "', email='" + email + "', isActive=" + isActive + '}';
    }
}
