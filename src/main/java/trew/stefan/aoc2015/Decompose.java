package trew.stefan.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Decompose {

    Molecule target;
    Map<String, Element> elementMap;
    HashMap<String, String> replacements = new HashMap<>();
    List<Pattern> regexPatterns = new ArrayList<>();
    List<String> known = new ArrayList<>();


    public Decompose(Molecule target, Map<String, Element> elementMap) {
        this.target = target;
        this.elementMap = elementMap;


        for (Element element : elementMap.values()) {
            for (Replacement replacement : element.replacements) {
                replacements.put(replacement.output.str, element.label);

                regexPatterns.add(Pattern.compile("(" + replacement.output.str + ")"));
            }
        }

        log.info("Target {}", target);
        log.info("Target {}", replacements);

        String input = target.str;

        log.info("final {}", decompose(input));

    }

    private Integer decompose(String input) {

        if (known.contains(input)) {
            return null;
        }

        if (input.equals("e")) {
            return 0;
        }

        for (Pattern regexPattern : regexPatterns) {
            Matcher matcher = regexPattern.matcher(input);

            while (matcher.find()) {
                String label = matcher.group(1);
                int position = matcher.start();
                String rep = replacements.get(label);
                if (rep.equals("e") && !input.equals(label)) {
//                    log.info("{} {} {}", input, label, rep);
                    continue;
                }
                String newString = "";
                if (position > 0) {
                    newString = input.substring(0, position);
                }

                newString += rep;

                if (position + label.length() < input.length()) {
                    newString += input.substring(position + label.length());
                }

//                log.info("Found {} @ {} => {}", label, position, newString);

                if (newString.length() > 0) {
                    Integer result = decompose(newString);
                    if (result != null) {
                        return result + 1;
                    }
                }
            }

        }

        known.add(input);
        return null;
    }


}
