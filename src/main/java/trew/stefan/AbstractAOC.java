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


    public List<String> getStringInput() {
        return getStringInput("");
    }

    public List<String> getStringInput(String suffix) {
        return InputReader.readStrings(year, day, suffix);
    }

    public List<Integer> getIntegerInput() {
        return getIntegerInput("");
    }

    public List<Long> getLongInput() {
        return getLongInput("");
    }

    public List<Long> getLongInput(String suffix) {
        return InputReader.readLongs(year, day, suffix);
    }

    public List<Integer> getIntegerInput(String suffix) {
        return InputReader.readIntegers(year, day, suffix);
    }

    public List<Double> getDoubleInput() {
        return getDoubleInput("");
    }

    public List<Double> getDoubleInput(String suffix) {
        return InputReader.readDoubles(year, day, suffix);
    }


    public List<String> getStringSplit(String separator) {
        return getStringSplit(separator, "");
    }

    public List<String> getStringSplit(String separator, String suffix) {
        return InputReader.readSplitStrings(separator, year, day, suffix);
    }

    public List<Integer> getIntegerSplit(String separator) {
        return getIntegerSplit(separator, "");
    }

    public List<Integer> getIntegerSplit(String separator, String suffix) {
        return InputReader.readSplitIntegers(separator, year, day, suffix);
    }

    public List<Double> getDoubleSplit(String separator) {
        return getDoubleSplit(separator, "");
    }

    public List<Double> getDoubleSplit(String separator, String suffix) {
        return InputReader.readSplitDoubles(separator, year, day, suffix);
    }

    public List<Long> getLongSplit(String separator) {
        return getLongSplit(separator, "");
    }

    public List<Long> getLongSplit(String separator, String suffix) {
        return InputReader.readSplitLongs(separator, year, day, suffix);
    }

    protected String formatResult(int i) {
        return String.valueOf(i);
    }

    protected String formatResult(double i) {
        return String.valueOf(i);
    }

    protected String formatResult(long i) {
        return String.valueOf(i);
    }

    protected String formatResult(String s) {
        return s;
    }
}
