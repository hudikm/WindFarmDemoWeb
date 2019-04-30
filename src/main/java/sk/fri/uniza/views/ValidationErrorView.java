package sk.fri.uniza.views;

import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.views.View;

public class ValidationErrorView extends MaterializePage<MaterializeHeader, MaterializeFooter> {
    private ValidationErrorMessage message;

    public ValidationErrorView(ValidationErrorMessage message) {
        super("validation_error_page.ftl", null, new MaterializeHeader(null, "Error page", false), new MaterializeFooter());

        this.message = message;
    }

    public ValidationErrorMessage getMessage() {
        return message;
    }
}
