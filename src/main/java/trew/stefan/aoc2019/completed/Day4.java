package trew.stefan.aoc2019.completed;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.List;

@Slf4j
public class Day4 implements Day {

    private boolean isValid(String number) {

        boolean hasDouble = false;
        for (int i = 0; i < 5; i++) {
            if (number.charAt(i) > number.charAt(i + 1)) {
                return false;
            }
            if (number.charAt(i) == number.charAt(i + 1)) {
                if (i < 4 && number.charAt(i + 2) == number.charAt(i)) continue;
                if (i > 0 && number.charAt(i - 1) == number.charAt(i)) continue;
                hasDouble = true;
            }
        }

        return hasDouble;
    }

    public void run() {
        List<String> lines = InputReader2019.readStrings(4, "");
        log.info("{}", isValid("112233"));
        log.info("{}", isValid("123444"));
        log.info("{}", isValid("111122"));
        int counter = 0;
        for (int i = 156218; i < 652527; i++) {
            if (isValid(Integer.toString(i))) counter++;
        }
        log.info("{}", counter);
    }
}
