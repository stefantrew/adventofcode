package trew.stefan.aoc2020.day12;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day12 implements AOCDay {

    private int day = 12;

    @Override
    public String runPart1() {
        List<String> lines = InputReader2020.readStrings(12, "");

        int x = 0;
        int y = 0;
        String direction = "E";

        Pattern p = Pattern.compile("(\\w)(\\d*)");
        for (String line : lines) {

            Matcher m = p.matcher(line);
            if (m.find()) {
                String dir = m.group(1);
                int unit = Integer.parseInt(m.group(2));
                switch (dir) {
                    case "N":
                        y -= unit;
                        break;
                    case "S":
                        y += unit;
                        break;
                    case "E":
                        x += unit;
                        break;
                    case "W":
                        x -= unit;
                        break;
                    case "L":
                        while (unit > 0) {
                            switch (direction) {
                                case "N":
                                    direction = "W";
                                    break;
                                case "S":
                                    direction = "E";

                                    break;
                                case "E":
                                    direction = "N";

                                    break;
                                case "W":
                                    direction = "S";
                                    break;
                            }
                            unit -= 90;
                        }
                        break;
                    case "R":
                        while (unit > 0) {
                            switch (direction) {
                                case "N":
                                    direction = "E";
                                    break;
                                case "S":
                                    direction = "W";

                                    break;
                                case "E":
                                    direction = "S";

                                    break;
                                case "W":
                                    direction = "N";
                                    break;
                            }
                            unit -= 90;
                        }
                        break;
                    case "F":

                        switch (direction) {
                            case "N":
                                y -= unit;
                                break;
                            case "S":
                                y += unit;
                                break;
                            case "E":
                                x += unit;
                                break;
                            case "W":
                                x -= unit;
                                break;
                        }
                        break;
                }

            }


        }
        return String.valueOf(Math.abs(x) + Math.abs(y));
    }

    @Override
    public String runPart2() {

        List<String> lines = InputReader2020.readStrings(12, "");

        int x = 0;
        int y = 0;
        int wx = 10;
        int wy = -1;
        Pattern p = Pattern.compile("(\\w)(\\d*)");
        for (String line : lines) {

            Matcher m = p.matcher(line);
            if (m.find()) {
                String dir = m.group(1);
                int unit = Integer.parseInt(m.group(2));
                switch (dir) {
                    case "N":
                        wy -= unit;
                        break;
                    case "S":
                        wy += unit;
                        break;
                    case "E":
                        wx += unit;
                        break;
                    case "W":
                        wx -= unit;
                        break;
                    case "L":
                        switch (unit) {
                            case 90:
                                int t = wx;
                                wx = wy;
                                wy = -t;
                                break;
                            case 180:
                                wx = -wx;
                                wy = -wy;
                                break;
                            case 270:
                                int t1 = wx;
                                wx = -wy;
                                wy = t1;
                                break;
                        }
                        break;
                    case "R":
                        switch (unit) {
                            case 90:
                                int t = wx;
                                wx = -wy;
                                wy = t;
                                break;
                            case 180:
                                wx = -wx;
                                wy = -wy;
                                break;
                            case 270:

                                int t1 = wx;
                                wx = wy;
                                wy = -t1;
                                break;
                        }
                        break;
                    case "F":

                        y += wy * unit;
                        x += wx * unit;

                        break;
                }

            }


        }
        return String.valueOf(Math.abs(x) + Math.abs(y));
    }
}
