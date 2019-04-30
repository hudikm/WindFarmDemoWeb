package sk.fri.uniza.api;

import sk.fri.uniza.core.User;

import java.util.Set;

public class Person extends User {
    private String FirstName;
    private String LastName;
    private String email;
    private Set<Phone> phoneNumbers;

    public Person() {

    }

    public Person(String userName, Set<String> roles, String password, String firstName, String lastName, String email) {
        super(userName, roles, password);
        FirstName = firstName;
        LastName = lastName;
        this.email = email;
    }

    public Person(String userName, Set<String> roles, String firstName, String lastName, String email) {
        super(userName, roles, null);
        FirstName = firstName;
        LastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
