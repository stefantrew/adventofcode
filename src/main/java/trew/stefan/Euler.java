package trew.stefan;

import joptsimple.internal.Strings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.InputReader;
import trew.stefan.utils.Matrix;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Euler {


    public List<String> readStrings() {

        File file = new File("C:\\code\\aoc\\2021\\src\\main\\resources\\inputs\\codes.txt");
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    Map<Long, Long> validSquares = new HashMap<>();

    public Euler() {

        long i = 1;

        while (i * i <= 1000000000000L) {
            validSquares.put(i * i, i);
//            log.info("{} {}", i, i * i);
            i++;
        }

    }

    public void run() {
        var sum = BigDecimal.ZERO;
        for (Long num : validSquares.keySet().stream().sorted().collect(Collectors.toList())) {
            var result = isS(num);
            if (result) {
//                sum.add(num);
                log.info("{} {} {}", num, validSquares.get(num), result);
            }

        }
        log.info("final: {}",  sum);
    }

    public boolean isS(Long number) {

        if (!validSquares.containsKey(number)) {
            return false;
        }

        var target = validSquares.get(number);
        if (number < 10) {
            return false;
        }
        return isSum(String.valueOf(number), target);
    }

    private boolean isSum(String str, Long target) {
//        log.info("{}", target);
        var digits = Math.min(String.valueOf(target).length(), str.length());

        if (target < 0) {
            return false;
        }
        if (target == 0 && str.length() == 0) {
            return true;
        }
        if (str.length() == 0) {
            return false;
        }

        for (int i = 1; i <= digits; i++) {

            var t = str.substring(0, i);
            var r = str.substring(i);
            var c = Long.parseLong(t);
            var newTarget = target - c;

            var result = isSum(r, newTarget);
            if (result) {
                return true;
            }
//            log.info("{} {}", t, newTarget);
        }
        return false;
    }


    public static void main(String[] args) {
        var euler = new Euler();
//        euler.run();
        log.info("count: {}", 128088830506649L + 41333);
    }
}
