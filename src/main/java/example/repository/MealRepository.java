package example.repository;

import example.model.Meal;

import java.util.List;
import java.util.Optional;

public interface MealRepository {
    List<Meal> getAll();

    Optional<Meal> getById(Integer id);

    void save(Meal meal);

    boolean remove(Integer id);
}
