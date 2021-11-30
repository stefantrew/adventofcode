package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day07 extends AbstractAOC {

    class Step {

        String label;

        int timeLeft = 0;

        List<Step> neededBy = new ArrayList<>();
        List<Step> neededFor = new ArrayList<>();

        boolean completed = false;

        public Step(String label) {
            this.label = label;
        }

        boolean isReady() {
            if (completed) {
                return false;
            }

            for (Step step : neededBy) {
                if (!step.completed) {
                    return false;
                }

            }

            return true;
        }


        @Override
        public String toString() {
            return label;
        }
    }

    public Step getStep(String label, Map<String, Step> map) {

        return map.computeIfAbsent(label, s -> new Step(label));

    }

    @Override
    public String runPart1() {


        var list = getInput("");

        Map<String, Step> map = getSteps(list);

        final var result = new StringBuilder();

        while (true) {
            var next = map.values().stream().filter(Step::isReady).sorted(Comparator.comparing(o -> o.label)).findFirst();

            if (next.isEmpty()) {
                break;
            }
            next.ifPresent(step -> {
                step.completed = true;
                result.append(step.label);
            });
        }

        return result.toString();
    }

    private Map<String, Step> getSteps(List<String> list) {
        Map<String, Step> map = new HashMap<>();

        list.forEach(s -> {

            var p = Pattern.compile("Step (\\w) must be finished before step (\\w) can begin.");
            var m = p.matcher(s);
            if (m.find()) {

                var step1 = getStep(m.group(1), map);
                var step2 = getStep(m.group(2), map);
                step1.neededFor.add(step2);
                step2.neededBy.add(step1);
            }
        });
        return map;
    }

    @Override
    public String runPart2() {


        var list = getInput("");

        Map<String, Step> map = getSteps(list);
        map.values().forEach(step -> {
            int value = Character.getNumericValue(step.label.charAt(0));
            step.timeLeft = 60 + value - 9;
        });

        final var result = new StringBuilder();

        var time = 0;
        var workers = 5;
        while (true) {
            var next = map.values().stream().filter(Step::isReady).sorted(Comparator.comparing(o -> o.label)).limit(workers).collect(Collectors.toList());

            if (next.isEmpty()) {
                break;
            }

            time++;
            next.forEach(step -> {
                step.timeLeft--;
                if (step.timeLeft == 0) {
                    step.completed = true;
                    result.append(step.label);
                }
            });
        }

        return String.valueOf(time);
    }

    @Override
    public String getAnswerPart1() {
        return "IBJTUWGFKDNVEYAHOMPCQRLSZX";
    }

    @Override
    public String getAnswerPart2() {
        return "1118";
    }
}
