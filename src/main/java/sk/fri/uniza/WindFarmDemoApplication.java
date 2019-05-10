package sk.fri.uniza;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.bundles.redirect.HttpsRedirect;
import io.dropwizard.bundles.redirect.RedirectBundle;

import io.dropwizard.jersey.errors.ErrorEntityWriter;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.server.session.SessionHandler;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import sk.fri.uniza.api.PublicKey;
import sk.fri.uniza.auth.*;
import sk.fri.uniza.client.WindFarmRequest;
import sk.fri.uniza.configuration.WindFarmDemoConfiguration;
import sk.fri.uniza.core.User;
import sk.fri.uniza.health.TemplateHealthCheck;
import sk.fri.uniza.resources.HelloWorldResource;
import sk.fri.uniza.resources.LoginResource;
import sk.fri.uniza.resources.PersonsResource;
import sk.fri.uniza.views.ErrorView;
import sk.fri.uniza.views.ValidationErrorView;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class WindFarmDemoApplication extends Application<WindFarmDemoConfiguration> {

    public static WindFarmRequest windFarmServis;
    private static Key serverPublicKey = null;
    private Sessions sessionsDB = new Sessions();

    public static Key getServerPublicKey() {
        return serverPublicKey;
    }

    public static WindFarmRequest getWindFarmServis() {
        return windFarmServis;
    }

    public static void setWindFarmServis(WindFarmRequest windFarmServis) {
        WindFarmDemoApplication.windFarmServis = windFarmServis;
    }

    public static void main(final String[] args) throws Exception {
        new WindFarmDemoApplication().run(args);
    }

    @Override
    public String getName() {
        return "WindFarmDemo";
    }

    @Override
    public void initialize(final Bootstrap<WindFarmDemoConfiguration> bootstrap) {

        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets"));
        bootstrap.addBundle(new RedirectBundle(
                new HttpsRedirect()
        ));
        bootstrap.addBundle(new ViewBundle<WindFarmDemoConfiguration>());
    }

    @Override
    public void run(final WindFarmDemoConfiguration configuration,
                    final Environment environment) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:\\localhost:8085")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();


        windFarmServis = retrofit.create(WindFarmRequest.class);


        //Add HealthChecks
        final TemplateHealthCheck templateHealthCheck = new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("templateHealthCheck", templateHealthCheck);

        // Register Resources
        registerResources(configuration, environment);

        // Setup user auth
        registerUserAuth(configuration, environment);

////        environment.jersey().register(HttpSessionProvider.class);
//        SessionHandler sessionHandler = new SessionHandler();
//        // We are going to use cookies to store web session
//        sessionHandler.setUsingCookies(true);
//
//        environment.servlets().setSessionHandler(sessionHandler);

        // Register new error page handler
        environment.jersey().register(new ErrorEntityWriter<ErrorMessage, View>(MediaType.TEXT_HTML_TYPE, View.class) {
            @Override
            protected View getRepresentation(ErrorMessage errorMessage) {
                return new ErrorView(errorMessage);
            }
        });
        // Register new validation error handler
        environment.jersey().register(new ErrorEntityWriter<ValidationErrorMessage,View>(MediaType.TEXT_HTML_TYPE, View.class) {
            @Override
            protected View getRepresentation(ValidationErrorMessage message) {
                return new ValidationErrorView(message);
            }
        });
    }

    private void registerUserAuth(WindFarmDemoConfiguration configuration, Environment environment) {
        CustomAuthFilter filter = new CustomAuthFilter(new CustomAuthenticator(sessionsDB));
        environment.jersey().register(new AuthDynamicFeature(filter));

        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new OAuth2Authenticator(getSecreteKey()))
                        .setAuthorizer(new OAuth2Authorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()
        ));


        // Enable the resource protection annotations: @RolesAllowed, @PermitAll & @DenyAll
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        // Enable the @Auth annotation for binding authenticated users to resource method parameters
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    private void registerResources(WindFarmDemoConfiguration configuration, Environment environment) {
        final HelloWorldResource helloWorldResource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName(),sessionsDB);
        final PersonsResource personsResource = new PersonsResource(sessionsDB);
        environment.jersey().register(helloWorldResource);
        environment.jersey().register(new LoginResource(sessionsDB, configuration.getServiceDbAuth()));
        environment.jersey().register(personsResource);

    }

    /**
     * Retrieve public key from WindfarmDemo service
     * @return public key
     */
    private Key getSecreteKey() {
        if (serverPublicKey == null) {
            try {
                Response<PublicKey> response = windFarmServis.getPublicKey().execute();
                if (response.isSuccessful()) {
                    PublicKey publicKey = response.body();
                    serverPublicKey = publicKey.getKeyInstance();
                }
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
                return null;
            }
        }
        return serverPublicKey;
    }


}

