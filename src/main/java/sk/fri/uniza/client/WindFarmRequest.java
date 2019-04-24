package sk.fri.uniza.client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import sk.fri.uniza.api.AccessToken;
import sk.fri.uniza.api.Paged;
import sk.fri.uniza.api.Person;
import sk.fri.uniza.api.PublicKey;

import java.util.List;
import java.util.Map;

public interface WindFarmRequest {

    @GET("/api/login/public-key")
    Call<PublicKey> getPublicKey();

    @GET("/api/login/")
    Call<ResponseBody> getLoginHtml(@QueryMap Map<String, String> oauthRequest);

    @FormUrlEncoded
    @POST("/api/login/token")
    Call<AccessToken> getAccessToken(@FieldMap Map<String, String> tokenRequest);

    @GET("/api/persons")
    Call<List<Person>> getAllPersons(@Header("Authorization") String authorization);

    @GET("/api/persons/{id}")
    Call<Person> getPerson(@Path("id") long id);

    @GET("/api/persons")
    Call<Paged<List<Person>>> getPagedPersons(@Query("limit") int limit, @Query("page") int page);

}
