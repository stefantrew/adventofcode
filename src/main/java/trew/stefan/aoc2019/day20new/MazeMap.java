package trew.stefan.aoc2019.day20new;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static trew.stefan.aoc2019.day20new.Day20New.DIRECTIONS;

@Slf4j
@SuppressWarnings("DuplicatedCode")
public class MazeMap {

    @Getter
    int height;
    @Getter
    int width;
    @Getter
    MazePoint[][] map;
    @Getter
    private MazePoint entry;
    private MazePoint exit;


    public MazeMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new MazePoint[width][height];

    }

    public void printMap() {

        log.info("---------------------------------------------------------------");
        for (int i = 0; i < height; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < width; j++) {
                if (map[j][i] == null) {
                    builder.append(" ");
                } else {
                    builder.append(map[j][i]);

                }
            }
            log.info(builder.toString());
        }
        log.info("---------------------------------------------------------------");
    }


    public void linkPoints() {
        log.info("---------------------------------------------------------------");
        Map<String, MazePoint> portals = new HashMap<>();
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                MazePoint point = map[j][i];
                if (point == null || point.getType() != MazePoint.Type.LABEL) {
                    continue;
                }

                boolean flag = false;
                for (int[] direction : DIRECTIONS) {
                    MazePoint other = map[point.getX() + direction[0]][point.getY() + direction[1]];
                    if (other != null && other.isFree()) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    continue;
                }

                for (int[] direction : DIRECTIONS) {
                    MazePoint other = map[point.getX() + direction[0]][point.getY() + direction[1]];
                    if (other != null && other.getType() == MazePoint.Type.LABEL) {

                        String label1 = other.toString();
                        String label2 = point.toString();

                        if (label1.equals("A") && label2.equals("A")) {
                            entry = point;
                            point.setEntry();
                        } else if (label1.equals("Z") && label2.equals("Z")) {
                            exit = point;
                            point.setExit();
                        } else {
                            String id = label1.compareTo(label2) < 0 ? label1 + label2 : label2 + label1;
                            boolean isInner = other.getX() == 0 || other.getY() == 0 || other.getX() == width - 1 || other.getY() == height - 1;

                            if (portals.containsKey(id)) {
                                MazePoint otherPoint = portals.get(id);
                                otherPoint.setPortal(point, id, isInner);
                                point.setPortal(otherPoint, id, !isInner);
                            } else {
                                portals.put(id, point);
                            }
                        }


                    }
                }


            }
        }
        log.info("---------------------------------------------------------------");


    }


    public MazePoint getPoint(int x, int y) {
        if (x >= width) {
            return null;
        }
        if (y >= height) {
            return null;
        }
        if (x <= 0 || y <= 0) {
            return null;
        }
        return map[x][y];
    }
}
