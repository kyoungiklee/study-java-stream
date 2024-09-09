package org.opennuri.study.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings({"NonAsciiCharacters", "Convert2MethodRef", "SimplifyStreamApiCallChains", "ReplaceInefficientStreamCount", "NewClassNamingConvention"})
@Slf4j
public class StreamStudyTest02 {

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

    public static final List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER)
    );
    @Test
    void stream_filtering() {

        List<Dish> vegetarianDishes = menu.stream().filter(Dish::isVegetarian)
                .collect(Collectors.toList());
        System.out.println(vegetarianDishes);
    }

    @Test
    void stream_distinct() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(number -> number % 2 ==0)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    void stream_takewhile() {
        List<Dish> sliceMenu = specialMenu.stream()
                .takeWhile(dish -> {
                    System.out.println(dish.getName() + " : " + dish.getCalories());
                    return dish.getCalories() < 320;
                })
                .collect(Collectors.toList());
        System.out.println(sliceMenu);
    }

    @Test
    void stream_dropwhile() {
        List<Dish> sliceMenu = specialMenu.stream()
                .dropWhile(dish -> {
                    System.out.println(dish.getName() + " : " + dish.getCalories());
                    return dish.getCalories() < 320;
                })
                .collect(Collectors.toList());
        System.out.println(sliceMenu);
    }

    @Test
    void stream_skip() {
        List<Dish> skipMenu = menu.stream().filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());
        System.out.println(skipMenu);

    }

    @Test
    void stream_exercise() {
        // The two meat dishes that appear on the menu for the first time
        List<Dish> selectTwoMeat = menu.stream().filter(dish -> dish.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());
        System.out.println(selectTwoMeat);
    }

    @Test
    void stream_mapping_음식명_추출() {
        // 메뉴에서 음식 명을 추출한다.
        List<String> names = menu.stream()
                .map(dish -> dish.getName())
                .collect(Collectors.toList());
        System.out.println(names);


    }

    @Test
    void stream_mapping_음식명_문자열_길이() {
        List<Integer> dishNameLength  = menu.stream()
                .map(dish -> dish.getName().length())
                .filter(length -> length % 2 == 0)
                .collect(Collectors.toList());
        System.out.println(dishNameLength);
    }

    @Test
    void stream_mapping_스트림_평면화() {
        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> streamOfWords = Arrays.stream(arrayOfWords);

        List<String> stringList = streamOfWords
                .map(word -> word.split(""))
                .flatMap(word -> Arrays.stream(word))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(stringList);
    }

    @Test
    void stream_mapping_flatMap_사용() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println(squares);
    }

    @Test
    void stream_mapping_flatMap_사용_예제() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        List<int[]> intList = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        intList.forEach(pair -> System.out.println("(" + pair[0] + "," + pair[1] + ")"));
    }

    @Test
    void stream_mapping_flatMap_사용_예제2() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        List<int[]> intList = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        intList.forEach(pair -> System.out.println("(" + pair[0] + "," + pair[1] + ")"));


    }

    // 특정 속성이 데이터 집합에 있는지 여부를 검색하는 데이터 처리도 자주 사용 된다.
    // 스트림 API는 allMatch, anyMatch, noneMatch, findFirst, findAny 등 다양한 유틸리티 메서드를 제공한다.

    //프레디케이트가 적어도 한요소와 일치하는 지 확인
    @Test
    void stream_anyMatch() {
        if (menu.stream().anyMatch(dish -> dish.isVegetarian())) {
            System.out.println("The menu is (somewhat) vegetarian friendly");
        }
    }

    //프레디케이트가 모든 요소와 일치하는 지 검사
    @Test
    void stream_allMatch() {
        if (menu.stream().allMatch(dish -> dish.getCalories() < 1000)) {
            System.out.println("The menu is healthy");
        }
    }

    @Test
    void stream_noneMatch() {
        boolean noneMatch = menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);

        assertThat(noneMatch).isEqualTo(true);

    }

    @Test
    void stream_findAny() {
        menu.stream()
                .filter(dish -> dish.isVegetarian())
                .findAny()
                .ifPresent(System.out::println);
    }

    @Test
    void stream_findFirst() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> first = someNumbers.stream()
                .map(number -> number * number)
                .filter(number -> number % 3 == 0)
                .findFirst();
        System.out.println(first.isPresent());
    }

    @Test
    void stream_reduce_요소의합() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);

        int sum = someNumbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);
    }

    @Test
    void stream_reduce_초기값없음() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);

        Optional<Integer> sum = someNumbers.stream().reduce((a, b) -> a + b);
        sum.ifPresent(System.out::println);
    }

    @Test
    void stream_reduce_최댓값_최소값() {
        List<Integer> someNumbers = Arrays.asList(4,5,3,9);

        // Integer.sum() 람다 함수 호출
        Optional<Integer> max = someNumbers.stream().reduce(Integer::max);
        max.ifPresent(System.out::println);

        //(a, b)-> a >= b ? a : b  람다 표현식 사용
        Optional<Integer> max1 = someNumbers.stream().reduce((a, b) -> a >= b ? a : b);
        max1.ifPresent(System.out::println);

        // BinaryOperator<Integer>() 함수 사용
        Optional<Integer> max2 = someNumbers.stream().reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer >= integer2 ? integer : integer2;
            }
        });

        max2.ifPresent(System.out::println);

        Optional<Integer> min = someNumbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);
    }

    @Test
    void stream_reduce_예제() {
        Integer count = menu.stream()
                .map(dish -> 1)
                .reduce(0, Integer::sum);
        System.out.println(count);

        long count1 = menu.stream().count();
        System.out.println(count1);
    }

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mairo = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 500),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mairo, 2012, 710),
            new Transaction(mairo, 2012, 700),
            new Transaction(alan, 2012, 950)
    );
    @Test
    void stream_5장_실전연습() {
        // 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름 차순으로 정리하시오.
        List<Transaction> transactionList = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction o1, Transaction o2) {
                        return (Math.max(o1.getValue(), o2.getValue()));
                    }
                })
                .collect(Collectors.toList());
        System.out.println(transactionList);
    }

}
