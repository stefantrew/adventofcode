package trew.stefan.aoc2020.day19;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day19Part1 implements Day {

    private int day = 19;
    Map<String, Rule> rules = new HashMap<>();

    class Rule {
        String id;
        List<RuleSet> rules = new ArrayList<>();

        Character c = null;
        List<String> valid = new ArrayList<>();

        public Rule(String id) {
            this.id = id;
        }

        List<String> buildRule() {
            if (!valid.isEmpty()) {
                return valid;
            }

            if (c != null) {
                valid.add(c.toString());
            } else {
                for (RuleSet set : rules) {
                    valid.addAll(set.build(this));
                }
            }
            return valid;
        }

//
//        boolean validate(String term) {
//
//            boolean flag = false;
//            if (term.equals("")) {
//                return false;
//            }
//            if (c != null && term.length() > 0 && term.charAt(0) == c) {
//                return true;
//            } else if (c == null) {
//                for (RuleSet set : rules) {
//                    if (set.validate(term.substring(1))) {
//                        flag = true;
//                    }
//                }
//            }
//
//
//            return flag;
//        }
    }

    class RuleSet {

        List<Rule> rules = new ArrayList<>();


        public List<String> build(Rule parentRule) {

            List<String> valid = new ArrayList<>();
            valid.add("");
            for (Rule rule : rules) {
                List<String> temp = rule.buildRule();
                List<String> temp2 = new ArrayList<>();

                for (String valid1 : valid) {
                    for (String tempStr : temp) {
                        temp2.add(valid1 + tempStr);
                    }
                }

                valid = temp2;
            }
            return valid;
        }
    }

    public Rule getRule(String id) {
        if (!rules.containsKey(id)) {
            rules.put(id, new Rule(id));
        }
        return rules.get(id);
    }

    public void run() {
        List<String> lines = InputReader2020.readStrings(day, "");

        int counter = 0;
        int stage = 0;
        List<String> list = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("")) {
                stage = 1;
                list = getRule("0").buildRule();
                continue;
            }

            if (stage == 0) {
                String[] parts = line.split(":");
                Rule rule = getRule(parts[0]);

                if (parts[1].contains("\"")) {
                    rule.c = parts[1].trim().charAt(1);
                    continue;
                }

                String[] sets = parts[1].split("\\|");

                for (String set : sets) {
                    set = set.trim();
                    RuleSet ruleSet = new RuleSet();
                    for (String child : set.split(" ")) {
                        ruleSet.rules.add(getRule(child));
                    }
                    rule.rules.add(ruleSet);
                }
            } else if (stage == 1) {


//                log.info("{}", list);

                boolean contains = list.contains(line);
                if (contains) {
                    counter++;
                }
                log.info("{}", contains);
            }

        }
        log.info("{}", counter);
    }
}
