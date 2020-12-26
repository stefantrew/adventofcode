package trew.stefan.aoc2020.day25;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day25 implements AOCDay {

    private int day = 25;


    int computeLoopSize(int targetNumber) {


        long value = 1;
        int counter = 0;
        while (value != targetNumber) {

            counter++;
            value = (value * 7) % 20201227;

        }

        return counter;
    }


    @Override
    public String runPart2() {
        return "HoHoHo";
    }

    @Override
    public String runPart1() {


        int cardKey = 9717666;
        int doorKey = 20089533;


        long value = 1;
        long value2 = 1;
        while (value != cardKey) {

            value = (value * 7) % 20201227;
            value2 = (value2 * doorKey) % 20201227;
        }

        return  String.valueOf(value2);
    }
}
