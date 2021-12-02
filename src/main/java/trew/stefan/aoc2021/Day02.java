package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.regex.Pattern;

@Slf4j
public class Day02 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getStringInput();


        var p = Pattern.compile("(forward|down|up) (\\d*)");

        var d = 0;
        var x = 0;
        for (String s : list) {
            var m = new AOCMatcher(p.matcher(s));
            m.find();

            switch (m.group(1)) {
                case "forward":
                    x += m.getInt(2);
                    break;
                case "down":
                    d += m.getInt(2);
                    break;
                case "up":
                    d -= m.getInt(2);
                    break;
            }
        }


        return String.valueOf(d * x);
    }

    @Override
    public String runPart2() {


        var list = getStringInput();


        var p = Pattern.compile("(forward|down|up) (\\d*)");
        var x = 0;
        var d = 0;
        var aim = 0;
        for (String s : list) {
            var m = new AOCMatcher(p.matcher(s));
            m.find();
//            log.info("{} {}", m.getString(1), m.getInt(2));
            m.print();
            switch (m.group(1)) {
                case "forward":
                    x += m.getInt(2);
                    d += (aim * m.getInt(2));
                    break;
                case "down":
                    aim += m.getInt(2);
                    break;
                case "up":
                    aim -= m.getInt(2);
                    break;
            }
        }


        return String.valueOf(d * x);
    }

    @Override
    public String getAnswerPart1() {
        return "2102357";
    }

    @Override
    public String getAnswerPart2() {
        return "2101031224";
    }
}
