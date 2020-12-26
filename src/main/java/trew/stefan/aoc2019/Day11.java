package trew.stefan.aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.aoc2019.completed.DayProcessor11;
import trew.stefan.aoc2019.completed.PaintProcessor;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.Point;

import java.util.*;

/**
 * 9979
 * 9978
 */
@Slf4j
public class Day11 implements Day {

    private class SimplePoint {
        private int x;
        private int y;

        public SimplePoint(Point point) {
            x = point.getX();
            y = point.getY();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimplePoint that = (SimplePoint) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private class NumberPlatePanel {

        int sizeV;
        int sizeH;
        private int[][] map;
        private Set<SimplePoint> visited = new HashSet<>();

        NumberPlatePanel(int sizeV, int sizeH) {
            this.sizeV = sizeV;
            this.sizeH = sizeH;
            map = new int[sizeH][sizeV];
            for (int i = 0; i < sizeV; i++) {
                for (int j = 0; j < sizeH; j++) {
                    map[j][i] = 1;
                }

            }
        }

        int getVisitedCount() {
            return visited.size();
        }

        int readPoint(Point point) {
            if (point.getX() >= sizeH || point.getY() >= sizeV) {
                throw new IndexOutOfBoundsException(point.toString());
            }
            if (point.getX() < 0 || point.getY() < 0) {
                throw new IndexOutOfBoundsException(point.toString());
            }
            return map[point.getX()][point.getY()] == 1 ? 1 : 0;
        }

        void setPoint(Point point, int value) {

            if (point.getX() >= sizeH || point.getY() >= sizeV) {
                throw new IndexOutOfBoundsException(point.toString());
            }
            if (point.getX() < 0 || point.getY() < 0) {
                throw new IndexOutOfBoundsException(point.toString());
            }
            visited.add(new SimplePoint(point));
            map[point.getX()][point.getY()] = value;
        }

        public void printMap(PainterRobot robot) {

            log.info("");
            log.info("");
            log.info("");
            for (int i = 0; i < sizeV; i++) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < sizeH; j++) {
                    if (i == robot.position.getY() && j == robot.position.getX()) {
                        builder.append('*');

                    } else {
                        switch (map[j][i]) {
                            case -1:
                                builder.append(' ');
                                break;
                            case 0:
                                builder.append('.');
                                break;
                            case 1:
                                builder.append('#');
                                break;
                        }

                    }
                }
                log.info(builder.toString());
            }

        }
    }

    @Data
    private class PainterRobot {

        private Point position;
        private PaintProcessor processor;
        private NumberPlatePanel platePanel;

        PainterRobot(Point position, PaintProcessor processor, NumberPlatePanel platePanel) {
            this.position = position;
            this.processor = processor;
            this.platePanel = platePanel;
        }

        boolean doMove() throws Exception {
            // Read current colour
            int currentColour = platePanel.readPoint(position);
            log.info("Current colour {}", currentColour == 0 ? "Black" : "White");
            if (!this.processor.run(currentColour)) {
                log.info("Done");
                return false;
            }
            int nextColour = this.processor.getOutputColour().intValue();
            int nextDirection = this.processor.getOutputDirection().intValue();
            log.info("Next colour {}", nextColour == 0 ? "Black" : "White");
            platePanel.setPoint(position, nextColour);

            position.addDegrees(nextDirection == 1 ? 90 : -90);
            position.moveForward();
            return true;
        }
    }

    public void run() {
        List<String> lines = InputReader2019.readStrings(11, "");
        int sizeV = 6;
        int sizeH = 50;
        PaintProcessor processor = new DayProcessor11(lines);
        NumberPlatePanel platePanel = new NumberPlatePanel(sizeV, sizeH);
        PainterRobot robot = new PainterRobot(new Point(0, 0, 90), processor, platePanel);
        try {
            int x = 0;
            while (x++ < 50000000) {

                if (!robot.doMove()) break;
            }
// ABEKZGFG

            platePanel.printMap(robot);
            log.info("Visited {}", platePanel.getVisitedCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
