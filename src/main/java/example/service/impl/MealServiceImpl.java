package example.service.impl;

import example.model.Meal;
import example.model.MealWithExcess;
import example.repository.MealRepository;
import example.service.MealService;
import example.util.MealsUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Override
    public List<MealWithExcess> getAllOrderByDateTime(int maxCaloriesPerDay) {
        List<Meal> meals = repository.getAll();
        return MealsUtil.filter(meals, LocalTime.MIN, LocalTime.MAX, maxCaloriesPerDay).stream()
                .sorted(comparing(MealWithExcess::getDateTime)).collect(toList());
    }

    @Override
    public Optional<Meal> getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public void save(Meal meal) {
        repository.save(meal);
    }

    @Override
    public boolean remove(Integer id) {
        return repository.remove(id);
    }
}
