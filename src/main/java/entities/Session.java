package entities;

public class Session {
    private static Session instance;
    private User currentUser;
    private String currentUserEmail; // New field to store email directly

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // New method to set current user's email
    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
    }

    // New method to get current user's email
    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void clearSession() {
        currentUser = null;
        currentUserEmail = null; // Clear email along with user object
    }
}
