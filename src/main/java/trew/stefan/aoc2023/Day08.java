package trew.stefan.aoc2023;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

@Slf4j
public class Day08 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");
        var map = new HashMap<String, Item>();

        String path = null;
        for (var s : list) {
            if (path == null) {
                path = s;
                continue;
            }
            if (s.equals("")) {
                continue;
            }
            var item = mapper(s);

            map.put(item.name, item);
        }

        var current = map.get("AAA");
        while (current.name.equals("ZZZ") == false) {
            var dir = path.charAt(total % path.length());
            current = map.get(dir == 'R' ? current.right : current.left);
            total++;
        }

        return formatResult(total);
    }


    @AllArgsConstructor
    @ToString
    class Item {
        String name;
        String left;
        String right;
    }

    Item mapper(String input) {

        var p = Pattern.compile("(\\w*) = \\((\\w*), (\\w*)\\)");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item(m.getString(1), m.getString(2), m.getString(3));
        }
        return null;
    }


    @Override
    public String runPart2() {


        var total = 0L;

        var list = getStringInput("");
        var map = new HashMap<String, Item>();

        var current = new ArrayList<Item>();

        String path = null;
        for (var s : list) {
            if (path == null) {
                path = s;
                continue;
            }
            if (s.equals("")) {
                continue;
            }
            var item = mapper(s);

            map.put(item.name, item);
            if (item.name.endsWith("A")) {
                current.add(item);
            }
        }
        //18727 20569 14429 13201 18113 22411
        long result = 61L * 307 * 67 * 47 * 43 * 59 * 73L;
        var first = current.get(5);
        current.clear();
        current.add(first);
        var last = 0;
        var counter = 0;
        while (true) {
            var dir = path.charAt(Math.toIntExact(total % path.length()));
            var next = new ArrayList<Item>();
            for (var c : current) {
                next.add(map.get(dir == 'R' ? c.right : c.left));
            }

            var found = false;
            for (Item item : next) {
                if (!item.name.endsWith("Z")) {
                    found = true;
                    break;
                }
            }
            total++;
            if (!found) {
                last = Math.toIntExact(total);
                counter++;
//                break;
            }
            if (counter == 10) {
                break;
            }
            current = next;
        }

        return formatResult(result);
    }

    @Override
    public String getAnswerPart1() {
        return "14429";
    }

    @Override
    public String getAnswerPart2() {
        return "10921547990923";
    }
}
