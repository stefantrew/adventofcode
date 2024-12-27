package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day23 extends AbstractAOC {


    @Override
    public String runPart1() {


        var map = new HashMap<String, Set<String>>();


        var list = getStringInput("");

        for (var s : list) {
            var left = s.split("-")[0].trim();
            var right = s.split("-")[1].trim();

            addTag(map, right, left);
            addTag(map, left, right);
        }
        var result = new HashSet<String>();
        for (var stringSetEntry : map.entrySet()) {
            isSetOfThree(stringSetEntry, map, result);
        }

        return formatResult(result.size());
    }

    private void addTag(HashMap<String, Set<String>> map, String key, String value) {
        var list = map.getOrDefault(key, new HashSet<>());
        list.add(value);
        map.put(key, list);
    }


    private void isSetOfThree(Map.Entry<String, Set<String>> set, HashMap<String, Set<String>> map, Set<String> result) {

        var values = set.getValue();
        var key1 = set.getKey();
        for (var key2 : values) {
            var valueSet = map.get(key2);
            for (var key3 : valueSet) {
                var set3 = map.get(key3);
                if (set3.contains(key1)) {
                    if (key1.startsWith("t") || key2.startsWith("t") || key3.startsWith("t")) {

                        if (key1.compareTo(key2) < 0 && key2.compareTo(key3) < 0) {
                            result.add(key1 + "-" + key2 + "-" + key3);
                        }
                    }
                }
            }
        }


    }

    @Override
    public String runPart2() {


        var map = new HashMap<String, Set<String>>();


        var list = getStringInput("");

        for (var s : list) {
            var left = s.split("-")[0].trim();
            var right = s.split("-")[1].trim();

            addTag(map, right, left);
            addTag(map, left, right);
        }
        var best = "";
        for (var entry : map.entrySet()) {
            var result = new HashSet<String>();
            var res = process(entry.getKey(), entry.getValue(), map, result);
            if (res.length() > best.length()) {
                best = res;
            }
            log.info("Best {}", res);
        }
        return Arrays.stream(best.split("-")).sorted().collect(Collectors.joining(","));
    }

    private String process(String key, Set<String> set, HashMap<String, Set<String>> map, HashSet<String> current) {

        if (current.contains(key)) {
            return current.stream().reduce("", (a, b) -> a + "-" + b).substring(1);
        }
        var best = "";
        current.add(key);
        for (var key2 : set) {
            var subset = map.get(key2);
            var flag = true;
            for (var s : current) {
                if (s.equals(key2)) {
                    continue;
                }
                if (!subset.contains(s)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                var result = process(key2, subset, map, current);
                if (result.length() > best.length()) {
                    best = result;
                }
            }
        }


        return best;
    }

    @Override
    public String getAnswerPart1() {
        return "";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
