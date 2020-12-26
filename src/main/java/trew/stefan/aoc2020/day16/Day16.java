package trew.stefan.aoc2020.day16;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day16 implements AOCDay {
    public static List<Integer> readCommaStrings(String line) {
        String[] strs = line.split(",");
        return Arrays.stream(strs).map(Integer::parseInt).collect(Collectors.toList());
    }

    class Rule {
        public String action;
        public int range_start1;
        public int range_end1;
        public int range_start2;
        public Integer position = null;
        public int range_end2;
        public List<Integer> validPos = new ArrayList<>();

        public boolean isValid(int value) {
            if (value >= range_start1 && value <= range_end1) {
                return true;
            }
            if (value >= range_start2 && value <= range_end2) {
                return true;
            }
            return false;
        }

        public Rule(String action, int range_start1, int range_end1, int range_start2, int range_end2) {
            this.action = action;
            this.range_start1 = range_start1;
            this.range_end1 = range_end1;
            this.range_start2 = range_start2;
            this.range_end2 = range_end2;
        }
    }

    private int day = 16;

    class Ticket {
        public Ticket(List<Integer> values) {
            this.values = values;
        }

        List<Integer> values = new ArrayList<>();
    }

    @Override
    public String runPart1() {

        List<String> lines = InputReader2020.readStrings(day, "");
        Pattern p = Pattern.compile("^([\\w\\s]*):\\s(\\d*)-(\\d*) or (\\d*)-(\\d*)");

        List<Rule> rules = new ArrayList<>();
        List<Ticket> otherTickets = new ArrayList<>();
        int counter = 0;
        Ticket myTicket = new Ticket(new ArrayList<>());
        int stage = 0;
        for (String line : lines) {
            if (line.equals("")) {
                stage++;
                continue;
            }

            switch (stage) {
                case 0:
                    Matcher m = p.matcher(line);
                    if (m.matches()) {

                        rules.add(new Rule(
                                m.group(1),
                                Integer.parseInt(m.group(2)),
                                Integer.parseInt(m.group(3)),
                                Integer.parseInt(m.group(4)),
                                Integer.parseInt(m.group(5))
                        ));
                    }

                    break;
                case 1:
                    if (line.equals("your ticket:")) continue;
                    myTicket = new Ticket(readCommaStrings(line));
                    break;
                case 2:
                    if (line.equals("nearby tickets:")) continue;
                    Ticket ticket = new Ticket(readCommaStrings(line));


                    boolean dicard = false;
                    for (int value : ticket.values) {
                        boolean valid = false;
                        for (Rule rule : rules) {
                            if (value >= rule.range_start1 && value <= rule.range_end1) {
                                valid = true;
                            }
                            if (value >= rule.range_start2 && value <= rule.range_end2) {
                                valid = true;
                            }
                        }
                        if (!valid) {
                            dicard = true;
                            counter += value;
                            break;
                        }
                    }
                    if (!dicard) {
                        otherTickets.add(ticket);
                    }
                    break;
            }

        }

        return String.valueOf(counter);
    }


    @Override
    public String runPart2() {

        List<String> lines = InputReader2020.readStrings(day, "");
        Pattern p = Pattern.compile("^([\\w\\s]*):\\s(\\d*)-(\\d*) or (\\d*)-(\\d*)");

        List<Rule> rules = new ArrayList<>();
        List<Ticket> otherTickets = new ArrayList<>();
        int counter = 0;
        Ticket myTicket = new Ticket(new ArrayList<>());
        int stage = 0;
        for (String line : lines) {
            if (line.equals("")) {
                stage++;
                continue;
            }

            switch (stage) {
                case 0:
                    Matcher m = p.matcher(line);
                    if (m.matches()) {

                        rules.add(new Rule(
                                m.group(1),
                                Integer.parseInt(m.group(2)),
                                Integer.parseInt(m.group(3)),
                                Integer.parseInt(m.group(4)),
                                Integer.parseInt(m.group(5))
                        ));
                    }

                    break;
                case 1:
                    if (line.equals("your ticket:")) continue;
                    myTicket = new Ticket(readCommaStrings(line));
                    break;
                case 2:
                    if (line.equals("nearby tickets:")) continue;
                    Ticket ticket = new Ticket(readCommaStrings(line));


                    boolean dicard = false;
                    for (int value : ticket.values) {
                        boolean valid = false;
                        for (Rule rule : rules) {
                            if (value >= rule.range_start1 && value <= rule.range_end1) {
                                valid = true;
                            }
                            if (value >= rule.range_start2 && value <= rule.range_end2) {
                                valid = true;
                            }
                        }
                        if (!valid) {
                            dicard = true;
                            break;
                        }
                    }
                    if (!dicard) {
                        otherTickets.add(ticket);
                    }
                    break;
            }

        }
        return String.valueOf(process(otherTickets, rules, myTicket));
    }

    private long process(List<Ticket> otherTickets, List<Rule> rules, Ticket myTicker) {
        for (int i = 0; i < rules.size(); i++) {

            for (Rule rule : rules) {

                boolean flag = true;
                for (Ticket ticket : otherTickets) {
                    if (!rule.isValid(ticket.values.get(i))) {
                        flag = false;
                    }
                }

                if (flag) {
                    rule.validPos.add(i);
                }
            }

        }
        long result = 1;

        while (true) {
            boolean allValid = true;
            for (Rule rule : rules) {
                if (rule.position == null) {
                    allValid = false;
                    break;
                }
            }

            if (allValid) {
                break;
            }
            for (Rule rule : rules) {
                if (rule.validPos.size() == 1) {
                    rule.position = rule.validPos.get(0);
                    if (rule.action.startsWith("departure")) {
                        result *= myTicker.values.get(rule.position);
                    }
                    for (Rule rule2 : rules) {
                        rule2.validPos.remove(rule.position);
                    }

                }
            }


        }
        return result;

    }
}
