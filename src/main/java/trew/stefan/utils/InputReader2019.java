package trew.stefan.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class InputReader2019 {

    public static List<String> readStrings(int day, String suffix) {
        try {
            return Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/inputs/" + day + suffix + ".txt").toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Long> readCommaStringsLone(int day, String suffix) {
        try {
            List<String> lines = Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/inputs/" + day + suffix + ".txt").toPath());
            String[] strs = lines.get(0).split(",");
            return Arrays.stream(strs).map(Long::parseLong).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Integer> readCommaStrings(int day, String suffix) {
        try {
            List<String> lines = Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/inputs/" + day + suffix + ".txt").toPath());
            String[] strs = lines.get(0).split(",");
            return Arrays.stream(strs).map(Integer::parseInt).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<FabricClaim> readClaims(int day, String suffix) {
        List<String> lines = readStrings(day, suffix);
        List<FabricClaim> claims = new ArrayList<>();
        Pattern p = Pattern.compile("(#\\d*)\\s@\\s(\\d*),(\\d*):\\s(\\d*)x(\\d*)");
        for (String line : lines) {

            Matcher matcher = p.matcher(line);
            while (matcher.find()) {

                claims.add(new FabricClaim(matcher.group(1),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5))));
            }
        }
        return claims;
    }

    public static Map<Integer, GuardTime> readGuards(int day, String suffix) {
        List<String> lines = readStrings(day, suffix);
        Map<Integer, GuardTime> times = new HashMap<>();
        Pattern p = Pattern.compile("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}] Guard #(\\d*) begins shift");
        Pattern p2 = Pattern.compile("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:(\\d{2})] (falls asleep|wakes up)");
        int currentGaurd = 0;
        int fallAsleepMinute = 0;
        for (String line : lines) {
            log.info(line);
/**
 *
 * [1518-01-29 23:57] Guard #2417 begins shift
 * [1518-01-30 00:13] falls asleep
 * [1518-01-30 00:50] wakes up
 */
            Matcher matcher = p.matcher(line);
            if (matcher.matches()) {
                log.info("matched");
                currentGaurd = Integer.parseInt(matcher.group(1));
                log.info("Found id #{}", currentGaurd);
                continue;

            }
            matcher = p2.matcher(line);
            if (matcher.matches()) {
                if (matcher.group(2).equals("falls asleep")) {
                    fallAsleepMinute = Integer.parseInt(matcher.group(1));
                } else {
                    int wakes = Integer.parseInt(matcher.group(1));
                    log.info("Period {} {}", fallAsleepMinute, wakes);
                    GuardTime time = times.getOrDefault(currentGaurd, new GuardTime(currentGaurd));
                    for (int x = fallAsleepMinute; x < wakes; x++) {
                        time.minutes[x]++;
                    }
                    times.put(currentGaurd, time);
                }
            }
        }

        GuardTime temp = null;
        for (GuardTime time : times.values()) {
            log.info("ID {} => {} {} ", time.getId(), time.totalMinutes(), time.highestDay());
            if (temp == null || time.totalMinutes() > temp.totalMinutes()) {
                temp = time;
            }
        }

        if (temp != null) {
            log.info("{}", temp.getId() * temp.highestDay());
        }

        int d = 0;
        int v = 0;
        int id = 0;

        for (int x = 0; x < 60; x++) {
            for (GuardTime time : times.values()) {
                if (time.minutes[x] > v) {
                    d = x;
                    id = time.getId();
                    v = time.minutes[x];
                }
            }
        }
        log.info("{}", d * id);

        return times;
    }


}

