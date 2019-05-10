package sk.fri.uniza.auth;

import sk.fri.uniza.db.BasicDao;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
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

    /**
     *
     * @param headers
     * @return
     * @throws NullPointerException
     * @throws WebApplicationException
     */
    public Session getSession(HttpHeaders headers) throws NullPointerException, WebApplicationException {

        Cookie cookie = headers.getCookies().get(Sessions.COOKIE_SESSION);
        if (cookie == null) throw new NullPointerException();
        String sessionStr = cookie.getValue();
        Optional<Session> sessionOptional = get(sessionStr);
        sessionOptional.orElseThrow(WebApplicationException::new);

        return sessionOptional.get();

    }
}
