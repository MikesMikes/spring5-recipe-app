package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }


    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            log.error("IngredientCommandToIngredient:: convert - ingredientCommand is null");
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());

        if (ingredientCommand.getRecipeId() != null) {
            log.info("IngredientCommandToIngredient::convert - recipeId " + ingredientCommand.getRecipeId());
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommand.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }


        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setUnitOfMeasure(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUnitOfMeasure()));


        log.info("IngredientCommandToIngredient::convert - done");

        return ingredient;
    }


}
