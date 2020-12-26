package trew.stefan.aoc2019.day18;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.Point;

import java.util.HashMap;
import java.util.Map;

@Slf4j

@SuppressWarnings("DuplicatedCode")
public class MazeMap {

    @Getter
    int sizeV;
    @Getter
    int sizeH;
    @Getter
    private char[][] map;

    @Getter
    Point[] entries = new Point[4];
    @Getter
    Point[] current = new Point[4];

    Map<Character, Point> keys = new HashMap<>();
    Map<Character, Point> doors = new HashMap<>();

    public String getHash() {
        StringBuilder sb = new StringBuilder();
        keys.keySet().forEach(sb::append);
        for (Point entry : entries) {
            sb.append(entry.getHash());
        }
        return sb.toString();
    }

    MazeMap(int sizeV, int sizeH) {
        this.sizeV = sizeV;
        this.sizeH = sizeH;
        map = new char[sizeH][sizeV];
        for (int i = 0; i < sizeV; i++) {
            for (int j = 0; j < sizeH; j++) {
                map[j][i] = ' ';
            }
        }
    }

    MazeMap moveToKey(char key) {
        MazeMap result = new MazeMap(sizeV, sizeH);

        Point keyPoint = getKey(key);
        for (int y = 0; y < sizeV; y++) {
            for (int x = 0; x < sizeH; x++) {
                char c = map[x][y];
                byte quadrant = getQuadrant(x, y);
                boolean flag = false;
                for (Point entry : entries) {

                    if (entry.getY() == y && entry.getX() == x) {
                        flag = true;
                        break;
                    }
                }
                if (flag && keyPoint.getQuadrant() == quadrant) {
                    c = '.';
                } else if (flag || c == key) {
                    c = '@';
                } else if (c == Character.toUpperCase(key)) {
                    c = '.';
                }
                result.setPoint(x, y, c);
            }
        }

        return result;
    }

    public byte getQuadrant(int x, int y) {
        boolean isUpper = y < sizeV / 2;
        boolean isLeft = x < sizeH / 2;

        return (byte) ((isUpper ? 0 : 2) + (isLeft ? 0 : 1));
    }


    private void setPoint(int i, int j, char c) {
        byte quadrant = getQuadrant(i, j);
        if (c == '@') {
            c = '.';
            current[quadrant] = new Point(i, j, quadrant);
            entries[quadrant] = new Point(i, j, quadrant);
        } else if (Character.isLowerCase(c)) {
            keys.put(c, new Point(i, j, quadrant));
        } else if (Character.isUpperCase(c)) {
            doors.put(c, new Point(i, j, quadrant));
        }

        map[i][j] = c;
    }

    public Point getKey(char c) {
        return keys.getOrDefault(c, null);
    }

    public void printMap() {

        log.info("---------------------------------------------------------------");
        for (int i = 0; i < sizeV; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < sizeH; j++) {
                boolean flag = false;
                for (Point entry : current) {

                    if (entry.getY() == i && entry.getX() == j) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    builder.append('@');
                } else {
                    builder.append(map[j][i]);
                }
            }
            log.info(builder.toString());
        }
        log.info("---------------------------------------------------------------");
        log.info("Doors {}", doors.size());
        log.info("Keys {}", keys.size());
    }


    public boolean isWall(Integer x, Integer y) {
        return map[x][y] == '#';
    }

    public boolean isValidLocation(Integer x, Integer y, char c) {
        return map[x][y] == '.' || map[x][y] == c;
    }

    public void setRow(int row, String s) {
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            byte quadrant = getQuadrant(i, row);
            if (c == '@') {
                c = '.';
                current[quadrant] = new Point(i, row, quadrant);
                entries[quadrant] = new Point(i, row, quadrant);
            } else if (Character.isLowerCase(c)) {
                keys.put(c, new Point(i, row, quadrant));
            } else if (Character.isUpperCase(c)) {
                doors.put(c, new Point(i, row, quadrant));
            }

            map[i][row] = c;
        }
    }
}
