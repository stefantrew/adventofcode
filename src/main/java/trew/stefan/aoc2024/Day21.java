package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day21 extends AbstractAOC {


    char[][] keypad1 = new char[][]{
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {'_', '0', 'A'},

    };

    char[][] keypad2 = new char[][]{
            {'_', '^', 'A'},
            {'<', 'v', '>'}
    };
    Map<String, String> globalCache = new HashMap<>();
    Map<String, String> globalCache2 = new HashMap<>();
    Map<String, Set<String>> globalCache3 = new HashMap<>();
    Map<String, Set<String>> globalCache4 = new HashMap<>();
    Map<String, String> globalCache5 = new HashMap<>();


    @Override
    public String runPart1() {
        var total = 0;
        var list = getStringInput("");
        for (var code : list) {
            var shortest = computeShortest(code, 1);
            total += shortest.length() * Integer.parseInt(code.substring(0, 3));
            log.info("{} {} {} {}", code, shortest.length(), Integer.parseInt(code.substring(0, 3)), shortest);

        }


        return formatResult(total);
    }

    private String computeShortest(String code, int depth) {


        var results = new HashSet<String>();

        var paths = computePaths(code, keypad1);

        for (int i = 0; i < depth; i++) {
            log.info("Iteration {} {}", i, paths.size());
            paths = computePaths(paths, keypad2);
        }

        log.info("Iteration {} {}", depth, paths.size());
        results.addAll(computePaths(paths, keypad2));

        String shortest = null;
        for (var abc : results) {

            if (shortest == null || abc.length() < shortest.length()) {
                shortest = abc;
            }


        }
        return shortest;
    }


    private String computePath(String s, char[][] keypad) {
        if (globalCache2.containsKey(s)) {
            return globalCache2.get(s);
        }

        var parts = s.split("X");
        var sb = new StringBuilder();
        var start = 'A';
        for (var part : parts) {
            sb.append(computeSegmentPath(start + part + "X", keypad));
            if (!part.isEmpty()) {

                start = part.charAt(part.length() - 1);
            }
        }

        var result = sb.toString();
        globalCache2.put(s, result);

        return result;
    }

    private String computeSegmentPath(String s, char[][] keypad) {

        if (globalCache.containsKey(s)) {
            return globalCache.get(s);
        }


        var sb = new StringBuilder();
        var start = s.charAt(0);
        for (var i = 1; i < s.length(); i++) {

            var target = s.charAt(i);
            if (target == 'X') {
                sb.append("A");
                continue;
            }

            var sx = 0;
            var sy = 0;

            var tx = 0;
            var ty = 0;

            for (int row = 0; row < keypad.length; row++) {
                for (int col = 0; col < keypad[row].length; col++) {
                    if (keypad[row][col] == start) {
                        sx = col;
                        sy = row;
                    }

                    if (keypad[row][col] == target) {
                        tx = col;
                        ty = row;
                    }
                }
            }
            var dx = tx - sx;
            var dy = ty - sy;
            if (dx != 0) {
                sb.append(dx < 0 ? "<" : ">");
            }
            if (dy != 0) {
                sb.append(dy < 0 ? "^" : "v");
            }

            start = target;
        }
        var result = sb.toString();
        globalCache.put(s, result);
        return result;
    }

    private Set<String> computePaths(Set<String> input, char[][] keypad) {
        var result = new HashSet<String>();
        for (var string : input) {
            result.addAll(computePaths(string, keypad));
        }


        return result;

    }

    private Set<String> computePaths(String code, char[][] keypad) {
        if (globalCache3.containsKey(code)) {
            return globalCache3.get(code);
        }

        var paths = new ArrayList<String>();
        paths.add("A");
        var start = 'A';
        for (int i = 0; i < code.length(); i++) {
            var target = code.charAt(i);
            var temp = computePaths(start, target, keypad);
            if (paths.isEmpty()) {

                paths.addAll(temp);
            } else {
                var newPaths = new ArrayList<String>();
                for (var s : paths) {
                    for (var t : temp) {
                        newPaths.add(s + t + 'X');
                    }
                }
                paths = newPaths;
            }
            start = target;
        }

        var list = paths.stream().map(s -> computePath(s, keypad))
//                .sorted(Comparator.comparingInt(String::length))
//                .limit(4)
                .collect(Collectors.toSet());

        globalCache3.put(code, list);

        return list;
    }


    private Set<String> computePaths(char start, char target, char[][] keypad) {
        var code = start + "_" + target;
        if (globalCache4.containsKey(code)) {
            return globalCache4.get(code);
        }

        var paths = new HashSet<String>();

        if (start == target) {
            paths.add("");
            return paths;
        }

        var sx = 0;
        var sy = 0;

        var tx = 0;
        var ty = 0;

        for (int row = 0; row < keypad.length; row++) {
            for (int col = 0; col < keypad[row].length; col++) {
                if (keypad[row][col] == start) {
                    sx = col;
                    sy = row;
                }

                if (keypad[row][col] == target) {
                    tx = col;
                    ty = row;
                }
            }
        }
        var dx = tx - sx;
        var dy = ty - sy;
        if (dy != 0 && keypad[sy + dy][sx] != '_') {
            var temp1 = "";
            for (int i = sy + 1; i <= ty; i++) {
                temp1 += keypad[i][sx];
            }
            for (int i = sy - 1; i >= ty; i--) {
                temp1 += keypad[i][sx];
            }
            var temp = computePaths(keypad[sy + dy][sx], target, keypad);
            for (var s : temp) {
                paths.add(temp1 + s);
            }
        }

        if (dx != 0 && keypad[sy][sx + dx] != '_' ) {
            var temp1 = "";
            for (int i = sx + 1; i <= tx; i++) {
                temp1 += keypad[sy][i];
            }
            for (int i = sx - 1; i >= tx; i--) {
                temp1 += keypad[sy][i];
            }
            var temp = computePaths(keypad[sy][sx + dx], target, keypad);
            for (var s : temp) {
                paths.add(temp1 + s);
            }
        }


        globalCache4.put(code, paths);
        return paths;
    }

    @Override
    public String runPart2() {


        var total = 0;
        var list = getStringInput("");
        for (var code : list) {
            var shortest = computeShortest(code, 2);
            total += shortest.length() * Integer.parseInt(code.substring(0, 3));
            log.info("{} {} {} {}", code, shortest.length(), Integer.parseInt(code.substring(0, 3)), shortest);

        }


        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "171596";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
