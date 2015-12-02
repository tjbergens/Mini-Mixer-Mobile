package ucf.eel4915l.mini_mixermobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CreateRecipeActivity extends AppCompatActivity {

    private String token;
    public static final String BASE_URL = "http://192.168.8.1:8000";

    private EditText name;
    private Button button;
    private EditText description;

    private Recipe recipe;

    ArrayList<Drink> drinks = new ArrayList<>();

    private Drink drink1;
    private Drink drink2;
    private Drink drink3;
    private Drink drink4;
    private Drink drink5;
    private Drink drink6;


    private Spinner ingredient_1_spinner;
    private Spinner ingredient_2_spinner;
    private Spinner ingredient_3_spinner;
    private Spinner ingredient_4_spinner;
    private Spinner ingredient_5_spinner;
    private Spinner ingredient_6_spinner;

    private EditText ingredient_1_amount;
    private EditText ingredient_2_amount;
    private EditText ingredient_3_amount;
    private EditText ingredient_4_amount;
    private EditText ingredient_5_amount;
    private EditText ingredient_6_amount;

    private ArrayAdapter<Drink> ingredient_1_spinner_adapter;
    private ArrayAdapter<Drink> ingredient_2_spinner_adapter;
    private ArrayAdapter<Drink> ingredient_3_spinner_adapter;
    private ArrayAdapter<Drink> ingredient_4_spinner_adapter;
    private ArrayAdapter<Drink> ingredient_5_spinner_adapter;
    private ArrayAdapter<Drink> ingredient_6_spinner_adapter;

    private final int mSpinnerCount = 6;
    private int mSpinnersStarted = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("authtoken");


        ingredient_1_spinner = (Spinner) findViewById(R.id.ingredient_1_spinner);
        ingredient_2_spinner = (Spinner) findViewById(R.id.ingredient_2_spinner);
        ingredient_3_spinner = (Spinner) findViewById(R.id.ingredient_3_spinner);
        ingredient_4_spinner = (Spinner) findViewById(R.id.ingredient_4_spinner);
        ingredient_5_spinner = (Spinner) findViewById(R.id.ingredient_5_spinner);
        ingredient_6_spinner = (Spinner) findViewById(R.id.ingredient_6_spinner);

        ingredient_1_amount = (EditText) findViewById(R.id.create_ingredient_1);
        ingredient_2_amount = (EditText) findViewById(R.id.create_ingredient_2);
        ingredient_3_amount = (EditText) findViewById(R.id.create_ingredient_3);
        ingredient_4_amount = (EditText) findViewById(R.id.create_ingredient_4);
        ingredient_5_amount = (EditText) findViewById(R.id.create_ingredient_5);
        ingredient_6_amount = (EditText) findViewById(R.id.create_ingredient_6);

        name = (EditText) findViewById(R.id.create_recipe_name);
        description = (EditText) findViewById(R.id.create_recipe_description);


        button = (Button) findViewById(R.id.recipe_create_button);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ingredient_1_spinner_adapter = new ArrayAdapter<Drink>(this.getBaseContext(), android.R.layout.simple_spinner_item, drinks);
        // Specify the layout to use when the list of choices appears
        ingredient_1_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ingredient_1_spinner.setAdapter(ingredient_1_spinner_adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ingredient_2_spinner_adapter = new ArrayAdapter<Drink>(this.getBaseContext(), android.R.layout.simple_spinner_item, drinks);
        // Specify the layout to use when the list of choices appears
        ingredient_2_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ingredient_2_spinner.setAdapter(ingredient_2_spinner_adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ingredient_3_spinner_adapter = new ArrayAdapter<Drink>(this.getBaseContext(), android.R.layout.simple_spinner_item, drinks);
        // Specify the layout to use when the list of choices appears
        ingredient_3_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ingredient_3_spinner.setAdapter(ingredient_3_spinner_adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ingredient_4_spinner_adapter = new ArrayAdapter<Drink>(this.getBaseContext(), android.R.layout.simple_spinner_item, drinks);
        // Specify the layout to use when the list of choices appears
        ingredient_4_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ingredient_4_spinner.setAdapter(ingredient_4_spinner_adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ingredient_5_spinner_adapter = new ArrayAdapter<Drink>(this.getBaseContext(), android.R.layout.simple_spinner_item, drinks);
        // Specify the layout to use when the list of choices appears
        ingredient_5_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ingredient_5_spinner.setAdapter(ingredient_5_spinner_adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ingredient_6_spinner_adapter = new ArrayAdapter<Drink>(this.getBaseContext(), android.R.layout.simple_spinner_item, drinks);
        // Specify the layout to use when the list of choices appears
        ingredient_6_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ingredient_6_spinner.setAdapter(ingredient_6_spinner_adapter);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createRecipe(token);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ingredient_1_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(mSpinnersStarted < mSpinnerCount) {

                    mSpinnersStarted++;
                }

                else {


                    Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    drink1 = ingredient_1_spinner_adapter.getItem(pos);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        ingredient_2_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(mSpinnersStarted < mSpinnerCount) {

                    mSpinnersStarted++;
                }

                else {


                    Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    drink2 = ingredient_1_spinner_adapter.getItem(pos);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        ingredient_3_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(mSpinnersStarted < mSpinnerCount) {

                    mSpinnersStarted++;
                }

                else {


                    Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    drink3 = ingredient_1_spinner_adapter.getItem(pos);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        ingredient_4_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(mSpinnersStarted < mSpinnerCount) {

                    mSpinnersStarted++;
                }

                else {


                    Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    drink4 = ingredient_1_spinner_adapter.getItem(pos);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        ingredient_5_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(mSpinnersStarted < mSpinnerCount) {

                    mSpinnersStarted++;
                }

                else {


                    Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    drink5 = ingredient_1_spinner_adapter.getItem(pos);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        ingredient_6_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(mSpinnersStarted < mSpinnerCount) {

                    mSpinnersStarted++;
                }

                else {


                    Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    drink6 = ingredient_1_spinner_adapter.getItem(pos);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        fetchDrinks(token);


    }

    public void fetchDrinks(String token) {

        // Remember to CLEAR OUT old items before appending in the new ones
        drinks.clear();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MiniMixerServiceInterface apiService =
                retrofit.create(MiniMixerServiceInterface.class);
        Call<ArrayList<Drink>> call = apiService.drinkList(token);

        call.enqueue(new Callback<ArrayList<Drink>>() {
            @Override
            public void onResponse(Response<ArrayList<Drink>> response, Retrofit retrofit) {
                drinks.clear();
                drinks.addAll(response.body());


                ingredient_1_spinner_adapter.notifyDataSetChanged();
                ingredient_2_spinner_adapter.notifyDataSetChanged();
                ingredient_3_spinner_adapter.notifyDataSetChanged();
                ingredient_4_spinner_adapter.notifyDataSetChanged();
                ingredient_5_spinner_adapter.notifyDataSetChanged();
                ingredient_6_spinner_adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.d("OrderManager", "Failed retrieving!");
            }
        });


    }

    public void createRecipe(final String token) {
        recipe = new Recipe();
        recipe.setRecipeName(name.getText().toString());
        recipe.setRecipeDescription(description.getText().toString());
        recipe.setNumOrdered(0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MiniMixerServiceInterface apiService =
                retrofit.create(MiniMixerServiceInterface.class);
        Call<Recipe> call = apiService.createRecipe(token, recipe);

        Log.d("CreateRecipe", "Token is:" + token.toString());

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Response<Recipe> response, Retrofit retrofit) {

                recipe = response.body();
                createIngredients(token);
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.d("EditDrink", "Failed retrieving!");
            }
        });
    }

    public void createIngredients(String token) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        if(drink1 != null) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(drink1.getName().toString());
            ingredient.setQuantity(ingredient_1_amount.getText().toString());
            ingredient.setDrink(drink1.getId());
            ingredient.setRecipe(recipe.getId());
            ingredients.add(ingredient);
        }

        if(drink2 != null) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(drink2.getName().toString());
            ingredient.setQuantity(ingredient_2_amount.getText().toString());
            ingredient.setDrink(drink2.getId());
            ingredient.setRecipe(recipe.getId());
            ingredients.add(ingredient);
        }

        if(drink3 != null) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(drink3.getName().toString());
            ingredient.setQuantity(ingredient_3_amount.getText().toString());
            ingredient.setDrink(drink3.getId());
            ingredient.setRecipe(recipe.getId());
            ingredients.add(ingredient);
        }

        if(drink4 != null) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(drink4.getName().toString());
            ingredient.setQuantity(ingredient_4_amount.getText().toString());
            ingredient.setDrink(drink4.getId());
            ingredient.setRecipe(recipe.getId());
            ingredients.add(ingredient);
        }

        if(drink5 != null) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(drink5.getName().toString());
            ingredient.setQuantity(ingredient_5_amount.getText().toString());
            ingredient.setDrink(drink5.getId());
            ingredient.setRecipe(recipe.getId());
            ingredients.add(ingredient);
        }

        if(drink6 != null) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(drink6.getName().toString());
            ingredient.setQuantity(ingredient_6_amount.getText().toString());
            ingredient.setDrink(drink6.getId());
            ingredient.setRecipe(recipe.getId());
            ingredients.add(ingredient);
        }



        for (Ingredient ingredient: ingredients) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<Ingredient> call = apiService.createIngredient(token, ingredient);

            Log.d("CreateRecipe", "Token is:" + token.toString());

            call.enqueue(new Callback<Ingredient>() {
                @Override
                public void onResponse(Response<Ingredient> response, Retrofit retrofit) {

                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Log.d("EditDrink", "Failed retrieving!");
                }
            });

        }

        finish();


    }

}
