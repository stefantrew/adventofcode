package trew.stefan.utils;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GuardTime {
    private Integer id;

    public int[] minutes = new int[60];

    public GuardTime(Integer id) {
        this.id = id;
    }

    public int totalMinutes() {
        int sum = 0;
        for (int minute : minutes) {
            sum += minute;
        }
        return sum;
    }

    public int highestDay() {
        int d = 0;
        int v = 0;

        for (int x = 0; x < 60; x++) {
            if (minutes[x] > v) {
                d = x;
                v = minutes[x];
            }
        }
        return d;
    }
}
