package sk.fri.uniza.views;

import sk.fri.uniza.api.Person;
import sk.fri.uniza.core.User;

import javax.ws.rs.core.UriInfo;
import java.util.List;

public class PersonsView extends MaterializePage<MaterializeHeader, MaterializeFooter> {
    private final List<Person> persons;
    private final User loginUser;

    public PersonsView(UriInfo uriInfo, List<Person> persons, User loginUser) {
        super("persons_table.ftl", uriInfo, new MaterializeHeader(loginUser, "Users", true), new MaterializeFooter());
        this.persons = persons;
        this.loginUser = loginUser;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
