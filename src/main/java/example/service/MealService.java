package example.service;

import example.model.Meal;
import example.model.MealWithExcess;

import java.util.List;
import java.util.Optional;

public interface MealService {
    List<MealWithExcess> getAllOrderByDateTime(int maCaloriesPerDay);

    Optional<Meal> getById(Integer id);

    void save(Meal meal);

    boolean remove(Integer id);
}
