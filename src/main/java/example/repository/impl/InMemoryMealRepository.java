package example.repository.impl;

import example.model.Meal;
import example.repository.MealRepository;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> index = new ConcurrentHashMap<>();
    private final AtomicInteger keyGenerator = new AtomicInteger(0);

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(index.values());
    }

    @Override
    public Optional<Meal> getById(Integer id) {
        return Optional.ofNullable(index.get(id));
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == null)
            meal.setId(keyGenerator.incrementAndGet());
        index.put(meal.getId(), meal);
    }

    @Override
    public boolean remove(Integer id) {
        return index.remove(id) != null;
    }
}
