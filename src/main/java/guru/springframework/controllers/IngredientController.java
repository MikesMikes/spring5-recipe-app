package guru.springframework.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class IngredientController {

    public String getIngredients(){

        return "recipe/ingredient/list";
    }

}
