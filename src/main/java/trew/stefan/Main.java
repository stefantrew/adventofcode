package trew.stefan;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.InputReader;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class Main {

    private static final int NORMAL = 0;
    private static final int BRIGHT = 1;
    private static final int FOREGROUND_BLACK = 30;
    private static final int FOREGROUND_RED = 31;
    private static final int FOREGROUND_GREEN = 32;
    private static final int FOREGROUND_YELLOW = 33;
    private static final int FOREGROUND_BLUE = 34;
    private static final int FOREGROUND_MAGENTA = 35;
    private static final int FOREGROUND_CYAN = 36;
    private static final int FOREGROUND_WHITE = 37;


    private static final String PREFIX = "\u001b[";
    private static final String SUFFIX = "m";
    private static final char SEPARATOR = ';';


    public static List<String> readStrings() {
        try {
            return Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/2020/results.txt").toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static Map<Integer, DayResult> buildSummary() {
        List<String> lines = readStrings();
        Map<Integer, DayResult> result = new HashMap<>();
        Pattern p = Pattern.compile("^(\\d+)\\s*(\\d)\\s*(.{8})\\s(\\d*)\\s*(.*)");

        for (int i = 0; i < 25; i++) {

            DayResult dayResult = new DayResult();
            dayResult.day = i;
            String line = lines.get(2 * i);

            Matcher m = p.matcher(line);
            if (m.find()) {
                dayResult.day = Integer.parseInt(m.group(1));
                dayResult.part1 = m.group(5);
                dayResult.rank1 = Integer.parseInt(m.group(4));
                dayResult.time1 = m.group(3);
            }

            line = lines.get(2 * i + 1);

            m = p.matcher(line);
            if (m.find()) {
                dayResult.part2 = m.group(5);
                dayResult.rank2 = Integer.parseInt(m.group(4));
                dayResult.time2 = m.group(3);
            }
            result.put(dayResult.day, dayResult);
        }

        return result;
    }

    public static void runDay(int year, int day, DayResult summary) {

        String t = day < 10 ? "0" + day : String.valueOf(day);
        AbstractAOC day2 = null;

        String className = "trew.stefan.aoc" + year + ".Day" + t;
        try {
            Class<?> clazz = Class.forName(className);
            day2 = (AbstractAOC) clazz.getDeclaredConstructor().newInstance();
            day2.setDay(day, year);


        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
//            log.error(wrapColour(String.format("Day %s Class not found: %s", day, className), FOREGROUND_RED));
            return;
        }

        long startTime = System.nanoTime();
        String part1 = day2.runPart1();
        String answerPart1 = day2.getAnswerPart1();
        long midTime = System.nanoTime();
        String part2 = day2.runPart2();
        long endTime = System.nanoTime();
        String answerPart2 = day2.getAnswerPart2();

        long duration1 = (midTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
        long duration2 = (endTime - midTime) / 1000000;  //divide by 1000000 to get milliseconds.
        log.info("{} {} {} {} {} {} {} {} {} {} {}",
                wrapColour(String.format("%3s", day), FOREGROUND_WHITE),
                wrapTime(String.format("%7s", duration1 + " ms"), duration1),
                wrapColour(String.format("%5s", summary != null ? summary.time1 : ""), FOREGROUND_WHITE),
                wrapColour(String.format("%5s", summary != null ? summary.rank1 : ""), FOREGROUND_WHITE),
                wrapColour(String.format("%30s", answerPart1), FOREGROUND_WHITE),
                wrapColour(String.format("%30s", part1), answerPart1.equals(part1) ? FOREGROUND_WHITE : FOREGROUND_RED),
                wrapColour(String.format("| %-7s", summary != null ? summary.time2 : ""), FOREGROUND_WHITE),
                wrapTime(String.format("%7s", duration2 + " ms"), duration2),
                wrapColour(String.format("%-5s", summary != null ? summary.rank2 : ""), FOREGROUND_WHITE),
                wrapColour(String.format("%20s", answerPart2), FOREGROUND_WHITE),
                wrapColour(String.format("%20s", part2), answerPart2.equals(part2) ? FOREGROUND_WHITE : FOREGROUND_RED)

        );
//        log.info("\u001b["  // Prefix - see [1]
//                + "0"        // Brightness
//                + ";"        // Separator
//                + "32"       // Red foreground
//                + "m"        // Suffix
//                + "text"       // the text to output
//                + "\u001b[m " // Prefix + Suffix to reset color
//        );
    }

    private static String wrapTime(String text, long time) {

        int col = FOREGROUND_RED;
        if (time < 50) {
            col = FOREGROUND_GREEN;
        } else if (time < 100) {

            col = FOREGROUND_YELLOW;
        }

        return wrapColour(text, col);
    }


    private static String wrapColour(String text, int colour) {
        return "\u001b["  // Prefix - see [1]
                + BRIGHT        // Brightness
                + ";"        // Separator
                + colour       // Red foreground
                + "m"        // Suffix
                + text       // the text to output
                + "\u001b[m " // Prefix + Suffix to reset color
                ;
    }


    public static void main(String[] args) {
        Map<Integer, DayResult> summaries = new HashMap<>();//buildSummary();
        int year = 2021;
        for (int i = 15; i <= 17; i++) {
//            if (i + 1 != 15) continue;
            if (i % 5 == 0) {
                String div = "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                log.info(wrapColour(div, FOREGROUND_WHITE));
            }
            runDay(year, i + 1, summaries.get(i + 1));
        }
    }


}
