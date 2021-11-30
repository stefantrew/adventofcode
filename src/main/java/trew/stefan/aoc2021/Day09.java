package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
public class Day09 extends AbstractAOC {

    class Marble {
        Marble prev;
        Marble next;

        long value;

        boolean isCurrent;


        Marble getPrev(int distance) {
            if (distance == 1) {
                return prev;
            }
            return prev.getPrev(distance - 1);
        }

        public void setNext(Marble marble) {
            marble.next = this.next;
            this.next = marble;
            marble.prev = this;
            marble.next.prev = marble;
        }

        public Marble addMarble(int currentValue) {

            var newMarble = new Marble();
            newMarble.value = currentValue;

            this.next.setNext(newMarble);
            newMarble.isCurrent = true;
            this.isCurrent = false;
            return newMarble;
        }
    }

    @Override
    public String runPart1() {
        return run(493, 71863);
    }

    public String run(int players, long lastMarble) {

        int currentValue = 0;
        int currentPlayer = -1;

        var zero = new Marble();
        zero.value = currentValue;
        zero.setNext(zero);
        zero.isCurrent = true;

        var current = zero;
        var scores = new HashMap<Integer, Long>();

        while (currentValue < lastMarble) {


            currentValue++;
            currentPlayer++;
            currentPlayer %= players;

            if (currentValue % 23 == 0) {
                var temp = current.getPrev(7);

                current.isCurrent = false;
                current = temp.next;
                current.isCurrent = true;

                int finalCurrentValue = currentValue;
                scores.putIfAbsent(currentPlayer, 0L);
                scores.compute(currentPlayer, (index, value) -> value + temp.value + finalCurrentValue);

                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
            } else {
                current = current.addMarble(currentValue);

            }
        }
        Optional<Long> max = scores.values().stream().max(Comparator.naturalOrder());

        return String.valueOf(max.get());
    }

    void print(Marble marble) {

        var sb = new StringBuilder();
        var current = marble;
        while (true) {
            if (current.isCurrent) {

                sb.append("(" + current.value + ") ");
            } else {

                sb.append(current.value + " ");
            }

            if (current.next.value == 0) {
                break;
            }
            current = current.next;
        }

        log.info(sb.toString());
    }

    @Override
    public String runPart2() {


        return run(493, 7186300);
    }

    @Override
    public String getAnswerPart1() {
        return "367802";
    }

    @Override
    public String getAnswerPart2() {
        return "2996043280";
    }
}
