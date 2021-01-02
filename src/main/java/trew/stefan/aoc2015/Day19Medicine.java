package trew.stefan.aoc2015;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class Day19Medicine implements Day {

    Map<String, Element> elementMap = new ConcurrentHashMap<>();
    Set<Integer> distinct = new HashSet<>();

    @Override
    public void run() {

        List<String> lines = InputReader2019.readStrings(2015, "");
        Molecule target = null;

        Pattern p = Pattern.compile("(\\w*) => (\\w*)");
        for (String line : lines) {

            Matcher matcher = p.matcher(line);

            if (matcher.find()) {
                String label = matcher.group(1);
                Element element = elementMap.get(label);
                if (element == null) {
                    element = new Element(label);
                }
                String label2 = matcher.group(2);
                Molecule molecule = getMolecule(label2);
                element.replacements.add(new Replacement(element, molecule));
//                log.info("--- {}", element.replacements);
                elementMap.put(label, element);
            } else if (line.trim().length() > 0) {
                target = getMolecule(line);

            }
        }

        new Decompose(target, elementMap);

    }

    private void addMolecule(Molecule target, Molecule start, int id, List<MoleculeRunnable> runnables) {
        List<Element> elements = start.elements;
        for (int i = 0; i < elements.size(); i++) {
            Element ele = elements.get(i);
            for (Replacement replacement : ele.replacements) {
                Molecule clone = start.clone();
                clone.replaceElement(i, replacement.output);

                MoleculeRunnable runnable = new MoleculeRunnable(id++, elementMap, distinct, replacement.output, target, 2);
                (new Thread(runnable)).start();
                runnables.add(runnable);
            }
        }
    }


    private Molecule getMolecule(String label2) {
        Pattern p2 = Pattern.compile("([A-Z][a-z]?)");
        Matcher matcher2 = p2.matcher(label2);
        Molecule molecule = new Molecule();
        while (matcher2.find()) {
            String label = matcher2.group(1);
            Element output = elementMap.get(label);
            if (output == null) {
                output = new Element(label);
            }
            molecule.elements.add(output);
            elementMap.put(output.label, output);
        }
        molecule.hash();
        return molecule;
    }
}

