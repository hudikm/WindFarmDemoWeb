package sk.fri.uniza.resources;


import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import sk.fri.uniza.api.Saying;
import sk.fri.uniza.auth.Role;
import sk.fri.uniza.auth.User;
import sk.fri.uniza.views.PersonView;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Path("/hello-world")
    @Timed
    @RolesAllowed(Role.ADMIN)
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    @GET
    @Path("/hello-user")
    @Timed
    @RolesAllowed({Role.ADMIN, Role.USER_READ_ONLY})
    public Saying sayHello(@Auth User user) {
        final String value = String.format(template, user.getName());
        return new Saying(counter.incrementAndGet(), value);
    }

    @GET @Path("/user-info")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({Role.ADMIN, Role.USER_READ_ONLY})
    public PersonView getInfo(@Auth User user){
        return new PersonView(user);
    }
}