package sk.fri.uniza.api;

public class OauthRequestBuilder {
    private String clientId;
    private String scope;
    private String state;
    private String redirectUri;
    private String responseType;

    public OauthRequestBuilder setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public OauthRequestBuilder setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public OauthRequestBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public OauthRequestBuilder setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public OauthRequestBuilder setResponseType(String responseType) {
        this.responseType = responseType;
        return this;
    }

    public OauthRequest createOauthRequest() {
        return new OauthRequest(clientId, scope, state, redirectUri, responseType);
    }
}