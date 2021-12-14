package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class Day14 extends AbstractAOC {


    @Override
    public String runPart1() {
        return run(10);
    }


    @Override
    public String runPart2() {
        return run(40);
    }

    private String run(int i) {
        var list = getStringInput();

        var map = new HashMap<String, String>();

        String target = "";
        for (var s : list) {
            if (target.isEmpty()) {
                target = s;
            } else if (!s.isEmpty()) {
                var p = Pattern.compile("(\\w*) -> (\\w)");
                var m = new AOCMatcher(p.matcher(s));

                if (m.find()) {
                    map.put(m.getString(1), m.getString(2));
                }
            }
        }

        var map3 = new HashMap<String, Long>();
        for (int j = 0; j < target.length() - 1; j++) {
            String com = target.charAt(j) + String.valueOf(target.charAt(j + 1));

            map3.putIfAbsent(com, 0L);
            var temp = map3.get(com);
            map3.put(com, temp + 1);

        }
        while (i-- > 0) {
            map3 = doStep2(map, map3);
        }
        long max = -1;
        long min = -1;
        var map2 = new HashMap<Character, Long>();
        for (var c : map3.entrySet()) {
            var key = c.getKey().charAt(0);
            map2.putIfAbsent(key, 0L);
            map2.put(key, map2.get(key) + c.getValue());
        }

        for (var i1 : map2.values()) {
            if (max == -1) {
                max = i1;
                min = i1;
            } else {
                max = Math.max(max, i1);
                min = Math.min(min, i1);
            }

        }

        return formatResult(max - min - 1);
    }

    private HashMap<String, Long> doStep2(HashMap<String, String> map, HashMap<String, Long> map3) {
        var result = new HashMap<String, Long>();
        for (String s : map3.keySet()) {
            var c = map.get(s.substring(0, 2));
            var s1 = s.charAt(0) + c;
            var s2 = c + s.charAt(1);

            result.putIfAbsent(s1, 0L);
            result.putIfAbsent(s2, 0L);
            result.put(s1, result.get(s1) + map3.get(s));
            result.put(s2, result.get(s2) + map3.get(s));
        }
        return result;
    }

    @Override
    public String getAnswerPart1() {
        return "2602";
    }

    @Override
    public String getAnswerPart2() {
        return "2942885922173";
    }
}
