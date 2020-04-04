package example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class UserMeal {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
