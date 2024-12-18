package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        var total = 0;

        final List<Integer> left = new ArrayList<Integer>();
        final List<Integer> right = new ArrayList<Integer>();

        list.forEach(s -> {
            ;
            var parts = s.split("   ");
            left.add(Integer.valueOf(parts[0]));
            right.add(Integer.valueOf(parts[1]));
        });


        var left2 = left.stream().sorted().toList();
        var right2 = right.stream().sorted().toList();

        for (var i = 0; i < left.size(); i++) {
            total += Math.abs(left2.get(i) - right2.get(i));
        }

        return String.valueOf(total);
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");
        var total = 0L;

        final List<Integer> left = new ArrayList<Integer>();
        final List<Integer> right = new ArrayList<Integer>();

        list.forEach(s -> {
            ;
            var parts = s.split("   ");
            left.add(Integer.valueOf(parts[0]));
            right.add(Integer.valueOf(parts[1]));
        });



        for (var i = 0; i < left.size(); i++) {
            var digit = left.get(i);
            total +=  digit *  right.stream().filter(r -> Objects.equals(r, digit)).count();
        }

        return String.valueOf(total);
    }


    @Override
    public String getAnswerPart1() {
        return "1941353";
    }

    @Override
    public String getAnswerPart2() {
        return "22539317";
    }
}
