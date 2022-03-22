package guru.springframework.boostrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();


        //UOMs
        Optional<UnitOfMeasure> eachUOMOptional = unitOfMeasureRepository.findByDescription("Each");
        if (!eachUOMOptional.isPresent()) {
            throw new RuntimeException("Optional Unit of Measure not found!");
        }
        Optional<UnitOfMeasure> tbspUOMOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tbspUOMOptional.isPresent()) {
            throw new RuntimeException("Optional Unit of Measure not found!");
        }
        Optional<UnitOfMeasure> tspUOMOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!tspUOMOptional.isPresent()) {
            throw new RuntimeException("Optional Unit of Measure not found!");
        }
        Optional<UnitOfMeasure> cupUOMOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (!cupUOMOptional.isPresent()) {
            throw new RuntimeException("Optional Unit of Measure not found!");
        }
        Optional<UnitOfMeasure> pinchUOMOptional = unitOfMeasureRepository.findByDescription("Pinch");
        if (!pinchUOMOptional.isPresent()) {
            throw new RuntimeException("Optional Unit of Measure not found!");
        }
        Optional<UnitOfMeasure> ounchUOMOptional = unitOfMeasureRepository.findByDescription("Ounce");
        if (!ounchUOMOptional.isPresent()) {
            throw new RuntimeException("Optional Unit of Measure not found!");
        }
        Optional<UnitOfMeasure> cloveUOMOptional = unitOfMeasureRepository.findByDescription("Clove");
        if (!cloveUOMOptional.isPresent()){
            throw new RuntimeException("Optional Unit of Measure not found!");
        }
        Optional<UnitOfMeasure> dashUOMOptional = unitOfMeasureRepository.findByDescription("Dash");
        if (!dashUOMOptional.isPresent()){
            throw new RuntimeException("Optional Unit of Measure not found!");
        }

        //getUOMs
        UnitOfMeasure eachUom = eachUOMOptional.get();
        UnitOfMeasure tbspUom = tbspUOMOptional.get();
        UnitOfMeasure tspUom = tspUOMOptional.get();
        UnitOfMeasure dashUom = dashUOMOptional.get();
        UnitOfMeasure cupUom = cupUOMOptional.get();
        UnitOfMeasure pinchUom = pinchUOMOptional.get();

        //category Optionals
        Optional<Category> americanCatOptional = categoryRepository.findByDescription("American");
        if (!americanCatOptional.isPresent()){
            throw new RuntimeException("Category not found");
        }
        Optional<Category> mexicanCatOptional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCatOptional.isPresent()){
            throw new RuntimeException("Category not found");
        }

        //getCategories
        Category americanCategory = americanCatOptional.get();
        Category mexicanCategory = mexicanCatOptional.get();

        //perfect-guacamole
        Recipe perfectGuacRecipe = new Recipe();
        perfectGuacRecipe.setDescription("Perfect Guacamole");
        perfectGuacRecipe.setPrepTime(10);
        perfectGuacRecipe.setCookTime(10);
        perfectGuacRecipe.setDifficulty(Difficulty.EASY);
        perfectGuacRecipe.setDirections("" +
                "1:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
                "2: \n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "3: \n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
                "4: \n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days."
        );
        Notes perfectGuacNotes = new Notes();
        perfectGuacNotes.setRecipeNotes("Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        perfectGuacNotes.setRecipe(perfectGuacRecipe);
        perfectGuacRecipe.setNotes(perfectGuacNotes);

        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "Avocado", eachUom, new BigDecimal(2)));
        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "Salt", tspUom, new BigDecimal(0.5)));
        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "lime or lemon juice", tbspUom, new BigDecimal(1)));
        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "diced red onion", tbspUom, new BigDecimal(2)));
        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "serrano (or jalapeño) chilis, stems and seeds removed, minced", eachUom, new BigDecimal(1)));
        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "cilantro (leaves and tender stems), finely chopped", tbspUom, new BigDecimal(2)));
        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "Pepper", pinchUom, new BigDecimal(1)));
        perfectGuacRecipe.addIngredient(new Ingredient(perfectGuacRecipe, "ripe tomato, chopped (optional)", tspUom, new BigDecimal(1)));



        perfectGuacRecipe.getCategories().add(americanCategory);
        perfectGuacRecipe.getCategories().add(mexicanCategory);

        recipes.add(perfectGuacRecipe);

        return recipes;
    }
}
