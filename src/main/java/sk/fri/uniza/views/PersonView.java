package sk.fri.uniza.views;

import sk.fri.uniza.core.User;

import javax.ws.rs.core.UriInfo;

public class PersonView extends MaterializePage<MaterializeHeader, MaterializeFooter> {
    private final User person;

    public PersonView(UriInfo uriInfo, User person) {
        super("person.ftl", uriInfo, new MaterializeHeader(person, "User Info", true), new MaterializeFooter());
        this.person = person;
    }

    public User getPerson() {
        return person;
    }
}
