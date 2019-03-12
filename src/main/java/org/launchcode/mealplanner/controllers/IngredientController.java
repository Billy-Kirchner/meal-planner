package org.launchcode.mealplanner.controllers;


import org.launchcode.mealplanner.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("ingredient")
public class IngredientController extends AbstractController{


    @RequestMapping(value = "")
    public String index( Model model, HttpServletRequest request) {

/*        if(getUserFromSession(request.getSession()) == null) {
            return "redirect:" + "/login";
        }*/
        List<Ingredient> userIngredients = new ArrayList<>();

        for(Ingredient ingredient : ingredientDao.findAll()) {
            if(ingredient.getUser() == getUserFromSession(request.getSession())) {
                userIngredients.add(ingredient);
            }
        }

        model.addAttribute("ingredients", userIngredients);
        model.addAttribute("title", "Available Ingredients");

        return "ingredient/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddIngredientForm(Model model) {
        model.addAttribute("title", "Add New Ingredient");
        model.addAttribute(new Ingredient());

        return "ingredient/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddIngredientForm(@ModelAttribute @Valid Ingredient newIngredient, Errors errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add New Ingredient");

            return "ingredient/add";
        }

        newIngredient.setUser(getUserFromSession(request.getSession()));
        newIngredient.calculateTotalFat();
        newIngredient.calculateNetCarbohydrate();
        ingredientDao.save(newIngredient);

        return "redirect:";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewIngredient (Model model, @PathVariable(value = "id") int id) {

        model.addAttribute("ingredient", ingredientDao.findById(id).orElse(null));

        return "ingredient/view";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String displayEditForm( Model model, @PathVariable(value ="id") int id) {

        model.addAttribute("ingredient", ingredientDao.findById(id).orElse(null));

        return "ingredient/edit";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public String processEditForm (@ModelAttribute @ Valid Ingredient editedIngredient, Errors errors, @PathVariable(value ="id") int id) {

        Ingredient oldIngredient = ingredientDao.findById(id).orElse(null);

        oldIngredient.setName(editedIngredient.getName());
        oldIngredient.setCalories(editedIngredient.getCalories());
        oldIngredient.setSaturatedFat(editedIngredient.getSaturatedFat());
        oldIngredient.setPolyUnsaturatedFat(editedIngredient.getPolyUnsaturatedFat());
        oldIngredient.setMonoUnsaturatedFat(editedIngredient.getMonoUnsaturatedFat());
        oldIngredient.setTransFat(editedIngredient.getTransFat());
        oldIngredient.setCholesterol(editedIngredient.getCholesterol());
        oldIngredient.setSodium(editedIngredient.getSodium());
        oldIngredient.setPotassium(editedIngredient.getPotassium());
        oldIngredient.setTotalCarbohydrate(editedIngredient.getTotalCarbohydrate());
        oldIngredient.setDietaryFiber(editedIngredient.getDietaryFiber());
        oldIngredient.setSugar(editedIngredient.getSugar());
        oldIngredient.setProtein(editedIngredient.getProtein());

        oldIngredient.calculateTotalFat();
        oldIngredient.calculateNetCarbohydrate();

        ingredientDao.save(oldIngredient);



        for (Meal meal : mealDao.findAll()) {
            meal.calculateTotals();
            mealDao.save(meal);
        }

        for (Day day : dayDao.findAll()) {
            day.calculateTotals();
            dayDao.save(day);
        }

        return "redirect:/ingredient/view/" + id;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deleteIngredient( Model model, @PathVariable(value ="id") int id) {

        model.addAttribute("ingredient", ingredientDao.findById(id).orElse(null));
        Ingredient deletedIngredient = ingredientDao.findById(id).orElse(null);

        for (Meal meal : mealDao.findAll()) {
            List<Component> components = new ArrayList<>(meal.getComponents());
            for (Component component : components) {
                if (component.getIngredient() == deletedIngredient) {
                    meal.removeComponent(component);
                    componentDao.delete(component);
                }
            }
            meal.calculateTotals();
            mealDao.save(meal);
        }

        for (Day day : dayDao.findAll()) {
            day.calculateTotals();
            dayDao.save(day);
        }

        ingredientDao.delete(deletedIngredient);


        return "ingredient/delete";
    }
}
