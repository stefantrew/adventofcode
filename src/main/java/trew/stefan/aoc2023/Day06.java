package trew.stefan.aoc2023;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.regex.Pattern;

@Slf4j
public class Day06 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 1;
//        var time = new int[]{7, 15, 30};
//        var distance = new int[]{9, 40, 200};
        var time = new int[]{41, 66, 72, 66};
        var distance = new int[]{244, 1047, 1228, 1040};


        for (int i = 0; i < time.length; i++) {
            var t = time[i];
            var d = distance[i];

            var winningWays = computeWinning(t, d);
            log.info("Winning ways for t={}, d={} is {}", t, d, winningWays);
            total *= winningWays;
        }

        return formatResult(total);
    }

    private int computeWinning(long t, long d) {
        var count = 0;
        for (int i = 1; i < t; i++) {
            var dist = (t - i) * i;

            if (dist > d) {
                count++;
            }


        }

        return count;
    }


    @Override
    public String runPart2() {


        var total = 1;
//        var time = new int[]{71530};
//        var distance = new int[]{940200};
        var time = new long[]{41667266};
        var distance = new long[]{244104712281040L};


        for (int i = 0; i < time.length; i++) {
            var t = time[i];
            var d = distance[i];

            var winningWays = computeWinning(t, d);
            log.info("Winning ways for t={}, d={} is {}", t, d, winningWays);
            total *= winningWays;
        }

        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
