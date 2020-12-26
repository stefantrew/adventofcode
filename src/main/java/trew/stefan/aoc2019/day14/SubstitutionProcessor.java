package trew.stefan.aoc2019.day14;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
class SubstitutionProcessor {


    Map<String, Integer> extras = new HashMap<>();
    Map<String, Substitute> substitutes = new HashMap<>();

    public SubstitutionProcessor(Map<String, Substitute> substitutes) {
        this.substitutes = substitutes;
    }


    public List<Element> processRound(List<Element> input) {
//        log.info("------------------------------------------------------------------");
//        log.info("1 => {}", input);
        int currentOre = 0;

        Map<String, Integer> preStage = new HashMap<>();

        for (Element ele : input) {
            int current = preStage.getOrDefault(ele.id, 0);
            preStage.put(ele.id, ele.quantity + current);
        }

        for (String id : extras.keySet()) {
            int extra = extras.getOrDefault(id, 0);
            int current = preStage.getOrDefault(id, 0);

            if (current >= extra) {
                current -= extra;
                extras.put(id, 0);
            } else {
                extra -= current;
                current = 0;
                extras.put(id, extra);
            }
            preStage.put(id, current);
        }
        input = new ArrayList<>();
        for (String id : preStage.keySet()) {
            int current = preStage.getOrDefault(id, 0);
            if (current > 0) {
                input.add(new Element(id, current));
            }
        }
//        log.info("1 => {}", input);


        List<Element> stage = new ArrayList<>();

        for (Element ele : input) {

            if (ele.id.equals("ORE")) {
                currentOre += ele.quantity;
                continue;
            }

            int qty = ele.quantity;
            int extra = 0;
            Substitute substitute = substitutes.get(ele.id);
            int number = qty / substitute.quantity;
            if (number * substitute.quantity != qty) {
                number++;
                extra = number * substitute.quantity - qty;
                extras.put(ele.id, extra);
            }
            stage.add(new Element(ele.id, number * substitute.quantity));
        }
//        log.info("2 => {}", stage);
//        log.info("x => {}", extras);


        List<Element> stage2 = new ArrayList<>();
        for (Element ele : stage) {
            int qty = ele.quantity;


            Substitute substitute = substitutes.get(ele.id);

            int number = qty / substitute.quantity;
            for (Element sub : substitute.requirements) {
                stage2.add(new Element(sub.id, sub.quantity * number));

            }
        }
//        log.info("3 => {}", stage2);

        Map<String, Integer> stage3 = new HashMap<>();

        for (Element ele : stage2) {
            int current = stage3.getOrDefault(ele.id, 0);
            current += ele.quantity;
            stage3.put(ele.id, current);
        }
//        log.info("4 => {}", stage3);

        for (String id : extras.keySet()) {
            int extra = extras.getOrDefault(id, 0);
            int current = stage3.getOrDefault(id, 0);

            if (current >= extra) {
                current -= extra;
                extras.put(id, 0);
            } else {
                extra -= current;
                current = 0;
                extras.put(id, extra);
            }
            stage3.put(id, current);
        }
//        log.info("5 => {}", stage3);
//        log.info("x => {}", extras);

        List<Element> output = new ArrayList<>();
        for (String id : stage3.keySet()) {
            int current = stage3.getOrDefault(id, 0);
            if (current > 0) {
                output.add(new Element(id, current));
            }
        }
        if (currentOre > 0) {

            output.add(new Element("ORE", currentOre));
        }
//        log.info("! => {}", output);
        return output;
    }
}
