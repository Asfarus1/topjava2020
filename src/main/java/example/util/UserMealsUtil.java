package example.util;

import example.model.UserMeal;
import example.model.UserMealWithExcess;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filter(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filter(List<UserMeal> meals, LocalTime startTime, LocalTime endTime,
                                                  int maxCaloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDays = meals.stream()
                .collect(groupingBy(UserMeal::getDate, summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenInclusive(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal, caloriesPerDays.get(meal.getDate()) > maxCaloriesPerDay))
                .collect(toList());
    }

    @SuppressWarnings("unused")
    public static List<UserMealWithExcess> filterComplicityN(List<UserMeal> meals, LocalTime startTime, LocalTime endTime,
                                                             int maxCaloriesPerDay) {
        @Getter
        class MealsPerDay {
            private final List<UserMeal> meals = new ArrayList<>(5);
            private int calories;

            void addCalories(int calories) {
                this.calories += calories;
            }

            void add(UserMeal meal) {
                meals.add(meal);
            }

            MealsPerDay merge(MealsPerDay mealsPerDay) {
                meals.addAll(mealsPerDay.meals);
                calories += mealsPerDay.calories;
                return this;
            }
        }

        return meals.stream().collect(groupingBy(UserMeal::getDate, Collector.of(MealsPerDay::new, (perDay, meal) -> {
                    perDay.addCalories(meal.getCalories());
                    if (TimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime))
                        perDay.add(meal);
                },
                MealsPerDay::merge, mealsPerDay -> {
                    boolean excess = mealsPerDay.getCalories() > maxCaloriesPerDay;
                    return mealsPerDay.getMeals().stream().map(m -> new UserMealWithExcess(m, excess));
                }))).values().stream().flatMap(Function.identity())
                .collect(toList());
    }
}
