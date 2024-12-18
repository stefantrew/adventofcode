package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class Day03 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        StringBuilder string = new StringBuilder();
        for (var s : list) {
            string.append(s);
        }


        var total = 0L;

        final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string.toString());

        while (matcher.find()) {
            total += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
        }

        return String.valueOf(total);
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");
        StringBuilder string = new StringBuilder();
        for (var s : list) {
            string.append(s);
        }

        var total = 0L;

        final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)";


        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string.toString());

        var enabled = true;
        while (matcher.find()) {

            if (Objects.equals(matcher.group(), "do()")) {
                enabled = true;
                continue;
            }
            if (Objects.equals(matcher.group(), "don't()")) {
                enabled = false;
                continue;
            }

            if (!enabled) {
                continue;
            }

            total += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
        }

        return String.valueOf(total);
    }


    @Override
    public String getAnswerPart1() {
        return "178886550";
    }

    @Override
    public String getAnswerPart2() {
        return "87163705";
    }
}
