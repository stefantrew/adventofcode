package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;


@Slf4j
public class Day11 extends AbstractAOC {


    @Override
    public String runPart1() {


        var input = "5 127 680267 39260 0 26 3553 5851995";
//        var input = "125 17";

        return run(input, 25);
    }

    @Override
    public String runPart2() {
        var input = "5 127 680267 39260 0 26 3553 5851995";
//        var input = "125 17";

        return run(input, 750);
    }

    private String run(String input, int numberOfBlinks) {
        var array = Arrays.stream(input.split(" ")).mapToLong(Long::parseLong).toArray();
        var state = new HashMap<Long, Long>();

        for (int i = 0; i < array.length; i++) {
            state.put(array[i], 1L);
        }

        log.info("{}", state);
        for (int i = 0; i < numberOfBlinks; i++) {

            state = iterate(state);
//            log.info("{} {} {} ", i, state.size(), state);
        }
        var total = 0L;
        for (var i : state.entrySet()) {
            total += i.getValue();
        }

        return String.valueOf(total);
    }


    private void addValue(HashMap<Long, Long> state, long key, long value) {
        if (state.containsKey(key)) {
            state.put(key, state.get(key) + value);
        } else {
            state.put(key, value);
        }

    }

    private HashMap<Long, Long> iterate(HashMap<Long, Long> state) {

        var newState = new HashMap<Long, Long>();
        for (var i : state.entrySet()) {
            if (i.getKey() == 0) {
                addValue(newState, 1, i.getValue());
            } else {

                var s = String.valueOf(i.getKey());

                if (s.length() % 2 == 0) {
                    addValue(newState, Long.parseLong(s.substring(0, s.length() / 2)), i.getValue());
                    addValue(newState, Long.parseLong(s.substring(s.length() / 2)), i.getValue());
                } else {
                    addValue(newState, i.getKey() * 2024, i.getValue());

                }

            }
        }
        return newState;
    }


    @Override
    public String getAnswerPart1() {
        return "216042";
    }

    @Override
    public String getAnswerPart2() {
        return "255758646442399";
    }
}
