import java.util.ArrayList;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Window window = new Window(300, 300);
        window.openWindow();
        ArrayList<String> ingredients = new ArrayList<String>();
        while (window.open) {
            while (true) {
                if (window.buttonPressed) {
                    ingredients = window.retrieveArrayListFromTextArea();
                    window.buttonPressed = false;
                    break;
                }
                Thread.sleep(10);
            }
            System.out.println(ingredients);
            ArrayList<String> recipes = recipesInfo(ingredients);
            while (recipes.contains("failure") || recipes.contains("s")) {
                if (recipes.contains("failure")) {
                    recipes.remove("failure");
                } else if (recipes.contains("s")) {
                    recipes.remove("s");
                }
            }
            System.out.println(recipes);
            window.printArrayListToTextArea(recipes);

            while (!window.resetPressed) {
                Thread.sleep(10);
            }
            window.resetPressed = false;
            ingredients.clear();
            window.resetAll();
        }
    }

    // Gathers all the info we need on the recipe
    // Gathers all the info we need on the recipe
    public static ArrayList<String> recipesInfo(ArrayList<String> ingredients)
            throws IOException, InterruptedException {

        ArrayList<String> recipesId = recipesId(ingredients);

        ArrayList<String> recipesLink = new ArrayList<String>(); // new array of strings
        for (int i = 0; i < recipesId.size(); i++) {

            HttpRequest request2 = HttpRequest.newBuilder() // Takes the recipeID and puts it into this link to find the
                                                            // info we need
                    .uri(URI.create("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"
                            + recipesId.get(i) + "/information"))
                    .header("X-RapidAPI-Key", "b51a1e0825msh27e4bfa684f6706p1b3fd2jsn177edef0e555")
                    .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response2 = HttpClient.newHttpClient().send(request2,
                    HttpResponse.BodyHandlers.ofString());
            String title = response2.body().substring(response2.body().indexOf("title") + 8);
            recipesLink.add(title.substring(0, title.indexOf("\"")));
            String url = response2.body().substring(response2.body().indexOf("sourceUrl") + 12);
            recipesLink.add(url.substring(0, url.indexOf("\"")));
        }
        return recipesLink;

    }

    // Gets the ID from the ingredients we have so we can look up the info
    public static ArrayList<String> recipesId(ArrayList<String> ingredients) throws IOException, InterruptedException {
        // Takes all of the ingredients in the list the user provides
        // And turns it into a format that works for the API url
        String ingredientsUrl = "";
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).contains(" ")) {
                ingredients.set(i, ingredients.get(i).replace(" ", "%20"));
            }
            ingredientsUrl += ingredients.get(i);

            if (i != ingredients.size() - 1) {
                ingredientsUrl += "%2C";
            }
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients="
                                + ingredientsUrl + "&number=0&ignorePantry=true&ranking=2"))
                .header("X-RapidAPI-Key", "b51a1e0825msh27e4bfa684f6706p1b3fd2jsn177edef0e555")
                .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String list = response.body();
        ArrayList<String> recipes = new ArrayList<String>();

        while (list.contains("\"id\":")) { // repeats if list contains "id":

            recipes.add(list.substring(list.indexOf("\"id\":") + 5, list.indexOf(",")));
            // adds the number after "id": and before the first comma to the list recipes
            list = list.substring(list.indexOf(","));
            // list becomes everything after the first comma
            if (list.contains("\"id\":")) {
                list = list.substring(list.indexOf("\"id\":"));
                // Stops from indexing out of bounds
            }
        }
        return recipes;
    }
}