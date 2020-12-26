package trew.stefan.aoc2020.day23;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class Day23 implements AOCDay {

    private int day = 23;


    static class Cup {
        public Cup(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Cup{" +
                    "value=" + value +
                    '}';
        }

        int value;
        Cup next;

        public int getValue() {
            return value;
        }

        public Cup getNext() {
            return next;
        }


    }

    int size = 0;

    int getIndex(int index) {
        return (index + size) % size;
    }

    int getNext(int current, int first, int second, int third) {
        while (current-- > 1) {
            if (current == first || current == second || current == third) {
                continue;
            }
            return current;
        }

        int max = size;
        while (true) {
            if (max == first || max == second || max == third) {
                max--;
            } else {
                break;
            }

        }
        return max;
    }

    @Override
    public String runPart1() {
        HashMap<Integer, Cup> cupHashMap = run(100, 9);
        Cup oneCup = cupHashMap.get(1);
        StringBuilder sb = new StringBuilder();
        while (true) {
            oneCup = oneCup.next;
            sb.append(oneCup.value);

            if (oneCup.next.value == 1) {
                break;
            }
        }

        return sb.toString();
    }

    @Override
    public String runPart2() {
        HashMap<Integer, Cup> cupHashMap = run(10000000, 1000000);
        Cup oneCup = cupHashMap.get(1);
        BigDecimal left = new BigDecimal(oneCup.next.value);
        BigDecimal right = new BigDecimal(oneCup.next.next.value);
        return left.multiply(right).toString();

    }

    public HashMap<Integer, Cup> run(int limit, int numberCups) {
        String input = "394618527";
//        String input = "389125467";

        HashMap<Integer, Cup> cupHashMap = new HashMap<>();


        Cup startCup = createCups(input, cupHashMap, numberCups);
        size = cupHashMap.size();


        int counter = 0;
        while (counter++ < limit) {

            Cup firstCup = startCup.next;
            Cup secondCup = firstCup.next;
            Cup thirdCup = secondCup.next;
            startCup.next = thirdCup.next;

            int next = getNext(startCup.value, firstCup.value, secondCup.value, thirdCup.value);

            Cup nextCup = cupHashMap.get(next);
            thirdCup.next = nextCup.next;
            nextCup.next = firstCup;
            startCup = startCup.next;


        }
        return cupHashMap;
    }

    private Cup createCups(String input, HashMap<Integer, Cup> cupHashMap, int numberCups) {
        Cup firstCup = null;
        Cup lastCup = null;

        for (char c : input.toCharArray()) {

            Cup current = new Cup(Integer.parseInt(String.valueOf(c)));
            cupHashMap.put(current.value, current);

            if (firstCup == null) {
                firstCup = current;
                lastCup = current;
            }

            lastCup.next = current;

            lastCup = current;

        }


        for (int i = 10; i <= numberCups; i++) {
            Cup current = new Cup(i);
            cupHashMap.put(current.value, current);

            if (firstCup == null) {
                firstCup = current;
                lastCup = current;
            }

            lastCup.next = current;

            lastCup = current;
        }

        if (lastCup != null) {

            lastCup.next = firstCup;
        }
        return firstCup;
    }

    private void printNodes(Cup first) {
        Cup current = first;

        while (current != null) {
            log.info("Cup {}", current.getValue());
            current = current.next;
        }
    }

    private List<Integer> insertAfter(List<Integer> cups, int next, int first, int second, int third) {
        int index = cups.indexOf(next);
        cups.add(++index, first);
        cups.add(++index, second);
        cups.add(++index, third);

        return cups;
    }
}
