package sk.fri.uniza.views;

import javax.ws.rs.core.UriInfo;

public class LoginView extends MaterializePage<MaterializeHeader, MaterializeFooter> {

    private final String loginOAuthUrl;
    private final String redirectUrl;

    public LoginView(UriInfo uriInfo, String loginOAuthUrl, String redirectUrl) {
        super("login.ftl", uriInfo, new MaterializeHeader(null, "Login",false), new MaterializeFooter());
        this.loginOAuthUrl = loginOAuthUrl;
        this.redirectUrl = redirectUrl;
    }

    public String getLoginOAuthUrl() {
        return loginOAuthUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
