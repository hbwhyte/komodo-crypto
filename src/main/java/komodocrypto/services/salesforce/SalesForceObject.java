package komodocrypto.services.salesforce;

/**
 * Created by ryandesmond on 5/16/18.
 */
public class SalesForceObject {

    String Name;
    String email__c;
    String name__c;
    String password__c;
    double userSettings_id__c;
    int user_id__c;

    public SalesForceObject(String name, String email__c, String name__c, String password__c, double userSettings_id__c, int user_id__c) {
        Name = name;
        this.email__c = email__c;
        this.name__c = name__c;
        this.password__c = password__c;
        this.userSettings_id__c = userSettings_id__c;
        this.user_id__c = user_id__c;
    }

    public SalesForceObject(String name, String email__c, String name__c, String password__c, double userSettings_id__c) {
        Name = name;
        this.email__c = email__c;
        this.name__c = name__c;
        this.password__c = password__c;
        this.userSettings_id__c = userSettings_id__c;
        this.user_id__c = user_id__c;
    }

    public SalesForceObject() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail__c() {
        return email__c;
    }

    public void setEmail__c(String email__c) {
        this.email__c = email__c;
    }

    public String getName__c() {
        return name__c;
    }

    public void setName__c(String name__c) {
        this.name__c = name__c;
    }

    public String getPassword__c() {
        return password__c;
    }

    public void setPassword__c(String password__c) {
        this.password__c = password__c;
    }

    public double getUserSettings_id__c() {
        return userSettings_id__c;
    }

    public void setUserSettings_id__c(double userSettings_id__c) {
        this.userSettings_id__c = userSettings_id__c;
    }

    public int getUser_id__c() {
        return user_id__c;
    }

    public void setUser_id__c(int user_id__c) {
        this.user_id__c = user_id__c;
    }
}
