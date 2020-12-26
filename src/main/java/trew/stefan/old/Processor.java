package trew.stefan.old;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.FabricClaim;
import trew.stefan.utils.InputReader2019;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Processor {


    public Processor() {
        FabricClaimCheck();
    }

    public void FabricClaimCheck() {
        List<FabricClaim> claims = InputReader2019.readClaims(3, "");
        log.info("{}", claims);
        int[][] map = new int[1000][1000];
        int counter = 0;

        for (FabricClaim claim : claims) {
            for (int x = 0; x < claim.getWidth(); x++) {
                for (int y = 0; y < claim.getHeight(); y++) {
                    map[claim.getX() + x][claim.getY() + y]++;
                    if (map[claim.getX() + x][claim.getY() + y] == 2) {
                        counter++;
                    }
                }
            }
        }

        for (FabricClaim claim : claims) {
            boolean flag = false;
            for (int x = 0; x < claim.getWidth(); x++) {
                for (int y = 0; y < claim.getHeight(); y++) {
                    if (map[claim.getX() + x][claim.getY() + y] != 1) {
                        flag = true;
                    }
                }
            }
            if (!flag)
                log.info("ID {}", claim.getId());
        }
        for (int[] line : map) {
//            log.info("{}", line);
        }
        log.info("Result {}", counter);
    }

    public void checkSum2() {
        List<String> lines = InputReader2019.readStrings(2, "");
        log.info("{}", lines);
        for (String left : lines) {
            for (String right : lines) {
                if (left.equals(right)) {
                    continue;
                }
                int diff = 0;
                String common = "";
                for (int x = 0; x < left.length(); x++) {
                    if (right.charAt(x) != left.charAt(x)) {
                        diff++;
                    } else {
                        common += right.charAt(x);
                    }
                }

                if (diff == 1) {
                    log.info(common);

                }
            }
        }
    }

    public void checkSum() {
        List<String> lines = InputReader2019.readStrings(2, "");
        log.info("{}", lines);
        int counter_2 = 0;
        int counter_3 = 0;
        for (String id : lines) {
            HashMap<Character, Integer> counter = new HashMap<>();

            for (char c : id.toCharArray()) {
                int temp = counter.getOrDefault(c, 0);
                counter.put(c, temp + 1);
            }

            log.info("{} {}", id, counter);
            if (counter.containsValue(2)) {
                counter_2++;
            }
            if (counter.containsValue(3)) {
                counter_3++;
            }
        }
        log.info("has 2 {}; has 3 {}; checksum {}", counter_2, counter_3, counter_2 * counter_3);
    }

    public void frequencyAnalysis() {

        Set<Integer> passed = new HashSet<>();
        List<String> lines = InputReader2019.readStrings(1, "");
        log.info("{}", lines);
        List<Integer> numbers = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
        int total = 0;
        while (true) {

            for (int number : numbers) {
                total += number;
                if (passed.contains(total)) {
                    log.info("{}", total);
                    return;
                }
                passed.add(total);
            }
        }
    }
}
