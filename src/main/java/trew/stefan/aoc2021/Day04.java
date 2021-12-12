package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day04 extends AbstractAOC {


    @Override
    public String runPart1() {

        var numbers = getIntegerSplit(",");
        var boards = getBoards();


        for (Integer number : numbers) {
            for (var board : boards) {
                var points = board.find(number);
                for (Matrix<Integer>.MatrixPoint point : points) {
                    board.setVisited(point, true);

                    Integer score = validateBoard(board, number);
                    if (score != null) {
                        return formatResult(score);
                    }
                }
            }
        }

        return formatResult("");
    }

    private Integer validateBoard(Matrix<Integer> board, int number) {
        for (int i = 0; i < 5; i++) {
            if (!board.getVisitedCol(i).contains(false) || !board.getVisitedRow(i).contains(false)) {
                return computeScore(board, number);
            }
        }

        return null;
    }

    private Integer computeScore(Matrix<Integer> board, int number) {
        int sum = board.getUnvisited().stream().map(Matrix.MatrixPoint::getValue).reduce(Integer::sum).orElse(0);

        return sum * number;
    }


    @Override
    public String runPart2() {
        var numbers = getIntegerSplit(",");
        var boards = getBoards();
        var temp = new ArrayList<Matrix<Integer>>();

        for (Integer number : numbers) {
            for (var board : boards) {
                var points = board.find(number);
                for (Matrix<Integer>.MatrixPoint point : points) {
                    board.setVisited(point, true);

                    Integer score = validateBoard(board, number);
                    if (score != null) {
                        if (boards.size() == 1) {
                            return formatResult(score);
                        }
                        temp.add(board);
                    }
                }
            }


            for (Matrix<Integer> integerMatrix : temp) {

                boards.remove(integerMatrix);

            }
            temp.clear();
        }

        return "";
    }

    private ArrayList<Matrix<Integer>> getBoards() {
        var list = getStringInput();

        var boards = new ArrayList<Matrix<Integer>>();
        list.remove(0);
        list.remove(0);

        Matrix<Integer> current = null;
        var row = 0;
        for (var s : list) {
            if (current == null) {
                current = new Matrix<>(5, 5, Integer.class, 0);
                boards.add(current);
                current.setId(boards.size());
            }

            if (s.isEmpty()) {

                current = null;
                row = 0;
                continue;
            }


            var temp = Arrays.stream(s.trim().replaceAll("( )+", " ").split(" "))
                    .map(Integer::parseInt).collect(Collectors.toList());

            current.setRow(row, temp);
            row++;


        }
        return boards;
    }

    @Override
    public String getAnswerPart1() {
        return "58838";
    }

    @Override
    public String getAnswerPart2() {
        return "6256";
    }
}
