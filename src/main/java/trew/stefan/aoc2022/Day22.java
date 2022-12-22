package trew.stefan.aoc2022;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Direction;
import trew.stefan.utils.Matrix;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day22 extends AbstractAOC {

    enum Tile {
        WALL("#"),
        OPEN("."),
        MONKEY("M"),
        VISITED("V"),
        VOID(" ");

        final String name;

        Tile(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public String runPart1() {

        var total = 0;
        var result = "";

        var list = getStringInput("");

        var code = list.remove(list.size() - 1);

        var max = 0;
        var rows = 0;
        for (var s : list) {
            max = Math.max(max, s.length());
            if (!s.isBlank()) {
                rows++;
            }
        }

        log.info("max {}", max);
        var matrix = new Matrix<Tile>(rows, max, Tile.class, Tile.VOID);

        for (int row = 0; row < list.size(); row++) {
            String s = list.get(row);

            for (int col = 0; col < s.toCharArray().length; col++) {
                var tile = switch (s.charAt(col)) {
                    case '.' -> Tile.OPEN;
                    case '#' -> Tile.WALL;
                    case ' ', default -> Tile.VOID;
                };
                matrix.set(row, col, tile);

            }
        }


        log.info("tiles {}", matrix.count(tile -> tile != Tile.VOID));
        var p = Pattern.compile("[LR]");
        var m = p.matcher(code);

        var codes = Arrays.stream(m.replaceAll(",$0,").split(",")).toList();

        var start = list.get(0).indexOf(".");
        matrix.set(0, start, Tile.MONKEY);

        var dir = Direction.RIGHT;

        matrix.printMatrix(false);
        return formatResult(doWork(matrix, dir, codes));
    }

    private int doWork(Matrix<Tile> matrix, Direction dir, List<String> codes) {

        for (String code : codes) {
//            log.info("================== {} ==================", code);
//            matrix.printMatrix(false);
            var current = matrix.find(Tile.MONKEY).get(0);

            if (code.equals("L")) {
                dir = dir.rotateCounterClockWise();
            } else if (code.equals("R")) {
                dir = dir.rotateClockWise();
            } else {
                var distance = Integer.parseInt(code);
                while (distance-- > 0) {

                    var next = current.move(dir);
                    if (!matrix.checkDimensions(next)) {
                        next = getPoint(matrix, next, dir);
                    }
                    var tile = matrix.get(next);

                    if (tile == Tile.VOID) {
                        next = getPoint(matrix, next, dir);
                        tile = matrix.get(next);
                    }

                    if (tile == Tile.WALL) {
//                        log.info("WALL");
                        break;
                    } else if (tile == Tile.OPEN || tile == Tile.VISITED) {
                        matrix.set(current, Tile.VISITED);
                        matrix.set(next, Tile.MONKEY);
                        current.setPos(next);
                    }

                }


            }
        }

        var monkey = matrix.find(Tile.MONKEY).get(0);
        var total = (monkey.getRow() + 1) * 1000 + (monkey.getCol() + 1) * 4;
        log.info("{} {} {}", monkey.getRow(), monkey.getCol(), dir);
        switch (dir) {

            case UP -> total += 3;
            case DOWN -> total += 1;
            case LEFT -> total += 2;
            case RIGHT -> {
            }
        }
        return total;
    }

    private Matrix<Tile>.RCPoint getPoint(Matrix<Tile> matrix, Matrix<Tile>.RCPoint next, Direction dir) {
        var temp = next.clonePoint();
        switch (dir) {

            case UP -> temp.setRow(matrix.getHeight() - 1);
            case DOWN -> temp.setRow(0);
            case LEFT -> temp.setCol(matrix.getWidth() - 1);
            case RIGHT -> temp.setCol(0);
        }
        while (matrix.get(temp) == Tile.VOID) {
            temp = temp.move(dir);
        }
//        log.info("Returning {}", temp);
        return temp;
    }


    @AllArgsConstructor
    class Item {

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
