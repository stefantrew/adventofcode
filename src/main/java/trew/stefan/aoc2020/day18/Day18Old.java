package trew.stefan.aoc2020.day18;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day18Old implements Day {


    private int day = 18;

    private String doAdd(String term) {
        term = term.trim();
        while (true) {
            Pattern p = Pattern.compile("(\\d*) \\+ (\\d*)");
            Matcher match = p.matcher(term);
            if (match.find()) {

                String group = match.group(1);
                String group2 = match.group(2);

                long result = Long.parseLong(group) + Long.parseLong(group2);
                log.info("{} {} <<{}>>", group, result, match.start());
                String pre = term.substring(0, match.start());
                String post = term.substring(match.end(2));
                term = pre + result + post;

            } else {
               return term;
            }
        }
    }

    private long evaluate(String term) {
        String[] parts = term.split(" ");
        long number = Long.parseLong(parts[0]);
        String op = "";
        for (int i = 1; i < parts.length; i++) {
            switch (parts[i]) {
                case "+":
                    op = "+";
                    break;
                case "*":
                    op = "*";
                    break;
                default:
                    if (op.equals("+")) {
                        number += Long.parseLong(parts[i]);
                    } else {
                        number *= Long.parseLong(parts[i]);

                    }
            }
        }

        return number;
    }

    public void run() {
        List<String> lines = InputReader2020.readStrings(day, "");
        long sum = 0;
        int number = 1;
        for (String line : lines) {
            log.info("--------------------------------------");
            log.info("{}: {}", number++, line);

            while (true) {
                Pattern p = Pattern.compile("\\(([^()]+)\\)");
                Matcher match = p.matcher(line);
                if (match.find()) {

                    String group = doAdd(match.group(1));

                    long result = evaluate(group);
                    log.info("{} {} <<{}>>", group, result, match.start());
                    String pre = line.substring(0, match.start());
                    String post = line.substring(match.end());
                    line = pre + result + post;

                } else {
                    line = doAdd(line);
                    long i = evaluate(line);
                    sum += i;
                    log.info("Result {}", i);
                    break;
                }
            }


        }
        log.info("TOTAL {}", sum);
    }
}
//1735429
