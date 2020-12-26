package trew.stefan.aoc2019.day10;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.PermutationUtil;

import java.util.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class Day2016_24Test {

    char[][] buildMap(List<String> lines) {
        char[][] map = new char[lines.get(0).length()][lines.size()];
        for (int y = 0; y < lines.size() - 1; y++) {
            char[] chars = lines.get(y + 1).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                map[x][y] = chars[x];
            }

        }

        return map;
    }

    public void printMap(boolean[][] map) {

        log.info("---------------------------------------------------------------");
        for (int i = 0; i < map[0].length; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < map.length; j++) {
                if (map[j][i]) {
                    builder.append('@');
                } else {
                    builder.append('.');
                }
            }
            log.info(builder.toString());
        }
        log.info("---------------------------------------------------------------");
    }

    class AmazonMazePointTest {
        public int x;
        public int y;
        public AmazonMazePointTest parent;
        public int distance = 0;

        public AmazonMazePointTest(int x, int y, AmazonMazePointTest parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.distance = parent == null ? 0 : (parent.distance + 1);
        }

        int getHashCode() {
            return x * 100 + y;
        }
    }

    int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    @Test
    public void run() {

        List<String> lines = InputReader2019.readStrings(2016, "");
        char[][] map = buildMap(lines);

        int start_x = 0;
        int start_y = 0;
        List<Integer> numbers = new ArrayList<>();

        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                char c = map[x][y];
                switch (c) {
                    case '#':
                    case '.':
                        continue;
                    case '0':
                        start_x = x;
                        start_y = y;
                        break;
                    default:
                        try {

                            numbers.add(Integer.parseInt(c + ""));
                            numberCache.put(c, new AmazonMazePointTest(x, y, null));
                        } catch (Exception e) {

                        }
                        break;
                }
            }
        }

        int[] targets2 = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            targets2[i] = numbers.get(i);
        }
        log.info("{}", targets2);
        List<int[]> perms = PermutationUtil.getPermutationsVectors(targets2);
        Integer least = null;
        for (int[] targets : perms) {
            int result = run2(map, targets, 0, start_x, start_y);
            log.info("{} {}", result, targets);

            if (least == null || result < least) {
                least = result;
            }

        }
        log.info("{}", least);
    }

    Map<String, Integer> cache = new HashMap<>();
    Map<Character, AmazonMazePointTest> numberCache = new HashMap<>();

    private int run2(char[][] map, int[] targets, int index, int x, int y) {

        char target = index == targets.length ? '0' : Integer.toString(targets[index]).charAt(0);

        char start = map[x][y];

        if (cache.containsKey("" + start + target)) {
            AmazonMazePointTest test = numberCache.get(target);
            if (target == '0') {
                return +cache.get("" + start + target);
            } else {
                return run2(map, targets, index + 1, test.x, test.y) + cache.get("" + start + target);

            }
        }

        int width = map.length;
        int height = map[0].length;
        boolean[][] visited = new boolean[width][height];
        Queue<AmazonMazePointTest> q = new LinkedList<>();

        q.add(new AmazonMazePointTest(x, y, null));
        while (q.size() > 0) {

            AmazonMazePointTest current = q.poll();

            visited[current.x][current.y] = true;

            if (map[current.x][current.y] == '#') {
                continue;
            }

            for (int[] direction : directions) {
                AmazonMazePointTest test = new AmazonMazePointTest(current.x + direction[0], current.y + direction[1], current);
                if (test.x < 0 || test.y < 0 || test.x == width || test.y == height) {
                    continue;
                }
                if (visited[test.x][test.y]) {
                    continue;
                }

                char c = map[test.x][test.y];
                if (c != '.' && c != '#' && !cache.containsKey("" + start + c)) {
                    cache.put("" + start + c, test.distance);
                    cache.put("" + c + start, test.distance);
                    log.info("Caching {} {} {}", "" + start + c, test.distance, cache.size());
                    log.info("Caching {} {} {}", "" + c + start, test.distance, cache.size());
                }
                if (map[test.x][test.y] == target) {
                    if (target == '0') {
                        return test.distance;
                    }
                    return run2(map, targets, index + 1, test.x, test.y) + test.distance;
                }


                q.add(test);
            }
        }

        return 0;

    }


}
