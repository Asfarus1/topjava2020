package example.util;

import example.model.Meal;
import example.model.MealWithExcess;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.*;

public class MealsUtil {

    public static List<MealWithExcess> filter(List<Meal> meals, LocalTime startTime, LocalTime endTime,
                                              int maxCaloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDays = meals.stream()
                .collect(groupingBy(Meal::getDate, summingInt(Meal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenInclusive(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new MealWithExcess(meal, caloriesPerDays.get(meal.getDate()) > maxCaloriesPerDay))
                .collect(toList());
    }

    @SuppressWarnings("unused")
    public static List<MealWithExcess> filterComplicityN(List<Meal> meals, LocalTime startTime,
                                                         LocalTime endTime, int maxCaloriesPerDay) {
        @Getter
        class MealsPerDay {
            private final List<Meal> meals = new ArrayList<>(5);
            private int calories;

            void addCalories(int calories) {
                this.calories += calories;
            }

            void add(Meal meal) {
                meals.add(meal);
            }

            MealsPerDay merge(MealsPerDay mealsPerDay) {
                meals.addAll(mealsPerDay.meals);
                calories += mealsPerDay.calories;
                return this;
            }
        }

        return meals.stream().collect(groupingBy(Meal::getDate, Collector.of(MealsPerDay::new, (perDay, meal) -> {
                    perDay.addCalories(meal.getCalories());
                    if (TimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime))
                        perDay.add(meal);
                },
                MealsPerDay::merge, mealsPerDay -> {
                    boolean excess = mealsPerDay.getCalories() > maxCaloriesPerDay;
                    return mealsPerDay.getMeals().stream().map(m -> new MealWithExcess(m, excess));
                }))).values().stream().flatMap(Function.identity())
                .collect(toList());
    }
}
