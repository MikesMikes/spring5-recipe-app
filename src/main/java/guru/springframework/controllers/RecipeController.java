package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final String RECIPEFORM_URL = "recipe/recipeform";

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @RequestMapping({"recipe/new", "/recipe/new"})
    public String newRecipe(Model model) {

        model.addAttribute("recipe", new RecipeCommand());

        return RECIPEFORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPEFORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @RequestMapping("/recipe/{id}/update")
    public String update(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));

        return RECIPEFORM_URL;
}

    @RequestMapping("recipe/{id}/delete")
    public String delete(@PathVariable String id){

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        log.info("handleNotFound - ");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView("errors/404error");
        modelAndView.addObject("error", exception);

        return modelAndView;
    }



}
