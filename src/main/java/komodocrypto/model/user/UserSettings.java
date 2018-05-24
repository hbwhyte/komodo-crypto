package komodocrypto.model.user;

public class UserSettings {

    int userSettings_id;
    String risk_tolerance;

    public UserSettings(int userSettings_id, String risk_tolerance) {
        this.userSettings_id = userSettings_id;
        this.risk_tolerance = risk_tolerance;
    }

    public UserSettings() {
    }

    public int getUserSettings_id() {
        return userSettings_id;
    }

    public void setUserSettings_id(int userSettings_id) {
        this.userSettings_id = userSettings_id;
    }

    public String getRisk_tolerance() {
        return risk_tolerance;
    }

    public void setRisk_tolerance(String risk_tolerance) {
        this.risk_tolerance = risk_tolerance;
    }
}
