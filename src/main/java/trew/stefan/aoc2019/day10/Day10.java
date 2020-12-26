package trew.stefan.aoc2019.day10;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
public class Day10 implements Day {

    @Getter
    class AsteroidField {

        int width;
        char[][] map;
        List<Point> points = new ArrayList<>();

        AsteroidField(List<String> input) {
            width = input.get(0).length();
            map = new char[input.size()][width];
            for (int i = 0; i < input.size(); i++) {
                map[i] = input.get(i).toCharArray();
                for (int j = 0; j < width; j++) {
                    if (map[i][j] != '.') {
                        points.add(new Point(j, i));
                    }
                }
            }
        }

        int calculatePoint(Point point) throws Exception {
            Set<Point> excluded = new HashSet<>();
            excluded.add(point);
            for (Point asteroid : points) {

                if (point.equals(asteroid)) {
                    continue;
                }
                StraightLine line = new StraightLine(point, asteroid);
                for (Point testPoint : points) {
                    if (testPoint.equals(point) || testPoint.equals(asteroid)) {
                        continue;
                    }
                    if (!line.canSeePoint(testPoint)) {
                        excluded.add(testPoint);
                    }
                }
            }


            return points.size() - excluded.size();
        }

        public List<Point> findVisibleAsteroids(Point point, List<Point> pointList) throws Exception {
            Set<Point> excluded = new HashSet<>();
            excluded.add(point);
            for (Point asteroid : pointList) {

                if (point.equals(asteroid)) {
                    continue;
                }
                StraightLine line = new StraightLine(point, asteroid);
                for (Point testPoint : pointList) {
                    if (testPoint.equals(point) || testPoint.equals(asteroid)) {
                        continue;
                    }
                    if (!line.canSeePoint(testPoint)) {
                        excluded.add(testPoint);
                    }
                }
            }

            List<Point> visible = new ArrayList<>();
            for (Point asteroid : pointList) {
                if (!excluded.contains(asteroid)) {
                    point.getDegrees(asteroid);
                    visible.add(asteroid);
                }
            }

            return visible;
        }

        void printPoints() {
            log.info("{}", points);
        }

        void printMap() {

//            log.info("   00000000001111111111222222222233333333334444444444");
//            log.info("   01234567890123456789012345678901234567890123456789");
            log.info("    01234567890");

            for (int i = 0; i < map.length; i++) {
                char[] c = map[i];
                log.info(" {} {}", String.format("%2d", i), new String(c));
            }
        }

    }

    public void run() {
        run3();
    }

    public void run3() {

        List<String> lines = InputReader2019.readStrings(10, "");
        AsteroidField field = new AsteroidField(lines);

        List<Point> points = field.getPoints();
        Point station = new Point(26, 36);

        int counter = 1;
        if (!points.contains(station)) {
            try {
                throw new Exception("Station not found");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        points.remove(station);
        int result = 0;

        while (points.size() > 0) {
            log.info("----------------------------------------------");
            try {
                List<Point> visible = field.findVisibleAsteroids(station, points);

                visible.sort((a, b) -> {
                    if (a.getDegrees() == b.getDegrees()) return 0;
                    return a.getDegrees() > b.getDegrees() ? 1 : -1;
                });

                for (Point pt : visible) {
                    points.remove(pt);
                    if (counter == 200) result = pt.getX() * 100 + pt.getY();
                    log.info("{} {} {}", counter++, pt, points.size());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        log.info("RESULT {}", result);
    }

    public void run2() {
        List<String> lines = InputReader2019.readStrings(10, "");
        AsteroidField field = new AsteroidField(lines);

        Integer max = null;
        Point maxPoint = null;

        for (Point point : field.getPoints()) {
            int result = 0;
            try {
                result = field.calculatePoint(point);
                if (max == null || result > max) {
                    max = result;
                    maxPoint = point;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("RESULT {} {}", point, result);

//            break;
        }
        log.info("RESULT {} {}", max, maxPoint);
//        field.printMap();
//        field.printPoints();
    }

}

