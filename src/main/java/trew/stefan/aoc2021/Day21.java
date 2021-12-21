package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day21 extends AbstractAOC {
    Map<Integer, Integer> d2 = new HashMap<>();


    public int rollDice(int d) {
        d++;

        if (d > 100) {
            return 1;
        }

        return d;
    }

    @Override
    public String runPart1() {


        var p1 = 3;
        var p2 = 2;

        var score1 = 0;
        var score2 = 0;
        var d = 0;
        var count = 0;

        while (true) {

            var temp = 0;
            var rolls = new int[3];
            d = rollDice(d);
            rolls[0] = d;
            temp += d;
            d = rollDice(d);
            rolls[1] = d;
            temp += d;
            d = rollDice(d);
            rolls[2] = d;
            temp += d;

            count += 3;
            p1 += temp;
            p1 %= 10;
            score1 += p1 + 1;

            if (score1 >= 1000) {
                break;
            }


            temp = 0;
            d = rollDice(d);
            temp += d;
            d = rollDice(d);
            temp += d;
            d = rollDice(d);
            temp += d;

            count += 3;
            p2 += temp;
            p2 %= 10;
            score2 += p2 + 1;

            if (score2 >= 1000) {
                break;
            }
        }
        var loser = score1 >= 1000 ? score2 : score1;
        return formatResult(loser * count);
    }

    static class Result {
        long win1;
        long win2;

        public Result(long win1, long win2) {
            this.win1 = win1;
            this.win2 = win2;
        }
    }

    Map<Integer, Result> cache = new HashMap<>();

    Result playRound(int p1, int p2, int s1, int s2) {

        var state = Objects.hash(p1, p2, s1, s2);
        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        var result = new Result(0, 0);
        for (var entry : d2.entrySet()) {
            var temp = p1 + entry.getKey();
            temp %= 10;
            var tempS1 = s1 + temp + 1;

            if (tempS1 >= 21) {
                result.win1 += entry.getValue();
            } else {

                for (var entry2 : d2.entrySet()) {
                    var temp2 = p2 + entry2.getKey();
                    temp2 %= 10;
                    var tempS2 = s2 + temp2 + 1;
                    if (tempS2 >= 21) {
                        result.win2 += entry.getValue() + entry2.getValue();
                    } else {

                        var res = playRound(temp, temp2, tempS1, tempS2);
                        result.win1 += res.win1 * entry.getValue() * entry2.getValue();
                        result.win2 += res.win2 * entry.getValue() * entry2.getValue();
                    }
                }


            }
        }


        cache.put(state, result);
        return result;
    }


    @Override
    public String runPart2() {

        var p1 = 3;
        var p2 = 2;

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= 3; k++) {
                    var sum = i + j + k;
                    d2.putIfAbsent(sum, 0);
                    d2.compute(sum, (integer, integer2) -> integer2 + 1);
                }
            }
        }
        var result = playRound(p1, p2, 0, 0);
        return formatResult(Math.max(result.win1, result.win2));
    }

    @Override
    public String getAnswerPart1() {
        return "734820";
    }

    @Override
    public String getAnswerPart2() {
        return "193170338541590";
    }
}
