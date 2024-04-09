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

}