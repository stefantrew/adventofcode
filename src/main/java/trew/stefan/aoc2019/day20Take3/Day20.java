package trew.stefan.aoc2019.day20Take3;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.utils.InputReader2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day20 implements AOCDay {


    @Override
    public String runPart1() {

        List<String> lines = InputReader2019.readStrings(20, "");

        String part1 = null;
        char[][] input = new char[lines.size() - 1][];
        int i = 0;

        for (String line : lines) {
            if (part1 == null) {
                part1 = line.split(" ")[0];
                continue;
            }

            input[i++] = line.toCharArray();
        }

        int firstRow = 0;
        int donutHeight = 0;
        int donutWidth = 0;
        int ringHeight = 0;
        int ringWidth = 0;

        Pattern p = Pattern.compile("([#\\.]+)");
        int row = 0;

        for (char[] chars : input) {

            Matcher m = p.matcher(new String(chars));

            if (m.find()) {
                if (firstRow == 0) {
                    firstRow = row;
                    donutWidth = m.group(1).length();
                }
                if (m.find()) {
                    if (ringHeight == 0) {
                        ringHeight = row - firstRow;
                        ringWidth = m.group(1).length();

                    }
                }

            } else if (firstRow != 0) {
                donutHeight = row - firstRow - 1;
            }

            row++;
        }

        Donut donut = new Donut(donutHeight, donutWidth, ringHeight, ringWidth);

        row = 0;
        for (char[] chars : input) {

            Matcher m = p.matcher(new String(chars));

            if (m.find()) {

                char[] line = m.group(1).toCharArray();

                if (m.find()) {
                    char[] line2 = m.group(1).toCharArray();
                    donut.addRow(line, line2, row);
                } else {
                    donut.addRow(line, row);

                }

            }

            for (int x = 0; x < chars.length; x++) {
                char c = chars[x];
                if (Character.isAlphabetic(c)) {
                    Donut.PortalType type = null;
                    char c2 = ' ';
                    if (row == 0 || row == donutHeight + firstRow + 1 || x == 0 || x == donutWidth + 3) {
                        continue;
                    } else if (row == 1) {
                        type = Donut.PortalType.OUTER_TOP;
                        c2 = c;
                        c = input[row - 1][x];
                    } else if (x == donutWidth + 2) {
                        type = Donut.PortalType.OUTER_RIGHT;
                        c2 = input[row][x + 1];
                    } else if (row == donutHeight + firstRow) {
                        type = Donut.PortalType.OUTER_BOTTOM;
                        c2 = input[row + 1][x];
                    } else if (x == 1) {
                        type = Donut.PortalType.OUTER_LEFT;
                        c2 = c;
                        c = input[row][x - 1];
                    } else if (x == ringWidth + 2) {
                        type = Donut.PortalType.INNER_LEFT;
                        c2 = input[row][x + 1];
                    } else if (x == donutWidth - ringWidth + 1) {
                        type = Donut.PortalType.INNER_RIGHT;
                        c2 = c;
                        c = input[row][x - 1];
                    } else if (row == ringHeight + 2) {
                        type = Donut.PortalType.INNER_TOP;
                        c2 = input[row + 1][x];
                    } else if (row == donutHeight - ringHeight + 1) {
                        type = Donut.PortalType.INNER_BOTTOM;
                        c2 = c;
                        c = input[row - 1][x];
                    }

                    if (type != null) {
                        donut.addPortal(row, x, type, new String(new char[]{c, c2}));
                    }
                }
            }

            row++;
        }

//        for (Donut.PortalTile tile : donut.portalTiles) {
//            log.info("{} {}", tile.s, tile.otherTile != null ? tile.otherTile.s : "");
//        }


//        donut.printGrid();

        Donut.PortalTile start = donut.getStartTile();

        List<Donut.DonutTile> result = doWalk(donut, start, 1, null, start);

        if (result != null) {

            log.info("Result {}", getSize(result));

//            for (Donut.DonutTile tile : result) {
//                if (tile instanceof Donut.PortalTile) {
//
//                    log.info("{} {} {}", tile.x, tile.y, ((Donut.PortalTile) tile).s);
//                } else {
//                    log.info("{} {} ", tile.x, tile.y);
//
//                }
//            }
        } else {
            log.info("No Luck :(");
        }

        return null;
    }

    int getSize(List<Donut.DonutTile> list) {

        int count = 0;
        for (Donut.DonutTile tile : list) {
            if (tile instanceof Donut.EmptyTile) {
                count++;
            }
        }

        return count;
    }

    List<Donut.DonutTile> doWalk(Donut donut, Donut.DonutTile current, int currentDepth, Donut.DonutTile prev, Donut.PortalTile currentPortal) {


        if (prev instanceof Donut.PortalTile && ((Donut.PortalTile) prev).otherTile != null) {
            prev = ((Donut.PortalTile) prev).otherTile;
        }

        if (currentDepth == 15000) {
            return null;
        }

        if (current instanceof Donut.PortalTile && ((Donut.PortalTile) current).isExit && current.level == 0) {
            return new ArrayList<>();
        }

        List<Donut.DonutTile> tiles = donut.getAdjacentTiles(current, currentPortal);
        List<Donut.DonutTile> min = null;


//        log.info("=========================== {} ================================", currentDepth);
//        donut.printGrid(current);
//        log.info("Cur Tile: {} X={}; Y={};   level {}", current.getPrintableChar(), current.x, current.y, current.level);
//        if (prev != null) {
//
//            log.info("Pre Tile: {} X={}; Y={};   level {}", prev.getPrintableChar(), prev.x, prev.y, prev.level);
//        }
//
//        for (Donut.DonutTile tile : tiles) {
//            log.info("Adj Tile: {} X={}; Y={};   level {}", tile.getPrintableChar(), tile.x, tile.y, tile.level);
//        }

        for (Donut.DonutTile tile : tiles) {

            if (tile.equals(prev)) {
                continue;
            }

            if (tile instanceof Donut.PortalTile) {
//                log.info("Portal Tile: {} X={}; Y={};   level {}", ((Donut.PortalTile) tile).s, tile.x, tile.y, tile.level);
            }

            List<Donut.DonutTile> list = doWalk(donut, tile, currentDepth + 1, current, tile instanceof Donut.PortalTile ? (Donut.PortalTile) tile : currentPortal);

            if (list != null) {
                list.add(tile);

                if (min == null) {
                    min = list;
                } else {
                    min = getSize(min) < getSize(list) ? min : list;
                }
            }

        }

        return min;
    }

    @Override
    public String runPart2() {
        return null;
    }
}
/*
5152
8677 ***************
 */
