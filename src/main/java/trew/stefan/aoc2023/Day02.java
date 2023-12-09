package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashMap;


@Slf4j
public class Day02 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput();
        var total = 0;

        for (String s : list) {
            var parts = s.split(":");
            var id = parts[0].substring(5);
            var games = parts[1].split(";");

            HashMap<String, Long> map = getMap(games);

            if (map.get("blue") > 14 || map.get("red") > 12 || map.get("green") > 13) {
                continue;
            }
            total += Long.parseLong(id);
        }


        return String.valueOf(total);
    }


    @Override
    public String runPart2() {

        var list = getStringInput();
        var total = 0;

        for (String s : list) {
            var parts = s.split(":");
            var games = parts[1].split(";");

            HashMap<String, Long> map = getMap(games);

            var power = map.get("blue") * map.get("red") * map.get("green");
            total += power;
        }


        return String.valueOf(total);
    }

    private static HashMap<String, Long> getMap(String[] games) {
        var map = new HashMap<String, Long>();

        for (String sets : games) {
            for (String cubes : sets.split(",")) {
                var parts3 = cubes.trim().split(" ");
                long value = Long.parseLong(parts3[0].trim());
                var key = parts3[1].trim();
                var current = map.getOrDefault(key, 0L);
                map.put(key, Math.max(current, value));
            }
        }
        return map;
    }

    @Override
    public String getAnswerPart1() {
        return "2528";
    }

    @Override
    public String getAnswerPart2() {
        return "67363";
    }
}
