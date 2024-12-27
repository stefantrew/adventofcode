package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Slf4j
public class Day22 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0L;
        var list = getStringInput("");

        for (var string : list) {

            var input = Long.parseLong(string);

            for (int i = 0; i < 2000; i++) {

                input = nextSecret(input);
            }
            total += input;

        }

        return formatResult(total);
    }

    private long nextSecret(long input) {
        var secret = input * 64L;
        secret = input ^ secret;
        input = prune(secret);


        secret = input / 32L;
        secret = input ^ secret;
        input = prune(secret);

        secret = input * 2048L;
        secret = input ^ secret;
        input = prune(secret);
        return input;
    }

    long prune(long input) {
        return input % 16777216;
    }


    @Override
    public String runPart2() {
//        1419
        // 1450
        //1442

        var list = getStringInput("");
        var cache = new HashMap<String, Long>();
        var total = 0L;

        for (var string : list) {

            var input = Long.parseLong(string);


            var last = ((int) input) % 10;
            var sequence = new ArrayList<Integer>();

            var localCache = new HashSet<String>();
            for (int i = 0; i < 2000; i++) {


                input = nextSecret(input);
                var current = ((int) input) % 10;
                sequence.add(current - last);
                if (sequence.size() == 5) {

                    sequence.remove(0);
                }
                if (sequence.size() == 4) {
                    var key = sequence.get(0) + "_" + sequence.get(1) + "_" + sequence.get(2) + "_" + sequence.get(3);
//                    log.info("{}", key);
                    if (!localCache.contains(key)) {


                        localCache.add(key);
                        var value = cache.getOrDefault(key, 0L);
                        value += current;
                        cache.put(key, value);
                    }
                }
//                log.info("secret {} {} {} ", input, current, sequence);
                last = current;
            }


        }
        var best = 0L;
        var bestKey = "";
        for (var entry : cache.entrySet()) {

            log.info("cache {} {} ", entry.getKey(), entry.getValue());

            if (entry.getValue() > best) {
                best = entry.getValue();
                bestKey = entry.getKey();
            }
        }
        log.info("cache {} {} {}", bestKey, best, cache);

        return formatResult(best);
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
