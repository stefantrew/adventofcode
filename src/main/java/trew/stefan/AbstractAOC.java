package trew.stefan;

import trew.stefan.utils.InputReader;

import java.util.List;

public abstract class AbstractAOC implements AOCDay {

    protected int day = 1;
    protected int year = 1;


    public void setDay(int day, int year) {

        this.year = year;
        this.day = day;
    }

    public List<String> getInput() {
        return getInput("");
    }

    public List<String> getInput(String suffix) {
        return InputReader.readStrings(year, day, suffix);

    }
}
