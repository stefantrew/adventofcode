package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.HashMap;
import java.util.regex.Pattern;

@Slf4j
public class Day06 extends AbstractAOC {


    @Override
    public String runPart1() {

        return run(80);
    }

    private String run(int i) {
        var total = 0L;
        var result = "";


        var list = getLongSplit(",", "");

        var pots = new HashMap<Integer, Long>();

        for (var s : list) {
            if (!pots.containsKey(Math.toIntExact(s))) {
                pots.put(Math.toIntExact(s), 0L);
            }

            pots.compute(Math.toIntExact(s), (integer, integer2) -> integer2 + 1);
        }
        while (i > 0) {

            pots = run2(pots);

            i--;
        }

        for (Long integer : pots.values()) {
            total += integer;
        }
        return formatResult(total);
    }

    private HashMap<Integer, Long> run2(HashMap<Integer, Long> pots) {

        var temp = new HashMap<Integer, Long>();

        pots.forEach((integer, integer2) -> {
            if (integer == 0) {
                temp.put(8, integer2);
                if (temp.containsKey(6)) {
                    integer2 += temp.get(6);
                }
                temp.put(6, integer2);
            } else {
                if (temp.containsKey(integer - 1)) {
                    integer2 += temp.get(integer - 1);
                }
                temp.put(integer - 1, integer2);
            }
        });
        return temp;
    }

    private void run(java.util.List<Integer> list) {
        var toAdd = 0;
        for (int i = 0; i < list.size(); i++) {

            var v = list.get(i);
            v--;

            if (v == -1) {
                list.set(i, 6);
                toAdd++;
            } else {

                list.set(i, v);
            }

        }

        while (toAdd > 0) {
            list.add(8);
            toAdd--;
        }
    }


    @Override
    public String runPart2() {


        return run(256);
    }

    @Override
    public String getAnswerPart1() {
        return "353079";
    }

    @Override
    public String getAnswerPart2() {
        return "1605400130036";
    }
}
