package trew.stefan.aoc2019;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.aoc2019.completed.Processor;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.PermutationUtil;

import java.util.List;

@Slf4j
public class Day7 implements Day {

    int runSeq(List<String> lines, int[] seq) {

        int round = 0;
        int current = 0;
        Processor[] proc = new Processor[5];
        for (int index = 0; index < seq.length; index++) {
            proc[index] = new Processor(lines, seq[index]);
        }

        while (true) {
            for (int index = 0; index < seq.length; index++) {
                current = proc[index].run2(current);
            }

            if (!proc[0].isRunning())
                return current;
        }

    }

    public void run() {
//        List<String> lines = InputReader.readStrings(6, "");
        List<String> lines = InputReader2019.readStrings(7, "");
        int[] input = new int[]{5, 6, 7, 8, 9};
        List<int[]> vectors = PermutationUtil.getPermutationsVectors(input);
        int max = 0;
        int[] maxVector = null;
        for (int[] vector : vectors) {
            int output = runSeq(lines, vector);
            if (output > max) {
                max = output;
                maxVector = vector;
            }
//            log.info("+++++++++++++++++ {} +++++++++++++++++", output);
        }
        log.info("{} 1 {}", max, maxVector);

    }
}
