package sk.fri.uniza.auth;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import sk.fri.uniza.core.User;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.Optional;

@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class CustomAuthFilter extends AuthFilter {

    private CustomAuthenticator authenticator;

    public CustomAuthFilter(CustomAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        Optional<User> user;

        try {
            String session = getCredentials(containerRequestContext);

            user = authenticator.authenticate(session);

        } catch (AuthenticationException e) {
            throw new WebApplicationException("Unable to validate credentials", Response.Status.UNAUTHORIZED);
        }

        if (user.isPresent()) {
            SecurityContext securityContext = new CustomSecurityContext(user.get(), containerRequestContext.getSecurityContext());
            containerRequestContext.setSecurityContext(securityContext);
        } else {
            throw new WebApplicationException("Credentials not valid", Response.Status.UNAUTHORIZED);
        }

    }

    private String getCredentials(ContainerRequestContext containerRequestContext) {
        Cookie user_session = containerRequestContext.getCookies().get("user_session");

        if (user_session == null) return null;
        return containerRequestContext.getCookies().get("user_session").getValue();
//        return  "abcdef";

    }
}
