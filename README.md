# Komodo Crypto


## Security
The Komodo API is secured with OAuth2. In order to obtain an access token
you should make a POST request to the Authorization Server with your client credentials.
```
curl -X POST --user 'client_id:client_secret' -d 'grant_type=client_credentials' http://localhost:8080/oauth/token
```

This will return an access token that will expire after the allotted time has passed.
You can make a request to any endpoint using the access token in the authorization header.
```
curl -X GET -H "Authorization: Bearer {access_token}" http://localhost:8080/test-security
```