package komodocrypto.model.database;

public class User {

    int user_id;
    String name;
    String password;
    String email;
    int userSettings_id;

    public User(int user_id, String name, String password, String email, int userSettings_id) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.userSettings_id = userSettings_id;
    }

    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserSettings_id() {
        return userSettings_id;
    }

    public void setUserSettings_id(int userSettings_id) {
        this.userSettings_id = userSettings_id;
    }
}
