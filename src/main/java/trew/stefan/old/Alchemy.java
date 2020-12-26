package trew.stefan.old;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.InputReader2019;

import java.util.List;

@Slf4j
public class Alchemy {

    private boolean matcher(char a, char b) {
        if (a == b) {
            return false;
        }

        return Character.toLowerCase(a) == Character.toLowerCase(b);
    }

    private String iterate(String input) {
        StringBuilder output = new StringBuilder();
        for (int x = 0; x < input.length(); x++) {


            if (x + 1 < input.length() && matcher(input.charAt(x), input.charAt(x + 1))) {
                x++;
                continue;
            }
            output.append(input.charAt(x));
        }

        return output.toString();
    }

    public void reduce() {
        String str = "dabAcCaCBAcCcaDA";

        List<String> lines = InputReader2019.readStrings(5, "");
        if (lines.size() > 0) {
            str = lines.get(0);
        }

        log.info(str);
        log.info("{}", str.length());
        Integer min = null;
        Character minC = null;

        for (char c = 'a'; c <= 'z'; c++) {

            String newStr = str.replaceAll("[" + c + Character.toUpperCase(c) + "]", "");
            int l = run(newStr);
            log.info("{} {}", c, l);
            if (min == null || l < min) {
                min = l;
                minC = c;
            }
        }
        log.info("{} {}", min, minC);

//        run(str);
    }

    private int run(String str) {
        while (true) {
//            10774

            String old = str;

            str = iterate(str);
//            log.info("{}", str.length());
            if (str.equals(old)) break;
        }
        return str.length();
    }
}
