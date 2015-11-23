package ucf.eel4915l.mini_mixermobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class DrinksAdapter extends
        RecyclerView.Adapter<DrinksAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View drinkView = inflater.inflate(R.layout.item_drink, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(drinkView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Drink drink = mDrinks.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(drink.getName());

        textView = viewHolder.descriptionTextView;
        textView.setText(drink.getDescription());

        textView = viewHolder.totalTextView;
        textView.setText(drink.getTotalAvaliable());

        textView = viewHolder.pumpTextView;
        textView.setText(drink.getAssignedPump());

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
        }
    }


    private List<Drink> mDrinks;

    // Pass in the contact array into the constructor
    public DrinksAdapter(List<Drink> drinks) {
        mDrinks = drinks;
    }
}
