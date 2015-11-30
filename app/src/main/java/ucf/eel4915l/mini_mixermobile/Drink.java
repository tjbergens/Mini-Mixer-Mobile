package ucf.eel4915l.mini_mixermobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Drink {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("total_available")
    @Expose
    private String totalAvailable;
    @SerializedName("in_pump")
    @Expose
    private String inPump;

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
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The totalAvailable
     */
    public String getTotalAvailable() {
        return totalAvailable;
    }

    /**
     *
     * @param totalAvailable
     * The total_available
     */
    public void setTotalAvailable(String totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    /**
     *
     * @return
     * The inPump
     */
    public String getInPump() {
        return inPump;
    }

    /**
     *
     * @param inPump
     * The in_pump
     */
    public void setInPump(String inPump) {
        this.inPump = inPump;
    }

}