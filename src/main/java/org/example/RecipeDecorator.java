package org.example;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

public class RecipeDecorator {
    public Recipe decorateRecipe(Recipe recipe, String ingredient, double quantity, RecipeQuantityType recipeQuantityType){
        Recipe newRecipe = new Recipe(recipe.getName(), recipe.getDescription(), recipe.getRecipeCategory(), recipe.getRecipeTag());
        newRecipe.setIngredient(new SimpleEntry<>(ingredient, new SimpleEntry<>(quantity, recipeQuantityType)));
        newRecipe.setRecipe(recipe);
        return newRecipe;
    }
    public Recipe urunCikar(Recipe recipe, String ingredient){
        Recipe temporaryRecipe = recipe.getRecipe();
        if(recipe.getIngredient().getKey().equals(ingredient)){
                recipe.setIngredient(new SimpleEntry<>(recipe.getIngredient().getKey(), new SimpleEntry<>(0d, recipe.getIngredient().getValue().getValue())));
        }
        while (temporaryRecipe!=null){
            if(temporaryRecipe.getIngredient().getKey().equals(ingredient)) {
                    temporaryRecipe.setIngredient(new SimpleEntry<>(temporaryRecipe.getIngredient().getKey(), new SimpleEntry<>(0d, temporaryRecipe.getIngredient().getValue().getValue())));
            }
            temporaryRecipe = temporaryRecipe.getRecipe();

        }
        return recipe;
    }
}
