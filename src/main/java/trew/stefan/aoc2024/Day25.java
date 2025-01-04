package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;

@Slf4j
public class Day25 extends AbstractAOC {

    class Item {
        char[][] values = new char[7][5];
        int[] heights = null;

        boolean isLock() {
            return values[0][0] == '#';
        }

        @Override
        public String toString() {
            var result = new StringBuilder();
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 7; j++) {
//                    result.append(values[j][i]);
//                }
//                result.append("\n");
//            }
            var heights = getHeights();
            for (int i = 0; i < 5; i++) {
                result.append(heights[i]);
            }

            return result.toString();
        }

        int[] getHeights() {

            if (heights != null) {
                return heights;
            }
            heights = new int[5];

            var target = isLock() ? '#' : '.';

            for (int i = 0; i < 5; i++) {
                heights[i] = 0;
                for (int j = 1; j < 6; j++) {
                    if (values[j][i] == '#') {
                        heights[i]++;
                    }
                }
            }

            /*
0,5,3,4,3
1,2,0,5,3

5,0,2,1,3
4,3,4,0,2
3,0,2,0,1
 */

            return heights;

        }
    }

    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");

        var keys = new ArrayList<Item>();
        var locks = new ArrayList<Item>();

        var item = new Item();
        var row = 0;
        for (var s : list) {
            log.info("{}", s);
            if (s.isBlank()) {
                if (item.isLock()) {
                    locks.add(item);
                } else {
                    keys.add(item);
                }
                item = new Item();
                row = 0;
            } else {
                item.values[row] = s.toCharArray();
                row++;
            }
        }

        for (var lock : locks) {
            for (var key : keys) {
                var canFit = canFit(lock, key);
                if (canFit) {
                    total++;
                }
            }
        }


        return formatResult(total);
    }

    private boolean canFit(Item lock, Item key) {
        var lockHeights = lock.getHeights();
        var keyHeights = key.getHeights();
//        log.info("{}", lock);
//        log.info("{}", key);
        for (int i = 0; i < 5; i++) {
            if (lockHeights[i] + keyHeights[i] > 5) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
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
