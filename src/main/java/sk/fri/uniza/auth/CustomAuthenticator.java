package sk.fri.uniza.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import sk.fri.uniza.core.User;

import java.util.Optional;

public class CustomAuthenticator implements Authenticator<String, User> {
    public CustomAuthenticator(Sessions sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    private Sessions sessionDAO;

    @Override
    public Optional<User> authenticate(String s) throws AuthenticationException {
        Optional<Session> session = sessionDAO.get(s);
        if (session.isPresent()) return Optional.ofNullable(session.get().getUser());
        return Optional.empty();
    }

}
