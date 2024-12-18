package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.Arrays;
import java.util.BitSet;


@Slf4j
public class Day07 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        var total = 0L;

        for (var string : list) {

            var target = Long.parseLong(string.split(":")[0]);
            var values = Arrays.stream(string.split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

            var valid = isValid(values, target);
            if (valid) {
                total += target;
            }
            log.info("{} {}", target, valid);


        }

        return String.valueOf(total);
    }

    private static boolean isValid(long[] values, long target) {
        // int to bitset
        Integer n = 0;
        var max = Math.pow(2, values.length - 1);
        while (n < max) {

            BitSet bs = BitSet.valueOf(new long[]{n});
            var binaryString = getBinaryString(n, values.length - 1);

            var current = values[0];
            for (int i = 0; i < binaryString.length(); i++) {
                if (binaryString.charAt(i) == '1') {
                    current += values[i + 1];
                } else {
                    current *= values[i + 1];
                }
            }

            if (current == target) {
                return true;
            }


            n++;
        }
        return false;
    }

    private static String getBinaryString(Integer n, int length) {
        var binaryString = Integer.toBinaryString(n);
        while (binaryString.length() < length) {
            binaryString = "0" + binaryString;
        }
        return binaryString;
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");
        var total = 0L;

        for (var string : list) {

            var target = Long.parseLong(string.split(":")[0]);
            var values = Arrays.stream(string.split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

            var valid = isValid2(values, target);
            if (valid) {
                total += target;
            }
            log.info("{} {}", target, valid);


        }

        return String.valueOf(total);
    }

    private static boolean isValid2(long[] values, long target) {
        // int to bitset
        Integer n = 0;
        var max = Math.pow(2, (values.length - 1) * 2);
        while (n < max) {

            if (doRun(values, target, n)) {
                return true;
            }

            n++;
        }
        return false;
    }

    private static boolean doRun(long[] values, long target, Integer n) {
        var binaryString = getBinaryString(n, (values.length - 1) * 2);

        var current = values[0];
        for (int i = 0; i < binaryString.length() / 2; i++) {
            var b1 = binaryString.charAt(i * 2);
            var b2 = binaryString.charAt(i * 2 + 1);

            if (b1 == '0' && b2 == '0') {
                current += values[i + 1];
            } else if (b1 == '0' && b2 == '1') {
                current *= values[i + 1];
            } else if (b1 == '1' && b2 == '1') {
                current = Long.parseLong(current + "" + values[i + 1]);
            } else {
                return false;
            }
        }

        return current == target;
    }


    @Override
    public String getAnswerPart1() {
        return "5239";
    }

    @Override
    public String getAnswerPart2() {
        return "1753";
    }
}
