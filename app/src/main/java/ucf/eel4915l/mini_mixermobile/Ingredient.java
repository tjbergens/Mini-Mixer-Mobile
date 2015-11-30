package ucf.eel4915l.mini_mixermobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("recipe")
    @Expose
    private Integer recipe;
    @SerializedName("drink")
    @Expose
    private Integer drink;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The recipe
     */
    public Integer getRecipe() {
        return recipe;
    }

    /**
     *
     * @param recipe
     * The recipe
     */
    public void setRecipe(Integer recipe) {
        this.recipe = recipe;
    }

    /**
     *
     * @return
     * The drink
     */
    public Integer getDrink() {
        return drink;
    }

    /**
     *
     * @param drink
     * The drink
     */
    public void setDrink(Integer drink) {
        this.drink = drink;
    }

}
