package sk.fri.uniza.auth;

import sk.fri.uniza.core.User;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public class Session {
    private String session;
    private int expires_in;
    private LocalDateTime timeOfCreation;
    private String token;
    private User user;

    public Session(String session, int expires_in, String token, User user) {
        this.session = session;
        this.expires_in = expires_in;
        this.user = user;
        this.timeOfCreation = LocalDateTime.now();
        this.token = token;
    }

    public Session(int expires_in, String token, User user) {
        this(UUID.randomUUID().toString(), expires_in, token, user);
    }

    boolean isExpiried() {
        long between = ChronoUnit.MILLIS.between(LocalDateTime.now(), timeOfCreation);
        return between > expires_in;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBearerToken() {
        return "Bearer " + getToken();
    }

}
