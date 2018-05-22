package komodocrypto.services.salesforce.ryan_force_sdk;

import com.force.api.ApiConfig;
import com.force.api.ForceApi;
import com.force.api.QueryResult;

import java.util.List;

/**
 * Created by ryandesmond on 5/21/18.
 */
public class TestController {

    public static void main(String[] args) {
        ApiConfig mycfg = new ApiConfig().setApiVersionString("v42.0");

//        ForceApi api = new ForceApi(new ApiConfig()
//                .setUsername("jbudreski@codingnomads.co")
//                .setPassword("codingnomads0"));

        ForceApi api = new ForceApi(new ApiConfig()
                .setUsername("[SET ME]")
                .setPassword("[SET ME]")
                .setClientId("[SET ME]")
                .setClientSecret("[SET ME]"));

        Account a = new Account();
        KomodoUser obj = new KomodoUser("CodingNomads3", "ryan3@codingnomads.co", "Ryan Desmond 3", "password3", 5);

        // Create an Account in Salesforce
        a.setName("Ryan Test Account 2");
        String account_id = api.createSObject("account", a);
        System.out.println("Account ID = " + account_id);

        // Create a Komodo_User__c in Salesforce
        String user_id = api.createSObject("Komodo_User__c/", obj);
        System.out.println("Komodo_User__c ID = " + user_id);

        // this doesn't quite work yet - I need to chat with you - I don't have access to the Komodo_User__c in Salesforce which I need
        //QueryResult<KomodoUser> users = api.query("SELECT * FROM Komodo_User__c'", KomodoUser.class);

        System.out.println("all done!");
    }

}
