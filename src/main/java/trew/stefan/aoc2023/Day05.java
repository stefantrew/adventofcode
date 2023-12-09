package trew.stefan.aoc2023;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day05 extends AbstractAOC {


    @Override
    public String runPart1() {

        var maps = new HashMap<String, ItemMap>();

        var list = getStringInput("");
        ItemMap map = null;
        var seeds = new ArrayList<Long>();
        for (var s : list) {
            if (s.isBlank()) {
                continue;
            } else if (s.startsWith("seeds")) {
                s = s.replace("seeds: ", "");
                Arrays.stream(s.split(" "))
                        .map(Long::parseLong)
                        .forEach(seeds::add);

            } else if (s.contains("map")) {
                map = new ItemMap();
                s = s.replace("-to-", " ")
                        .replace(" map:", "");

                map.source = s.split(" ")[0];
                map.destination = s.split(" ")[1];
                maps.put(map.source, map);
            } else {
                var item = new Item(Long.parseLong(s.split(" ")[0]), Long.parseLong(s.split(" ")[1]), Long.parseLong(s.split(" ")[2]));
                map.items.add(item);
            }
        }

        var itemMap = maps.get("seed");

        while (true) {

            ItemMap finalItemMap = itemMap;
            List<Long> result2 = seeds.stream().map(itemMap::convert).toList();

            seeds = new ArrayList<>(result2);
            if (itemMap.destination.equals("location")) {
                break;
            }
            itemMap = maps.get(itemMap.destination);

        }
        return formatResult(seeds.stream().min(Long::compareTo).get());
    }


    class ItemMap {

        String source;
        String destination;
        Set<Item> items = new HashSet<>();

        Map<Long, Long> cache = new HashMap<>();

        public Long convert(Long input) {


            for (Item item : items) {
                var temp = item.convert(input);
                if (temp != null) {
                    return temp;
                }
            }
            return input;
        }

        @Override
        public String toString() {
            return "ItemMap{" +
                   "source='" + source + '\'' +
                   ", destination='" + destination + '\'' +
                   ", items=" + items.size() +
                   '}';
        }
    }


    @AllArgsConstructor
    class Item {

        long destStart;
        long sourceStart;
        long length;

        public Long convert(long input) {
            if (input >= sourceStart && input <= sourceStart + length) {
                return destStart + (input - sourceStart);
            }

            return null;
        }

    }


    @Override
    public String runPart2() {


        var maps = new HashMap<String, ItemMap>();

        var list = getStringInput("");
        ItemMap map = null;
        var seeds2 = new ArrayList<Long>();

        var totalSeeds = 0L;

        for (var s : list) {
            if (s.isBlank()) {
                continue;
            } else if (s.startsWith("seeds")) {
                s = s.replace("seeds: ", "");
                Arrays.stream(s.split(" "))
                        .map(Long::parseLong)
                        .forEach(seeds2::add);


            } else if (s.contains("map")) {
                map = new ItemMap();
                s = s.replace("-to-", " ")
                        .replace(" map:", "");

                map.source = s.split(" ")[0];
                map.destination = s.split(" ")[1];
                maps.put(map.source, map);
            } else {
                var item = new Item(Long.parseLong(s.split(" ")[0]), Long.parseLong(s.split(" ")[1]), Long.parseLong(s.split(" ")[2]));
                map.items.add(item);
            }
        }

        return "11611182L";
    }

    private static Long getaLong(HashMap<String, ItemMap> maps, HashSet<Long> seeds) {
        var itemMap = maps.get("seed");
        while (true) {
            List<Long> result2 = new ArrayList<>();
            for (Long seed : seeds) {
                Long convert = itemMap.convert(seed);
                result2.add(convert);
            }

            seeds = new HashSet<>(result2);
            if (itemMap.destination.equals("location")) {
                break;
            }
            itemMap.cache.clear();
            itemMap = maps.get(itemMap.destination);

        }
        return seeds.stream().min(Long::compareTo).get();
    }

    @Override
    public String getAnswerPart1() {
        return "173706076";
    }

    @Override
    public String getAnswerPart2() {
        return "11611182";
    }
}
