# Komodo Crypto

Komodo Crypto integrates with the following exchanges:
  - Binance
  - Bitstamp
  - Bittrex
  - GDAX
  - Kraken
  
## Deployment

### API Keys

In order to run Komodo Crypto, you will need verified accounts and API keys for 
each of our integrated exchanges. You can enter your specific credentials into the 
`application.properties` file in the `resources` directory. Please note, it can 
sometimes take some time to verify your account, and if you have an account with 
coins in it be careful before running any methods that will make actual changes 
to your account.  

### Dependencies

Before starting the application, you need to resolve the Binance dependency by building
the maven artifact yourself. After that the dependency can be resolved through your 
local maven repository. To do this you need to:

1) Clone the binance-java-api git repo
2) Change to that directory
3) Issue a mvn install

```
git clone https://github.com/binance-exchange/binance-java-api.git
cd binance-java-api
mvn install
```

## Indicators

```
/dailyindicator?type={INDICATOR}&fromcurrency={BASE_CURRENCY}&tocurrency={COUNTER_CURRENCY}&trailing={DAYS}
```

An indicator can be the following:
* SMA - Simple Moving Average
* EMA - Exponential Moving Average

The trailing days must be between one and the historical daily data available.

## MVC Security
The Komodo MVC is secured with Basic Auth. You must register and obtain a valid username
and password before you can use this application.

## API Security
The Komodo API is secured with OAuth2 Client Credentials Grants. To access secure endpoints you need to exchange your client
credentials for an access token.

You can register client credentials by making a POST request to the Authorization Server with your email as the
client_id and a client_secret of your choosing.

```
curl -X POST -H 'Content-Type: application/json' -d '{"client_id": "YOUR_EMAIL_HERE", "client_secret": "YOUR_SECRET_HERE", "authorized_grant_types":"client_credentials"}' http://localhost:8080/oauth/client
```

In order to obtain an access token
you should make a POST request to the Authorization Server with your client credentials.
```
curl -X POST --user 'YOUR_EMAIL_HERE:YOUR_SECRET_HERE' -d 'grant_type=client_credentials' http://localhost:8080/oauth/token
```

This will return an access token that will expire after the allotted time has passed.
You can make a request to any endpoint using the access token in the authorization header.
```
curl -X GET -H "Authorization: Bearer ACCESS_TOKEN_HERE" http://localhost:8080/test-security
```
