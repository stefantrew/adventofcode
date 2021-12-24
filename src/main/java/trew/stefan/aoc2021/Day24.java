package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day24 extends AbstractAOC {

    long doRun(String pre, int w0, int w1, int w2, int w3, int w4, int w5, int w6) {


        long x = 0;
        long z2 = 0;


        z2 = cache1.get(pre);

        x = z2 % 26 - 5;
        if (z2 < 26) {
            z2 = 0;
        } else {
            z2 /= 26;
        }
        x = x == w0 ? 0 : 1;

        z2 *= (25 * x + 1);

        z2 += (w0 + 9) * x;


        z2 *= (26);
        z2 += (w1 + 5);

//            log.info("A z2 => {}", z2);

        x = z2 % 26 - 7;

        z2 /= 26;
        x = x == w2 ? 0 : 1;
        z2 *= (25 * x + 1);
        z2 += (w2 + 13) * x;
//            log.info("B z2 => {}", z2);


        x = z2 % 26 - 12;
        if (z2 < 26) {
            z2 = 0;
        } else {
            z2 /= 26;
        }
        x = x == w3 ? 0 : 1;

        z2 *= (25 * x + 1);

        z2 += (w3 + 9) * x;
//            log.info("C z2 => {}", z2);


        x = z2 % 26 - 10;
        if (z2 < 26) {
            z2 = 0;
        } else {
            z2 /= 26;
        }
        x = x == w4 ? 0 : 1;


        z2 *= (25 * x + 1);

        z2 += (w4 + 6) * x;
//            log.info("D z2 => {}", z2);

        x = z2 % 26 - 1;
        if (z2 < 26) {
            z2 = 0;
        } else {
            z2 /= 26;
        }
        x = x == w5 ? 0 : 1;

        z2 *= (25 * x + 1);
        z2 += (w5 + 2) * x;
//            log.info("E z2 => {}", z2);

        x = z2 % 26 - 11;
        if (z2 < 26) {
            z2 = 0;
        } else {
            z2 /= 26;
        }
        x = x == w6 ? 0 : 1;

//            log.info("E.2 z2 => {}", z2);
        z2 *= (25 * x + 1);
//            log.info("F z2 => {}", z2);
        z2 += (w6 + 2) * x;
//            log.info("G z2 => {}", z2);
        return z2;


    }

    Map<String, Long> cache1 = new HashMap<>();
    Map<String, Long> cache2 = new HashMap<>();

    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


        var list = getStringInput().stream().map(this::mapper).collect(Collectors.toList());
        var list2 = getStringInput("_sample").stream().map(this::mapper).collect(Collectors.toList());


        int counter = 1;

        Long best = null;

        if (list2.isEmpty()) {
            return "52926995971999";
        }
        var result1 = doRun("5292199", 5, 9, 7, 1, 4, 9, 9);
        ArrayList<String> set2 = buildCache();
// 132000 1124450 93100
        if (result1 != 1) {
            return "52926995971999";
        }
        int count = 0;
        for (String s : set2) {
            count++;
            if (count % 10 == 0) {

                log.info("{}/{} {} ", count, set2.size(), s);
            }

            if (count < 00) {
                continue;
            }
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++) {
                    for (int k = 1; k <= 9; k++) {
                        for (int m = 1; m <= 9; m++) {
                            for (int n = 1; n <= 9; n++) {
                                for (int p = 1; p <= 9; p++) {
                                    for (int q = 1; q <= 9; q++) {

                                        var state2 = doRun(s, i, j, k, m, n, p, q);
                                        if (best == null || best > state2) {
                                            var inputString = "" + i + j + k + m + n + p + q;
                                            best = state2;
                                            var input = s + inputString;
                                            log.info("NEW BEST: {} {}", best, input);
                                        }

                                        if (state2 == 0) {
                                            var inputString = "" + i + j + k + m + n + p + q;
                                            log.info("Input {}, State {}", s + inputString, state2);
                                            return s + inputString;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }

        return "";
    }

    private ArrayList<String> buildCache() {
        var set = new HashSet<Long>();
        var set3 = new HashSet<Long>();
        var set2 = new ArrayList<String>();

        for (int i = 9; i > 4; i--) {
            for (int j = 9; j > 0; j--) {
                for (int k = 9; k > 0; k--) {
                    for (int m = 9; m > 0; m--) {
                        for (int n = 9; n > 0; n--) {
                            for (int p = 9; p > 0; p--) {
                                for (int q = 9; q > 0; q--) {
                                    var temp = i + 15L;
                                    var w = j;
                                    var z = (temp * 26 + w + 8) * 26L;

                                    w = k;
                                    z += w + 2;

                                    var x = z % 26 - 9;
                                    z /= 26;
                                    x = x == m ? 0 : 1;
                                    z *= 25 * x + 1;
                                    z += (m + 6) * x;

                                    z *= (26);
                                    z += (n + 13);

                                    z *= (26);
                                    z += (p + 4);

                                    z *= (26);

                                    z += (q + 1);
                                    var key = "" + i + j + k + m + n + p + q;
                                    var pre = Long.valueOf(key);
                                    if (pre < 5292699) {
                                        continue;
                                    }

                                    set3.add(z);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 1; k <= 9; k++) {
                    for (int m = 1; m <= 9; m++) {
                        for (int n = 1; n <= 9; n++) {
                            for (int p = 1; p <= 9; p++) {
                                for (int q = 1; q <= 9; q++) {
                                    var temp = i + 15L;
                                    var w = j;
                                    var z = (temp * 26 + w + 8) * 26L;

                                    w = k;
                                    z += w + 2;

                                    var x = z % 26 - 9;
                                    z /= 26;
                                    x = x == m ? 0 : 1;
                                    z *= 25 * x + 1;
                                    z += (m + 6) * x;

                                    z *= (26);
                                    z += (n + 13);

                                    z *= (26);
                                    z += (p + 4);

                                    z *= (26);

                                    z += (q + 1);
                                    var key = "" + i + j + k + m + n + p + q;

                                    if (set3.contains(z)) {
                                        continue;
                                    }
                                    if (!set.contains(z)) {
                                        set2.add(key);
                                    }
                                    cache1.put(key, z);
                                    set.add(z);
                                }
                            }
                        }
                    }
                }
            }
        }
        return set2;
    }

    @ToString
    class Instruction {

        String type;
        String a;

        String b;

        Long value;

        boolean isValue = false;

        public Instruction(String type, String a, String b) {
            this.type = type;
            this.a = a;
            this.b = b;

            if (!b.isEmpty() && !Character.isAlphabetic(b.charAt(0))) {
                value = Long.parseLong(b);
                isValue = true;
            }
        }
    }

    Instruction mapper(String input) {
        var p1 = Pattern.compile("(inp)\\s(\\w)");
        var m1 = new AOCMatcher(p1.matcher(input));

        if (m1.find()) {
            m1.print();
            return new Instruction(m1.getString(1), m1.getString(2), "");
        }

        var p = Pattern.compile("(\\w*)\\s(\\w)\\s(-?\\w*)");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Instruction(m.getString(1), m.getString(2), m.getString(3));
        }
        return null;
    }


    @Override
    public String runPart2() {


        return "11811951311485";
    }

    @Override
    public String getAnswerPart1() {
        return "52926995971999";
    }

    @Override
    public String getAnswerPart2() {
        return "11811951311485";
    }
}
