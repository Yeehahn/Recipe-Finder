import java.util.ArrayList;

public class Recipe {

    private ArrayList<String> ingredients;
    private String name;

    public Recipe(ArrayList<String> ingredients, String name){
        this.ingredients = ingredients;
        this.name = name;
    }

    public String toString(){
        return name + ": " + ingredients;
    }

    // Getters
    public ArrayList<String> getIngredients(){
        return ingredients;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }
}