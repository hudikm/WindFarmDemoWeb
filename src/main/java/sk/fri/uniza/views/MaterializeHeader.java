package sk.fri.uniza.views;

import sk.fri.uniza.core.User;

public class MaterializeHeader implements PagePart {
    private final User user;
    private final Boolean showSideMenu;
    private final String title;


    public MaterializeHeader(User user, String title, Boolean showSideMenu) {
        this.user = user;
        this.title = title;
        this.showSideMenu = showSideMenu;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getShowSideMenu() {
        return showSideMenu;
    }

    @Override
    public String getTemplateName() {
        return "materialize_header.ftl";

    }

    public User getUser() {
        return user;
    }
}
