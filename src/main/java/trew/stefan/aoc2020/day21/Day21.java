package trew.stefan.aoc2020.day21;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 implements AOCDay {

    private int day = 21;
    String part1 = "";
    String part2 = "";

    class Allergen {


        String name;
        List<String> possibleIngredients = new ArrayList<>();

        public Allergen(String name) {
            this.name = name;
        }
    }

    Map<String, Allergen> allergenMap = new HashMap<>();

    Allergen getAllergen(String name) {

        if (!allergenMap.containsKey(name)) {
            allergenMap.put(name, new Allergen(name));
        }

        return allergenMap.get(name);

    }

    @Override
    public String runPart1() {
        return part1;
    }

    @Override
    public String runPart2() {
        return part2;
    }

    public Day21() {
        List<String> lines = InputReader2020.readStrings(day, "");
        Pattern p = Pattern.compile("([\\w\\s]*)\\(contains ([\\w\\s,]*)\\)");

        List<String> words = new ArrayList<>();
        for (String line : lines) {
            Matcher match = p.matcher(line);

            if (match.find()) {
                String ingredients = match.group(1);
                String allergens = match.group(2);

                List<String> asList = Arrays.asList(ingredients.split(" "));

                for (String w : asList)
                    words.add(w);
                for (String allergen : allergens.split(",")) {
                    allergen = allergen.trim();
                    Allergen a = getAllergen(allergen);


                    if (a.possibleIngredients.isEmpty()) {
                        a.possibleIngredients.addAll(asList);
                    } else {

                        List<String> temp = new ArrayList<>();

                        for (String in1 : asList) {
                            in1 = in1.trim();
                            if (a.possibleIngredients.contains(in1)) {
                                temp.add(in1);
                            }
                        }

                        a.possibleIngredients = temp;

                    }
                }

            }
        }

        while (!iterate()) ;
//        log.info("{} {}", words.size(), words);
        int count = 0;
        for (Allergen a : allergenMap.values()) {
            for (String w : a.possibleIngredients) {
                while (words.contains(w)) {
                    count++;
                    words.remove(w);
                }
            }
        }
        part1 = String.valueOf(words.size());

        List<String> keys = new ArrayList<>(allergenMap.keySet());
        keys.sort((o1, o2) -> {
            if (o1.charAt(0) == o2.charAt(0)) {
                return o1.charAt(1) - o2.charAt(1);
            }
            return o1.charAt(0) - o2.charAt(0);

        });

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            Allergen a = allergenMap.get(key);
            sb.append(a.possibleIngredients.get(0));
        }


        part2 = sb.toString();
    }

    private boolean iterate() {

        boolean result = true;
        for (Allergen a : allergenMap.values()) {
            if (a.possibleIngredients.size() == 1) {
                for (Allergen a2 : allergenMap.values()) {
                    if (!a2.name.equals(a.name)) {
                        a2.possibleIngredients.remove(a.possibleIngredients.get(0));
                    }
                }
            } else {
                result = false;
            }
        }

        return result;
    }
}
