package trew.stefan.aoc2019;

import lombok.extern.slf4j.Slf4j;
import org.ejml.simple.SimpleMatrix;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.NumberUtil;

import java.util.List;

@Slf4j
public class Day16 implements Day {
    SimpleMatrix secondMatrix;

    double[] genSequence(int index, int width) {
        int[] baseSeq = new int[]{0, 1, 0, -1};

        double[] output = new double[width];
        for (int i = 0; i < index - 1; i++) {
            output[i] = 0;
        }

        int offset = index - 1;
        int current = 1;
        while (true) {

            for (int i = 0; i < index; i++) {
                if (i + offset == width) {
                    return output;
                }
                output[i + offset] = baseSeq[current];
            }
            offset += index;
            current++;
            current %= 4;
        }

    }

    double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            log.info("Cell ID:{} {} * {} = {}", i, firstMatrix[row][i], secondMatrix[i][col], firstMatrix[row][i] * secondMatrix[i][col]);
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int col = 0; col < result[0].length; col++) {
            result[0][col] = multiplyMatricesCell(firstMatrix, secondMatrix, 0, col);
        }

        return result;
    }

    private String compute2(String input) {
        double[] digits = NumberUtil.BurstDigitsDouble(input);
        int width = input.length();
        SimpleMatrix firstMatrix = new SimpleMatrix(
                new double[][]{
                        digits
                }
        );


        SimpleMatrix actual = firstMatrix.mult(secondMatrix);

        return NumberUtil.ImplodeDigitsToString(actual);
    }

    private String compute(String input) {
//        log.info("enter");
        byte[] digits = NumberUtil.BurstDigits(input);
//        log.info("done burst");
        int width = input.length();
        for (int i = 0; i < width; i++) {
            if (i % 100000 == 0) {
//                log.info("{}", ((double)i/ width) * 100);
            }
//            int[] seq = genSequence(i + 1, width);
//            int sum = 0;
//            for (int j = 0; j < width; j++) {
//
//                sum += digits[j] * seq[j];
//            }
//            digits[i] = (byte) (Math.abs(sum) % 10);
        }


        return NumberUtil.ImplodeDigitsToString(digits);
    }

    public void run() {
        List<String> lines = InputReader2019.readStrings(16, "_sample");
        String input = lines.get(0);
//        int offset = Integer.parseInt(input.substring(0, 7));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            sb.append(input);
        }
        input = sb.toString();
        int counter = 0;
//        while (counter++ < 100) {
//            input2 = compute(input2);
//            log.info("{} {}", counter, input2.substring(offset, offset + 8));
//
//        }

        double[][] temp = new double[input.length()][input.length()];
        for (int i = 0; i < input.length(); i++) {
            temp[i] = genSequence(i + 1, input.length());
        }

        secondMatrix = new SimpleMatrix(temp);
        secondMatrix = secondMatrix.transpose();

        while (counter++ < 100) {
            input = compute2(input);
            log.info("{} {}", counter, input);
        }
    }


}
