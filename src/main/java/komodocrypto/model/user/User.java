package komodocrypto.model.user;

public class User {

    int user_id;
    String first_name;
    String last_name;
    String password;
    String email;
    int userSettings_id;

    public User(int user_id, String first_name, String last_name, String password, String email, int userSettings_id) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
