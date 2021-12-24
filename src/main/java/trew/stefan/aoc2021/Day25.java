package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day25 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


        var list = getStringInput("");


        Matrix<Creature> matrix = new Matrix<Creature>(list.size(), list.get(0).length(), Creature.class, null);


        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            for (int i1 = 0; i1 < s.toCharArray().length; i1++) {
                var c = s.charAt(i1);
                if (c == 'v' || c == '>') {
                    var value = new Creature(c == 'v' ? Direction.SOUTH : Direction.EAST);
                    matrix.set(i, i1, value);
                }
            }

        }

        while (true) {
            total++;
            var b2 = doMove(matrix, Direction.EAST);
            var b1 = doMove(matrix, Direction.SOUTH);

            if (!b1 && !b2) {
                break;
            }

        }

        return formatResult(total);
    }

    private boolean doMove(Matrix<Creature> matrix, Direction direction) {
        var list = matrix.find(creature -> creature != null && creature.direction == direction);

        var didMove = false;
        matrix.startTransaction();
        for (Matrix<Creature>.MatrixPoint matrixPoint : list) {
            if (direction == Direction.EAST) {

                var col = matrixPoint.getCol();
                var targetCol = col == matrix.getWidth() - 1 ? 0 : col + 1;
                if (matrix.get(matrixPoint.getRow(), targetCol) == null) {
                    matrix.set(matrixPoint.getRow(), targetCol, matrixPoint.getValue());
                    matrix.set(matrixPoint.getRow(), col, null);
                    didMove = true;
                }

            } else {

                var row = matrixPoint.getRow();
                var targetRow = row == matrix.getHeight() - 1 ? 0 : row + 1;
                if (matrix.get(targetRow, matrixPoint.getCol()) == null) {
                    matrix.set(targetRow, matrixPoint.getCol(), matrixPoint.getValue());
                    matrix.set(row, matrixPoint.getCol(), null);
                    didMove = true;
                }
            }
        }
        matrix.commitTransaction();
        return didMove;
    }

    enum Direction {
        SOUTH, EAST;
    }

    @AllArgsConstructor
    class Creature {

//        int x;
//        int y;
//        char type;

        Direction direction;

        @Override
        public String toString() {
            return direction == Direction.SOUTH ? "v" : ">";
        }
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
