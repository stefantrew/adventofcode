package trew.stefan;

import trew.stefan.AOCDay;
import trew.stefan.utils.InputReader2020;

import java.util.List;

public abstract class AbstractAOC implements AOCDay {

    protected int day = 1;


    public void setDay(int day) {


        this.day = day;
    }

    public List<String> getInput() {
        return getInput("");
    }

    public List<String> getInput(String suffix) {
        return InputReader2020.readStrings(day, "");

    }
}
