package trew.stefan.aoc2022;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.NumberList;

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getStringInput();

        long count = 0;
        var totals = new NumberList();

        for (var s : list) {

            if (s.isBlank()) {
                totals.add(count);
                count = 0;
            } else {
                var i = Integer.parseInt(s);
                count += i;
            }

        }
        if (count > 0) {
            totals.add(count);
        }


        return String.valueOf(totals.average());
    }

    @Override
    public String runPart2() {


        var list = getStringInput();


        long count = 0;
        var totals = new NumberList();

        for (var s : list) {

            if (s.isBlank()) {
                totals.add(count);
                count = 0;
            } else {

                var i = Integer.parseInt(s);
                count += i;
            }

        }
        if (count > 0) {
            totals.add(count);
        }

        var i = totals.reverseSort().take(3).sum();

        return String.valueOf(i);
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
