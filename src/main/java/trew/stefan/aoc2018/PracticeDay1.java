package trew.stefan.aoc2018;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class PracticeDay1 implements Day {

    class Guard {
        public int[] array = new int[60];
        public int id;

        public Guard(int id) {
            this.id = id;
        }

        public int getMinuteMax() {
            int max = 0;
            int maxMinute = 0;
            for (int i = 0; i < 60; i++) {
                if (array[i] > max) {
                    maxMinute = i;
                    max = array[i];
                }
            }
            return maxMinute;
        }

        public long getCount() {
            return Arrays.stream(array).reduce(Integer::sum).orElse(0);
        }

        public long getHash() {
            return id * getMinuteMax();
        }
    }

    private int day = 1;

    Map<Integer, Guard> guards = new HashMap();


    private Guard getGuard(int id) {
        Guard g = guards.getOrDefault(id, new Guard(id));
        guards.put(id, g);
        return g;
    }

    @Override
    public void run() {
        List<String> lines = InputReader2020.readStrings(2018, day, "");

        Guard currentGuard = new Guard(1);
        int currentSleepStart = 0;

        Pattern p = Pattern.compile("\\[(\\d*)\\-(\\d*)\\-(\\d*) (\\d*)\\:(\\d*)\\] (.*)");
        Pattern p2 = Pattern.compile("Guard #(\\d*) begins shift");
        for (String line : lines.stream().sorted().collect(Collectors.toList())) {

            Matcher matcher = p.matcher(line);
            if (matcher.find()) {

                int year = Integer.parseInt(matcher.group(1));
                int month = Integer.parseInt(matcher.group(2));
                int day = Integer.parseInt(matcher.group(3));
                int hour = Integer.parseInt(matcher.group(4));
                int minute = Integer.parseInt(matcher.group(5));
                String action = matcher.group(6);
//                log.info(action);
                Matcher matcher2 = p2.matcher(action);
                if (matcher2.find()) {
                    currentGuard = getGuard(Integer.parseInt(matcher2.group(1)));
                } else if (action.equals("falls asleep")) {
                    currentSleepStart = minute;
                } else {

                    for (int i = currentSleepStart; i < minute; i++) {
                        currentGuard.array[i]++;
                    }
                }

            }

        }

        currentGuard = null;
        for (Guard guard : guards.values()) {
            if (currentGuard == null || currentGuard.getCount() < guard.getCount()) {
                currentGuard = guard;
            }
            log.info("{} {} {} {}", guard.id, guard.getCount(), guard.getMinuteMax(), guard.getHash());
        }
        log.info("{} {} {} {}", currentGuard.id, currentGuard.getCount(), currentGuard.getMinuteMax(), currentGuard.getHash());


    }
}
