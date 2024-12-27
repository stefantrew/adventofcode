package trew.stefan.aoc2024;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
public class Day17 extends AbstractAOC {

    @ToString
    static class Machine {

        Long aX;
        Long aY;
        Long bX;
        Long bY;
        Long tX;
        Long tY;


    }

    @Override
    public String runPart1() {

        var list = getStringInput("");

        var register = new HashMap<Character, Long>();
        var program = new ArrayList<Integer>();
        for (var s : list) {
            if (s.isBlank()) {
                continue;
            }

            var s1 = s.split(": ")[1];
            if (s.contains("Register")) {
                var value = Long.parseLong(s1);
                var key = s.charAt(9);
                register.put(key, value);
            } else if (s.contains("Program")) {
                Arrays.stream(s1.split(",")).mapToInt(Integer::parseInt).forEach(program::add);
            }
        }
        var result = process(register, program, false);
        return result.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    List<Integer> process(Map<Character, Long> register, List<Integer> program, boolean flag) {
        var index = 0;
        var result = new ArrayList<Integer>();
        var counter = 0;

        while (index < program.size()) {
            if (counter++ > 5000) {
                log.info("Breaking due to counter");
                break;
            }

            var instruction = program.get(index);
            var literalOperand = program.get(index + 1);
            var comboOperand = switch (literalOperand) {
                case 4 -> register.get('A');
                case 5 -> register.get('B');
                case 6 -> register.get('C');
                default -> literalOperand;
            };

            var a = register.get('A');
            var b = register.get('B');
            var c = register.get('C');

            switch (instruction) {
                case 0: //adv
                    var denominate = Math.pow(2, comboOperand);
                    register.put('A', (long) (a / denominate));
                    index += 2;
                    break;
                case 1: //bxl
                    register.put('B', (long) (b.intValue() ^ literalOperand));
                    index += 2;
                    break;
                case 2: //bst
                    register.put('B', comboOperand % 8L);
                    index += 2;
                    break;
                case 3: //jnz
                    if (a != 0) {
                        index = literalOperand;
                    } else {
                        index += 2;
                    }
                    break;
                case 4: //bxc
                    register.put('B', b ^ c);
                    index += 2;
                    break;
                case 5: //out
                    var ind = result.size();
                    result.add((int) (comboOperand % 8L));
                    if (flag) {
                        if (result.size() > program.size()) {
                            return null;
                        }
                        if (!Objects.equals(result.get(ind), program.get(ind))) {
                            return null;
                        }
                    }
                    index += 2;
                    break;
                case 6: //bdv
                    var denominate2 = Math.pow(2, comboOperand);
                    register.put('B', (long) (a / denominate2));
                    index += 2;
                    break;
                case 7: //cdz
                    var denominate3 = Math.pow(2, comboOperand);
                    register.put('C', (long) (a / denominate3));
                    index += 2;
                    break;
            }

        }
        if (flag) {
            return result.size() == program.size() ? result : null;
        }
        return result;
    }

    @Override
    public String runPart2() {

        var list = getStringInput("");

        var register = new HashMap<Character, Long>();
        var program = new ArrayList<Integer>();
        var programStr = "";
        for (var s : list) {
            if (s.isBlank()) {
                continue;
            }

            var s1 = s.split(": ")[1];
            if (s.contains("Register")) {
                var value = Long.parseLong(s1);
                var key = s.charAt(9);
                register.put(key, value);
            } else if (s.contains("Program")) {
                programStr = s1;
                Arrays.stream(s1.split(",")).mapToInt(Integer::parseInt).forEach(program::add);
            }
        }

        var a = 35189490000011L;//
//        var a = 120002950000001L;//
        String last = "";
//        var a = 119050000000L;//
//                110001010000001
        while (a < 1000000000000000L) {
//                  119050000000
            if (a % 10000000 == 11) {
                log.info("a: {} {} {} {}", a, last, last.length(), programStr.length());
            }

            register.put('A', a);
            register.put('B', 0L);
            register.put('C', 0L);

            var result = process(register, program, true);
            if (result != null) {
                var str = result.stream().map(String::valueOf).collect(Collectors.joining(","));
                last = str;
                if (str.startsWith("2,4,1,1,7,5,1,5")) {

                    log.info("str: {} {}       {} ", a, programStr, str);
                }
                if (str.equals(programStr)) {
                    log.info("Found match for a: {}", a);
                    break;
                }
            }
            a++;
        }

        return a + "";
    }

    @Override
    public String getAnswerPart1() {
        return "1546338";
    }

    @Override
    public String getAnswerPart2() {
        return "978590";
    }
    //978590 too low
}
