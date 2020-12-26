package trew.stefan.aoc2019.day14;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
1925375
1920219
 */

@Slf4j
public class Day14 implements Day {

    int target = 0;
    int target2 = 0;
    Map<String, Substitute> substitutes = new HashMap<>();

    public void run() {
        getElements();
        SubstitutionProcessor processor = new SubstitutionProcessor(substitutes);
        int counter = 0;
        long storage = 1000000000000L;
        while (storage > 0) {
            List<Element> output = processor.processRound(substitutes.get("FUEL").requirements);

            while (output.size() > 1) {
                output = processor.processRound(output);

            }
//        log.info("TARGET {}, OUTPUT {}", target, output.get(0).quantity);
            storage -= output.get(0).quantity;
            if (storage >= 0) {
                counter++;
            }
            if (counter % 100000 == 0) {
                log.info("{} {}", counter, storage);
            }
        }
        log.info("TARGET {}, OUTPUT {}", target2, counter);
    }


    public void getElements() {
        List<String> lines = InputReader2019.readStrings(14, "");
        target = Integer.parseInt(lines.get(0));
        target2 = Integer.parseInt(lines.get(1));

        for (int i = 2; i < lines.size(); i++) {
            log.info(lines.get(i));
            if (lines.get(i).equals("===")) {
                break;
            }
            Pattern p = Pattern.compile("(.*) => (\\d*) (\\w*)");
            Matcher matcher = p.matcher(lines.get(i));
            while (matcher.find()) {
                String input = matcher.group(1);
                int quantity = Integer.parseInt(matcher.group(2));
                String id = matcher.group(3);
                Substitute ele = new Substitute(id, quantity);


                Pattern p2 = Pattern.compile("^(\\d*) (\\w*)$");
                for (String part : input.split(",\\s")) {

                    Matcher matcher2 = p2.matcher(part);
                    while (matcher2.find()) {
                        int quantity2 = Integer.parseInt(matcher2.group(1));
                        String id2 = matcher2.group(2);
                        ele.addRequirement(new Element(id2, quantity2));

                    }
                }

                substitutes.put(id, ele);
//                log.info("{}", ele);
            }

        }
    }
}
