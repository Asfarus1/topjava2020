package example.web;

import example.model.Meal;
import example.service.MealService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@RequestMapping("/meals")
@AllArgsConstructor
@Slf4j
@Controller
public class MealController {

    private final MealService mealService;

    @GetMapping
    public String getMeals(Model model) {
        log.info("meals");
        model.addAttribute("meals", mealService.getAllOrderByDateTime(2000));
        return "meals";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") Integer id, Model model) {
        Optional<Meal> meal = ofNullable(id).flatMap(mealService::getById);
        if (meal.isPresent()) {
            model.addAttribute(meal.get());
            return "mealForm";
        }
        log.warn("edit meal:unknown id={}", id);
        return "redirect:/meals";
    }

    @GetMapping("/new")
    public String createNew(@ModelAttribute Meal meal) {
        return "mealForm";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") Integer id) {
        if (mealService.remove(id))
            log.warn("remove meal:unknown id={}", id);
        return "redirect:/meals";
    }

    @PostMapping("/save")
    public String save(Meal meal) {
        mealService.save(meal);
        return "redirect:/meals";
    }
}
