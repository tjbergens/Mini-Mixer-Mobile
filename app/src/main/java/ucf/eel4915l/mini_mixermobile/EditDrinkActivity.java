package ucf.eel4915l.mini_mixermobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class EditDrinkActivity extends AppCompatActivity {

    private String token;
    private int drinkID;
    public static final String BASE_URL = "http://192.168.8.1:8000";

    private EditText name;
    private EditText description;
    private EditText total;
    private Button button;

    private Drink drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drink);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Edit Drink");

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("authtoken");
        drinkID = bundle.getInt("drinkid");

        name = (EditText) findViewById(R.id.edit_name);
        description = (EditText) findViewById(R.id.edit_description);
        total = (EditText) findViewById(R.id.edit_total);
        button = (Button) findViewById(R.id.drink_save_button);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchDrinks(token);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                updateDrink(token);
            }
        });




    }

    public void fetchDrinks(String token) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MiniMixerServiceInterface apiService =
                retrofit.create(MiniMixerServiceInterface.class);
        Call<Drink> call = apiService.getDrink(token, drinkID);

        Log.d("EditDrink", "Token is:" + token.toString());

        call.enqueue(new Callback<Drink>() {
            @Override
            public void onResponse(Response<Drink> response, Retrofit retrofit) {

                drink = response.body();
                name.setText(drink.getName());
                description.setText(drink.getDescription());
                total.setText(drink.getTotalAvailable());
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.d("EditDrink", "Failed retrieving!");
            }
        });


    }

    public void updateDrink(String token) {

        drink.setName(name.getText().toString());
        drink.setDescription(description.getText().toString());
        drink.setTotalAvailable(total.getText().toString());



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MiniMixerServiceInterface apiService =
                retrofit.create(MiniMixerServiceInterface.class);
        Call<Drink> call = apiService.updateDrink(token, drink, drinkID);

        Log.d("EditDrink", "Token is:" + token.toString());

        call.enqueue(new Callback<Drink>() {
            @Override
            public void onResponse(Response<Drink> response, Retrofit retrofit) {

                drink = response.body();
                name.setText(drink.getName());
                description.setText(drink.getDescription());
                total.setText(drink.getTotalAvailable());
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.d("EditDrink", "Failed retrieving!");
            }
        });


    }

}
