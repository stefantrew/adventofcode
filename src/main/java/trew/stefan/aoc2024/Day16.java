package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

@Slf4j
public class Day16 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


//        var list = getStringInput().stream().map(this::mapper).toList();

        var list = getStringInput("_sample");
        var matrix = new Matrix<Character>(6, 6, Character.class, '.');
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        for (var s : list) {
            log.info("{}", s);
            var x = Integer.parseInt(s.split(",")[0]);
            var y = Integer.parseInt(s.split(",")[1]);
            matrix.set(x, y, '#');
        }
        matrix.printMatrix(false);


        return formatResult(total);
    }

    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
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
