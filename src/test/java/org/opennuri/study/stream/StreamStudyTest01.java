package org.opennuri.study.stream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

class StreamStudyTest01 {

    public static final List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 400, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    @Test
    void three_high_calories_dish_names() {

        List<String> threeHighCaloriesDishNames = menu.stream().filter(dish -> dish.getCalories() > 300)
                .map(dish -> dish.getName())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(threeHighCaloriesDishNames);
    }

    @Test
    void external_iterator() {
        List<String> names = new ArrayList<>();
        for (Dish dish : menu) {
            names.add(dish.getName());
        }

        System.out.println(names);
        names.clear();

        Iterator<Dish> iterator = menu.iterator();

        //noinspection WhileLoopReplaceableByForEach
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            names.add(dish.getName());
        }
    }

    @Test
    void stream_operation() {
        List<String> names = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering: " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping: " + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(names);
    }

    @Test
    void stream_operation_for_each() {
        menu.stream()
                .filter(dish -> {
                    System.out.println("filtering: " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping: " + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .forEach(System.out::println);
    }
}

