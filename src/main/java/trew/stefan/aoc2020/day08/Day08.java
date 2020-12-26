package trew.stefan.aoc2020.day08;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day08 implements AOCDay {

    private int day = 8;
    List<String> lines;

    class Instruction {

        public String type;
        public int val1;
        boolean hasRun = false;

        public Instruction(String type, int val1) {
            this.type = type;
            this.val1 = val1;
        }
    }

    public Day08() {
        lines = InputReader2020.readStrings(day, "");


    }

    @Override
    public String runPart1() {

        Pattern p = Pattern.compile("(\\w*) ([+-]\\d*)");

        ArrayList<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {


            Matcher matcher = p.matcher(line);
            while (matcher.find()) {
                int qty = Integer.parseInt(matcher.group(2));
                String type = (matcher.group(1));

                instructions.add(new Instruction(type, qty));

            }
        }

        int pointer = 0;
        int r1 = 0;

        while (true) {


            Instruction instruction = instructions.get(pointer);

            if (instruction.hasRun) {
                return String.valueOf(r1);
            }
            instruction.hasRun = true;

//            log.info("{} {} {}", pointer, r1, instruction.type);

            switch (instruction.type) {
                case "nop":
                    pointer++;
                    break;
                case "jmp":
                    pointer += instruction.val1;
                    break;
                case "acc":
                    r1 += instruction.val1;
                    pointer++;
                    break;
            }
        }

    }

    @Override
    public String runPart2() {
        return run();
    }

    public String run() {


        run(-1, lines);
        for (int i = 0; i < lines.size(); i++) {
            Integer run = run(i, lines);
            if (run != null) {

                return String.valueOf(run);
            }
        }
        return null;
    }

    public Integer run(int offset, List<String> lines) {

        Pattern p = Pattern.compile("(\\w*) ([+-]\\d*)");

        ArrayList<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {


            Matcher matcher = p.matcher(line);
            while (matcher.find()) {
                int qty = Integer.parseInt(matcher.group(2));
                String type = (matcher.group(1));

                instructions.add(new Instruction(type, qty));

            }
        }

        int pointer = 0;
        int r1 = 0;

        if (offset > 0) {

            Instruction temp = instructions.get(offset);

            switch (temp.type) {
                case "nop":
                    temp.type = "jmp";
                    break;
                case "jmp":
                    temp.type = "nop";
                    break;
                case "acc":
                    return null;
            }
        }

        while (true) {

            if (pointer > instructions.size() - 1) {
                return r1;
            }
            Instruction instruction = instructions.get(pointer);

            if (instruction.hasRun) {
                return null;
            }
            instruction.hasRun = true;

//            log.info("{} {} {}", pointer, r1, instruction.type);

            switch (instruction.type) {
                case "nop":
                    pointer++;
                    break;
                case "jmp":
                    pointer += instruction.val1;
                    break;
                case "acc":
                    r1 += instruction.val1;
                    pointer++;
                    break;
            }
        }

    }
}
