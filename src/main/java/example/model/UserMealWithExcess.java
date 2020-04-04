package example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public UserMealWithExcess(UserMeal meal, boolean excess) {
        this(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
