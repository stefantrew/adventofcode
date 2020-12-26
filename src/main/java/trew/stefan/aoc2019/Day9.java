package trew.stefan.aoc2019;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.aoc2019.completed.ProcessorOld;
import trew.stefan.utils.InputReader2019;

import java.util.List;


@Slf4j
public class Day9 implements Day {


    public void run()    {
        List<String> lines = InputReader2019.readStrings(9, "");

        ProcessorOld old = new ProcessorOld();
        try {
            old.run2(0, 2, lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




