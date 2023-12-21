package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Day15 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");

        for (String s : list) {
            var parts = s.split(",");

            for (String part : parts) {
                total += computeHash(part, 0);
            }
        }


        return formatResult(total);
    }

    private static int computeHash(String str, int start) {
        for (int i = 0; i < str.toCharArray().length; i++) {
            var intVal = str.toCharArray()[i];

            start += intVal;
            start *= 17;
            start %= 256;
        }
        return start;
    }

    class Lens {
        String label;
        int focal;

        public Lens(String label, int focal) {
            this.label = label;
            this.focal = focal;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Lens lens = (Lens) o;
            return Objects.equals(label, lens.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }

        @Override
        public String toString() {
            return label + " " + focal;
        }
    }

    class Box {

        Integer id;
        List<Lens> parts = new ArrayList<>();

        public Box(Integer id) {
            this.id = id;
        }

        public void removeLens(Lens part) {
            parts.remove(part);

        }

        public void addLens(Lens part) {
            if (parts.contains(part)) {
                var index = parts.indexOf(part);
                parts.get(index).focal = part.focal;
            } else {

                parts.add(part);
            }
        }

        public int getPower() {
            var power = 0;
            for (int i = 0; i < parts.size(); i++) {
                var power1 = (id + 1) * ((i + 1) * parts.get(i).focal);
                power += power1;
            }
            return power;
        }
    }

    @Override
    public String runPart2() {


        var total = 0;

        var list = getStringInput("");

        var boxes = new Box[256];
        for (int i = 0; i < 256; i++) {
            boxes[i] = new Box(i);
        }
        for (String s : list) {
            var parts = s.split(",");

            for (String part : parts) {
                var index = part.indexOf("-");
                if (index == -1) {
                    index = part.indexOf("=");
                }

                var label = part.substring(0, index);
                var action = part.substring(index, index + 1);
                var focal = 0;
                if (!action.equals("-")) {
                    focal = Integer.parseInt(part.substring(index + 1));
                }
                var box = computeHash(label, 0);
                var lens = new Lens(label, focal);
                if (action.equals("-")) {
                    boxes[box].removeLens(lens);
                } else {
                    boxes[box].addLens(lens);
                }

            }
        }

        for (int i = 0; i < 256; i++) {
            total += boxes[i].getPower();
        }
        return formatResult(total);
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
