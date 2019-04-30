package sk.fri.uniza.views;

import sk.fri.uniza.api.Person;
import sk.fri.uniza.auth.Role;
import sk.fri.uniza.core.User;

import javax.ws.rs.core.UriInfo;

public class NewPersonView extends MaterializePage<MaterializeHeader, MaterializeFooter> {
    private final User loginUser;
    private final String toastMsg;


    public NewPersonView(UriInfo uriInfo, User loginUser, String toastMsg) {
        super("new_person.ftl", uriInfo, new MaterializeHeader(loginUser, "Nový užívateľ", true), new MaterializeFooter());
        this.loginUser = loginUser;
        this.toastMsg = toastMsg;
    }

    public String getToastMsg() {
        return toastMsg;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public Role getSystemRoles() {
        return Role.getInstance();
    }
}
