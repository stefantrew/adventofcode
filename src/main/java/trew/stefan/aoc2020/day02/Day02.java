package trew.stefan.aoc2020.day02;

import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.invocation.AbstractAwareMethod;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day02 implements AOCDay {

    private int day = 2;
    List<String> lines;

    public Day02() {
        lines = InputReader2020.readStrings(day, "");


    }


    @Override
    public String runPart1() {
        int result = 0;
        Pattern p = Pattern.compile("(\\d*)-(\\d*) (\\w): (.*)");
        for (String line : lines) {

            Matcher matcher = p.matcher(line);
            while (matcher.find()) {

                int num1 = Integer.parseInt(matcher.group(1));
                int num2 = Integer.parseInt(matcher.group(2));
                String target = matcher.group(3);
                String input = matcher.group(4);
                int counter = 0;
                for (char c : input.toCharArray()) {
                    if (c == target.charAt(0)) counter++;
                }

                if (counter >= num1 && counter <= num2) result++;

            }
        }

        return String.valueOf(result);
    }

    @Override
    public String runPart2() {
        int result = 0;
        Pattern p = Pattern.compile("(\\d*)-(\\d*) (\\w): (.*)");
        for (String line : lines) {

            Matcher matcher = p.matcher(line);
            while (matcher.find()) {

                int num1 = Integer.parseInt(matcher.group(1));
                int num2 = Integer.parseInt(matcher.group(2));
                String target = matcher.group(3);
                String input = matcher.group(4);

                boolean b1 = input.charAt(num1 - 1) == target.charAt(0);
                boolean b2 = input.charAt(num2 - 1) == target.charAt(0);

                if (b1 && !b2 || !b1 && b2) result++;

            }
        }

        return String.valueOf(result);
    }
}
