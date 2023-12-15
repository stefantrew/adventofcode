package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day11 extends AbstractAOC {

    class Galaxy {

        int x;
        int y;
        int id;

        public Galaxy(int x, int y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }

    }

    @Override
    public String runPart1() {

        return formatResult(runPart(2));
    }

    private List<String> getList(int factor) {
        var newList = new ArrayList<String>();
        var list = getStringInput("");

        var colSet = new boolean[list.get(0).length()];


        for (var s : list) {
            for (int i = 0; i < s.toCharArray().length; i++) {
                if (s.toCharArray()[i] == '#') {
                    colSet[i] = true;
                }
            }
        }

        for (var s : list) {

            var newStr = "";
            for (int i = 0; i < s.toCharArray().length; i++) {
                newStr += s.toCharArray()[i];
                if (!colSet[i]) {
                    for (int j = 0; j < factor - 1; j++) {
                        newStr += ".";
                    }
                }
            }

            newList.add(newStr);
            if (!s.contains("#")) {
                for (int i = 0; i < factor - 1; i++) {
                    newList.add(newStr);
                }
            }
        }
        return newList;
    }

    @Override
    public String runPart2() {

        Integer prev = 0;
        var diff = 0L;
        for (int i = 0; i < 10; i++) {
            var o1 = runPart(i);
            diff = o1 - prev;
            prev = o1;
        }

        return formatResult(runPart(2) + diff * (1000000-2));
    }

    private Integer runPart(int factor) {
        var total = 0;
        var list = getList(factor);

        var galaxyList = new ArrayList<Galaxy>();
        for (int row = 0; row < list.size(); row++) {
            var s = list.get(row);
            for (int col = 0; col < s.length(); col++) {
                if (s.charAt(col) == '#') {
                    galaxyList.add(new Galaxy(col, row, galaxyList.size()));
                }
            }
        }


        for (Galaxy galaxy : galaxyList) {
            var min = Integer.MAX_VALUE;

            for (Galaxy galaxy2 : galaxyList) {
                if (galaxy.id == galaxy2.id) {
                    continue;
                }
                var dist = Math.abs(galaxy.x - galaxy2.x) + Math.abs(galaxy.y - galaxy2.y);
                if (dist < min) {
                    min = dist;
                }
                total += dist;
            }

        }

        return (total / 2);
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
