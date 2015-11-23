package ucf.eel4915l.mini_mixermobile;

import java.util.ArrayList;
import java.util.List;

public class Drink {
    private String mName;
    private String mDescription;
    private int mTotalAvaliable;
    private char mAssignedPump;

    public Drink(String name, String description, int totalAvaliable, char assignedPump) {
        mName = name;
        mDescription = description;
        mTotalAvaliable = totalAvaliable;
        mAssignedPump = assignedPump;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTotalAvaliable() {
        return String.valueOf(mTotalAvaliable);
    }

    public String getAssignedPump() {
        return String.valueOf(mAssignedPump);
    }

    private static int drinkID = 0;

    public static List<Drink> createDrinksList(int numDrinks) {
        List<Drink> drinks = new ArrayList<>();

        for (int i = 1; i <= numDrinks; i++) {
            drinks.add(new Drink("Drink " + ++drinkID, "Drink Description", drinkID, 'A'));
        }

        return drinks;
    }
}