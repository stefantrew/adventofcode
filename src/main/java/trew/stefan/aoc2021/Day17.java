package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

@Slf4j
public class Day17 extends AbstractAOC {

    int txMin = 230;
    int txMax = 283;
    int tyMin = -107;
    int tyMax = -57;

    @Override
    public String runPart1() {

        var total = tyMax;
        for (int i = 0; i < txMax; i++) {
            for (int j = -1000; j < 1000; j++) {

                var compute = compute(i, j);
                if (compute != null) {
                    total = Math.max(total, compute);
                }
            }
        }

        return formatResult(total);
    }


    Integer compute(int dx, int dy) {
        int x = 0;
        int y = 0;
        var maxY = tyMin;
        while (true) {

            maxY = Math.max(maxY, y);
            if (x <= txMax && x >= txMin && y <= tyMax && y >= tyMin) {
                return maxY;
            }

            if (x > txMax || x < txMin && dx == 0 || y < tyMin) {
                return null;
            }

            x += dx;
            y += dy;
            dy -= 1;
            if (dx > 0) {
                dx -= 1;
            }
        }

    }

    @Override
    public String runPart2() {


        var total = 0;
        for (int i = 0; i < txMax + 10; i++) {
            for (int j = -1000; j < 2000; j++) {

                var compute = compute(i, j);
                if (compute != null) {
                    total++;
                }
            }
        }

        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "5671";
    }

    @Override
    public String getAnswerPart2() {
        return "4556";
    }
}
