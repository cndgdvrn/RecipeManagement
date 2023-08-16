package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RecipeManagement {
    public void addRecipe(Recipe recipe);
    public Recipe updateRecipe(Recipe recipe, String name, String description, Map.Entry<String, Map.Entry<Double, RecipeQuantityType>> ingredients);
    public void removeRecipe(Recipe recipe);
    public List<Recipe> getRecipes();
    public Recipe findRecipe(String name);

    public int getRecipeIndex(Recipe recipe);

    public void rateRecipe(int index, int rate);
    public String recipeToString(Recipe recipe);
}
