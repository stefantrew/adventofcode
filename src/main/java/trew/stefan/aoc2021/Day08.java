package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

@Slf4j
public class Day08 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


//        var list = getStringInput().stream().map(this::mapper).collect(Collectors.toList());

        var list = getStringInput("");
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        for (var s : list) {
            Character top = null;
            Character bottom = null;
            Character mid = null;
            Character upperLeft = null;
            Character lowerLeft = null;
            Character upperRight = null;
            Character lowerRight = null;

            var topCandidates = new HashSet<Character>();
            var bottomCandidates = new HashSet<Character>();
            var midCandidates = new HashSet<Character>();
            var upperLeftCandidates = new HashSet<Character>();
            var lowerLeftCandidates = new HashSet<Character>();
            var upperRightCandidates = new HashSet<Character>();
            var lowerRightCandidates = new HashSet<Character>();
            var parts = s.split("\\|");
            var patterns = parts[0].split("\\s");
            var numbers = parts[1].split("\\s");


//            log.info("{} {}", parts[0], patterns);
            var one = "";
            var seven = "";
            var four = "";
            var fives = new ArrayList<String>();
            var sixes = new ArrayList<String>();
            for (String pattern : patterns) {
                if (pattern.length() == 2) {
                    one = pattern;
                } else if (pattern.length() == 3) {
                    seven = pattern;
                } else if (pattern.length() == 4) {
                    four = pattern;
                } else if (pattern.length() == 5) {
                    fives.add(pattern);
                } else if (pattern.length() == 6) {
                    sixes.add(pattern);
                }
            }
//            log.info("1:{}, 7:{}", one, seven);

            for (var c : seven.toCharArray()) {
                if (one.contains(String.valueOf(c))) {
                    lowerRightCandidates.add(c);
                    upperRightCandidates.add(c);
                } else {
                    top = c;
                }
            }

            var f1 = fives.get(0);
            var f2 = fives.get(1);
            var f3 = fives.get(2);
            for (var c : f1.toCharArray()) {
                if (c == top) {
                    continue;
                }
                if (f2.contains(String.valueOf(c)) && f3.contains(String.valueOf(c))) {
                    midCandidates.add(c);
                    bottomCandidates.add(c);
                }
            }

            var last = "";
            for (Character midCandidate : midCandidates) {
                if (four.contains(String.valueOf(midCandidate))) {
                    mid = midCandidate;
                    bottomCandidates.remove(mid);
                    bottom = bottomCandidates.stream().findFirst().get();
                }
            }

            for (String s1 : sixes) {
                if (!s1.contains(String.valueOf(mid))) {
                    //0
                    for (char c : s1.toCharArray()) {
                        if (!upperRightCandidates.contains(c) && c != top && c != mid && c != bottom) {
                            lowerLeftCandidates.add(c);
                            upperLeftCandidates.add(c);
                        }
                    }
                } else if (s1.contains(String.valueOf(one.charAt(0))) && s1.contains(String.valueOf(one.charAt(1)))) {
                    //9
                    if (lowerLeft != null) {
                        continue;
                    }
                    for (char c : s1.toCharArray()) {
                        if (upperLeftCandidates.contains(c)) {
                            upperLeft = c;
                            lowerLeftCandidates.remove(c);
                            lowerLeft = lowerLeftCandidates.stream().findFirst().get();
                            break;
                        }
                    }
                } else {
                    last = s1;
                }
            }
            for (String s1 : sixes) {
                if (!s1.contains(String.valueOf(mid))) {
                    //0
                    for (char c : s1.toCharArray()) {
                        if (!upperRightCandidates.contains(c) && c != top && c != mid && c != bottom) {
                            lowerLeftCandidates.add(c);
                            upperLeftCandidates.add(c);
                        }
                    }
                } else if (s1.contains(String.valueOf(one.charAt(0))) && s1.contains(String.valueOf(one.charAt(1)))) {
                    //9
                    if (lowerLeft != null) {
                        continue;
                    }
                    for (char c : s1.toCharArray()) {
                        if (upperLeftCandidates.contains(c)) {
                            upperLeft = c;
                            lowerLeftCandidates.remove(c);
                            lowerLeft = lowerLeftCandidates.stream().findFirst().get();
                            break;
                        }
                    }
                } else {
                    last = s1;
                }
            }
            if (last.contains(String.valueOf(upperLeft))) {
                for (char c : last.toCharArray()) {
                    if (lowerRightCandidates.contains(c)) {
                        lowerRight = c;
                        upperRightCandidates.remove(c);
                        upperRight = upperRightCandidates.stream().findFirst().get();
                        break;
                    }
                }

            }
//
//            log.info("topCandidates {}", topCandidates);
//            log.info("bottomCandidates {}", bottomCandidates);
//            log.info("midCandidates {}", midCandidates);
//            log.info("upperLeftCandidates {}", upperLeftCandidates);
//            log.info("lowerLeftCandidates {}", lowerLeftCandidates);
//            log.info("upperRightCandidates {}", upperRightCandidates);
//            log.info("lowerRightCandidates {}", lowerRightCandidates);
//
//            log.info("top {}", top);
//            log.info("bottom {}", bottom);
//            log.info("mid {}", mid);
//            log.info("upperLeft {}", upperLeft);
//            log.info("lowerLeft {}", lowerLeft);
//            log.info("upperRight {}", upperRight);
//            log.info("lowerRight {}", lowerRight);

//            for (String digit : numbers) {
//                if (digit.length() == 2) {
//                    total++;
//                } else if (digit.length() == 3) {
//                    total++;
//                } else if (digit.length() == 4) {
//                    total++;
//                } else if (digit.length() == 7) {
//                    total++;
//                }
//            }

            var digits = "";
            for (String digit : numbers) {
                if (digit.length() == 2) {
                    digits += "1";
                } else if (digit.length() == 3) {
                    digits += "7";
                } else if (digit.length() == 4) {
                    digits += "4";
                } else if (digit.length() == 5) {
                    if (digit.contains(String.valueOf(lowerRight)) && digit.contains(String.valueOf(upperRight))) {
                        digits += "3";
                    } else if (digit.contains(String.valueOf(lowerRight))) {
                        digits += "5";
                    } else {
                        digits += "2";

                    }
                } else if (digit.length() == 6) {

                    if (!digit.contains(String.valueOf(mid))) {
                        digits += "0";
                    } else if (digit.contains(String.valueOf(upperRight))) {
                        digits += "9";
                    } else{

                        digits += "6";
                    }
                } else if (digit.length() == 7) {
                    digits += "8";
                }
            }
            log.info("^^^^^{}", digits);
            total += Integer.parseInt(digits);
        }


        return formatResult(total);
    }


    @AllArgsConstructor
    class Item {

    }

    Item mapper(String input) {

        var p = Pattern.compile("");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item();
        }
        return null;
    }


    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
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
