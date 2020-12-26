package trew.stefan.aoc2020.day19;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.*;

@Slf4j
public class Day19 implements AOCDay {

    private int day = 19;
    Map<String, Rule> rules = new HashMap<>();

    class Rule {
        String id;
        List<RuleSet> rules = new ArrayList<>();

        Character c = null;

        public Rule(String id) {
            this.id = id;
        }

        boolean validate(String term, Rule[] nextRules) {

            boolean flag = false;
            if (term.equals("")) {
                return false;
            }
            if (c != null) {
                if (term.charAt(0) != c) {
                    return false;
                }
                term = term.substring(1);
                flag = true;
            } else {
                for (RuleSet set : rules) {
                    if (set.validate(term, nextRules)) {
                        return true;
                    }
                }
            }

            if (!flag) {
                return false;
            }

            if (nextRules.length > 0) {
                Rule nextRule = nextRules[0];
                Rule[] temp = new Rule[nextRules.length - 1];
                System.arraycopy(nextRules, 1, temp, 0, temp.length);
                return nextRule.validate(term, temp);
            }

            return term.equals("");
        }
    }

    class RuleSet {

        List<Rule> rules = new ArrayList<>();

        public boolean validate(String substring, Rule[] nextRules) {

            if (substring.length() == 0) {
                return false;
            }

            Rule[] temp = new Rule[rules.size() - 1 + nextRules.length];
            for (int i = 0; i < rules.size() - 1; i++) {
                temp[i] = rules.get(i + 1);
            }

            System.arraycopy(nextRules, 0, temp, rules.size() - 1, nextRules.length);
            return rules.get(0).validate(substring, temp);

        }
    }

    public Rule getRule(String id) {
        if (!rules.containsKey(id)) {
            rules.put(id, new Rule(id));
        }
        return rules.get(id);
    }

    @Override
    public String runPart1() {
        return String.valueOf(run(false));
    }

    @Override
    public String runPart2() {
        return String.valueOf(run(true));
    }

    public int run(boolean isPart2) {
        List<String> lines = InputReader2020.readStrings(day, "");
        rules.clear();
        int counter = 0;
        int stage = 0;
        for (String line : lines) {
            if (line.equals("")) {
                stage = 1;
                continue;
            }

            if (stage == 0) {
                String[] parts = line.split(":");
                Rule rule = getRule(parts[0]);

                if (parts[1].contains("\"")) {
                    rule.c = parts[1].trim().charAt(1);
                    continue;
                }
                if (isPart2) {

                    if (rule.id.equals("8")) {
                        parts[1] = "42 | 42 8";
                    }
                    if (rule.id.equals("11")) {
                        parts[1] = "42 31 | 42 11 31";
                    }
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

//                List<String> list = getRule("0").buildRule();
//                log.info("{}", list);

                boolean contains = getRule("0").validate(line, new Rule[0]);
                if (contains) {
                    counter++;
                }
            }

        }
        return counter;
    }
}
