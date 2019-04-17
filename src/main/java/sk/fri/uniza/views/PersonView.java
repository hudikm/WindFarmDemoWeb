package sk.fri.uniza.views;

import io.dropwizard.views.View;
import sk.fri.uniza.auth.User;

public class PersonView extends View {
    private final User person;

    public PersonView(User person) {
        super("person.ftl");
        this.person = person;
    }

    public User getPerson() {
        return person;
    }
}
