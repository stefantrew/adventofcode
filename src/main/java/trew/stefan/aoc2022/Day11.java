package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.NumberList;

import java.util.*;


@Slf4j
public class Day11 extends AbstractAOC {

    static class Monkey {

        int id;
        List<Long> items = new ArrayList<>();

        char operator;

        Integer value;

        Double test;

        Integer targetFalse;

        Integer targetTrue;

        int inspection = 0;

        Monkey targetTrueMonkey;
        Monkey targetFalseMonkey;


    }


    public static ArrayList<Long> readSplitIntegers(String list) {
        var list2 = Arrays.stream(list.split(", ")).map(Long::parseLong).toList();
        return new ArrayList<>(list2);
    }

    private void takeTurn(Monkey monkey, HashMap<Integer, Monkey> monkeys, Long modulus) {

        for (var item : monkey.items) {
            monkey.inspection++;
            var value = monkey.value == null ? item : monkey.value;
            item = switch (monkey.operator) {
                case '*' -> item * value;
                case '+' -> item + value;
                case default -> throw new IllegalArgumentException("Not supported");
            };
            if (modulus != null) {
                item = item % modulus;
            } else {
                item /= 3;
            }
            var rem = item / monkey.test;
            if (rem == Math.floor(rem)) {
                monkey.targetTrueMonkey.items.add(item);
            } else {
                monkey.targetFalseMonkey.items.add(item);
            }

        }
        monkey.items.clear();
    }

    @Override
    public String runPart1() {

        HashMap<Integer, Monkey> monkeys = getMonkeys();

        for (int i = 0; i < 20; i++) {
            monkeys.forEach((integer, monkey) -> takeTurn(monkey, monkeys, null));

        }

        return getResult(monkeys);
    }

    private HashMap<Integer, Monkey> getMonkeys() {
        var input = getStringInput("");
        var queue = new LinkedList<>(input);

        var monkeys = new HashMap<Integer, Monkey>();
        while (!queue.isEmpty()) {

            var monkey = new Monkey();

            monkey.id = Integer.parseInt(queue.pop().substring(7, 8));
            monkey.items = readSplitIntegers(queue.pop().substring(18));
            var operation = queue.pop();
            monkey.operator = operation.charAt(23);

            var value = operation.substring(25);
            if (value.equals("old")) {
                monkey.value = null;
            } else {
                monkey.value = Integer.parseInt(value);
            }
            monkey.test = Double.parseDouble(queue.pop().substring(21));
            monkey.targetTrue = Integer.parseInt(queue.pop().substring(29));
            monkey.targetFalse = Integer.parseInt(queue.pop().substring(30));
            if (!queue.isEmpty()) {
                queue.pop();
            }
            monkeys.put(monkey.id, monkey);
        }

        monkeys.forEach((integer, monkey) -> {
            monkey.targetFalseMonkey = monkeys.get(monkey.targetFalse);
            monkey.targetTrueMonkey = monkeys.get(monkey.targetTrue);

        });
        return monkeys;
    }


    @Override
    public String runPart2() {
        HashMap<Integer, Monkey> monkeys = getMonkeys();

        final long modulus = getModulus(monkeys);
        for (int i = 0; i < 10000; i++) {
            monkeys.forEach((integer, monkey) -> takeTurn(monkey, monkeys, modulus));
        }
        return getResult(monkeys);
    }

    private static long getModulus(HashMap<Integer, Monkey> monkeys) {
        var modulus = 1L;
        for (Map.Entry<Integer, Monkey> entry : monkeys.entrySet()) {
            Monkey value = entry.getValue();
            modulus *= value.test;
        }
        return modulus;
    }

    private static String getResult(HashMap<Integer, Monkey> monkeys) {
        var totals = new NumberList();
        monkeys.forEach((integer, monkey) -> totals.add((long) monkey.inspection));

        return String.valueOf(totals.reverseSort().take(2).multiply());
    }


    @Override
    public String getAnswerPart1() {
        return "99852";
    }

    @Override
    public String getAnswerPart2() {
        return "25935263541";
    }
}
