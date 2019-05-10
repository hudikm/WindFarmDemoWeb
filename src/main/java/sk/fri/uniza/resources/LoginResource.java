package sk.fri.uniza.resources;

import com.google.common.collect.ImmutableMap;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.views.View;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.fri.uniza.WindFarmDemoApplication;
import sk.fri.uniza.api.AccessToken;
import sk.fri.uniza.api.OauthRequest;
import sk.fri.uniza.api.OauthRequestBuilder;
import sk.fri.uniza.auth.Session;
import sk.fri.uniza.auth.Sessions;
import sk.fri.uniza.core.User;
import sk.fri.uniza.configuration.ServiceConnector;
import sk.fri.uniza.configuration.ServiceDbAuth;
import sk.fri.uniza.views.DoneView;
import sk.fri.uniza.views.LoginView;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Path("/login")
public class LoginResource {

    final Logger myLogger = LoggerFactory.getLogger(this.getClass());
    private Sessions sessionDao;
    private ServiceDbAuth serviceDbAuthConfig;


    public LoginResource(Sessions sessionDao, ServiceDbAuth serviceDbAuthConfig) {

        this.sessionDao = sessionDao;
        this.serviceDbAuthConfig = serviceDbAuthConfig;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public View showLoginPage(@Context UriInfo uriInfo) {
        String oauthUrl = null;
        uriInfo.getBaseUri().toString();
        try {
            OauthRequest oauthRequest = new OauthRequestBuilder()
                    .setClientId(serviceDbAuthConfig.getClientId())
                    .setRedirectUri(uriInfo.resolve(new URI("/login/oauth-callback")).toString()) //http://localhost:8080/login/oauth-callback
                    .setResponseType("code")
                    .setScope("")
                    .setState("blabla")
                    .createOauthRequest();


            final int serverPort = serviceDbAuthConfig.getServiceConnectors().stream()
                    .filter(serviceConnector -> serviceConnector.getType().equals(uriInfo.getBaseUri().getScheme()))
                    .findFirst()
                    .map(ServiceConnector::getPort)
                    .orElse(8085);

            //            final int serverPort = uriInfo.getBaseUri().getScheme().equals("http") ? 8085 : 8445;
            URI build = new URIBuilder(uriInfo.getBaseUriBuilder().port(serverPort).build().resolve("/api/login").toString()) //http://localhost:8085/api/login
                    .addParameters(oauthRequest)
                    .build();
            oauthUrl = build.toASCIIString();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new LoginView(uriInfo, oauthUrl, "/persons/user-info");
    }

    @GET
    @Path("oauth-callback")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response oauthCallback(@QueryParam("code") String code, @QueryParam("state") String state, @QueryParam("stay_signin") Boolean stay_signin /*MultivaluedMap<String, String> formParams*/) {

        Map<String, String> codeRequest = ImmutableMap.of(
                "client_id", serviceDbAuthConfig.getClientId(),
                "client_secret", serviceDbAuthConfig.getSecret(),
                "code", code);
        Session session = null;
        try {
            retrofit2.Response<AccessToken> tokenResponse = WindFarmDemoApplication.getWindFarmServis().getAccessToken(codeRequest).execute();
            if (tokenResponse.isSuccessful()) {
                AccessToken accessToken = tokenResponse.body();
                User user;

                try {
                    user = User.getInstance(accessToken.getAccessToken(), WindFarmDemoApplication.getServerPublicKey());
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                    throw new WebApplicationException("Token doesn't contain User info", Response.Status.BAD_REQUEST);
                }
                session = new Session(1000, accessToken.getAccessToken(), user);
                sessionDao.save(session);

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException("Error getting access token", Response.Status.BAD_REQUEST);
        }

        final Boolean useSecureCookie = false; // determines whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL
        final int expiryTime = (stay_signin!=null && stay_signin) ? 3600 * 24 * 7 : -1;  // 1 week in seconds / // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.
        final String cookiePath = "/"; // The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's subdirectories

        NewCookie newCookie = new NewCookie("user_session", session.getSession(), cookiePath, "", "", expiryTime, useSecureCookie);

        return Response
                .ok()
                .cookie(newCookie)
                .type(MediaType.TEXT_HTML_TYPE)
                .entity(new DoneView())
                .build();
    }


    @GET
    @Path("logout")
    public Response logout() {


        final Boolean useSecureCookie = false; // determines whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL
        final int expiryTime = 0;  // 1 week in seconds / // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.
        final String cookiePath = "/"; // The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's subdirectories

        NewCookie newCookie = new NewCookie("user_session", "", cookiePath, "", "", expiryTime, useSecureCookie);

        URI uri = UriBuilder.fromPath("/login").build();

        return Response
                .seeOther(uri)
                .cookie(newCookie)
                .build();
    }

}

