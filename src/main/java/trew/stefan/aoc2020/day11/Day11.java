package trew.stefan.aoc2020.day11;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day11 implements AOCDay {

    private int day = 11;
    int ROWS;
    int COLS;

    public Day11() {

    }

    @Override
    public String runPart1() {
        return String.valueOf(run(true));
    }

    @Override
    public String runPart2() {
        return String.valueOf(run(false));
    }

    private boolean hasSeat(char[][] input, int dr, int dc, int r, int c, boolean checkOne) {

        try {

            while (true) {
                r += dr;
                c += dc;

                if (input[r][c] == '#') {
                    return true;
                }

                if (input[r][c] == 'L') {
                    return false;
                }

                if (checkOne) {
                    return false;
                }
            }

        } catch (Exception e) {
            return false;
        }

    }


    public char[][] iterate(char[][] input, boolean checkOne) {

        char[][] array2 = new char[ROWS][COLS];
        int limit = checkOne ? 4 : 5;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int count = 0;
                if (hasSeat(input, 1, 1, r, c, checkOne)) count++;
                if (hasSeat(input, -1, -1, r, c, checkOne)) count++;
                if (hasSeat(input, -1, 1, r, c, checkOne)) count++;
                if (hasSeat(input, 1, -1, r, c, checkOne)) count++;
                if (hasSeat(input, 0, 1, r, c, checkOne)) count++;
                if (hasSeat(input, 0, -1, r, c, checkOne)) count++;
                if (hasSeat(input, 1, 0, r, c, checkOne)) count++;
                if (hasSeat(input, -1, 0, r, c, checkOne)) count++;


                if (input[r][c] == 'L' && count == 0) {
                    array2[r][c] = '#';

                } else if (input[r][c] == '#' && count >= limit) {
                    array2[r][c] = 'L';
                } else {

                    array2[r][c] = input[r][c];
                }
            }
        }

        return array2;

    }

    public char[][] buildSample(String str) {
        List<String> lines = InputReader2020.readStrings(day, str);
        int i = 0;
        char[][] array2 = new char[ROWS][COLS];
        for (String line : lines) {

            array2[i++] = line.toCharArray();
        }

        return array2;
    }

    private void printDiffMap(char[][] array1, char[][] array2) {
        log.info("===========================================");
        for (int row = 0; row < ROWS; row++) {
            String s = "";
            for (int col = 0; col < COLS; col++) {
//                System.out.print(array2[row][col]);
                s += array1[row][col] == array2[row][col] ? " " : "x";
            }
            log.info("{}", s);
        }
        log.info("");
    }

    public int run(boolean checkOne) {
        List<String> lines = InputReader2020.readStrings(day, "");
        ROWS = lines.size();
        COLS = lines.get(0).length();
        char[][] sample = buildSample("");

        char[][] temp;

        while (true) {

            temp = iterate(sample, checkOne);


            int count = 0;
            boolean diff = false;
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (sample[r][c] != temp[r][c]) {
                        diff = true;
                    }
                    if (temp[r][c] == '#') {
                        count++;
                    }
                }
            }

            if (!diff) {
                return count;
            }
            sample = temp;
        }


    }


}
