package trew.stefan.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
public class NumberList extends ArrayList<Long> {


    public Long sum() {
        return this.stream().reduce(Long::sum).orElse(0L);
    }

    @Override
    public NumberList subList(int fromIndex, int toIndex) {
        var result = new NumberList();
        result.addAll(super.subList(fromIndex, toIndex));
        return result;
    }

    public NumberList take(int n) {
        var result = new NumberList();
        result.addAll(super.subList(0, n));
        return result;
    }

    public NumberList sort() {
        Collections.sort(this);
        return this;
    }

    public NumberList reverse() {
        Collections.reverse(this);
        return this;
    }

    public NumberList reverseSort() {
        Collections.sort(this);
        Collections.reverse(this);
        return this;
    }

    public Long max() {
        Long max = null;

        for (var l : this) {
            if (max == null || l > max) {
                max = l;
            }
        }
        return max;
    }

    public Long min() {
        Long min = null;

        for (var l : this) {
            if (min == null || l < min) {
                min = l;
            }
        }
        return min;
    }

    public Long average() {
        if (size() == 0) {
            return 0L;
        }

        return sum() / size();
    }
}
