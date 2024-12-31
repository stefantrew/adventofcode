package trew.stefan.aoc2024;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day15 extends AbstractAOC {

    class WallException extends RuntimeException {

    }

    enum Move {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        final int dx;
        final int dy;

        Move(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public static Move resolve(char c) {
            return switch (c) {
                case '^' -> UP;
                case 'v' -> DOWN;
                case '<' -> LEFT;
                case '>' -> RIGHT;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }
    }

    enum Tile {
        WALL,
        EMPTY,
        ROBOT,
        BOX,
        BOX_L,
        BOX_R;

        @Override
        public String toString() {
            return switch (this) {
                case WALL -> "#";
                case EMPTY -> ".";
                case ROBOT -> "@";
                case BOX_L -> "[";
                case BOX_R -> "]";
                case BOX -> "O";
            };
        }

        public boolean isBox() {
            return this == BOX || this == BOX_L || this == BOX_R;
        }

        public static Tile resolve(char c) {
            return switch (c) {
                case '#' -> WALL;
                case '.' -> EMPTY;
                case '@' -> ROBOT;
                case '[' -> BOX_L;
                case ']' -> BOX_R;
                case 'O' -> BOX;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }
    }

    @Override
    public String runPart1() {

        var total = 0;
        var result = "";

        var list = getStringInput("");
        var moves = new ArrayList<Move>();

        var matrix = new Matrix<>(list.get(0).length(), list.get(0).length(), Tile.class, Tile.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            if (s.isBlank()) {
                continue;
            }
            if (s.contains("#")) {
                for (var i1 = 0; i1 < s.toCharArray().length; i1++) {

                    matrix.set(i, i1, Tile.resolve(s.charAt(i1)));
                }
            } else {
                for (var c : s.toCharArray()) {
                    moves.add(Move.resolve(c));
                }
            }

        }

        var count = 0;
        for (var move : moves) {
            if (count++ > 4) {
//                break;
            }
            var robot = matrix.find(Tile.ROBOT).get(0);
            var next = matrix.getPoint(robot.getRow() + move.dy, robot.getCol() + move.dx);
            if (next != null && next.getValue() == Tile.EMPTY) {
                matrix.set(robot.getRow(), robot.getCol(), Tile.EMPTY);
                matrix.set(robot.getRow() + move.dy, robot.getCol() + move.dx, Tile.ROBOT);
            } else if (next != null && next.getValue() == Tile.BOX) {
                var boxes = new ArrayList<Matrix<Tile>.MatrixPoint>();
                boxes.add(next);
                var doMove = false;
                while (true) {

                    var next2 = matrix.getPoint(next.getRow() + move.dy, next.getCol() + move.dx);
                    if (next2 != null && next2.getValue() == Tile.EMPTY) {
                        doMove = true;
                        break;
                    } else if (next2 != null && next2.getValue() == Tile.BOX) {
                        boxes.add(next2);
                        next = next2;
                    } else {
                        break;
                    }
                }

                if (doMove) {
                    matrix.set(robot.getRow(), robot.getCol(), Tile.EMPTY);
                    matrix.set(robot.getRow() + move.dy, robot.getCol() + move.dx, Tile.ROBOT);
                    for (var box : boxes) {
                        matrix.set(box.getRow() + move.dy, box.getCol() + move.dx, Tile.BOX);
                    }
                }
            }
        }

        for (var matrixPoint : matrix.find(Tile.BOX)) {
            total += matrixPoint.getRow() * 100 + matrixPoint.getCol();
        }


        return formatResult(total);
    }

    @Override
    public String runPart2() {


        var total = 0;

        var list = getStringInput("");
        var moves = new ArrayList<Move>();

        var matrix = new Matrix<>(list.get(0).length(), list.get(0).length() * 2, Tile.class, Tile.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            if (s.isBlank()) {
                continue;
            }
            if (s.contains("#")) {
                for (var i1 = 0; i1 < s.toCharArray().length; i1++) {


                    var tile = Tile.resolve(s.charAt(i1));
                    if (tile == Tile.ROBOT) {
                        matrix.set(i, 2 * i1, tile);
                        matrix.set(i, 2 * i1 + 1, Tile.EMPTY);

                    } else if (tile == Tile.BOX) {
                        matrix.set(i, 2 * i1, Tile.BOX_L);
                        matrix.set(i, 2 * i1 + 1, Tile.BOX_R);
                    } else {
                        matrix.set(i, 2 * i1, tile);
                        matrix.set(i, 2 * i1 + 1, tile);

                    }
                }
            } else {
                for (var c : s.toCharArray()) {
                    moves.add(Move.resolve(c));
                }
            }

        }
        matrix.printMatrix(false);

        for (var move : moves) {
            var robot = matrix.find(Tile.ROBOT).get(0);
            var next = matrix.getPoint(robot.getRow() + move.dy, robot.getCol() + move.dx);
            if (next.getValue() == Tile.EMPTY) {
                matrix.set(robot.getRow(), robot.getCol(), Tile.EMPTY);
                matrix.set(robot.getRow() + move.dy, robot.getCol() + move.dx, Tile.ROBOT);
            } else if (next.getValue() == Tile.BOX_L || next.getValue() == Tile.BOX_R) {
                if (move == Move.UP || move == Move.DOWN) {

                    try {

                        var boxes = moveBoxes(matrix, next, move);
                        for (var i = boxes.size() - 1; i >= 0; i--) {
                            if (matrix.get(boxes.get(i).getRow() + move.dy, boxes.get(i).getCol() + move.dx) == Tile.EMPTY) {
                                matrix.set(boxes.get(i).getRow(), boxes.get(i).getCol(), Tile.EMPTY);
                                matrix.set(boxes.get(i).getRow() + move.dy, boxes.get(i).getCol() + move.dx, boxes.get(i).getValue());
                            }
                        }
                        matrix.set(robot.getRow(), robot.getCol(), Tile.EMPTY);
                        matrix.set(robot.getRow() + move.dy, robot.getCol() + move.dx, Tile.ROBOT);
                    } catch (WallException e) {

                    }
                } else {
                    var boxes = new ArrayList<Matrix<Tile>.MatrixPoint>();
                    boxes.add(next);
                    var doMove = false;
                    while (true) {

                        var next2 = matrix.getPoint(next.getRow() + move.dy, next.getCol() + move.dx);
                        if (next2 != null && next2.getValue() == Tile.EMPTY) {
                            doMove = true;
                            break;
                        } else if (next2 != null && next2.getValue().isBox()) {
                            boxes.add(next2);
                            next = next2;
                        } else {
                            break;
                        }
                    }

                    if (doMove) {
                        for (var i = boxes.size() - 1; i >= 0; i--) {
                            matrix.set(boxes.get(i).getRow(), boxes.get(i).getCol(), Tile.EMPTY);
                            matrix.set(boxes.get(i).getRow() + move.dy, boxes.get(i).getCol() + move.dx, boxes.get(i).getValue());
                        }
                        matrix.set(robot.getRow(), robot.getCol(), Tile.EMPTY);
                        matrix.set(robot.getRow() + move.dy, robot.getCol() + move.dx, Tile.ROBOT);
                    }
                }
//1524765
//1538862
            }
        }
        matrix.printMatrix(false);
        for (var matrixPoint : matrix.find(Tile.BOX_L)) {
            total += matrixPoint.getRow() * 100 + matrixPoint.getCol();
        }


        return formatResult(total);
    }

    private List<Matrix<Tile>.MatrixPoint> moveBoxes(Matrix<Tile> matrix, Matrix<Tile>.MatrixPoint next, Move move) {
        Matrix<Tile>.MatrixPoint boxLeft = null;
        Matrix<Tile>.MatrixPoint boxRight = null;

        if (next.getValue() == Tile.BOX_L) {
            boxLeft = next;
            boxRight = matrix.getPoint(next.getRow(), next.getCol() + 1);
        } else {
            boxLeft = matrix.getPoint(next.getRow(), next.getCol() - 1);
            boxRight = next;
        }

        var boxes = new ArrayList<Matrix<Tile>.MatrixPoint>();
        boxes.add(boxLeft);
        boxes.add(boxRight);

        var nextL = matrix.getPoint(boxLeft.getRow() + move.dy, boxLeft.getCol() + move.dx);
        var nextR = matrix.getPoint(boxRight.getRow() + move.dy, boxRight.getCol() + move.dx);
        if (nextL.getValue() == Tile.WALL || nextR.getValue() == Tile.WALL) {
            throw new WallException();
        }

        if (nextL.getValue().isBox()) {
            boxes.addAll(moveBoxes(matrix, nextL, move));
        }
        if (nextR.getValue().isBox()) {
            boxes.addAll(moveBoxes(matrix, nextR, move));
        }

        return boxes;
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
