package trew.stefan.aoc2015;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class MoleculeRunnable implements Runnable {

    Map<String, Element> elementMap;
    Set<Integer> distinct;
    Molecule start;
    int counter = 0;
    Molecule target;
    int id;
    @Getter
    boolean running = true;
    int offset = 7;
    int limitCounter = 0;
    String best = "";
    int bestIndex = 0;

    public MoleculeRunnable(int id, Map<String, Element> elementMap, Set<Integer> distinct, Molecule start, Molecule target, int offset) {
        this.elementMap = elementMap;
        this.distinct = distinct;
        this.start = start;
        this.target = target;
        this.id = id;
        this.offset += offset;
        log.info("New Runner {} {}", id, start);
    }

    @Override
    public void run() {

        log.info("{} Target {}", id, target.elements.size());
        log.info("{} result {} {}", start, id, makeMolecule(start, target, 0));
        running = false;
    }

    @Override
    public String toString() {
        return String.format("%d %b %s Limit Counter: %d Length: %d index: %d %s", id, running, start.toString(), limitCounter, best.length(), bestIndex, best);
    }

    private Integer makeMolecule(Molecule start, Molecule target, int depth) {

        if (distinct.contains(start.hash)) {
            return null;
        }
        distinct.add(start.hash);

        if (start.stop) {
            return null;
        }

        if (start.elements.size() > target.elements.size() -10) {
            limitCounter++;
            return null;
        }

        String startString = start.toString();
        String targetString = target.toString();
        if (startString.equals(targetString)) {
            return 0;
        }

        if (targetString.contains(startString)) {
            if (startString.length() > best.length()) {
                best = startString;
                bestIndex = targetString.indexOf(startString);
            }
        }

        if (counter++ % 1000 == 0) {
            log.info("{} {} cache: {}", id, counter, distinct.size());
        }
        List<Element> elements = start.elements;
        for (int i = 0; i < elements.size(); i++) {
            Element ele = elements.get(i);
            for (Replacement replacement : ele.replacements) {
                Integer hash = Objects.hash(replacement.output, replacement.input, start, i);
                if (distinct.contains(hash)) {
                    continue;
                }
                distinct.add(hash);


                Molecule clone = start.clone();
                clone.replaceElement(i, replacement.output);
                Integer result = makeMolecule(clone, target, depth + 1);
                if (result != null) {
                    return result + 1;
                }
            }
        }
        return null;
    }
}
