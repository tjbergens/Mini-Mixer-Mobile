package ucf.eel4915l.mini_mixermobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class OrdersAdapter extends
        RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private String token;
    public static final String BASE_URL = "http://192.168.8.1:8000";

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View orderView = inflater.inflate(R.layout.item_order, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(orderView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Recipe recipe = mOrders.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(recipe.getRecipeName());

        textView = viewHolder.descriptionTextView;
        textView.setText(recipe.getRecipeDescription());

        textView = viewHolder.numOrderedTextView;
        textView.setText(recipe.getNumOrdered().toString());

        textView = viewHolder.ownerTextView;
        textView.setText(recipe.getOwner());

        textView = viewHolder.ingredientsTextView;
        textView.setText(recipe.getIngredients().toString());

        Button button = viewHolder.orderButton;
        button.setText("Order");
        button.setTag(position);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //mOrders.get(view.getId()).getId().toString();
                int position=(Integer)view.getTag();
                int id=mOrders.get(position).getId();
                Log.d("OrdersAdapter", Integer.toString(position));

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MiniMixerServiceInterface apiService =
                        retrofit.create(MiniMixerServiceInterface.class);
                Call<OrderStatus> call = apiService.orderDrink(token, id);

                call.enqueue(new Callback<OrderStatus>() {
                    @Override
                    public void onResponse(Response<OrderStatus> response, Retrofit retrofit) {
                        Log.d("OrdersAdapter", response.body().getStatus());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // Log error here since request failed
                        Log.d("OrderManager", "Failed retrieving!");
                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {

        return mOrders.size();

    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView numOrderedTextView;
        public TextView ownerTextView;
        public TextView ingredientsTextView;
        public Button orderButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.recipe_description);
            numOrderedTextView = (TextView) itemView.findViewById(R.id.recipe_ordered);
            ownerTextView = (TextView) itemView.findViewById(R.id.recipe_owner);
            ingredientsTextView = (TextView) itemView.findViewById(R.id.recipe_ingredients);
            orderButton = (Button) itemView.findViewById(R.id.order_button);

        }

    }


    private List<Recipe> mOrders;

    // Pass in the contact array into the constructor
    public OrdersAdapter(List<Recipe> orders, String token) {
        mOrders = orders;
        this.token = token;
    }

    // Clean all elements of the recycler
    public void clear() {
        mOrders.clear();
        //notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Recipe> list) {
        mOrders.addAll(list);
        //notifyDataSetChanged();
    }
}
