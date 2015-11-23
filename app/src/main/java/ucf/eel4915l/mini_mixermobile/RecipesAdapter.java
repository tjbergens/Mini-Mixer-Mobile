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
public class RecipesAdapter extends
        RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.item_recipe, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(recipeView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Recipe recipe = mRecipes.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(recipe.getName());

        textView = viewHolder.descriptionTextView;
        textView.setText(recipe.getDescription());

        textView = viewHolder.numOrderedTextView;
        textView.setText(recipe.getNumOrdered());

        textView = viewHolder.ownerTextView;
        textView.setText(recipe.getOwner());

        textView = viewHolder.ingredientsTextView;
        textView.setText(recipe.getIngredients());



    }

    @Override
    public int getItemCount() {

        return mRecipes.size();

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
        }
    }


    private List<Recipe> mRecipes;

    // Pass in the contact array into the constructor
    public RecipesAdapter(List<Recipe> recipes) {
        mRecipes = recipes;
    }
}
