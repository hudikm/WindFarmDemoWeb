package sk.fri.uniza.views;

import sk.fri.uniza.api.Person;
import sk.fri.uniza.core.User;

import javax.ws.rs.core.UriInfo;

public class GraphView extends MaterializePage<MaterializeHeader, MaterializeFooter> {
    private final User loginUser;
    public GraphView(UriInfo uriInfo, User loginUser) {
        super("graphs.ftl", uriInfo, new MaterializeHeader(loginUser, "DashBoard", true), new MaterializeFooter());
        this.loginUser = loginUser;
    }

    public User getLoginUser() {
        return loginUser;
    }

}
