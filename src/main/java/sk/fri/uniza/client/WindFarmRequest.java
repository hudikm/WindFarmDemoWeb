package sk.fri.uniza.client;

import retrofit2.Call;
import retrofit2.http.GET;
import sk.fri.uniza.api.PublicKey;

public interface WindFarmRequest {

    @GET("/api/login/public-key")
    Call<PublicKey> getPublicKey();
}
