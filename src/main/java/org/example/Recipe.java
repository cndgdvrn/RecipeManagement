package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Recipe {

    private RecipeCategory recipeCategory;

    private RecipeTag recipeTag;
    private Recipe recipe;
    private String name;
    private String description;
    private Entry<String, Entry<Double, RecipeQuantityType>> ingredient;
    public Recipe(String name, String description, RecipeCategory recipeCategory,RecipeTag recipeTag) {
        this.name = name;
        this.recipeCategory = recipeCategory;
        this.description = description;
        this.recipeTag = recipeTag;
    }
    public RecipeTag getRecipeTag() {
        return recipeTag;
    }
    public void setRecipeTag(RecipeTag recipeTag) {
        this.recipeTag = recipeTag;
    }
    public String getName() {
        return name;
    }
    public RecipeCategory getRecipeCategory() {
        return recipeCategory;
    }
    public void setRecipeCategory(RecipeCategory recipeCategory) {
        this.recipeCategory = recipeCategory;
    }
    public Recipe getRecipe() {
        return recipe;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public Entry<String, Entry<Double, RecipeQuantityType>> getIngredient() {
        return ingredient;
    }
    public String getDescription() {
        return description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setIngredient(Entry<String, Entry<Double, RecipeQuantityType>> ingredients) {
        this.ingredient = ingredients;
    }
    @Override
    public String toString() {
        return RecipeManager.getInstance().recipeToString(this);
    }
}
