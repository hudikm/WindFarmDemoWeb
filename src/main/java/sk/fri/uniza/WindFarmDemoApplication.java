package sk.fri.uniza;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.bundles.redirect.HttpsRedirect;
import io.dropwizard.bundles.redirect.RedirectBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.server.session.SessionHandler;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import sk.fri.uniza.api.PublicKey;
import sk.fri.uniza.auth.OAuth2Authenticator;
import sk.fri.uniza.auth.OAuth2Authorizer;
import sk.fri.uniza.auth.User;
import sk.fri.uniza.auth.Users;
import sk.fri.uniza.client.WindFarmRequest;
import sk.fri.uniza.health.TemplateHealthCheck;
import sk.fri.uniza.resources.HelloWorldResource;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class WindFarmDemoApplication extends Application<WindFarmDemoConfiguration> {

    public static WindFarmRequest windFarmServis;

    public static void main(final String[] args) throws Exception {
        new WindFarmDemoApplication().run(args);
    }

    @Override
    public String getName() {
        return "WindFarmDemo";
    }

    @Override
    public void initialize(final Bootstrap<WindFarmDemoConfiguration> bootstrap) {

        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
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

//        environment.jersey().register(HttpSessionProvider.class);
        SessionHandler sessionHandler = new SessionHandler();
        environment.servlets().setSessionHandler(sessionHandler);
    }

    private void registerUserAuth(WindFarmDemoConfiguration configuration, Environment environment) {
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

    private Key getSecreteKey() {
        try {
            Response<PublicKey> response = windFarmServis.getPublicKey().execute();
            if(response.isSuccessful()){
                PublicKey publicKey = response.body();
                return publicKey.getKeyInstance();


            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void registerResources(WindFarmDemoConfiguration configuration, Environment environment) {
        final Users users = new Users();
        final HelloWorldResource helloWorldResource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());

        environment.jersey().register(helloWorldResource);

    }


}

