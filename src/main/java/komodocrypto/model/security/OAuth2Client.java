package komodocrypto.model.security;

public class OAuth2Client {

    private String client_id;
    private String client_secret;
    private String scope;
    private String authorized_grant_types;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorized_grant_types() {
        return authorized_grant_types;
    }

    public void setAuthorized_grant_types(String authorized_grant_types) {
        this.authorized_grant_types = authorized_grant_types;
    }
}
