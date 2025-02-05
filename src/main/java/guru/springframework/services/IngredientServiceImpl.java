package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        log.info("@IngredientServiceImpl::findByRecipeIdAndIngredientId ");

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        log.info("IngredientService::findByRecipeIdAndIngredientId - recipe :" + recipe.getId());

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        log.info("@IngredientServiceImpl::saveIngredientCommand");

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("IngredientServiceImpl::saveIngredientCommand - ingredient's recipe not found!");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            log.info("IngredientServiceImpl::saveIngredientCommand:: recipeRepository.findById - isPresent: " + recipeOptional.isPresent());
            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                log.info("IngredientServiceImpl::saveIngredientCommand - ingredient.isPresent: " + ingredientOptional.get().toString());
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                log.info("IngredientServiceImpl::saveIngredientCommand - Ingredient Optional UOM : " + ingredientFound.getUnitOfMeasure().toString());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //new ingredient
                log.info("IngredientServiceImpl::saveIngredientCommand - Creating new ingredient");

                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);

                log.info("IngredientServiceImpl::saveIngredientCommand - Ingredient converted: " + ingredient.toString());
                log.info("IngredientServiceImpl::saveIngredientCommand - Ingredient recipeId: " + ingredient.getRecipe().getId());
            }

            log.info("IngredientServiceImpl::saveIngredientCommand - saving to recipe repository");
            log.info("IngredientServiceImpl::saveIngredientCommand - IngredientCommand ID: " + ingredientCommand.getId());

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> saveIngredientOptional = savedRecipe.getIngredients()
                    .stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

            log.info("IngredientServiceImpl::saveIngredientCommand - Optional IngredientCommand ID: " + ingredientCommand.getId());


            if (!saveIngredientOptional.isPresent()) {
                saveIngredientOptional = savedRecipe.getIngredients()
                        .stream()
                        .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            log.info("IngredientServiceImpl::saveIngredientCommand - END");


            //todo no value present
            return ingredientToIngredientCommand.convert(saveIngredientOptional.get());
        }
    }

    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        log.info("IngredientService::deleteIngredientById");
        Optional<Recipe> recipeReturned = recipeRepository.findById(recipeId);

        Recipe recipe = new Recipe();

        if (recipeReturned.isPresent()) {
            recipe = recipeReturned.get();
            log.info("IngredientService::deleteIngredientById - Recipe Found");
            log.info("IngredientService::deleteIngredientById - Recipe Ingredients Size: " + recipe.getIngredients().size());

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()){
                log.info("IngredientService::deleteIngredientById - Ingredient is present: " + ingredientOptional.toString());
                Ingredient ingredientDeleted = ingredientOptional.get();
                ingredientDeleted.setRecipe(null);
                recipe.getIngredients().remove(ingredientDeleted);
            }
        }
        log.info("IngredientService::deleteIngredientById - Saving Recipe - IngredientSize: " + recipe.getIngredients().size());
        recipeRepository.save(recipe);
    }
}
