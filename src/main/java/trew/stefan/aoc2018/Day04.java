package trew.stefan.aoc2018;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashMap;
import java.util.regex.Pattern;

@Slf4j
public class Day04 extends AbstractAOC {


    class Guard {

        String id;
        int[] minutes = new int[60];

        public Guard(String id) {
            this.id = id;
        }

        public int getSleepTime() {
            int total = 0;
            for (int minute : minutes) {

                total += minute;
            }
            return total;
        }

        public int getMaxTime() {
            int maxTime = 0;
            for (int i = 0; i < 60; i++) {
                if (minutes[i] > maxTime) {
                    maxTime = minutes[i];
                }
            }

            return maxTime;
        }

        public int getMaxMinute() {
            int maxTime = 0;
            int maxMinute = 0;
            for (int i = 0; i < 60; i++) {
                if (minutes[i] > maxTime) {
                    maxTime = minutes[i];
                    maxMinute = i;
                }
            }

            return maxMinute;
        }

        public int getId() {
            return Integer.parseInt(id);
        }
    }

    @Override
    public String runPart1() {

        HashMap<String, Guard> map = getGuards();

        Guard max = null;
        var maxTime = 0;
        for (var item : map.values()) {
            int sleepTime = item.getSleepTime();
            if (sleepTime > maxTime) {
                maxTime = sleepTime;
                max = item;
            }
        }
        if (max == null) {
            return "";
        }
        return String.valueOf(max.getMaxMinute() * max.getId());
    }

    @Override
    public String runPart2() {

        HashMap<String, Guard> map = getGuards();
        Guard max = null;
        var maxTime = 0;
        for (var item : map.values()) {
            int sleepTime = item.getMaxTime();
            if (sleepTime > maxTime) {
                maxTime = sleepTime;
                max = item;
            }
        }

        if (max == null) {
            return "";
        }
        return String.valueOf(max.getMaxMinute() * max.getId());
    }

    private HashMap<String, Guard> getGuards() {
        var p1 = Pattern.compile("#(\\d*)");
        var map = new HashMap<String, Guard>();

        var input = getStringInput("");
        input.sort((o1, o2) -> {
            var sub1 = o1.substring(1, 17);
            var sub2 = o2.substring(1, 17);

            return sub1.compareTo(sub2);
        });

        Guard current = null;
        var start = 0;

        for (String s : input) {
            if (s.contains("begins shift")) {
                var matcher = p1.matcher(s);
                if (matcher.find()) {

                    String id = matcher.group(1);
                    current = map.getOrDefault(id, new Guard(id));
                    map.putIfAbsent(id, current);

                }
            } else if (s.contains("falls")) {
                start = Integer.parseInt(s.substring(15, 17));
            } else if (s.contains("wakes")) {
                if (current == null) {
                    log.error("No guard");
                    continue;
                }
                var end = Integer.parseInt(s.substring(15, 17));

                for (int i = start; i < end; i++) {
                    current.minutes[i]++;
                }
            }
        }
        return map;
    }


    @Override
    public String getAnswerPart1() {
        return "119835";
    }

    @Override
    public String getAnswerPart2() {
        return "12725";
    }
}
