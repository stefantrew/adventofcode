package trew.stefan.aoc2015;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Replacement {

    Element input;
    Molecule output;
    String str;

    public Replacement(Element input, Molecule output) {
        this.input = input;
        this.output = output;
        this.str = output.toString();
    }

    @Override
    public String toString() {
        return input.toString() + " => " + output.toString();
    }


}
