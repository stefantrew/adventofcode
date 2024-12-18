package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
public class Day02 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        var total = 0;


        for (String s : list) {
            var valid = isValid(s);
            if (valid) {
                total++;
            }
        }


        return String.valueOf(total);
    }

    private boolean isValid(String s) {
        var subList = s.split(" ");

        String direction = null;
        for (int i = 0; i < subList.length - 1; i++) {
            var left = Integer.valueOf(subList[i]);
            var right = Integer.valueOf(subList[i + 1]);
            if (left.equals(right)) {
                return false;
            }
            if (Objects.isNull(direction)) {

                direction = left < right ? "ASC" : "DESC";
            } else {
                if (direction.equals("ASC") && left > right) {
                    return false;
                }
                if (direction.equals("DESC") && left < right) {
                    return false;
                }

            }

            if (Math.abs(left - right) > 3) {
                return false;
            }

        }


        return true;
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");
        var total = 0;


        for (String s : list) {
            var subList = s.split(" ");
            var valid = isValid2(subList);
//            log.info("---- {} ", valid);
            if (valid) {
                total++;
            }
        }


        return String.valueOf(total);
    }

    private boolean isValid2(String[] subList) {
        var valid = isValid3(subList);
        if (valid) {
            return true;
        }
        for (int i = 0; i < subList.length; i++) {
            var clone = new ArrayList<String>(List.of(subList));
            clone.remove(i);
            if (isValid3(clone.toArray(new String[0]))) {
                return true;
            }
        }

        return false;
    }

    private boolean isValid3(String[] subList) {

        String direction = null;
        for (int i = 0; i < subList.length - 1; i++) {
            var left = Integer.valueOf(subList[i]);
            var right = Integer.valueOf(subList[i + 1]);
            if (left.equals(right)) {
                return false;
            }
            if (Objects.isNull(direction)) {

                direction = left < right ? "ASC" : "DESC";
            } else {
                if (direction.equals("ASC") && left > right) {
                    return false;
                }
                if (direction.equals("DESC") && left < right) {
                    return false;
                }

            }

            if (Math.abs(left - right) > 3) {
                return false;
            }

        }


        return true;
    }

    @Override
    public String getAnswerPart1() {
        return "220";
    }

    @Override
    public String getAnswerPart2() {
        return "296";
    }
}
