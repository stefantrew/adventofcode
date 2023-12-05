package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashMap;
import java.util.HashSet;

@Slf4j
public class Day03 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0L;


        var list = getStringInput();


        var width = list.get(0).length();
        var str = "";
        var valid = false;
        for (int row = 0; row < list.size(); row++) {

            if (valid && !str.isEmpty()) {
                total += Integer.parseInt(str);
            }

            str = "";
            valid = false;

            for (int col = 0; col < width; col++) {
                var currentChar = list.get(row).charAt(col);
                if (!Character.isDigit(currentChar)) {
                    if (valid && str.length() > 0) {
                        total += Integer.parseInt(str);
                    }

                    str = "";
                    valid = false;
                    continue;
                }

                str += currentChar;

                if (valid) {
                    continue;
                }
                for (int x = -1; x < 2; x++) {
                    if (col + x < 0 || col + x >= width) {
                        continue;
                    }

                    for (int y = -1; y < 2; y++) {
                        if (row + y < 0 || row + y >= list.size()) {
                            continue;
                        }
                        if (y == 0 && x == 0) {
                            continue;
                        }
                        var test = list.get(y + row).charAt(x + col);

                        if (!Character.isDigit(test) && test != '.') {
                            valid = true;
                        }

                    }
                }
            }

        }

        return formatResult(total);
    }


    @Override
    public String runPart2() {


        var total = 0L;


        var list = getStringInput("");

        var map = new HashMap<String, Integer>();

        var width = list.get(0).length();
        var str = "";
        var valid = new HashSet<String>();
        for (int row = 0; row < list.size(); row++) {


            if (!valid.isEmpty() && !str.isEmpty()) {

                for (String pos : valid) {
                    if (map.containsKey(pos)) {
                        var first = map.get(pos);
                        total += first * Long.valueOf(str);
                    } else {
                        map.put(pos, Integer.valueOf(str));
                    }
                }
            }
            str = "";
            valid.clear();

            for (int col = 0; col < width; col++) {
                var currentChar = list.get(row).charAt(col);
                if (!Character.isDigit(currentChar)) {
                    if (!valid.isEmpty() && !str.isEmpty()) {

                        for (String pos : valid) {
                            if (map.containsKey(pos)) {
                                var first = map.get(pos);
                                total += first * Long.valueOf(str);
                            } else {
                                map.put(pos, Integer.valueOf(str));
                            }
                        }
                    }
                    str = "";
                    valid.clear();
                    continue;
                }

                str += currentChar;


                for (int x = -1; x < 2; x++) {
                    var index1 = x + col;
                    if (index1 < 0 || index1 >= width) {
                        continue;
                    }

                    for (int y = -1; y < 2; y++) {
                        var index = y + row;
                        if (index < 0 || index >= list.size()) {
                            continue;
                        }
                        if (y == 0 && x == 0) {
                            continue;
                        }
                        var test = list.get(index).charAt(index1);

                        if (test == '*') {
                            valid.add(index + "," + index1);
                        }

                    }
                }
            }

        }

        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "540212";
    }

    @Override
    public String getAnswerPart2() {
        return "87605697";
    }
}
