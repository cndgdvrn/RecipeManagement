package org.example;

public enum RecipeTag {
    Spicy(1),Gluten_Free(2),Vegetarian(3);

    private int value;

    RecipeTag(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static RecipeTag getType(int tpyeIndex){
        for (RecipeTag tag: RecipeTag.values()) {
            if(tag.getValue()==tpyeIndex){
                return tag;
            }
        }
        return null;
    }
}
