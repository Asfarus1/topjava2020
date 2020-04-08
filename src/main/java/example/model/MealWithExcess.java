package example.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MealWithExcess {
    private final int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealWithExcess(Meal meal, boolean excess) {
        id = meal.getId();
        dateTime = meal.getDateTime();
        description = meal.getDescription();
        calories = meal.getCalories();
        this.excess = excess;
    }
}
