package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

@Slf4j
public class Day10 extends AbstractAOC {

    public static final char[] EXTENDED = {0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0};

    public static final char getAscii(int code) {
        if (code >= 0x80 && code <= 0xFF) {
            return EXTENDED[code - 0x7F];
        }
        return (char) code;
    }

    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    enum MapTile {
        VERTICAL('|', 185, Direction.NORTH, Direction.NORTH, Direction.SOUTH, Direction.SOUTH),
        HORIZONTAL('-', 204, Direction.EAST, Direction.EAST, Direction.WEST, Direction.WEST),
        BEND_NE('L', 199, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.NORTH),
        BEND_NW('J', 188, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.NORTH),
        BEND_SE('F', 200, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH),
        BEND_SW('7', 186, Direction.NORTH, Direction.WEST, Direction.EAST, Direction.SOUTH),
        GROUND('.'),
        PATH('P'),
        INSIDE('I'),
        OUTSIDE('O'),
        START('S');

        char symbol;
        Direction directionIn1;
        Direction directionOut1;
        Direction directionIn2;
        Direction directionOut2;

        int code = 0;

        MapTile(char symbol, int code, Direction directionIn1, Direction directionOut1, Direction directionIn2, Direction directionOut2) {
            this.symbol = symbol;
            this.directionIn1 = directionIn1;
            this.directionOut1 = directionOut1;
            this.directionIn2 = directionIn2;
            this.directionOut2 = directionOut2;
            this.code = code;
        }

        MapTile(char symbol) {
            this.symbol = symbol;
        }

        Direction getNextDirection(Direction direction) {
            return directionIn1 == direction ? directionOut1 : directionOut2;
        }

        public static MapTile valueOf(char c) {
            for (MapTile d : MapTile.values()) {
                if (d.symbol == c) {
                    return d;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            if (code > 0) {
                return getAscii(code) + "";
            }
            return symbol + "";
        }
    }


    @Override
    public String runPart1() {

        var total = 0;


        var list = getStringInput("");
        var matrix = new Matrix<MapTile>(list.size(), list.get(0).length(), MapTile.class, MapTile.GROUND);

        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            matrix.setRow(i, s.chars().mapToObj(c -> MapTile.valueOf((char) c)).toList());

        }


        var current = matrix.find(MapTile.START).get(0);


        var currentDirection = Direction.WEST;

        while (true) {

            var next = switch (currentDirection) {
                case NORTH -> matrix.getPoint(current.getRow() - 1, current.getCol());
                case SOUTH -> matrix.getPoint(current.getRow() + 1, current.getCol());
                case EAST -> matrix.getPoint(current.getRow(), current.getCol() + 1);
                case WEST -> matrix.getPoint(current.getRow(), current.getCol() - 1);
            };



            currentDirection = next.getValue().getNextDirection(currentDirection);
//            log.info("{} {} {}", current, currentDirection, next);



            total++;
            if (next.getValue() == MapTile.START) {
                break;
            }
            current = next;
        }


        return formatResult(total / 2);
    }

    @Override
    public String runPart2() {


        var total = 0;


        var list = getStringInput("_sample");
        var matrix = new Matrix<MapTile>(list.size(), list.get(0).length(), MapTile.class, MapTile.GROUND);
        var cleanMatrix = new Matrix<MapTile>(list.size(), list.get(0).length(), MapTile.class, MapTile.GROUND);

        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            matrix.setRow(i, s.chars().mapToObj(c -> MapTile.valueOf((char) c)).toList());

        }


        var current = matrix.find(MapTile.START).get(0);


        var currentDirection = Direction.EAST;

        while (true) {

            cleanMatrix.set(current, current.getValue());
            var next = switch (currentDirection) {
                case NORTH -> matrix.getPoint(current.getRow() - 1, current.getCol());
                case SOUTH -> matrix.getPoint(current.getRow() + 1, current.getCol());
                case EAST -> matrix.getPoint(current.getRow(), current.getCol() + 1);
                case WEST -> matrix.getPoint(current.getRow(), current.getCol() - 1);
            };



            currentDirection = next.getValue().getNextDirection(currentDirection);
//            log.info("{} {} {}", current, currentDirection, next);


            total++;
            if (next.getValue() == MapTile.START) {
                break;
            }
            current = next;
        }

        cleanMatrix.printMatrix(false);


        return formatResult(total / 2);
    }

    @Override
    public String getAnswerPart1() {
        return "6613";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
