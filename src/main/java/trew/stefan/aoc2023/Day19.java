package trew.stefan.aoc2023;

import javafx.scene.shape.Box;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class Day19 extends AbstractAOC {

    @ToString
    class ValidSet {


        String action;
        int xStart;
        int xEnd;
        int mStart;
        int mEnd;
        int aStart;
        int aEnd;
        int sStart;
        int sEnd;

        public ValidSet(String action) {
            xStart = 0;
            xEnd = 4000;
            mStart = 0;
            mEnd = 4000;
            aStart = 0;
            aEnd = 4000;
            sStart = 0;
            sEnd = 4000;
            this.action = action;
        }

        public Long getVolume() {
            var l = (long) (xEnd - xStart) * (mEnd - mStart) * (aEnd - aStart) * (sEnd - sStart);
            return l;
        }

        class RangeOverlapResult {
            private boolean overlap;
            private int startOverlap;
            private int endOverlap;

            public RangeOverlapResult(boolean overlap, int startOverlap, int endOverlap) {
                this.overlap = overlap;
                this.startOverlap = startOverlap;
                this.endOverlap = endOverlap;
            }

            public boolean isOverlap() {
                return overlap;
            }

            public int getStartOverlap() {
                return startOverlap;
            }

            public int getEndOverlap() {
                return endOverlap;
            }
        }

        public RangeOverlapResult calculateRangeOverlap(int A, int B, int C, int D) {
            int startOverlap = Math.max(A, C);
            int endOverlap = Math.min(B, D);

            boolean overlap = startOverlap <= endOverlap;

            return new RangeOverlapResult(overlap, startOverlap, endOverlap);
        }

        public Long computeOverlap(ValidSet ref) {

            var overlaps = new ArrayList<RangeOverlapResult>();
            overlaps.add(calculateRangeOverlap(xStart, xEnd, ref.xStart, ref.xEnd));
            overlaps.add(calculateRangeOverlap(mStart, mEnd, ref.mStart, ref.mEnd));
            overlaps.add(calculateRangeOverlap(aStart, aEnd, ref.aStart, ref.aEnd));
            overlaps.add(calculateRangeOverlap(sStart, sEnd, ref.sStart, ref.sEnd));

            var total = 1L;
            for (RangeOverlapResult overlap : overlaps) {
                if (overlap.isOverlap()) {
                    total *= (overlap.getEndOverlap() - overlap.getStartOverlap());
                } else {
                    return 0L;
                }
            }

            return total;
        }

        public void applyRule(Rule rule) {
            switch (rule.field) {
                case "x" -> {
                    if (rule.operator.equals(">")) {
                        xStart = Math.max(xStart, rule.value + 1);
                    } else {
                        xEnd = Math.min(xEnd, rule.value - 1);
                    }
                }
                case "m" -> {
                    if (rule.operator.equals(">")) {
                        mStart = Math.max(mStart, rule.value + 1);
                    } else {
                        mEnd = Math.min(mEnd, rule.value - 1);
                    }
                }
                case "a" -> {
                    if (rule.operator.equals(">")) {
                        aStart = Math.max(aStart, rule.value + 1);
                    } else {
                        aEnd = Math.min(aEnd, rule.value - 1);
                    }
                }
                case "s" -> {
                    if (rule.operator.equals(">")) {
                        sStart = Math.max(sStart, rule.value + 1);
                    } else {
                        sEnd = Math.min(sEnd, rule.value - 1);
                    }
                }
            }
        }
    }

    @ToString
    class Rule {

        String action;

        String operator;
        String field;
        int value;

        public Rule(String s) {

            final String regex = "(.)(.)(\\d*):(.*)";

            var pattern = Pattern.compile(regex);
            var matcher = pattern.matcher(s);

            while (matcher.find()) {

                field = matcher.group(1);
                operator = matcher.group(2);
                value = Integer.parseInt(matcher.group(3));
                action = matcher.group(4);
            }
        }


        boolean isMatch(Part part) {

            var x = part.fields.get(field);
            if (operator.equals(">")) {
                return x > value;
            }

            return x < value;
        }

    }

    @ToString
    class Part {

        Map<String, Integer> fields = new HashMap<>();

        public Part(String s) {
            final String regex = "x=(\\d*),m=(\\d*),a=(\\d*),s=(\\d*)";

            var pattern = Pattern.compile(regex);
            var matcher = pattern.matcher(s);

            while (matcher.find()) {

                fields.put("x", Integer.parseInt(matcher.group(1)));
                fields.put("m", Integer.parseInt(matcher.group(2)));
                fields.put("a", Integer.parseInt(matcher.group(3)));
                fields.put("s", Integer.parseInt(matcher.group(4)));
            }
        }

        public Part(int x, int m, int a, int s) {
            fields.put("x", x);
            fields.put("m", m);
            fields.put("a", a);
            fields.put("s", s);
        }

        int getRating() {
            return fields.get("x") + fields.get("m") + fields.get("a") + fields.get("s");
        }

    }

    @ToString
    class Workflow {

        String id;
        String defaultAction;
        List<Rule> rules = new ArrayList<>();


        String process(Part part) {
            for (Rule rule : rules) {
                if (rule.isMatch(part)) {
                    return rule.action;
                }
            }

            return defaultAction;

        }


        public List<ValidSet> getBaseSet(Map<String, Workflow> maps) {
            if (defaultAction.equals("A") || defaultAction.equals("R")) {
                return List.of(new ValidSet(defaultAction));
            }

            return maps.get(defaultAction).getValidSets(maps);
        }


        public List<ValidSet> getValidSets(Map<String, Workflow> maps) {
            var list = new ArrayList<ValidSet>();

//            log.info("Computing {}", id);
            for (Rule rule : rules) {

                var subSet = switch (rule.action) {
                    case "A", "R" -> List.of(new ValidSet(rule.action));
                    default -> maps.get(rule.action).getValidSets(maps);
                };


                for (ValidSet validSet : subSet) {

                    validSet.applyRule(rule);
                    list.add(validSet);
                }

            }

            list.addAll(getBaseSet(maps));

            return list;
        }

    }


    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("_sample");
        var map = new HashMap<String, Workflow>();

        var partList = new ArrayList<Part>();
        for (var s : list) {
//            log.info("{}", s);

            if (s.indexOf("{") > 0) {
                s = s.replace("}", "");
                var parts = s.split("\\{");
                var id = parts[0];
                var rules = parts[1].split(",");

                var workflow = new Workflow();
                workflow.id = id;
                workflow.defaultAction = rules[rules.length - 1];

                for (var i = 0; i < rules.length - 1; i++) {
                    workflow.rules.add(new Rule(rules[i]));
                }
                map.put(id, workflow);
            }
            if (s.startsWith("{")) {
                partList.add(new Part(s));
            }
        }


        for (Part part : partList) {
            var in = map.get("in");

            while (true) {

                var result = in.process(part);
                if (result.equals("A")) {
                    total += part.getRating();
                    break;
                } else if (result.equals("R")) {
                    break;
                } else {
                    in = map.get(result);
                    if (in == null) {
//                        log.info("{}", result);
                    }
                }
            }
        }

        return formatResult(total);
    }

    @Override
    public String runPart2() {


        var totalA = 0L;
        var totalR = 0L;

        var list = getStringInput("_sample");
        var map = new HashMap<String, Workflow>();

        var partList = new ArrayList<Part>();
        for (var s : list) {
//            log.info("{}", s);

            if (s.indexOf("{") > 0) {
                s = s.replace("}", "");
                var parts = s.split("\\{");
                var id = parts[0];
                var rules = parts[1].split(",");

                var workflow = new Workflow();
                workflow.id = id;
                workflow.defaultAction = rules[rules.length - 1];

                for (var i = 0; i < rules.length - 1; i++) {
                    workflow.rules.add(new Rule(rules[i]));
                }
                map.put(id, workflow);
            }
        }

        var validSets = map.get("pv").getValidSets(map);

        for (int i = 0; i < validSets.size(); i++) {
            ValidSet validSet = validSets.get(i);
            var vol = validSet.getVolume();
            if (validSet.action.equals("A")) {
                totalA += vol;
            } else {
//                totalA -= vol;
            }
//            log.info("Volumn {}", vol);
            for (int j = 0; j < i; j++) {
                var overlap = validSet.computeOverlap(validSets.get(j));
//                log.info("overlap {}", overlap);
                if (overlap > 0L) {
                    if (validSets.get(j).action.equals("A")) {
//                        totalA += overlap;
                    } else {
                        totalA -= overlap;
                    }
                }
            }
//            log.info("{}", validSet);
        }
        //A 495709756631235
        //R 438202896361380
        // 260965840113597
        // 1256996724258420
        //  119730195977928
        //  256000000000000
        //F 167409079868000

//        log.info("{}", totalA);
//        log.info("{}", totalR);

//        for (int x = 0; x < 4000; x++) {
//            log.info("{}", x);
//            for (int m = 0; m < 4000; m++) {
//                log.info("m {}", m);
//                for (int a = 0; a < 4000; a++) {
//                    for (int s = 0; s < 4000; s++) {
//                        if (isAccepted(map, new Part(x, m, a, s))) {
//                            total++;
//                        }
//
//                    }
//
//                }
//            }
//        }


        return formatResult(totalA);
    }

    private static boolean isAccepted(HashMap<String, Workflow> map, Part part) {
        var in = map.get("in");

        while (true) {

            var result = in.process(part);
            if (result.equals("A")) {
                return true;
            } else if (result.equals("R")) {
                return false;
            } else {
                in = map.get(result);
                if (in == null) {
//                    log.info("{}", result);
                }
            }
        }
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
