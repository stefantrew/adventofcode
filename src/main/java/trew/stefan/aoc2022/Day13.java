package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


@Slf4j
public class Day13 extends AbstractAOC {


    @Override
    public String runPart1() {
        var list = getStringInput("_sample");



        var ascore = 0;
        var queue = new LinkedList<>(list);
        var parser = new JSONParser();
        var count = 0;
        while (!queue.isEmpty()) {

            try {
                JSONArray left = (JSONArray) parser.parse(queue.pop());
                JSONArray right = (JSONArray) parser.parse(queue.pop());
                log.info("==================================================");
                var result = compare(left, right, "  ");
                if (Boolean.TRUE.equals(result)) {
                    ascore += count + 1;
                }
                log.info("{} result {}", count + 1, result);
                count++;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            if (!queue.isEmpty()) {

                queue.pop();
            }
//            if (count == 4)
//            break;

        }


        return String.valueOf(ascore);
    }

    private Boolean compare(JSONArray left, JSONArray right, String indent) {

        log.info("{}Comparing {} vs {}", indent, left.toJSONString(), right.toJSONString());
        for (int i = 0; i < left.size(); i++) {
            if (i > right.size() - 1) {
                log.info("Right ran out of items");
                return false;
            }

            var leftEle = left.get(i);
            var rightEle = right.get(i);

            switch (leftEle) {
                case Long l && rightEle instanceof Long r -> {
                    log.info("{}Comparing {} vs {}", indent, l, r);
                    if (l < r) {
                        log.info("{}Left side is smaller", indent);
                        return true;
                    } else if (r < l) {
                        log.info("{}Right side is smaller, so inputs are not in the right order", indent);
                        return false;
                    }
                }
                case JSONArray l && rightEle instanceof JSONArray r -> {
                    var result = compare(l, r, indent + "  ");
                    if (result != null) {
                        return result;
                    }
                }
                case JSONArray l && rightEle instanceof Long r -> {
                    var ar = new JSONArray();
                    ar.add(r);
                    var result = compare(l, ar, indent + "  ");
                    if (result != null) {
                        return result;
                    }
                }
                case Long l && rightEle instanceof JSONArray r -> {
                    var ar = new JSONArray();
                    ar.add(l);
                    var result = compare(ar, r, indent + "  ");
                    if (result != null) {
                        return result;
                    }
                }
                case null, default -> throw new IllegalArgumentException("Unknown");
            }

        }
        if (left.size() != right.size()) {

            log.info("{}Left side ran out of items, so inputs are in the right order", indent);
            return true;
        }

        return null;
    }


    @Override
    public String runPart2() {
        var list = getStringInput("");

        var ascore = 0;


        return String.valueOf(ascore);
    }

    @Override
    public String getAnswerPart1() {
        return "7553";
    }

    @Override
    public String getAnswerPart2() {
        return "2758";
    }
}
