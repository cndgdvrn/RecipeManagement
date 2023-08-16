package org.example;

import java.util.AbstractMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    static Scanner scanner;
    static RecipeDecorator recipeDecorator;
    static RecipeManagement recipeManagement;

    public static void main(String[] args) {
        recipeManagement = RecipeManager.getInstance();
        recipeDecorator = new RecipeDecorator();

        scanner = new Scanner(System.in);
        Recipe currentRecipe = null;

        boolean programOn = true;
        boolean decorate = true;

        while(programOn){
            int secim = printMenu();
            switch (secim){
                case 1:
                    currentRecipe = createRecipe();
                    System.out.println("Daha fazla içerik eklemek ister misiniz ? ");
                    System.out.print("1 evet , 0 hayır : ");
                    int intInput = scanner.nextInt();
                    scanner.nextLine();
                    if (intInput ==1){
                        decorateRecipe(currentRecipe);
                    } else if (intInput==0) {
                        System.out.println(recipeManagement.recipeToString(currentRecipe));
                    }
                    break;
                case 2:
                    searchRecipe("İçeriğini görmek istediğiniz tarif numarasını seçin : ");
                    break;
                case 3:
                    rateRecipe();
                    break;
                case 4:
                    updateRecipe();
                    break;
                case 5:
                    programOn = false;
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }


    private static Recipe updateRecipe(){
        System.out.println("*** Tarif güncelle ***");
        Recipe recipe = searchRecipe("İçeriğini değiştirmek istediğiniz tarifin numarasını girin : ");
        System.out.println("1. İsim güncelleme");
        System.out.println("2. Açıklama güncelleme");
        System.out.println("3. Kategori güncelleme");
        System.out.println("4. Tür güncelleme");
        System.out.println("5. Tarife ürün ekle");
        System.out.println("6. Tariften ürün çıkar");
        System.out.print("Lütfen bir seçim yapınız : ");
        int secenek = takeInput(1,6);
        switch (secenek){
            case 1:
                System.out.print("Tarif ismini girin : ");
                String name = scanner.nextLine();
                recipe = recipeManagement.updateRecipe(recipe, name, recipe.getDescription(), recipe.getIngredient());
                break;
            case 2:
                System.out.print("Tarif açıklamasını girin : ");
                String description = scanner.nextLine();
                recipe = recipeManagement.updateRecipe(recipe, recipe.getName(), description, recipe.getIngredient());
                break;
            case 3:
                int categoryIndex = getCategoryIndex("Kategori seçin : ");
                int recipeIndex = recipeManagement.getRecipes().indexOf(recipe);
                recipe.setRecipeCategory(RecipeCategory.getCategory(categoryIndex));
                recipe = recipeManagement.getRecipes().set(recipeIndex, recipe);
                break;
            case 4:
                int tagInput = getTypeIndex();
                recipe.setRecipeTag(RecipeTag.getType(tagInput));
                break;
            case 5:
                recipe = decorateRecipe(recipe);
                break;
            case 6:
                recipe = urunCikar(recipe);
                break;
            default:
                System.out.println("Geçersiz seçim!");
        }
        return recipe;
    }

    private static Recipe urunCikar(Recipe recipe){
        int recipeIndex = recipeManagement.getRecipeIndex(recipe);
        recipeManagement.recipeToString(recipe);
        System.out.print("Çıkarmak istediğiniz ürünün ismini girin : ");
        String urunAdi = scanner.nextLine();
        recipe = recipeDecorator.urunCikar(recipe, urunAdi);
        recipeManagement.getRecipes().set(recipeIndex, recipe);
        return  recipe;
    }

    private static Recipe searchRecipe(String message){
        int selectedRecipeIndex = listRecipes(getCategoryIndex("Aramak istediğiniz kategoriyi seçin : "), getTypeIndex(), message);
        Recipe selectedRecipe= null;
        if(selectedRecipeIndex==-1){
            System.out.println("Bu kategoride henüz tarif oluşturulmamıştır!");
        }else {
            selectedRecipe = recipeManagement.getRecipes().get(selectedRecipeIndex-1);
            System.out.println(recipeManagement.recipeToString(selectedRecipe));
        }
        return selectedRecipe;
    }

    private static int listRecipes(int categoryIndex, int tagIndex, String message){
        int recipeIndex=1;
        List<Recipe> recipes = recipeManagement.getRecipes();
        if(recipes.size()==0){
            return -1;
        }
        for (Recipe recipe: recipes) {
            if(recipe.getRecipeCategory()==RecipeCategory.getCategory(categoryIndex) && recipe.getRecipeTag()==RecipeTag.getType(tagIndex)){
                System.out.println(recipeIndex + ". Tarif ismi : " +  recipe.getName() + ", tarif açıklaması : " + recipe.getDescription());
            }
            recipeIndex++;
        }
        System.out.print(message);
        return takeInput(1,recipeIndex-1);
    }


    private static void rateRecipe() {
        int recipeIndex = listRecipes(getCategoryIndex("Aramak istediğiniz kategoriyi seçin : "), getTypeIndex() ,"Tarif numarası seçin : ");
        if(recipeIndex==-1){
            System.out.println("Bu kategoride henüz tarif oluşturulmamıştır!");
        }else {
            System.out.print("Puan : ");
            int rate = takeInput(1,5);
            recipeManagement.rateRecipe(recipeIndex, rate);
        }
    }

    private static int printMenu() {
        System.out.println("****** Tarif Yönetim Sistemi ******");
        System.out.println("1. Tarif Oluştur");
        System.out.println("2. Tarif Ara");
        System.out.println("3. Tarif Değerlendir");
        System.out.println("4. Tarif Düzenle");
        System.out.println("5. Çıkış");
        System.out.print("Lütfen bir seçim yapınız:");
        int secim = takeInput(1,5);
        return secim;
    }

    public static Recipe createRecipe()
    {
        System.out.print("Tarif ismini girin : ");
        String name = scanner.nextLine();
        System.out.print("Tarif açıklamasını girin : ");
        String description = scanner.nextLine();
        int categoryIndex = getCategoryIndex("Kategori seçin : ");
        int tagIndex = getTypeIndex();

        Recipe recipe = new Recipe(name, description, RecipeCategory.getCategory(categoryIndex), RecipeTag.getType(tagIndex));

        System.out.print("İçerik ismi girin : ");
        String ingredientName = scanner.nextLine();

        System.out.println("*** İçerik tipleri ***");
        System.out.println("1. Adet");
        System.out.println("2. Gr");
        System.out.println("3. Ml");
        System.out.print("İçerik tipi seçin : ");
        int typeIndex = takeInput(1,3);

        System.out.print("İçerik miktarı girin : ");
        double amount = scanner.nextDouble();
        recipe.setIngredient(new AbstractMap.SimpleEntry<>(ingredientName, new AbstractMap.SimpleEntry<>(amount, RecipeQuantityType.getType(typeIndex))));
        recipeManagement.addRecipe(recipe);
        return recipe;
    }

    public static Recipe decorateRecipe(Recipe recipe){
        int recipeIndex = recipeManagement.getRecipeIndex(recipe);
        System.out.print("İçerik ismi girin : ");
        String ingredientName = scanner.nextLine();

        System.out.println("*** İçerik tipleri ***");
        System.out.println("1. Adet");
        System.out.println("2. Gr");
        System.out.println("3. Ml");
        System.out.print("İçerik tipi seçin : ");
        int typeIndex = takeInput(1,3);

        System.out.print("İçerik miktarı girin : ");
        double amount = scanner.nextDouble();

        recipe = recipeDecorator.decorateRecipe(recipe, ingredientName, amount, RecipeQuantityType.getType(typeIndex));
        System.out.println(recipeManagement.recipeToString(recipe));

        System.out.println("Daha fazla içerik eklemek ister misiniz ? ");
        System.out.print("1 evet , 0 hayır : ");
        if (scanner.nextInt()==1){
            scanner.nextLine();
            recipeManagement.getRecipes().set(recipeIndex, recipe);
            return decorateRecipe(recipe);
        }
            scanner.nextLine();
            recipeManagement.getRecipes().set(recipeIndex, recipe);
            return recipe;
    }

    private static int getCategoryIndex(String message){
        System.out.println("*** Kategoriler ***");
        System.out.println("1. Ana yemekler");
        System.out.println("2. İçecekler");
        System.out.println("3. Diğer");
        System.out.print(message);
        return takeInput(1,3);
    }
    private static int getTypeIndex(){
        System.out.println("*** Türler ***");
        System.out.println("1. Baharatlı ");
        System.out.println("2. Glütensiz ");
        System.out.println("3. Vejetaryen");
        System.out.print("Tür seçin : ");
        return takeInput(1,3);
    }

    private static int takeInput(int min, int max){
       int secim = scanner.nextInt();
       while (!(secim>=min && secim<=max)){
           System.out.println(min + " - " + max + " aralığında olmalıdır!");
           System.out.print("Tekrar deneyin : ");
           secim = scanner.nextInt();
       }
       scanner.nextLine();
       return secim;
    }


}