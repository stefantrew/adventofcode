package trew.stefan.aoc2020;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.StringUtil;

import java.util.regex.Pattern;

@Slf4j
public class Day02 extends AbstractAOC {


    @Override
    public String runPart1() {
        var total = 0;
        var list = getStringInput();

        var p = Pattern.compile("(\\d*)-(\\d*) ([a-z]): (\\w*)");
        for (String s : list) {

            var m = new AOCMatcher(p.matcher(s));
            if (m.find()) {

                var target = m.getChar(3);
                var min = m.getInt(1);
                var max = m.getInt(2);

                var count = StringUtil.countChars(m.getString(4), target);
                if (count <= max && count >= min) {
                    total++;
                }
            }

        }
        return String.valueOf(total);
    }

    @Override
    public String runPart2() {


        var total = 0;
        var list = getStringInput();

        var p = Pattern.compile("(\\d*)-(\\d*) ([a-z]): (\\w*)");
        for (String s : list) {

            var m = p.matcher(s);
            if (m.find()) {

                String str = m.group(4);
                var target = m.group(3).toCharArray()[0];
                var min = Integer.parseInt(m.group(1));
                var max = Integer.parseInt(m.group(2));


                var c1 = str.charAt(min - 1);
                var c2 = str.charAt(max - 1);

                if (c1 != c2 && (c1 == target || c2 == target)) {
                    total++;
                }
            }

        }
        return String.valueOf(total);
    }

    @Override
    public String getAnswerPart1() {
        return "643";
    }

    @Override
    public String getAnswerPart2() {
        return "388";
    }
}
