package org.example;

public enum RecipeQuantityType {
    adet(1),
    gr(2),
    ml(3);

    private int value;

    RecipeQuantityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static RecipeQuantityType getType(int categoryIndex){
        for (RecipeQuantityType type: RecipeQuantityType.values()) {
            if(type.getValue()==categoryIndex){
                return type;
            }
        }
        return null;
    }
}
