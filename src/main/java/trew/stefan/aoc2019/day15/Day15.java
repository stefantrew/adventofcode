package trew.stefan.aoc2019.day15;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.aoc2019.completed.OxygenProcessor;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.Point;

import java.util.List;

/*
251
 */
@Slf4j
public class Day15 implements Day {

    private class Droid {

        Point point;

        public Droid(Point point) {
            this.point = point;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    class FloorMap {

        @Getter
        int sizeV;
        @Getter
        int sizeH;
        @Getter
        private char[][] map;
        private boolean[][] visited;
        private OxygenProcessor[][] cpus;
        private Droid droid;
        @Getter
        Point entry;
        @Getter
        Point oxygenPoint;

        FloorMap(int sizeV, int sizeH, Droid droid, OxygenProcessor oxygenProcessor) {
            this.sizeV = sizeV;
            this.sizeH = sizeH;
            this.droid = droid;
            map = new char[sizeH][sizeV];
            visited = new boolean[sizeH][sizeV];
            cpus = new OxygenProcessor[sizeH][sizeV];
            for (int i = 0; i < sizeV; i++) {
                for (int j = 0; j < sizeH; j++) {
                    map[j][i] = ' ';
                }
            }
            entry = droid.point.getClone();
            map[droid.point.getX()][droid.point.getY()] = 'S';
            cpus[droid.point.getX()][droid.point.getY()] = oxygenProcessor.cloneProcessor();

        }

            public void printMap() {

                log.info("---------------------------------------------------------------");
                for (int i = 0; i < sizeV; i++) {
                    StringBuilder builder = new StringBuilder();
                    for (int j = 0; j < sizeH; j++) {
                        if (droid.point.getX() == j && droid.point.getY() == i) {
                            builder.append('D');
                        } else {
                            builder.append(map[j][i]);
                        }
                    }
                    log.info(builder.toString());
                }
                log.info("---------------------------------------------------------------");
            }

        public OxygenProcessor getNextProcessor() {
            for (int i = 0; i < sizeV; i++) {
                for (int j = 0; j < sizeH; j++) {
                    if ((map[j][i] == '.' || map[j][i] == 'S') && getNextMove(j, i) > 0) {
                        droid.point.setX(j);
                        droid.point.setY(i);
                        return cpus[j][i];
                    }
                }
            }
            return null;
        }

        public int getNextMove(int x, int y) {
            if (map[x][y - 1] == ' ') {
                return NORTH;
            } else if (map[x][y + 1] == ' ') {
                return SOUTH;
            } else if (map[x + 1][y] == ' ') {
                return EAST;
            } else if (map[x - 1][y] == ' ') {
                return WEST;
            }
            return 0;
        }

        public void mark(int nextMove, boolean open, OxygenProcessor oxygenProcessor, boolean isOxygen) {
            int x = droid.point.getX();
            int y = droid.point.getY();
            switch (nextMove) {
                case NORTH:
                    y--;
                    break;
                case SOUTH:
                    y++;
                    break;
                case EAST:
                    x++;
                    break;
                case WEST:
                    x--;
                    break;
            }
            if (open) {

                if (isOxygen) {
                    oxygenPoint = new Point(x, y);
                }
                map[x][y] = isOxygen ? 'S' : '.';
                droid.point.setX(x);
                droid.point.setY(y);
                cpus[x][y] = oxygenProcessor.cloneProcessor();
            } else {
                map[x][y] = '#';

            }
        }

        public boolean isWall(Integer x, Integer y) {
            return map[x][y] == '#';
        }

        public void setVisited(Integer x, Integer y, boolean b) {
            visited[x][y] = b;
        }

        public boolean isExit(Integer x, Integer y) {
            return oxygenPoint.getX().equals(x) && oxygenPoint.getY().equals(y);
        }

        public boolean isExplored(Integer x, Integer y) {
            return visited[x][y];
        }

        public boolean isValidLocation(Integer x, Integer y) {
            return map[x][y] != '#';
        }
    }

    private final int NORTH = 1;
    private final int SOUTH = 2;
    private final int WEST = 3;
    private final int EAST = 4;
    Droid droid = new Droid(new Point(30, 22));

    public void run() {
        List<String> lines = InputReader2019.readStrings(15, "");

        OxygenProcessor processor = new OxygenProcessor(lines);
        FloorMap map = new FloorMap(42, 60, droid, processor);

        try {
            while (processor != null) {

                while (nextMove(map, processor)) ;
                processor = map.getNextProcessor();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        map.printMap();
        log.info("{}", (new Day15MazeWalker()).solve(map).size());
        Day15OxygenSpreader spreader = new Day15OxygenSpreader();
        spreader.solve(map);
    }

    private boolean nextMove(FloorMap map, OxygenProcessor processor) throws Exception {
        int nextMove = map.getNextMove(droid.point.getX(), droid.point.getY());
        if (nextMove == 0) {
            return false;
        }
        int output = processor.run(nextMove);
        switch (output) {
            case 0:
                //hits wall
                map.mark(nextMove, false, processor, false);
                break;
            case 1:
                map.mark(nextMove, true, processor, false);
                break;
            case 2:
                map.mark(nextMove, true, processor, true);
                break;
        }
        return true;
    }

}
