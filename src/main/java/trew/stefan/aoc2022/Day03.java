package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.NumberList;


@Slf4j
public class Day03 extends AbstractAOC {


    @Override
    public String runPart1() {
        var list = getStringInput("");

        var ascore = 0;
        for (var item : list) {

            var a = item.substring(0, item.length() / 2);
            var b = item.substring(item.length() / 2);

            var max = 0;
            for (int i = 0; i < a.length(); i++) {
                if (b.contains(Character.toString(a.charAt(i)))) {
                    Character c = a.charAt(i);
                    int b1 = getP(c);

                    max = Math.max(max, b1);
                }


            }
            ascore += max;


        }


        return String.valueOf(ascore);
    }

    private static int getP(Character c) {
        int b1 = (int) c - 96;
        if (Character.isUpperCase(c)) {
            b1 = (int) c - 96 + 58;
        }
        return b1;
    }


    @Override
    public String runPart2() {
        var list = getStringInput("");

        var ascore = 0;


        for (int i = 0; i < list.size() / 3; i++) {

            String line1 = list.get(3 * i);
            String line2 = list.get(3 * i + 1);
            String line3 = list.get(3 * i + 2);
            log.info(line1);

            log.info(list.get(3 * i + 1));
            log.info(list.get(3 * i + 2));
            log.info("");

            var max = 0;
            for (int j = 0; j < line1.length(); j++) {
                Character c = line1.charAt(j);
                var s = Character.toString(c);
                if (line2.contains(s) && line3.contains(s)) {
                    int b1 = getP(c);

                    max = Math.max(max, b1);
                    log.info("{} {}", c, b1);
                }


            }
            ascore += max;
        }

        return String.valueOf(ascore);
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
