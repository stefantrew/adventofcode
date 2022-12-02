package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

@Slf4j
public class Day02 extends AbstractAOC {

    enum RPS {
        ROCK, PAPER, SCISSORS
    }

    private int getScore(RPS option) {
        return switch (option) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSORS -> 3;
        };

    }

    private static RPS getRps(String a) {
        return switch (a) {
            case "A", "X", default -> RPS.ROCK;
            case "B", "Y" -> RPS.PAPER;
            case "C", "Z" -> RPS.SCISSORS;
        };
    }

    private int getResult(RPS them, RPS me) {
        if (them == RPS.ROCK && me == RPS.PAPER
                || them == RPS.PAPER && me == RPS.SCISSORS
                || them == RPS.SCISSORS && me == RPS.ROCK) {
            return 6;
        }

        return them == me ? 3 : 0;
    }

    @Override
    public String runPart1() {

        var list = getStringInput("");
        var total = 0;
        for (var s : list) {
            var a = s.substring(0, 1);
            var b = s.substring(2, 3);

            RPS me = getRps(b);
            total += getScore(me) + getResult(getRps(a), me);
        }

        return String.valueOf(total);
    }


    @Override
    public String runPart2() {


        var list = getStringInput("");
        var total = 0;
        for (var s : list) {
            var a = s.substring(0, 1);
            var b = s.substring(2, 3);

            RPS them = getRps(a);

            var us = switch (b) {
                case "X" -> switch (them) {
                    case ROCK -> RPS.SCISSORS;
                    case SCISSORS -> RPS.PAPER;
                    case PAPER -> RPS.ROCK;
                };
                case "Z" -> switch (them) {
                    case ROCK -> RPS.PAPER;
                    case SCISSORS -> RPS.ROCK;
                    case PAPER -> RPS.SCISSORS;
                };
                default -> them;
            };

            total += getScore(us) + getResult(them, us);
        }


        return String.valueOf(total);
    }

    @Override
    public String getAnswerPart1() {
        return "14531";
    }

    @Override
    public String getAnswerPart2() {
        return "11258";
    }
}
