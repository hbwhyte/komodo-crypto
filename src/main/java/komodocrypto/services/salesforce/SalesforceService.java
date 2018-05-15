package komodocrypto.services.salesforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;
import org.springframework.stereotype.Service;

@Service
public class SalesforceService {

        static final String USERNAME     = "jbudreski@codingnomads.co";
        static final String PASSWORD     = "codingnomads0";
        static final String LOGINURL     = "https://login.salesforce.com";
        static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
        static final String CLIENTID     = "3MVG9d8..z.hDcPIDPRSQ6j6WmOZQTVF_vGrR3NoWdSvvvbkb35RpF8C8z7bhVb7a8dY6uG7B26rfOmuutOyW";
        static final String CLIENTSECRET = "5036308529578685178";
        private static String REST_ENDPOINT = "/services/data" ;
        private static String API_VERSION = "/v32.0" ;
        private static String baseUri;
        private static Header oauthHeader;
        private static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
        private static String leadId ;

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

        // Create Leads using REST HttpPost
        public static void createKomodoUser() {
            System.out.println("\n_______________ Komodo User INSERT _______________");

            //********************************
            //https://ap5.lightning.force.com/one/one.app#/sObject/Komodo_User__c/
            //UPDATE baseUri????
            //********************************

            String uri = baseUri + "/sobjects/Lead/";
            try {

                //create the JSON object containing the new KomodoUser details.
                //*****************************
                //Value in key-value pairs will need to be updated to pull data from new user registration page
                //*****************************

                JSONObject komodoUser = new JSONObject();
                komodoUser.put("Komodo User Name", "iAmaUser1000");
                komodoUser.put("name", "Stan Rogers");
                komodoUser.put("user_id", 0);
                komodoUser.put("password", "password");
                komodoUser.put("email", "testing@beepboop.com");
                komodoUser.put("userSettings_id",1 );


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
                    // Store the retrieved lead id to use when we update the lead.
                    leadId = json.getString("id");
                    System.out.println("New Lead id from response: " + leadId);
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

    

