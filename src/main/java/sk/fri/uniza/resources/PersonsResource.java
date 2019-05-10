package sk.fri.uniza.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.views.View;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;
import sk.fri.uniza.WindFarmDemoApplication;
import sk.fri.uniza.api.Paged;
import sk.fri.uniza.api.Person;
import sk.fri.uniza.api.PersonBuilder;
import sk.fri.uniza.auth.Role;
import sk.fri.uniza.auth.Session;
import sk.fri.uniza.auth.Sessions;
import sk.fri.uniza.core.User;
import sk.fri.uniza.views.NewPersonView;
import sk.fri.uniza.views.PersonView;
import sk.fri.uniza.views.PersonsView;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Path("/persons")
public class PersonsResource {
    final Logger myLogger = LoggerFactory.getLogger(this.getClass());
    private Sessions sessionDao;

    public PersonsResource(Sessions sessionDao) {
        this.sessionDao = sessionDao;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({Role.ADMIN})
    public View getPersonsTable(@Auth User user, @Context UriInfo uriInfo, @Context HttpHeaders headers, @QueryParam("page") Integer page) {
        String sessionStr = headers.getCookies().get(Sessions.COOKIE_SESSION).getValue();
        Optional<Session> sessionOptional = sessionDao.get(sessionStr);
        sessionOptional.orElseThrow(() -> new WebApplicationException());
        Session session = sessionOptional.get();

        try {
            // Get user info
            Person personLogin = null;
            Response<Person> personResponse = WindFarmDemoApplication.getWindFarmServis().getPerson(session.getBearerToken(), user.getId()).execute();
            if (personResponse.isSuccessful()) {
                personLogin = personResponse.body();
            }

            Response<Paged<List<Person>>> execute = WindFarmDemoApplication.getWindFarmServis().getPagedPersons("Bearer " + session.getToken(), 10, page).execute();
            if (execute.isSuccessful()) {
                return new PersonsView(uriInfo, execute.body().getData(), execute.body(), personLogin);
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }

    }

    @GET
    @Path("/user-info")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({Role.USER_READ_ONLY, Role.ADMIN})
    public PersonView getPersonInfo(@Auth User user, @Context UriInfo uriInfo, @Context HttpHeaders headers, @QueryParam("id") Long personID) {

        if (personID == null) personID = user.getId();

        if (!user.getRoles().contains(Role.ADMIN) && user.getId() != personID)
            throw new WebApplicationException(javax.ws.rs.core.Response.Status.UNAUTHORIZED);

        Session session = sessionDao.getSession(headers);

        Response<Person> personResponse;
        try {

            personResponse = WindFarmDemoApplication.getWindFarmServis().getPerson(session.getBearerToken(), personID).execute();
            if (personResponse.isSuccessful()) {
                Person personLoggedIn = personResponse.body();
                return new PersonView(uriInfo, user, personLoggedIn, null);
            }
            throw new WebApplicationException(personResponse.code());
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }

    }

    @GET
    @Path("/user-delete")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({Role.USER_READ_ONLY, Role.ADMIN})
    public javax.ws.rs.core.Response userDelete(@Auth User user, @Context UriInfo uriInfo, @Context HttpHeaders headers, @QueryParam("id") Long personID, @QueryParam("page") Integer page) {

        if (personID == null) return null;

        if (!user.getRoles().contains(Role.ADMIN) || user.getId() == personID)
            throw new WebApplicationException(javax.ws.rs.core.Response.Status.UNAUTHORIZED);

        Session session = sessionDao.getSession(headers);

        Response<Void> response;
        try {

            response = WindFarmDemoApplication.getWindFarmServis().deletePerson(session.getBearerToken(), personID).execute();
            if (response.isSuccessful()) {
                URI uri = UriBuilder.fromPath("/persons")
                        .queryParam("page", page)
                        .build();
                return javax.ws.rs.core.Response.seeOther(uri)
                        .build();

            }
            throw new WebApplicationException(response.code());
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }

    }

    @GET
    @Path("/new-user")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed(Role.ADMIN)
    public NewPersonView newPerson(@Auth User user, @Context UriInfo uriInfo, @Context HttpHeaders headers) {
        return new NewPersonView(uriInfo, user, null);
    }

    @POST
    @Path("/new-user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({Role.ADMIN, Role.USER_READ_ONLY})
    public javax.ws.rs.core.Response newPerson(@Auth User user, @Context UriInfo uriInfo, @Context HttpHeaders headers,
                                               @NotEmpty @FormParam("username") String username,
                                               @NotEmpty @FormParam("first_name") String first_name,
                                               @NotEmpty @FormParam("last_name") String last_name,
                                               @NotEmpty @FormParam("password") String password,
                                               @NotEmpty @FormParam("email") String email,
                                               @NotEmpty @FormParam("roles") Set<String> roles) {

        Session session = sessionDao.getSession(headers);
        Response<Person> personResponse;
        try {


            Person personToBeSaved = new PersonBuilder()
                    .setUserName(username)
                    .setFirstName(first_name)
                    .setLastName(last_name)
                    .setEmail(email)
                    .setPassword(null)
                    .setRoles(roles)
                    .createPerson();


            personResponse = WindFarmDemoApplication.getWindFarmServis()
                    .savePersons(session.getBearerToken(), personToBeSaved)
                    .execute();

            if (!personResponse.isSuccessful())
                throw new WebApplicationException(personResponse.errorBody().string(), personResponse.code());

            // If password has changed save it!
            Response response = WindFarmDemoApplication.getWindFarmServis().saveNewPassword(session.getBearerToken(), personResponse.body().getId(), password).execute();


            if (response.isSuccessful()) {
                URI uri = UriBuilder.fromPath("/persons")
                        .build();

                return javax.ws.rs.core.Response.seeOther(uri)
                        .build();
            } else {
                // If password cannot be saved roll back new person
                WindFarmDemoApplication.getWindFarmServis().deletePerson(session.getBearerToken(), personResponse.body().getId()).execute();
                throw new WebApplicationException(response.errorBody().string(), response.code());
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }

    }

    @POST
    @Path("/user-info")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({Role.ADMIN, Role.USER_READ_ONLY})
    public PersonView setPersonInfo(@Auth User user, @Context UriInfo uriInfo, @Context HttpHeaders headers,
                                    @FormParam("id") Long id,
                                    @NotEmpty @FormParam("first_name") String first_name,
                                    @NotEmpty @FormParam("last_name") String last_name,
                                    @FormParam("password") String password,
                                    @NotEmpty @FormParam("email") String email,
                                    @NotEmpty @FormParam("roles") Set<String> roles) {

        Session session = sessionDao.getSession(headers);
        Response<Person> personResponse;
        try {

            // If password has changed save it!
            if (password != null && !password.isEmpty()) {
                WindFarmDemoApplication.getWindFarmServis().saveNewPassword(session.getBearerToken(), id, password).execute();
            }

            personResponse = WindFarmDemoApplication.getWindFarmServis().getPerson(session.getBearerToken(), id).execute();
            if (personResponse.isSuccessful()) {
                Person personToBeSaved = personResponse.body();

                personToBeSaved.setFirstName(first_name);
                personToBeSaved.setLastName(last_name);
                personToBeSaved.setEmail(email);

                if (user.getRoles().contains(Role.ADMIN)) {
                    if (roles != null && !roles.isEmpty()) {
                        personToBeSaved.setRoles(roles);
                    }
                }

                personResponse = WindFarmDemoApplication.getWindFarmServis()
                        .savePersons(session.getBearerToken(), personToBeSaved)
                        .execute();

                if (personResponse.isSuccessful()) {
                    return new PersonView(uriInfo, user, personToBeSaved, "Zmeny boli uložené");
                }
            }

            throw new WebApplicationException(personResponse.errorBody().string(), personResponse.code());
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }

    }


}
