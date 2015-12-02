package ucf.eel4915l.mini_mixermobile;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface MiniMixerServiceInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    //@GET("/users/{username}")
    //Call<User> getUser(@Path("username") String username);

    @POST("/api-token-auth/?format=json")
    Call<AuthToken> getAuthToken(@Body User user);

    @POST("/users/register")
    Call<User> createUser(@Body User user);


    @GET("/neworder/?format=json")
    Call<ArrayList<Recipe>> orderList(@Header("Authorization") String token);

    @GET("/recipes/?format=json")
    Call<ArrayList<Recipe>> recipeList(@Header("Authorization") String token);

    @GET("/myrecipes/?format=json")
    Call<ArrayList<Recipe>> myrecipeList(@Header("Authorization") String token);

    @GET("/drinks/?format=json")
    Call<ArrayList<Drink>> drinkList(@Header("Authorization") String token);

    @GET("/loaded_drinks/?format=json")
    Call<ArrayList<Drink>> loadedDrinks(@Header("Authorization") String token);

    @PUT("/neworder/{id}/order_drink/?format=json")
    Call<OrderStatus> orderDrink(@Header("Authorization") String token, @Path("id") int id);

    @PUT("/drinks/{id}/?format=json")
    Call<Drink> updateDrink(@Header("Authorization") String token, @Body Drink drink, @Path("id") int id);

    @POST("/drinks/?format=json")
    Call<Drink> createDrink(@Header("Authorization") String token, @Body Drink drink);

    @GET("/drinks/{id}/?format=json")
    Call<Drink> getDrink(@Header("Authorization") String token, @Path("id") int id);

    @PUT("/recipes/{id}/?format=json")
    Call<Recipe> updateRecipe(@Header("Authorization") String token, @Body Recipe recipe, @Path("id") int id);

    @POST("/recipes/?format=json")
    Call<Recipe> createRecipe(@Header("Authorization") String token, @Body Recipe recipe);

    @PUT("/ingredients/{id}/?format=json")
    Call<Ingredient> updateIngredient(@Header("Authorization") String token, @Body Ingredient ingredient, @Path("id") int id);

    @POST("/ingredients/?format=json")
    Call<Ingredient> createIngredient(@Header("Authorization") String token, @Body Ingredient ingredient);

}
