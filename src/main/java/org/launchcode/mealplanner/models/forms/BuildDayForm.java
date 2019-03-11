package org.launchcode.mealplanner.models.forms;

import org.launchcode.mealplanner.models.Day;
import org.launchcode.mealplanner.models.Meal;

import javax.validation.constraints.NotNull;

public class BuildDayForm {

    private Day day;

    private Iterable<Meal> meals;

    @NotNull
    private int dayId;

    @NotNull
    private int mealId;

/*
    private Day differentDay;

    @NotNull
    private int differentDayId;
*/

    public BuildDayForm (Day day, Iterable<Meal> meals){
        this.day = day;
        this.meals = meals;

    }

    public BuildDayForm () {

    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Iterable<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Iterable<Meal> meals) {
        this.meals = meals;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

/*    public Day getDifferentDay() {
        return differentDay;
    }

    public void setDifferentDay(Day differentDay) {
        this.differentDay = differentDay;
    }

    public int getDifferentDayId() {
        return differentDayId;
    }

    public void setDifferentDayId(int differentDayId) {
        this.differentDayId = differentDayId;
    }*/
}
