package trew.stefan.aoc2020.day15;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;

import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
public class Day15 implements AOCDay {

    List<Number> numbers = new ArrayList<>();
    Map<Integer, Number> numberMap = new HashMap<>();


    static class Number {
        int number;
        int age = 0;
        int age2 = 0;
        int counter = 0;

        public Number(int number) {
            this.number = number;
        }

        public Number(int number, int age, int counter) {
            this.number = number;
            this.age = age;
            this.counter = counter;
        }
    }

    private Number getNumber(int number) {

        return numberMap.computeIfAbsent(number, Number::new);

    }

    @Override
    public String runPart1() {
        return String.valueOf(run(2020));
    }

    @Override
    public String runPart2() {

        return String.valueOf(run(30000000));
    }


    public int run(int limit) {
        numbers.clear();
        numberMap.clear();
        Number prev = null;
        String input = "6,19,0,5,7,13,1";
        int a = 1;
        for (String val : input.split(",")) {
            int num = Integer.parseInt(val);

            prev = getNumber(num);
            prev.age = a++;
            prev.counter = 1;
        }

        int x = a - 1;
        Number zero = getNumber(0);
        int last = 0;
        while (true) {
            x++;

            Number number = prev.counter == 1 ? zero : getNumber(prev.age - prev.age2);

            number.counter++;
            number.age2 = number.age;
            number.age = x;

            prev = number;


            if (x == limit) {
//                log.info("{} {} {} {} {}", map1, map2, map3, map4, map5);
                return number.number;
            }
        }


    }

}
