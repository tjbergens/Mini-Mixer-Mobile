package ucf.eel4915l.mini_mixermobile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class DrinksAdapter extends
        RecyclerView.Adapter<DrinksAdapter.ViewHolder> {

    private String token;
    public static final String BASE_URL = "http://192.168.8.1:8000";
    private Activity context;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View drinkView = inflater.inflate(R.layout.item_drink, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(drinkView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Drink drink = mDrinks.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(drink.getName());

        textView = viewHolder.descriptionTextView;
        textView.setText(drink.getDescription());

        textView = viewHolder.totalTextView;
        textView.setText(drink.getTotalAvailable().toString());

        textView = viewHolder.pumpTextView;
        textView.setText(drink.getInPump());

        Button button = viewHolder.drinkEditButton;
        button.setText("Edit");
        button.setTag(position);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=(Integer)v.getTag();
                int id=mDrinks.get(position).getId();

                Log.d("DrinksAdapter", "Starting intent...");

                final Intent intent;

                intent = new Intent(context, EditDrinkActivity.class);
                intent.putExtra("authtoken", token);
                intent.putExtra("drinkid", id);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {

        return mDrinks.size();

    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView totalTextView;
        public TextView pumpTextView;
        public Button drinkEditButton;
        public Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.drink_name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.drink_description);
            totalTextView = (TextView) itemView.findViewById(R.id.drink_total);
            pumpTextView = (TextView) itemView.findViewById(R.id.drink_pump);
            drinkEditButton = (Button) itemView.findViewById(R.id.edit_drink_button);
        }

    }


    private List<Drink> mDrinks;

    // Pass in the contact array into the constructor
    public DrinksAdapter(List<Drink> drinks, String token, Activity context) {
        this.context = context;
        mDrinks = drinks;
        this.token = token;
    }

    // Clean all elements of the recycler
    public void clear() {
        mDrinks.clear();
        //notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Drink> list) {
        mDrinks.addAll(list);
        //notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClicked(Drink drink, View view);
    }
}
