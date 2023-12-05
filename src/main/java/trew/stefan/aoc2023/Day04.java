package trew.stefan.aoc2023;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day04 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


//        var list = getStringInput().stream().map(this::mapper).toList();

        var list = getStringInput("");
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        for (var s : list) {
            var parts = s.split(":");
            var winning = parts[1].trim().split("\\|")[0].trim().split(" ");
            var numbers = parts[1].trim().split("\\|")[1].trim().split(" ");
            var winningSet = Arrays.stream(winning).collect(Collectors.toSet());
            winningSet.remove(" ");
            winningSet.remove("");
            var chosen = Arrays.stream(numbers).collect(Collectors.toSet());

            var count = chosen.stream().filter(winningSet::contains).count();
            if (count > 0) {

                total += Math.pow(2, count - 1);
            }
        }


        return formatResult(total);
    }


    @Override
    public String runPart2() {

        var total = 0;
        var result = "";


        var list = getStringInput("");
        total += list.size();


        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);

            total += compute(i, list);

        }

        return formatResult(total);
    }

    private HashMap<Long, Long> cache = new HashMap<>();

    private long compute(long index, List<String> list) {

        if (cache.containsKey(index)) {
            return cache.get(index);
        }

        var s = list.get(Math.toIntExact(index));
        var parts = s.split(":");
        var winning = parts[1].trim().split("\\|")[0].trim().split(" ");
        var numbers = parts[1].trim().split("\\|")[1].trim().split(" ");
        var winningSet = Arrays.stream(winning).collect(Collectors.toSet());
        winningSet.remove(" ");
        winningSet.remove("");
        var chosen = Arrays.stream(numbers).collect(Collectors.toSet());

        var count = chosen.stream().filter(winningSet::contains).count();
        var total = count;
        for (int i = 0; i < count; i++) {
            total += compute(index + i + 1, list);
        }

        cache.put(index, total);
        return total;
    }

    @Override
    public String getAnswerPart1() {
        return "21558";
    }

    @Override
    public String getAnswerPart2() {
        return "10425665";
    }
}
