package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/chicken", "/chicken-tacos"})
    public String getChickenRecipe() {

        return "spicy-grilled-chicken-tacos";
    }

    @RequestMapping("/perfect-guacamole")
    public String getPerfectGuacamoleRecipe(Model model){

        model.addAttribute("recipes", recipeService.getRecipes());

        return "perfect-guacamole";
    }

}
