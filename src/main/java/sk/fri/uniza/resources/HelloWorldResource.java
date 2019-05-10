package sk.fri.uniza.resources;


import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import sk.fri.uniza.api.Saying;
import sk.fri.uniza.auth.Role;
import sk.fri.uniza.auth.Sessions;
import sk.fri.uniza.core.User;
import sk.fri.uniza.views.GraphView;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private Sessions sessionDao;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName, Sessions sessionDao) {
        this.template = template;
        this.defaultName = defaultName;
        this.sessionDao = sessionDao;
        this.counter = new AtomicLong();
    }

    @GET
    @Path("")
    public Response redirect(@Context HttpHeaders headers) {
        URI uri;
        User user = null;

        try {
            user = sessionDao.getSession(headers).getUser();
        } catch (NullPointerException | WebApplicationException e) {
            user = null;
        }

        if (user == null) {
            uri = UriBuilder.fromPath("/login")
                    .build();
        } else {
            uri = UriBuilder.fromPath("/persons/user-info")
                    .build();
        }


        return javax.ws.rs.core.Response.seeOther(uri)
                .build();
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


    @GET
    @Path("/graphs")
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    public GraphView graphView(@Auth User user, @Context UriInfo uriInfo, @Context HttpHeaders headers) {

        return new GraphView(uriInfo, user);
    }

}