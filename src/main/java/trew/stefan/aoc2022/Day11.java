package trew.stefan.aoc2022;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.NumberList;

import java.math.BigDecimal;
import java.util.*;


@Slf4j
public class Day11 extends AbstractAOC {

    @ToString
    class Monkey {

        int id;
        List<BigDecimal> items = new ArrayList<>();

        char operator;

        Integer value;

        Integer test;

        Integer targetFalse;

        Integer targetTrue;

        int inspection = 0;

    }


    public static ArrayList<BigDecimal> readSplitIntegers(String list) {
        var list2 = Arrays.stream(list.split(", ")).map(Long::parseLong).map(BigDecimal::valueOf).toList();
        return new ArrayList<>(list2);
    }

    private void takeTurn(Monkey monkey, HashMap<Integer, Monkey> monkeys, BigDecimal modulus) {

        for (var item : monkey.items) {
            monkey.inspection++;
            var value = monkey.value == null ? item : BigDecimal.valueOf(monkey.value);
            item = switch (monkey.operator) {
                case '*' -> item.multiply(value);
                case '+' -> item.add(value);
                case default -> throw new IllegalArgumentException("Not supported");
            };
            if (modulus != null) {
                item = item.remainder(modulus);
            } else {
                item = item.divideToIntegralValue(BigDecimal.valueOf(3));
            }
            var rem = item.remainder(BigDecimal.valueOf(monkey.test));
            if (rem.equals(BigDecimal.ZERO)) {
                monkeys.get(monkey.targetTrue).items.add(item);
            } else {
                monkeys.get(monkey.targetFalse).items.add(item);
            }

        }
        monkey.items.clear();
    }

    private void doRound(HashMap<Integer, Monkey> monkeys, BigDecimal modulus) {

        monkeys.forEach((integer, monkey) -> {
            takeTurn(monkey, monkeys, modulus);
        });

    }

    @Override
    public String runPart1() {

        HashMap<Integer, Monkey> monkeys = getMonkeys();

        for (int i = 0; i < 20; i++) {
            doRound(monkeys, null);

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
            monkey.test = Integer.parseInt(queue.pop().substring(21));
            monkey.targetTrue = Integer.parseInt(queue.pop().substring(29));
            monkey.targetFalse = Integer.parseInt(queue.pop().substring(30));
            if (!queue.isEmpty()) {
                queue.pop();
            }
            monkeys.put(monkey.id, monkey);
        }
        return monkeys;
    }


    @Override
    public String runPart2() {
        HashMap<Integer, Monkey> monkeys = getMonkeys();

        var modulus = BigDecimal.ONE;
        for (Map.Entry<Integer, Monkey> entry : monkeys.entrySet()) {
            Monkey value = entry.getValue();
            modulus = modulus.multiply(BigDecimal.valueOf(value.test));
        }


        for (int i = 0; i < 10000; i++) {
            doRound(monkeys, modulus);
        }
        return getResult(monkeys);
    }

    private static String getResult(HashMap<Integer, Monkey> monkeys) {
        var totals = new NumberList();
        monkeys.forEach((integer, monkey) -> {
            totals.add((long) monkey.inspection);
        });

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
