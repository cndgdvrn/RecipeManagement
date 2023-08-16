package org.example;

import java.util.*;

public class RecipeManager implements RecipeManagement{

    private static RecipeManager instance;
    private List<Recipe> recipes = new ArrayList<>();

    private List<Double> ratings = new ArrayList<>();
    private List<Integer> ratingCounts = new ArrayList<>();
    private RecipeManager() {}

    public static RecipeManager getInstance() {
        if (instance == null) {
            instance = new RecipeManager();
        }
        return instance;
    }
    @Override
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        int index = recipes.indexOf(recipe);
        ratings.add(index,0d);
        ratingCounts.add(index,0);
    }
    @Override
    public Recipe updateRecipe(Recipe recipe, String name, String description, Map.Entry<String, Map.Entry<Double, RecipeQuantityType>> ingredient){
        int recipeIndex = recipes.indexOf(recipe);
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setIngredient(ingredient);
        recipes.set(recipeIndex, recipe);
        return recipe;
    }
    @Override
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }
    @Override
    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override

    public Recipe findRecipe(String name) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public int getRecipeIndex(Recipe recipe) {
        return recipes.indexOf(recipe);
    }

    @Override
    public void rateRecipe(int index, int rate) {
        index = index-1;
        int numberOfRates = ratingCounts.get(index);
        double rating = ratings.get(index);
        double newRating = (rating * numberOfRates + rate) / (numberOfRates+1);
        ratings.set(index, newRating);
        ratingCounts.set(index, ratingCounts.get(index)+1);
        System.out.println("Tarifin yeni puanı = " + newRating);
    }

    @Override
    public String recipeToString(Recipe recipe){
        String string =recipe.getRecipeCategory().toString() + " ";

        HashMap<String, Map.Entry<Double, RecipeQuantityType>> hashMap = new HashMap<>();
        Recipe temporaryRecipe = recipe.getRecipe();
        hashMap.put(recipe.getIngredient().getKey(), recipe.getIngredient().getValue());

        while (temporaryRecipe!=null){
            if(hashMap.containsKey(temporaryRecipe.getIngredient().getKey())){
                hashMap.put(temporaryRecipe.getIngredient().getKey(), 
                        new AbstractMap.SimpleEntry<>(temporaryRecipe.getIngredient().getValue().getKey()+hashMap.get(temporaryRecipe.getIngredient().getKey()).getKey(),
                                temporaryRecipe.getIngredient().getValue().getValue()));
            }else {
                hashMap.put(temporaryRecipe.getIngredient().getKey(), temporaryRecipe.getIngredient().getValue());
            }
            temporaryRecipe = temporaryRecipe.getRecipe();
        }
       int i =1;
        int max = hashMap.keySet().size();
        for (String ingredientKey: hashMap.keySet()) {
            if(hashMap.get(ingredientKey).getKey()!=0d){
                string += ingredientKey.toString()+ " : ";
                string += hashMap.get(ingredientKey).getKey().toString()+ " ";
                string += hashMap.get(ingredientKey).getValue().toString();
                if(i<max){
                    string += ", ";
                }
            }
            i++;
        }

        return string;
    }
}