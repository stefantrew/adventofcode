package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Matrix;

import java.util.regex.Pattern;

@Slf4j
public class Day20 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";

//5857 5437 [5269] 5366 5408 5763 [5379
//        var list = getStringInput().stream().map(this::mapper).collect(Collectors.toList());

        var list = getStringInput("");
//        var list = getStringInput("_sample");
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        var alg = list.get(0);
        list.remove(0);
        list.remove(0);

        var w = list.get(0).length() * 1;
        var h = list.size() * 1;
        var offset = 50;
        var matrix = new Matrix<Integer>(w + offset, h + offset, Integer.class, 0);

        for (int row = 0; row < list.size(); row++) {
            String s = list.get(row);
            for (int col = 0; col < s.toCharArray().length; col++) {
                matrix.set(row + offset / 2, col + offset / 2, s.toCharArray()[col] == '#' ? 1 : 0);
            }
        }


        for (int i = 0; i < 25; i++) {

            matrix = optimise(matrix, alg);
            matrix = optimise(matrix, alg);
//            matrix.printMatrix(false);
            for (int j = 0; j < 5; j++) {
                for (int a = 0; a < matrix.getHeight(); a++) {
                    try {
                    matrix.set(a, j, 0);
                        matrix.set(a, matrix.getWidth() - j, 0);
                        matrix.set(matrix.getWidth() - j, a, 0);
                        matrix.set(j, a, 0);

                    }catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            }
        }
        var count = 0;
        for (int i = 5; i < matrix.getWidth() - 5; i++) {
            for (int j = 5; j < matrix.getWidth() - 5; j++) {

                if (matrix.get(i, j) == 1) {
                    count++;
                }
            }
        }
        return formatResult(count);
    }

    private Matrix<Integer> optimise(Matrix<Integer> matrix, String alg) {
        var temp = new Matrix<Integer>(matrix.getWidth() + 2, matrix.getHeight() + 2, Integer.class, 0);


        for (int row = 0; row < matrix.getHeight(); row++) {
            for (int col = 0; col < matrix.getWidth(); col++) {


                var str = getString(matrix, row, col);
                var i = Integer.parseInt(str, 2);

                temp.set(row + 1, col + 1, alg.charAt(i) == '#' ? 1 : 0);
            }
        }

        return temp;
    }

    private String getString(Matrix<Integer> matrix, int row, int col) {

        var sb = new StringBuilder();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                try {

                    sb.append(matrix.get(row + i, col + j));
                } catch (ArrayIndexOutOfBoundsException e) {
                    sb.append("0");
                }

            }
        }

        return sb.toString();
    }


    @AllArgsConstructor
    class Item {

    }

    Item mapper(String input) {

        var p = Pattern.compile("");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item();
        }
        return null;
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
