package trew.stefan.aoc2019.day15;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.Point;

@Slf4j
public class Day15OxygenSpreader {

    int sizeV;
    int sizeH;

    char[][] iterate(char[][] map) throws Exception {
        char[][] output = new char[sizeH][sizeV];
        int dots = 0;

        for (int y = 0; y < sizeV; y++) {
            for (int x = 0; x < sizeH; x++) {
                output[x][y] = map[x][y];
                if (map[x][y] == '.') {
                    dots++;
                }
            }
        }
        if (dots == 0) {
            throw new Exception("Not more dots");
        }

        for (int y = 0; y < sizeV; y++) {
            for (int x = 0; x < sizeH; x++) {
                if (map[x][y] == 'O') {

                    if (output[x + 1][y] == '.') {
                        output[x + 1][y] = 'O';
                    }
                    if (output[x][y + 1] == '.') {
                        output[x][y + 1] = 'O';
                    }
                    if (output[x - 1][y] == '.') {
                        output[x - 1][y] = 'O';
                    }
                    if (output[x][y - 1] == '.') {
                        output[x][y - 1] = 'O';
                    }
                }
            }
        }


        return output;
    }

    public int solve(Day15.FloorMap maze) {

        char[][] current = maze.getMap();

        Point oxygen = maze.oxygenPoint;
        Point start = maze.entry;
        current[oxygen.getX()][oxygen.getY()] = 'O';
        current[start.getX()][start.getY()] = '.';

        sizeV = maze.getSizeV();
        sizeH = maze.getSizeH();
        int counter = 0;
        try {
            while (true) {
                current = iterate(current);
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        printMap(current);

        log.info("Result {}", counter);
        return 0;
    }

    public void printMap(char[][] map) {

        log.info("---------------------------------------------------------------");
        for (int i = 0; i < sizeV; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < sizeH; j++) {
                builder.append(map[j][i]);
            }
            log.info(builder.toString());
        }
        log.info("---------------------------------------------------------------");
    }

}
