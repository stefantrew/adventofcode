package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.NumberList;


@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {
        return String.valueOf(getTotals().max());
    }

    private NumberList getTotals() {
        var list = getStringInput();

        long count = 0;
        var totals = new NumberList();

        for (var s : list) {

            if (s.isBlank()) {
                totals.add(count);
                count = 0;
            } else {
                count += Integer.parseInt(s);
            }

        }
        totals.add(count);
        return totals;
    }

    @Override
    public String runPart2() {
        return String.valueOf(getTotals().reverseSort().take(3).sum());
    }

    @Override
    public String getAnswerPart1() {
        return "69693";
    }

    @Override
    public String getAnswerPart2() {
        return "200945";
    }
}
