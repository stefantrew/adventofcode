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

    List<Integer> process2(Map<Character, Long> register) {
        //2,4, 1,1 ,7,5, 1,5 ,0,3 ,4,3, 5,5 ,3,0
        //bst

        var result = new ArrayList<Integer>();
        while (true) {
//            log.info("=============================");
//            log.info("Start   A: {} B: {} C: {}", register.get('A'), register.get('B'), register.get('C'));

            register.put('B', register.get('A') % 8);
//            log.info("A mod 8 A: {} B: {} C: {}", register.get('A'), register.get('B'), register.get('C'));

            register.put('B', register.get('B') ^ 1);
//            log.info("B XOR 1 A: {} B: {} C: {}", register.get('A'), register.get('B'), register.get('C'));

            var denominate = Math.pow(2, register.get('B'));
            register.put('C', (long) (register.get('A') / denominate));
//            log.info("A/2^B   A: {} B: {} C: {}", register.get('A'), register.get('B'), register.get('C'));

            register.put('B', register.get('B') ^ 5);
//            log.info("B XOR 5 A: {} B: {} C: {}", register.get('A'), register.get('B'), register.get('C'));

            register.put('A', (long) (register.get('A') / 8));
//            log.info("A/8     A: {} B: {} C: {}", register.get('A'), register.get('B'), register.get('C'));

            register.put('B', register.get('B') ^ register.get('C'));
//            log.info("B^C     A: {} B: {} C: {}", register.get('A'), register.get('B'), register.get('C'));

            result.add((int) (register.get('B') % 8L));
//            log.info("Result  {}", register.get('B') % 8L);

            if (register.get('A') == 0) {
                break;
            }

        }


        return result;
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
        // 0 => 4
        // 1 => 5
        // 2 => 6
        // 3 => 7
        // 4 => 0
        //40

        var subs = new String[]{
                "000", "001", "010", "011", "100", "101", "110", "111"
        };
        var subs5 = new String[]{
                "001",
                "100",
        };
        var subs8 = new String[]{
                "110",
                "111",
        };

        var subs9 = new String[]{
                "010",
                "011",
                "111",
        };
        var subs10 = new String[]{
                "111",
                "010",
                "100"
        };
        var subs11 = new String[]{
                "110", "011", "100", "101"
        };

        var subs12 = new String[]{
                "111", "011", "100"
        };
        var subs13 = new String[]{
                "011", "110", "100", "010"
        };
        var subs14 = new String[]{
                "110", "000", "001", "111", "101", "010"
        };

        var input = new String[16];
        for (int i = 0; i < 16; i++) {
            input[i] = "100";
        }
        input[0] = "100";
        input[1] = "101";
        input[2] = "011";
        input[3] = "010";
        input[4] = "011";
        input[5] = "001";
        input[6] = "110";
        input[7] = "010";
        input[8] = "110";
        input[9] = "111";
        input[10] = "111";
        input[11] = "111";
        input[12] = "111";
        input[13] = "111";
        input[14] = "111";
        input[15] = "111";

        var test = 15;
        var items = new HashSet<>();
        Long lowest = null;


        for (var sub15 : subs) {
            for (var sub14 : subs14) {
                for (var sub13 : subs13) {
                    for (var sub12 : subs12) {
                        for (var sub11 : subs11) {
                            for (var sub10 : subs10) {
                                for (var sub9 : subs9) {
                                    for (var sub8 : subs8) {
                                        for (var sub1 : subs5) {
                                            for (var sub : subs) {

//            var a = Long.valueOf("100" + "101" + "011" + "010011" + "001" + "110" + "010" + "110" + "111" + "100" + "011" + "011" + "011" + "001" + sub, 2);//

                                                var sb = new StringBuilder();
                                                for (var i = 0; i < input.length; i++) {
                                                    if (i == 5) {
                                                        sb.append(sub1);
                                                    } else if (i == 13) {
                                                        sb.append(sub13);
                                                    } else if (i == 12) {
                                                        sb.append(sub12);
                                                    } else if (i == 11) {
                                                        sb.append(sub11);
                                                    } else if (i == 10) {
                                                        sb.append(sub10);
                                                    } else if (i == 9) {
                                                        sb.append(sub9);
                                                    } else if (i == 14) {
                                                        sb.append(sub14);
                                                    } else if (i == 15) {
                                                        sb.append(sub15);
                                                    } else if (i == 8) {
                                                        sb.append(sub8);
                                                    } else if (i == test) {
                                                        sb.append(sub);
                                                    } else {
                                                        sb.append(input[i]);
                                                    }
                                                }
                                                var a = Long.parseLong(sb.toString(), 2);


                                                register.put('A', a);
                                                register.put('B', 0L);
                                                register.put('C', 0L);


                                                var result = process2(register);
                                                if (result != null) {
                                                    var str = result.stream().map(String::valueOf).collect(Collectors.joining(","));
                                                    if (Objects.equals(result.get(16 - test - 1), program.get(16 - test - 1))

                                                        && str.equals(programStr)
                                                    ) {
                                                        if (lowest == null || a < lowest) {
                                                            lowest = a;
                                                        }
                                                        log.info("str: {} {} [5]=> {} [8] => {} [9] = {}, [10] = {}, [11] = {} [12] = {} | {}       {} ", a, sub, sub1, sub8, sub9, sub10, sub11, sub12, programStr, str);
                                                        items.add(sub);
                                                    }
                                                    //2,4,1,1,7,5,1,5,0,3,4,3,5,5,3,0
//                log.info("str: {} {} {}       {} ", a, sub, programStr, str);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //164542125273021
        log.info("Lowest {}", lowest);
        return String.valueOf(lowest);
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
