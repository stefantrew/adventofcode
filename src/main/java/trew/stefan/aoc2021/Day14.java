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

        var total = 0;
        var result = "";


//        var list = getStringInput().stream().map(this::mapper).collect(Collectors.toList());

        var list = getStringInput();


//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        var map = new HashMap<String, String>();

        String target = null;
        for (var s : list) {
            if (target == null) {
                target = s;
                continue;
            } else if (s.isEmpty()) {
                continue;
            } else {
                var p = Pattern.compile("(\\w*) -> (\\w)");
                var m = new AOCMatcher(p.matcher(s));

                if (m.find()) {
                    m.print();
                    map.put(m.getString(1), m.group(2));
                }
            }
            log.info("{}", s);
        }


        var history = new HashMap<Character, List<Long>>();

        long max = -1;
        long min = -1;
        long last = -1;
        var i = 10;
        while (i-- > 0) {
            log.info("step {}", i);
            target = doStep(map, target);


            var map2 = new HashMap<Character, Long>();
            for (char c : target.toCharArray()) {
                map2.putIfAbsent(c, 0L);
                map2.put(c, map2.get(c) + 1);


                max = -1;
                min = -1;
            }
            for (var key : map2.keySet()) {
                history.putIfAbsent(key, new ArrayList<>());
                var arr = history.get(key);
                arr.add(map2.get(key));
                history.put(key, arr);
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
        }
        for (var key : history.keySet()) {
            log.info("{} {}", key, history.get(key));
        }
//                log.info("{}", map2);
        return formatResult(max - min);
    }

    private String doStep(HashMap<String, String> map, String target) {
        StringBuilder res = new StringBuilder(target.length() + 10);
        for (int i = 0; i < target.length() - 1; i++) {
            String com = String.valueOf(target.charAt(i)) + String.valueOf(target.charAt(i + 1));
            res.append(target.charAt(i) + map.get(com));

        }
        res.append(target.charAt(target.length() - 1));
        return res.toString();
    }


    @AllArgsConstructor
    class Item {

    }

    Item mapper(String input) {

        var p = Pattern.compile("");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item();
        }
        return null;
    }


    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
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
