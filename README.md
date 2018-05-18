# Komodo Crypto


## Indicators

```
/dailyindicator?type={INDICATOR}&fromcurrency={BASE_CURRENCY}&tocurrency={COUNTER_CURRENCY}&trailing={DAYS}
```

An indicator can be the following:
* SMA - Simple Moving Average
* EMA - Exponential Moving Average

The trailing days must be between 1 and 250, and there must be enough historical daily data.

## Security
The Komodo API is secured with OAuth2. To access secure endpoints you need to exchange your client
credentials for an access token.

You can register client credentials by making a POST request to the Authorization Server with your email as the
client_id and a client_secret of your choosing.

```
curl -X POST -H 'Content-Type: application/json' -d '{"client_id": "{YOUR_EMAIL_HERE}", "client_secret": "temp-secret", "authorized_grant_types":"client_credentials"}' http://localhost:8080/oauth/client
```

In order to obtain an access token
you should make a POST request to the Authorization Server with your client credentials.
```
curl -X POST --user '{YOUR_EMAIL_HERE}:temp-secret' -d 'grant_type=client_credentials' http://localhost:8080/oauth/token
```

This will return an access token that will expire after the allotted time has passed.
You can make a request to any endpoint using the access token in the authorization header.
```
curl -X GET -H "Authorization: Bearer {access_token}" http://localhost:8080/test-security
```
