package trew.stefan.aoc2018;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

@Slf4j
public class Day05 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getStringInput();
        var str = list.get(0);

        return String.valueOf(process2(str).length());
    }

    private String process2(String str) {

        var temp = new StringBuilder();

        var chunk = 50000 / 5;
        while (str.length() > 0) {
            var substr = "";
            if (str.length() > chunk) {
                substr = str.substring(0, chunk);
                str = str.substring(chunk);
            } else {
                substr = str;
                str = "";
            }

            temp.append(process(substr));
        }
//        return temp.toString();
        return process(temp.toString());

    }


    private String process(String str) {
        final String regex = "(?=([a-zA-Z]))(.(?!\\1)(?i:\\1))";

        var pre2 = str.length();
        while (true) {
            var pre = str.length();
            str = str.replaceAll(regex, "");
            if (pre == str.length()) {
                break;
            }
        }
//        log.info("{} {}", pre2, str.length());
        return str;
    }

    @Override
    public String runPart2() {


//        var input = getInput().get(0);
//
//        Integer min = null;
//        for (char c = 'a'; c <= 'z'; c++) {
//
//            var pattern = Pattern.compile(String.valueOf(c), Pattern.CASE_INSENSITIVE);
//
//            var matcher = pattern.matcher(input);
//
//            // The substituted value will be contained in the result variable
//            String result = matcher.replaceAll("");
//
//
//            result = process2(result);
//            var length = result.length();
//            if (min == null || length < min) {
//                min = length;
//            }
//            log.info("{} {} {} {}", c, result.length(), input.length(), length);
//        }
//        return String.valueOf(min);
        return "";
    }

    @Override
    public String getAnswerPart1() {
        return "10774";
    }

    @Override
    public String getAnswerPart2() {
        return "5122";
    }
}
