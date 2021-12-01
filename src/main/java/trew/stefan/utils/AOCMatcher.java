package trew.stefan.utils;

import java.util.regex.Matcher;

public class AOCMatcher {

    final private Matcher matcher;

    public AOCMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public Long getLong(int group) {
        return Long.valueOf(matcher.group(group));
    }

    public String getString(int group) {
        return matcher.group(group);
    }

    public String group(int group) {
        return matcher.group(group);
    }

    public Integer getInt(int group) {
        return Integer.valueOf(matcher.group(group));
    }

    public Double getDouble(int group) {
        return Double.valueOf(matcher.group(group));
    }

    public char getChar(int group) {
        return matcher.group(group).charAt(0);
    }

    public boolean find() {
        return matcher.find();
    }
}
