package sk.fri.uniza.auth;

import sk.fri.uniza.db.BasicDao;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import java.util.*;

public class Sessions implements BasicDao<Session, String> {

    public static String COOKIE_SESSION = "user_session";
    private static Map<String, Session> sessionDB = new HashMap<>();


    @Override
    public Optional<Session> get(String id) {
        Session session = sessionDB.get(id);
        return Optional.ofNullable(session);
    }

    @Override
    public List<Session> getAll() {
        return new ArrayList<>(sessionDB.values());
    }

    @Override
    public void save(Session session) {
        sessionDB.put(session.getSession(), session);
    }

    @Override
    public void update(Session session, String[] params) {

    }

    @Override
    public void delete(Session session) {
        sessionDB.remove(session.getSession());
    }

    public Session getSession(HttpHeaders headers) {
        String sessionStr = headers.getCookies().get(Sessions.COOKIE_SESSION).getValue();
        Optional<Session> sessionOptional = get(sessionStr);
        sessionOptional.orElseThrow(() -> new WebApplicationException());
        return sessionOptional.get();

    }
}
