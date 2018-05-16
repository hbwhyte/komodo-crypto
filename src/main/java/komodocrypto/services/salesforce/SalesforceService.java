package komodocrypto.services.salesforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;
import org.springframework.stereotype.Service;

@Service
public class SalesforceService {

        static final String USERNAME     = "";
        static final String PASSWORD     = "";
        static final String LOGINURL     = "https://login.salesforce.com";
        static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
        static final String CLIENTID     = "";
        static final String CLIENTSECRET = "";
        private static String REST_ENDPOINT = "/services/data" ;
        private static String API_VERSION = "/v32.0" ;
        private static String baseUri;
        private static Header oauthHeader;
        private static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
        private static String komodoUserSFID ;
        //private static String baseUrl = "https://ap5.lightning.force.com/one/one.app#";

    public static Header getOauthHeader() {
        return oauthHeader;
    }

    public static void setOauthHeader(Header oauthHeader) {
        SalesforceService.oauthHeader = oauthHeader;
    }

    public static void authenticate() {

        HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        String loginURL = LOGINURL +
                GRANTSERVICE +
                "&client_id=" + CLIENTID +
                "&client_secret=" + CLIENTSECRET +
                "&username=" + USERNAME +
                "&password=" + PASSWORD;
        System.out.println(loginURL);

        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpPost);
        } catch (ClientProtocolException cpException) {
            cpException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return;
        }

        String getResult = null;
        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        JSONObject jsonObject = null;
        String loginAccessToken = null;
        String loginInstanceUrl = null;

        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION ;
        oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken) ;
        System.out.println("oauthHeader1: " + oauthHeader);
        System.out.println("\n" + response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: "+loginInstanceUrl);
        System.out.println("access token/session ID: "+loginAccessToken);
        System.out.println("baseUri: "+ baseUri);

        // release connection
        httpPost.releaseConnection();
    }

    // Query Komodo Users using REST HttpGet
    public static void queryKomodoUsers() {
        System.out.println("\n_______________ Komodo User QUERY _______________");
        try {

            //Set up the HTTP objects needed to make the request.
            HttpClient httpClient = HttpClientBuilder.create().build();

            String uri = baseUri + "/query?q=Select+user_id__c+,+Name+,+name__c+,+password__c+,+email__c+,+userSettings_id__c+From+Komodo_User__c+Limit+5";
            System.out.println("Query URL: " + uri);
            HttpGet httpGet = new HttpGet(uri);
            System.out.println("oauthHeader2: " + oauthHeader);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);

            // Make the request.
            HttpResponse response = httpClient.execute(httpGet);

            // Process the result
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String response_string = EntityUtils.toString(response.getEntity());
                try {
                    JSONObject json = new JSONObject(response_string);
                    System.out.println("JSON result of Query:\n" + json.toString(1));
                    JSONArray j = json.getJSONArray("records");
                    for (int i = 0; i < j.length(); i++){
                        komodoUserSFID = json.getJSONArray("records").getJSONObject(i).getString("user_id__c");
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            } else {
                System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                System.out.println(getBody(response.getEntity().getContent()));
                System.exit(-1);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    // Create Leads using REST HttpPost

    public static void createKomodoUser() {
        System.out.println("\n_______________ Komodo User INSERT _______________");

        String uri = baseUri + "/sobjects/Komodo_User__c/";

        try {

            //create the JSON object containing the new lead details.
            JSONObject komodoUser = new JSONObject();
            komodoUser.put("Name", "moneyluvaa");
            komodoUser.put("email__c", "luloo@hotmail.com");
            komodoUser.put("name__c", "laura loo");
            komodoUser.put("password__c", "isuckatpasswords100");
            komodoUser.put("userSettings_id__c", 5);
            komodoUser.put("user_id__c", 11);


            System.out.println("JSON for lead record to be inserted:\n" + komodoUser.toString(1));

            //Construct the objects needed for the request
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost httpPost = new HttpPost(uri);
            httpPost.addHeader(oauthHeader);
            httpPost.addHeader(prettyPrintHeader);
            // The message we are going to post
            StringEntity body = new StringEntity(komodoUser.toString(1));
            body.setContentType("application/json");
            httpPost.setEntity(body);

            //Make the request
            HttpResponse response = httpClient.execute(httpPost);

            //Process the results
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 201) {
                String response_string = EntityUtils.toString(response.getEntity());
                JSONObject json = new JSONObject(response_string);

                 //Store the retrieved lead id to use when we update the lead.
                komodoUserSFID = json.getString("id");
                System.out.println("New Komodo User Name from response: " + komodoUserSFID);
            } else {
                System.out.println("Insertion unsuccessful. Status code returned is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    // Update Leads using REST HttpPatch. We have to create the HTTPPatch, as it does not exist in the standard library
    // Since the PATCH method was only recently standardized and is not yet implemented in Apache HttpClient
    public static void updateKomodoUser() {
        System.out.println("\n_______________ Komodo User UPDATE _______________");

        //Notice, the id for the record to update is part of the URI, not part of the JSON
        String uri = baseUri + "/sobjects/Komodo_User__c/" + komodoUserSFID;
        try {
            //Create the JSON object containing the updated lead last name
            //and the id of the lead we are updating.
            JSONObject komodoUser = new JSONObject();
            komodoUser.put("Name", "ThisIsMYNEWNAME!!");
            System.out.println("JSON for update of lead record:\n" + komodoUser.toString(1));

            //Set up the objects necessary to make the request.
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.addHeader(oauthHeader);
            httpPatch.addHeader(prettyPrintHeader);
            StringEntity body = new StringEntity(komodoUser.toString(1));
            body.setContentType("application/json");
            httpPatch.setEntity(body);

            //Make the request
            HttpResponse response = httpClient.execute(httpPatch);

            //Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                System.out.println("Updated the lead successfully.");
            } else {
                System.out.println("Lead update NOT successfully. Status code is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    // Extend the Apache HttpPost method to implement an HttpPatch
    private static class HttpPatch extends HttpPost {
        public HttpPatch(String uri) {
            super(uri);
        }

        public String getMethod() {
            return "PATCH";
        }
    }

    private static String getBody(InputStream inputStream) {
        String result = "";
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            String inputLine;
            while ( (inputLine = in.readLine() ) != null ) {
                result += inputLine;
                result += "\n";
            }
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }
}

    

