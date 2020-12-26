package trew.stefan.aoc2020.day07;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day07 implements AOCDay {


    class Bag {
        public String name;

        public List<BagRule> rules;

        public Bag(String name) {
            this.name = name;
            rules = new ArrayList<>();
        }
    }

    class BagRule {

        public Bag bag;
        public int qty;


        public BagRule(int qty, Bag bag) {
            this.qty = qty;
            this.bag = bag;
        }
    }

    public Map<String, Bag> bags = new HashMap<>();

    private int day = 7;
    String part1;
    String part2;


    public Day07() {
        run();
    }

    @Override
    public String runPart1() {
        return part1;
    }

    @Override
    public String runPart2() {
        return part2;
    }


    private Bag getBag(String name) {

        if (!bags.containsKey(name)) {
            bags.put(name, new Bag(name));
        }

        return bags.get(name);
    }

    public void run() {
        List<String> lines = InputReader2020.readStrings(day, "");


        for (String line : lines) {

            String[] strings = line.split(" bags contain ");

            String name = strings[0];

            Bag bag = getBag(name);

            String[] parts = strings[1].split(", ");

            for (String part : parts) {


                if (part.equals("no other bags")) {
                    continue;
                }

                Pattern p = Pattern.compile("(\\d*) (\\w* \\w*) bag");

                Matcher matcher = p.matcher(part);
                while (matcher.find()) {
                    int qty = Integer.parseInt(matcher.group(1));
                    String type = (matcher.group(2));

                    bag.rules.add(new BagRule(qty, getBag(type)));
                }


            }

        }

        int count = 0;

        for (Bag bag : bags.values()) {

            if (checkBag(bag)) {
                count++;
            }

        }
        part1 = String.valueOf(count - 1);
        part2 = String.valueOf(countBag(getBag("shiny gold")) - 1);

    }

    private int countBag(Bag bag) {

        int count = 1;

//        if (bag.rules.size() == 0) {
//            return 1;
//        }

        for (BagRule rule : bag.rules) {
            count += rule.qty * countBag(rule.bag);
        }

        return count;
    }

    private boolean checkBag(Bag bag) {

        if (bag.name.equals("shiny gold")) {
            return true;
        }


        for (BagRule rule : bag.rules) {
            if (checkBag(rule.bag)) {
                return true;
            }
        }

        return false;
    }
}
