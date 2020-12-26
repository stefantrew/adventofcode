package trew.stefan.aoc2019.day10;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class Day2016_13Test {

    boolean isWall(int x, int y) {

        int res = x * x + 3 * x + 2 * x * y + y + y * y;
        res += 1350;

        String s = Integer.toBinaryString(res);

        return s.replace("0", "").length() % 2 == 1;
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

        int size = 100;
        Set<Integer> distinct = new HashSet<>();
        boolean[][] map = new boolean[size][size];
        boolean[][] visited = new boolean[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                map[x][y] = isWall(x, y);
            }
        }

        Queue<AmazonMazePointTest> q = new LinkedList<>();

        q.add(new AmazonMazePointTest(1, 1, null));
        while (q.size() > 0) {

            AmazonMazePointTest current = q.poll();

            visited[current.x][current.y] = true;

            if (map[current.x][current.y]) {
                continue;
            }
            distinct.add(current.getHashCode());

            for (int[] direction : directions) {
                AmazonMazePointTest test = new AmazonMazePointTest(current.x + direction[0], current.y + direction[1], current);
                if (test.x < 0 || test.y < 0 || test.x == size || test.y == size) {
                    continue;
                }
                if (visited[test.x][test.y]) {
                    continue;
                }

                if (test.distance > 50) {
                    continue;
                }


                q.add(test);
            }
        }
        log.info("unique {}", distinct.size());

    }


}
