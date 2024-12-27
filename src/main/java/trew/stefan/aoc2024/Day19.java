package trew.stefan.aoc2024;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class Day19 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        var patterns = new HashMap<Character, List<String>>();
        var orders = new ArrayList<String>();
        for (var string : list) {
            if (string.isBlank()) {
                continue;
            }

            if (patterns.isEmpty()) {
                for (var s : string.split(", ")) {
                    if (!patterns.containsKey(s.charAt(0))) {
                        patterns.put(s.charAt(0), new ArrayList<>());
                    }
                    patterns.get(s.charAt(0)).add(s);

                }
            } else {
                orders.add(string);
            }
        }

        var count = 0;
        for (var order : orders) {
            var possible = isPossible(order, patterns);
            log.info("{} {}", order, possible);
            if (possible) {
                count++;
            }
        }
        log.info("patterns {}", patterns);
        log.info("orders {}", orders);

        return String.valueOf(count);

    }

    private boolean isPossible(String order, Map<Character, List<String>> patterns) {
        if (order.isEmpty()) {
            return true;
        }

        var c = order.charAt(0);
        if (!patterns.containsKey(c)) {
            return false;
        }
        var options = patterns.get(c);
        for (var option : options) {
            if (!order.startsWith(option)) {
                continue;
            }
            var substring = order.substring(option.length());
            if (isPossible(substring, patterns)) {
                return true;
            }
        }

        return false;
    }

    Map<String, Long> cache = new HashMap<>();

    private long numberPossibles(String order, Map<Character, List<String>> patterns) {

        if (cache.containsKey(order)) {
            return cache.get(order);
        }

        if (order.isEmpty()) {
            return 1;
        }

        var c = order.charAt(0);
        if (!patterns.containsKey(c)) {
            return 0;
        }
        var possible = 0L;
        var options = patterns.get(c);
        for (var option : options) {
            if (!order.startsWith(option)) {
                continue;
            }
            var substring = order.substring(option.length());
            possible += numberPossibles(substring, patterns);
        }

        cache.put(order, possible);

        return possible;
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");
        var patterns = new HashMap<Character, List<String>>();
        var orders = new ArrayList<String>();
        for (var string : list) {
            if (string.isBlank()) {
                continue;
            }

            if (patterns.isEmpty()) {
                for (var s : string.split(", ")) {
                    if (!patterns.containsKey(s.charAt(0))) {
                        patterns.put(s.charAt(0), new ArrayList<>());
                    }
                    patterns.get(s.charAt(0)).add(s);

                }
            } else {
                orders.add(string);
            }
        }

        var count = 0L;
        for (var order : orders) {
            var possible = numberPossibles(order, patterns);
            log.info("{} {}", order, possible);
            count += possible;
        }
        log.info("patterns {}", patterns);
        log.info("orders {}", orders);

        return String.valueOf(count);

    }

    @Override
    public String getAnswerPart1() {
        return "1546338";
    }

    @Override
    public String getAnswerPart2() {
        return "978590";
    }
    //978590 too low
}
