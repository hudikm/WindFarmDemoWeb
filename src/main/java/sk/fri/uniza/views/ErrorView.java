package sk.fri.uniza.views;

import io.dropwizard.jersey.errors.ErrorMessage;

public class ErrorView extends MaterializePage<MaterializeHeader, MaterializeFooter> {


    private ErrorMessage errorMessage;

    public ErrorView(ErrorMessage errorMessage) {
        super("error_page.ftl", null, new MaterializeHeader(null, "Error page", false), new MaterializeFooter());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }


}
