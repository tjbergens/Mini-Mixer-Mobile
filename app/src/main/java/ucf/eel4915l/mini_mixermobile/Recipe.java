package ucf.eel4915l.mini_mixermobile;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    @SerializedName("num_ordered")
    @Expose
    private Integer numOrdered;
    @SerializedName("recipe_name")
    @Expose
    private String recipeName;
    @SerializedName("recipe_description")
    @Expose
    private String recipeDescription;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     * The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     * The ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     *
     * @param ingredients
     * The ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     *
     * @return
     * The numOrdered
     */
    public Integer getNumOrdered() {
        return numOrdered;
    }

    /**
     *
     * @param numOrdered
     * The num_ordered
     */
    public void setNumOrdered(Integer numOrdered) {
        this.numOrdered = numOrdered;
    }

    /**
     *
     * @return
     * The recipeName
     */
    public String getRecipeName() {
        return recipeName;
    }

    /**
     *
     * @param recipeName
     * The recipe_name
     */
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    /**
     *
     * @return
     * The recipeDescription
     */
    public String getRecipeDescription() {
        return recipeDescription;
    }

    /**
     *
     * @param recipeDescription
     * The recipe_description
     */
    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

}
