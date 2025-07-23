package trew.stefan.aoc2023;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day22 extends AbstractAOC {

    @ToString
    static class Brick {

        int x0;
        int y0;
        int z0;
        int x1;
        int y1;
        int z1;
        String id;
        boolean isDisintegrated = false;
        boolean hasMoved = false;

        int snapZ0;
        int snapZ1;


        public Brick(int x0, int y0, int z0, int x1, int y1, int z1, String id) {
            this.x0 = x0;
            this.y0 = y0;
            this.z0 = z0;
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.id = id == null ? "?" : id;
        }

        public void createSnapShot() {
            snapZ0 = z0;
            snapZ1 = z1;
        }

        public void resetToSnapShot() {
            z0 = snapZ0;
            z1 = snapZ1;
            hasMoved = false;
            isDisintegrated = false;
        }

        public boolean hasCoord(int x, int y, int z) {
            if (x != -1 && (x < x0 || x > x1)) {
                return false;
            }
            if (y != -1 && (y < y0 || y > y1)) {
                return false;
            }
            if (z < z0 || z > z1) {
                return false;
            }

            return true;
        }

        public void moveDown() {
            z0--;
            z1--;
            hasMoved = true;
        }

        public boolean canMoveDown(List<Brick> bricks) {

            if (this.z0 == 1) {
                return false;
            }
            if (this.isDisintegrated) {
                return false;
            }

            for (Brick brick : bricks) {
                if (brick == this) {
                    continue;
                }

                if (!canMoveDown(brick)) {
                    return false;
                }
            }

            return true;

        }

        public boolean canMoveDown(Brick brick) {

            if (brick.isDisintegrated) {
                return true;
            }

            if (brick.x1 < x0 || brick.x0 > x1) {
                return true;
            }

            if (brick.y1 < y0 || brick.y0 > y1) {
                return true;
            }

            if (brick.z1 < z0 - 1 || brick.z0 > z1 - 1) {
                return true;
            }


            return false;
        }
    }

    @Override
    public String runPart1() {

        var bricks = getBricks();


        fallBricks(bricks);

        var total = 0;

        for (Brick testBrick : bricks) {
            testBrick.isDisintegrated = true;

            boolean anyMove = false;
            for (Brick brick : bricks) {
                boolean o1 = brick.canMoveDown(bricks);
                if (o1) {
                    anyMove = true;
                }
            }
            if (!anyMove) {
                total++;
            }

            testBrick.isDisintegrated = false;
        }

        return formatResult(total);
    }

    private static void fallBricks(ArrayList<Brick> bricks) {
        while (true) {
            boolean anyMove = false;

            for (Brick brick : bricks) {
                boolean o1 = brick.canMoveDown(bricks);
                if (o1) {
                    brick.moveDown();
                    anyMove = true;
                }
            }

            if (!anyMove) {
                break;
            }
        }
    }

    private ArrayList<Brick> getBricks() {
        final String regex = "(\\d*),(\\d*),(\\d*)~(\\d*),(\\d*),(\\d*),?(.)?";

        var pattern = Pattern.compile(regex);

        var bricks = new ArrayList<Brick>();
        var list = getStringInput("");

        for (var s : list) {
            var matcher = pattern.matcher(s);
            if (matcher.find()) {
                var brick = new Brick(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)),
                        Integer.parseInt(matcher.group(6)),
                        matcher.group(7)

                );
                bricks.add(brick);

            }
        }
        return bricks;
    }

    private void printBricks(ArrayList<Brick> bricks) {
        for (int z = 9; z > 0; z--) {

            var sb = new StringBuilder();
            for (int x = 0; x < 3; x++) {

                var c = findBrick(x, -1, z, bricks);
                sb.append(c);
            }

            log.info("{} {}", sb.toString(), z);
        }

        log.info("");
        for (int z = 9; z > 0; z--) {

            var sb = new StringBuilder();
            for (int y = 0; y < 3; y++) {

                var c = findBrick(-1, y, z, bricks);
                sb.append(c);
            }

            log.info("{} {}", sb.toString(), z);
        }
    }

    private char findBrick(int x, int y, int z, ArrayList<Brick> bricks) {
        for (Brick brick : bricks) {
            if (brick.hasCoord(x, y, z)) {
                return brick.id.charAt(0);
            }
        }


        return '.';
    }

    @Override
    public String runPart2() {

        var bricks = getBricks();


        fallBricks(bricks);

        var best = 0;
        for (Brick testBrick : bricks) {
            testBrick.createSnapShot();
        }

        for (Brick testBrick : bricks) {

            for (Brick temp : bricks) {
                temp.resetToSnapShot();
            }
            testBrick.isDisintegrated = true;
            fallBricks(bricks);
            int current = 0;
            for (Brick brick : bricks) {
                if (brick.hasMoved) {
                    current++;
                }
            }
            log.info("{} {}", testBrick.id, current);
            best += current;


        }

        return formatResult(best);

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
