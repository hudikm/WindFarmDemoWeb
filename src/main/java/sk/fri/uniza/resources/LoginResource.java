package sk.fri.uniza.resources;

import io.dropwizard.auth.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.fri.uniza.api.AccessToken;
import sk.fri.uniza.api.LoginApi;
import sk.fri.uniza.auth.Role;
import sk.fri.uniza.auth.User;
import sk.fri.uniza.auth.Users;


import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;


import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Path("/login")
public class LoginResource {

    final Logger myLogger = LoggerFactory.getLogger(this.getClass());

    private Key key;
    private Users users;
    private Claims claimsJws;

    public LoginResource(Key key, Users users) {
        //We will sign our JWT with our ApiKey secret
        this.key = new SecretKeySpec(key.getEncoded(), SignatureAlgorithm.forSigningKey(key).getJcaName());
        this.users = users;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getAccessToken(LoginApi loginApi) {

        if (loginApi != null) {
            myLogger.info(loginApi.toString());
        }

        /**
         * Test if login info is valid i.e. User name and password
         */
        Optional<User> optionalUser = users.get(loginApi.getUsername());
        if (!optionalUser.isPresent()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        User user = optionalUser.get();
        if (!user.testPassword(loginApi.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String jwt = createJWT(user.getUUID(), "me", user.getName(), Integer.MAX_VALUE, Map.of("scope", user.getRolesString()));

        AccessToken accessToken = new AccessToken().withAccessToken(jwt)
                .withTokenType("Bearer")
                .withExpiresIn(Integer.MAX_VALUE)
                .withRefreshToken("tGzv3JOkF0XG5Qx2TlKWIA")
                .withExampleParameter("example_value");

        CacheControl cacheControl = new CacheControl();
//        cacheControl.setNoStore(true);
        cacheControl.setNoCache(true);

        return Response.ok()
                .cacheControl(cacheControl)
                .cookie(new NewCookie("account", accessToken.getAccessToken(), "/", null, null, accessToken.getExpiresIn(), false, true))
                .type(MediaType.APPLICATION_JSON)
                .entity(accessToken)
                .build();
    }

    public String createJWT(String id, String issuer, String subject, long ttlMillis, Map<String, Object> claims) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(key);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        if (claims != null) {
            builder.addClaims(claims);
        }
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}

