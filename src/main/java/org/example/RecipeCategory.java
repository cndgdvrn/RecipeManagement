package org.example;

public enum RecipeCategory {

    MainDishes(1), Drinks(2), Other(3);
    private int value;

    RecipeCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static RecipeCategory getCategory(int categoryIndex){
        for (RecipeCategory category: RecipeCategory.values()) {
            if(category.getValue()==categoryIndex){
                return category;
            }
        }
        return null;
    }
}