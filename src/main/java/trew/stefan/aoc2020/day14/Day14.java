package trew.stefan.aoc2020.day14;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14 implements AOCDay {

    private int day = 14;

    private static long parseLong(String s, int base) {
        return new BigInteger(s, base).longValue();
    }

    @Override
    public String runPart1() {
        List<String> lines = InputReader2020.readStrings(day, "");

        Pattern p = Pattern.compile("mask = (.{36})");
        Pattern p2 = Pattern.compile("mem\\[(\\d*)\\] = (\\d*)");

        HashMap<Integer, Long> memory = new HashMap<>();

        String mask = "";
        for (String line : lines) {

            Matcher m = p.matcher(line);
            Matcher m2 = p2.matcher(line);

            if (m.find()) {
                mask = m.group(1);
            } else if (m2.find()) {
                int mem = Integer.parseInt(m2.group(1));
                int value = Integer.parseInt(m2.group(2));
                String bit = Integer.toBinaryString(value);
                while (bit.length() != 36) {
                    bit = "0" + bit;
                }

                char[] bitArray = mask.toCharArray();
                char[] valueArray = bit.toCharArray();

                String newStr = applyMask2(bitArray, valueArray);
                memory.put(mem, parseLong(newStr, 2));
            }
        }

        Long result = 0L;
        for (Integer key : memory.keySet()) {
            result += memory.get(key);
        }
        return String.valueOf(result);
    }

    @Override
    public String runPart2() {

        List<String> lines = InputReader2020.readStrings(day, "");

        Pattern p = Pattern.compile("mask = (.{36})");
        Pattern p2 = Pattern.compile("mem\\[(\\d*)\\] = (\\d*)");

        HashMap<Long, Long> memory = new HashMap<>();

        String mask = "";
        for (String line : lines) {

            Matcher m = p.matcher(line);
            Matcher m2 = p2.matcher(line);

            if (m.find()) {
                mask = m.group(1);
            } else if (m2.find()) {
                int mem = Integer.parseInt(m2.group(1));
                long value = Long.parseLong(m2.group(2));
                String bit = Integer.toBinaryString(mem);
                while (bit.length() != 36) {
                    bit = "0" + bit;
                }


                char[] bitArray = mask.toCharArray();
                char[] valueArray = bit.toCharArray();

                String newStr = applyMask(bitArray, valueArray);

                Set<String> numbers = new HashSet<>();
                numbers = applyFloat(newStr, numbers);

                for (String number : numbers) {
                    long aa = parseLong(number, 2);
                    memory.put(aa, value);
                }
            }


        }

        Long result = 0L;
        for (long key : memory.keySet()) {
            result += memory.get(key);
        }
        return String.valueOf(result);

    }

    private Set<String> applyFloat(String newStr, Set<String> numbers) {

        if (!newStr.contains("X")) {
            numbers.add(newStr);
            return numbers;
        }

        for (int i = 35; i >= 0; i--) {
            if (newStr.charAt(i) == 'X') {
                String temp = newStr;
                applyFloat(temp.replaceFirst("X", "1"), numbers);
                applyFloat(temp.replaceFirst("X", "0"), numbers);

                break;
            }
        }

        return numbers;
    }

    private String applyMask2(char[] bitArray, char[] valueArray) {
        String newStr = "";
        for (int i = 35; i >= 0; i--) {

            if (bitArray[i] == 'X') {
                newStr = valueArray[i] + newStr;
            } else {
                newStr = bitArray[i] + newStr;
            }

        }
        return newStr;
    }

    private String applyMask(char[] bitArray, char[] valueArray) {
        String newStr = "";
        for (int i = 35; i >= 0; i--) {

            if (bitArray[i] == 'X') {
                newStr = 'X' + newStr;
            } else if (bitArray[i] == '0') {
                newStr = valueArray[i] + newStr;
            } else {
                newStr = "1" + newStr;
            }

        }
        return newStr;
    }
}
