package trew.stefan.aoc2020;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

@Slf4j
public class Day03 extends AbstractAOC {


    @Override
    public String runPart1() {


        int sum = getSum(3, 1);

        return String.valueOf(sum);
    }

    private int getSum(int dx, int dy) {
        var list = getStringInput("");
        var w = list.get(0).length();
        var x = 0;
        var sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i % dy > 0) {
                continue;
            }
            if (list.get(i).charAt(x) == '#') {
                sum++;
            }
            x += dx;
            x %= w;
        }
        return sum;
    }

    @Override
    public String runPart2() {


        return String.valueOf( getSum(1, 1) *
                getSum(3, 1) *
                getSum(5, 1) *
                getSum(7, 1) *
                getSum(1, 2));

    }

    @Override
    public String getAnswerPart1() {
        return "220";
    }

    @Override
    public String getAnswerPart2() {
        return "2138320800";
    }
}
