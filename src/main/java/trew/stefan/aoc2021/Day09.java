package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class Day09 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var list = getStringInput("");

        var height = list.size();
        var width = list.get(0).length();
        var map = new int[height][width];

        buildMap(list, height, map);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var val = map[y][x];
                if (isMinimal(y < height - 1, x < width - 1, map, y, x)) {
                    total += val + 1;
                }
            }
        }
        return formatResult(total);
    }


    @Override
    public String runPart2() {


        var list = getStringInput("");

        var height = list.size();
        var width = list.get(0).length();
        var map = new int[height][width];

        buildMap(list, height, map);

        run(height, width, map);

        while (true) {
            if (!run2(height, width, map)) {
                break;
            }
        }

        var hash = new HashMap<Integer, Integer>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] < 0) {
                    if (!hash.containsKey(map[y][x])) {
                        hash.put(map[y][x], 0);
                    }
                    var a = hash.get(map[y][x]);
                    hash.put(map[y][x], a + 1);

                }
            }
        }
        var total = hash
                .values()
                .stream()
                .sorted((o1, o2) -> Integer.compare(o2, o1))
                .limit(3)
                .reduce(1, (integer, integer2) -> integer * integer2);

        return formatResult(total);
    }

    private void buildMap(List<String> list, int height, int[][] map) {
        for (int i = 0; i < height; i++) {
            var s = list.get(i);
            var index = 0;
            for (var c : s.toCharArray()) {
                map[i][index] = Integer.parseInt(String.valueOf(c));
                index++;
            }
        }
    }

    private void run(int height, int width, int[][] map) {
        var current = -1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isMinimal(y < height - 1, x < width - 1, map, y, x)) {
                    map[y][x] = current--;
                }
            }
        }
    }

    private boolean isMinimal(boolean b, boolean b1, int[][] map, int y, int x) {
        var val = map[y][x];
        var temp = new ArrayList<Integer>();

        if (x > 0) {
            temp.add(map[y][x - 1]);
        }
        if (y > 0) {
            temp.add(map[y - 1][x]);
        }
        if (b1) temp.add(map[y][x + 1]);
        if (b) temp.add(map[y + 1][x]);

        var f = true;

        for (Integer integer : temp) {
            if (integer <= val) {
                f = false;
                break;
            }
        }
        return f;
    }

    private boolean run2(int height, int width, int[][] map) {
        var changed = false;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var val = map[y][x];
                if (val >= 0) {
                    continue;
                }

                if (x > 0 && map[y][x - 1] != 9 && map[y][x - 1] != val) {
                    changed = true;
                    map[y][x - 1] = val;
                }
                if (y > 0 && map[y - 1][x] != 9 && map[y - 1][x] != val) {
                    changed = true;
                    map[y - 1][x] = val;
                }
                if (x < width - 1 && map[y][x + 1] != 9 && map[y][x + 1] != val) {
                    changed = true;
                    map[y][x + 1] = val;
                }
                if (y < height - 1 && map[y + 1][x] != 9 && map[y + 1][x] != val) {
                    changed = true;
                    map[y + 1][x] = val;
                }


            }
        }

        return changed;
    }

    @Override
    public String getAnswerPart1() {
        return "486";
    }

    @Override
    public String getAnswerPart2() {
        return "1059300";
    }
}
