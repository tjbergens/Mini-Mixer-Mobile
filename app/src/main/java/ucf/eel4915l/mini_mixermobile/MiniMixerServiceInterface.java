package ucf.eel4915l.mini_mixermobile;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface MiniMixerServiceInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    //@GET("/users/{username}")
    //Call<User> getUser(@Path("username") String username);

    @POST("/api-token-auth/")
    Call<AuthToken> getAuthToken(@Body User user);

    @POST("/users/register")
    Call<User> createUser(@Body User user);
}
