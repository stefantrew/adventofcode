package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.awt.geom.Point2D;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day18 extends AbstractAOC {

    private Integer count = 0;
    final String regex = "(.) (\\d*) .{2}(.{6})";
    final Pattern pattern = Pattern.compile(regex);

    @Override
    public String runPart1() {

        var list = getStringInput("");

        var col = 0;
        var row = 0;
        Integer minRow = null;
        Integer minCol = null;
        Integer maxRow = null;
        Integer maxCol = null;

        for (var s : list) {

            final Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {

                var dir = matcher.group(1);
                var len = Integer.parseInt(matcher.group(2));

                switch (dir) {
                    case "R" -> col += len;
                    case "L" -> col -= len;
                    case "D" -> row += len;
                    case "U" -> row -= len;
                }

                if (minRow == null) {
                    minRow = row;
                    minCol = col;
                    maxRow = row;
                    maxCol = col;
                } else {
                    minRow = Math.min(minRow, row);
                    minCol = Math.min(minCol, col);
                    maxRow = Math.max(maxRow, row);
                    maxCol = Math.max(maxCol, col);
                }

            }
        }


        col = -minCol + 2;
        row = -minRow + 2;

        var chars = Math.max(maxRow - minRow, maxCol - minCol) + 10;
        var visited = new char[chars][chars];

        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                visited[i][j] = '+';
            }
        }

        for (var s : list) {

            final Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {

                var dir = matcher.group(1);
                var len = Integer.parseInt(matcher.group(2));

                for (int i = 0; i < len; i++) {
                    visited[row][col] = '#';
                    switch (dir) {
                        case "R" -> col++;
                        case "L" -> col--;
                        case "D" -> row++;
                        case "U" -> row--;
                    }
                }


            }
        }

        visited = floodMatrix(chars, visited);

        var total = 0;

        total = getTotal(visited, total);

        return formatResult(total);
    }

    private char[][] floodMatrix(int chars, char[][] visited) {
        for (int i = 0; i < chars; i++) {
            visited[0][i] = '_';
            visited[chars - 1][i] = '_';
            visited[i][0] = '_';
            visited[i][chars - 1] = '_';
        }

        count = 1;

        while (count > 0) {
            visited = flood(visited);

        }
        return visited;
    }

    private static void printMatrix(char[][] visited) {
        for (int i = 0; i < visited.length; i++) {
            log.info("{}", new String(visited[i]));
        }
    }

    private static int getTotal(char[][] visited, int total) {
        for (int i = 0; i < visited.length; i++) {

            char[] temp = visited[i];
            for (int j = 0; j < temp.length; j++) {
                var c = temp[j];
                if (c != '_') {
                    total++;
                }
            }

        }
        return total;
    }

    private char[][] flood(char[][] visited) {
        var newChar = new char[visited.length][visited.length];

        count = 0;
        for (int row = 0; row < visited.length; row++) {
            for (int col = 0; col < visited[0].length; col++) {
                if (visited[row][col] == '#') {
                    newChar[row][col] = '#';
                    continue;
                } else if (visited[row][col] == '_') {
                    newChar[row][col] = '_';
                    continue;
                }

                if (visited[row + 1][col] == '_') {
                    newChar[row][col] = '_';
                    count++;
                    continue;
                }
                if (visited[row - 1][col] == '_') {
                    newChar[row][col] = '_';
                    count++;
                    continue;
                }

                if (visited[row][col - 1] == '_') {
                    newChar[row][col] = '_';
                    count++;
                    continue;
                }
                if (visited[row][col + 1] == '_') {
                    newChar[row][col] = '_';
                    count++;
                    continue;
                }

                newChar[row][col] = visited[row][col];
            }
        }


        return newChar;
    }


    public static String calculatePolygonArea(List<Point2D.Double> vertices, int lens) {
        int n = vertices.size();
        double area = 0.0;

        for (int i = 0; i < n; i++) {
            Point2D.Double current = vertices.get(i);
            Point2D.Double next = vertices.get((i + 1) % n);

            area += (current.getX() * next.getY()) - (next.getX() * current.getY());
        }

        area = 0.5 * Math.abs(area);

        return Double.toString(area + (lens /2.0) + 1);
    }


    @Override
    public String runPart2() {


        var list = getStringInput("");

        var col = 0;
        var row = 0;
        var lens = 0;
        List<Point2D.Double> polygonVertices = new Vector<>();
        polygonVertices.add(new Point2D.Double(col, row));
        for (var s : list) {

            final Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {

                var colour = matcher.group(3);

                Integer len = Integer.parseInt(colour.substring(0, 5), 16);
                lens += len;
                var dir = switch (colour.substring(5)) {
                    case "0" -> "R";
                    case "2" -> "L";
                    case "1" -> "D";
                    case "3" -> "U";
                    default -> throw new IllegalStateException("Unexpected value: " + colour.substring(5));
                };

                switch (dir) {
                    case "R" -> col += len;
                    case "L" -> col -= len;
                    case "D" -> row += len;
                    case "U" -> row -= len;
                }
                polygonVertices.add(new Point2D.Double(col, row));

            }
        }

        return formatResult(calculatePolygonArea(polygonVertices, lens));
    }

    @Override
    public String getAnswerPart1() {
        return "35401";
    }

    @Override
    public String getAnswerPart2() {
        return "4.8020869073824E13";
    }
}
