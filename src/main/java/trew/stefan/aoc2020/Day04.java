package trew.stefan.aoc2020;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
public class Day04 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");

        var current = new HashMap<String, String>();
        for (String s : list) {

            if (s.isEmpty()) {
                var b = validate(current);
                if (b) {
                    total++;
                }
                current = new HashMap<>();
            }

            var p = Pattern.compile("(\\w{3}):([^\\s]*)");
            var m = new AOCMatcher(p.matcher(s));

            while (m.find()) {
                current.put(m.group(1), m.group(2));

            }
        }
        if (!current.isEmpty() && validate(current)) {
            total++;
        }
        return String.valueOf(total);
    }

    private boolean validate(HashMap<String, String> current) {
        HashSet<String> set = getSet();

        for (Map.Entry<String, String> entry : current.entrySet()) {
            set.remove(entry.getKey());
        }
        return set.isEmpty();
    }

    private HashSet<String> getSet() {
        var set = new HashSet<String>();
        set.add("byr");
        set.add("iyr");
        set.add("eyr");
        set.add("hgt");
        set.add("hcl");
        set.add("ecl");
        set.add("pid");
        return set;
    }

    private boolean validate2(HashMap<String, String> current) {

        HashSet<String> set = getSet();

        for (Map.Entry<String, String> entry : current.entrySet()) {
            String value = entry.getValue();
            set.remove(entry.getKey());
            switch (entry.getKey()) {

                case "byr":
                    var value2 = Integer.parseInt(value);
                    if (value2 < 1920 || value2 > 2002) {
                        return false;
                    }
                    break;
                case "iyr":
                    var value3 = Integer.parseInt(value);
                    if (value3 < 2010 || value3 > 2020) {
                        return false;
                    }
                    break;
                case "eyr":
                    var value4 = Integer.parseInt(value);
                    if (value4 < 2020 || value4 > 2030) {
                        return false;
                    }
                    break;
                case "hgt":
                    var p = Pattern.compile("(\\d*)(cm|in)");
                    var m = new AOCMatcher(p.matcher(value));
                    if (m.find()) {
                        var unit = m.getString(2);
                        var val = m.getInt(1);
                        if (unit.equals("cm")) {
                            if (val < 150 || val > 193) {
                                return false;
                            }
                        } else if (unit.equals("in")) {
                            if (val < 59 || val > 76) {
                                return false;
                            }
                        } else {

                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                case "hcl":
                    var p2 = Pattern.compile("^#[\\da-f]{6}$");
                    var m2 = new AOCMatcher(p2.matcher(value));
                    if (!m2.find()) {

                        return false;
                    }
                    break;
                case "ecl":

                    var p3 = Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$");
                    var m3 = new AOCMatcher(p3.matcher(value));
                    if (!m3.find()) {
                        return false;
                    }
                    break;
                case "pid":
                    var p4 = Pattern.compile("^\\d{9}$");
                    var m4 = new AOCMatcher(p4.matcher(value));
                    if (!m4.find()) {
                        return false;
                    }
                    break;
                case "cid":
                    break;
            }
        }
        return set.isEmpty();
    }

    @Override
    public String runPart2() {


        var total = 0;

        var list = getStringInput("");

        var current = new HashMap<String, String>();
        for (String s : list) {

            if (s.isEmpty()) {
                if (validate2(current)) {
                    total++;
                }
                current = new HashMap<>();
            }

            var p = Pattern.compile("(\\w{3}):([^\\s]*)");
            var m = new AOCMatcher(p.matcher(s));

            while (m.find()) {
                current.put(m.group(1), m.group(2));

            }
        }
        if (!current.isEmpty() && validate2(current)) {
            total++;
        }
        return String.valueOf(total);
    }

    @Override
    public String getAnswerPart1() {
        return "182";
    }

    @Override
    public String getAnswerPart2() {
        return "109";
    }
}
