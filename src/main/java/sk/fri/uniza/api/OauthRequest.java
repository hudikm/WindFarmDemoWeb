package sk.fri.uniza.api;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;



public class OauthRequest extends ArrayList<NameValuePair> {

    public OauthRequest() {
    }

    public OauthRequest(String clientId, String scope, String state, String redirectUri, String responseType) {
        this.add(new BasicNameValuePair("client_id", clientId));
        this.add(new BasicNameValuePair("scope", scope));
        this.add(new BasicNameValuePair("state", state));
        this.add(new BasicNameValuePair("redirect_uri", redirectUri));
        this.add(new BasicNameValuePair("response_type", responseType));

    }


}