package ucf.eel4915l.mini_mixermobile;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String mName;
    private String mDescription;
    private String mOwner;
    private int mNumOrdered;
    private String mIngredients;


    public Recipe(String name, String description, int numOrdered, String ingredients, String owner) {
        mName = name;
        mDescription = description;
        mNumOrdered = numOrdered;
        mOwner = owner;
        mIngredients = ingredients;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getNumOrdered() {
        return String.valueOf(mNumOrdered);
    }

    public String getOwner() {
        return mOwner;
    }

    public String getIngredients() {
        return mIngredients;
    }

    private static int recipeID = 0;

    public static List<Recipe> createRecipesList(int numRecipes) {
        List<Recipe> recipes = new ArrayList<>();

        for (int i = 1; i <= numRecipes; i++) {
            recipes.add(new Recipe("Recipe " + ++recipeID, "Drink Description", recipeID, "DrinkIngredients", "Owner"));
        }

        return recipes;
    }
}
