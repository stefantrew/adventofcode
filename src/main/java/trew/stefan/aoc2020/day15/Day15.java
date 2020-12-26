package trew.stefan.aoc2020.day15;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day15 implements AOCDay {

    List<Number> numbers = new ArrayList<>();
    Map<Integer, Number> numberMap = new HashMap<>();

    @AllArgsConstructor
    class Number {
        int number;
        int age;
        int age2;
        int counter = 0;
    }

    private Number getNumber(int number) {

        if (numberMap.containsKey(number)) {
            return numberMap.get(number);
        }
        Number e = new Number(number, 0, 0, 0);
        numberMap.put(number, e);
        return e;
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
        numbers.add(new Number(6, 1, 0, 1));
        numbers.add(new Number(19, 2, 0, 1));
        numbers.add(new Number(0, 3, 0, 1));
        numbers.add(new Number(5, 4, 0, 1));
        numbers.add(new Number(7, 5, 0, 1));
        numbers.add(new Number(13, 6, 0, 1));
        numbers.add(new Number(1, 7, 0, 1));

        for (Number number1 : numbers) {
            numberMap.put(number1.number, number1);
        }

        int x = numbers.size();
        Number prev = numbers.get(x - 1);
        while (true) {
            x++;

            Number number = prev.counter == 1 ? getNumber(0) : getNumber(prev.age - prev.age2);

            number.counter++;
            number.age2 = number.age;
            number.age = x;

            prev = number;

            if (x == limit) {
                return number.number;
            }
        }


    }
}
