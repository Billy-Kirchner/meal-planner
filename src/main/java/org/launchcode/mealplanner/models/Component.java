package org.launchcode.mealplanner.models;


import javax.persistence.*;

@Entity
public class Component {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private Double servings;

    public Component (Ingredient ingredient, Double servings) {
        this.ingredient = ingredient;
        this.servings = servings;
    }

    public Component () {

    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getServings() {
        return servings;
    }

    public void setServings(Double servings) {
        this.servings = servings;
    }

    public int getId() {
        return id;
    }


}
