package sea.cpsc233projectgui;

import java.util.ArrayList;
import java.util.List;

public class Login {
    private List<User> users;

    public Login() {
        // Initialize users or load from database
        users = new ArrayList<>();
        users.add(new User("admin", "admin123"));

    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Login successful
            }
        }
        return false; // Login failed
    }
}