package trew.stefan.aoc2019.day20Take3;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Slf4j
public class Donut {


    enum PortalType {
        OUTER_TOP,
        INNER_TOP,
        INNER_BOTTOM,
        OUTER_BOTTOM,
        OUTER_LEFT,
        INNER_LEFT,
        INNER_RIGHT,
        OUTER_RIGHT

    }

    abstract class DonutTile {

        abstract char getPrintableChar();

        abstract DonutTile cloneTile(int level);

        int x;
        int y;
        int level = 0;

        DonutTile setLevel(int level) {
            this.level = level;
            return this;
        }

        public DonutTile(int y, int x) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return Character.toString(getPrintableChar());
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DonutTile donutTile = (DonutTile) o;
            return x == donutTile.x &&
                    y == donutTile.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    class VoidTile extends DonutTile {
        public VoidTile(int y, int x) {
            super(y, x);
        }

        @Override
        char getPrintableChar() {
            return ' ';
        }

        @Override
        DonutTile cloneTile(int level) {
            return (new VoidTile(y, x)).setLevel(level);
        }
    }

    class PortalTile extends DonutTile implements Cloneable {

        PortalType type;
        String s;
        boolean isEntry = false;
        boolean isExit = false;
        boolean isInner = false;
        PortalTile otherTile;

        public PortalTile(PortalType type, String s, int y, int x) {
            super(y, x);
            this.type = type;
            this.s = s;
            this.isEntry = s.equals("AA");
            this.isExit = s.equals("ZZ");
            switch (type) {


                case OUTER_TOP:
                case OUTER_BOTTOM:
                case OUTER_LEFT:
                case OUTER_RIGHT:
                    isInner = false;
                    break;
                case INNER_TOP:
                case INNER_BOTTOM:
                case INNER_LEFT:
                case INNER_RIGHT:
                    isInner = true;
                    break;
            }
        }

        @Override
        DonutTile cloneTile(int level) {
            PortalTile donutTile = new PortalTile(type, s, y, x);
            if (otherTile != null) {

                PortalTile otherTile = new PortalTile(this.otherTile.type, this.otherTile.s, this.otherTile.y, this.otherTile.x);
                donutTile.otherTile = otherTile;
                otherTile.otherTile = donutTile;
                if (otherTile.isInner) {

                    otherTile.setLevel(level - 1);
                } else {
                    otherTile.setLevel(level + 1);

                }
            }
            donutTile.setLevel(level);
            return donutTile;
        }

        @Override
        char getPrintableChar() {
            if (isEntry) {
                return 'S';
            } else if (isExit) {
                return 'X';
            }
            return 'P';
        }
    }

    class WallTile extends DonutTile implements Cloneable {
        public WallTile(int y, int x) {
            super(y, x);
        }

        @Override
        char getPrintableChar() {
            return '#';
        }

        @Override
        DonutTile cloneTile(int level) {
            return (new WallTile(y, x)).setLevel(level);
        }
    }

    class EmptyTile extends DonutTile implements Cloneable {
        public EmptyTile(int y, int x) {
            super(y, x);
        }

        @Override
        char getPrintableChar() {
            return '.';
        }

        @Override
        DonutTile cloneTile(int level) {
            return (new EmptyTile(y, x)).setLevel(level);
        }
    }

    DonutTile[][] grid;
    int donutHeight = 0;
    int donutWidth = 0;
    int ringHeight = 0;
    int ringWidth = 0;
    PortalTile startTile;
    PortalTile endTile;
    List<PortalTile> portalTiles = new ArrayList<>();
    List<String> visitedPortalTiles = new ArrayList<>();

    public Donut(int donutHeight, int donutWidth, int ringHeight, int ringWidth) {
        this.donutHeight = donutHeight + 4;
        this.donutWidth = donutWidth + 4;
        this.ringHeight = ringHeight;
        this.ringWidth = ringWidth;

        grid = new DonutTile[this.donutHeight][this.donutWidth];

        for (int r = 0; r < this.donutHeight; r++) {
            for (int c = 0; c < this.donutWidth; c++) {
                grid[r][c] = new VoidTile(r, c);
            }
        }
    }

    public void addPortal(int row, int col, PortalType type, String s) {

        if (type == PortalType.INNER_RIGHT)
            log.info("ADD Portal {} {} {} {}", row, col, type, s);
        PortalTile portalTile = new PortalTile(type, s, row, col);
        grid[row][col] = portalTile;
        if (s.equals("AA")) {
            startTile = portalTile;
        } else if (s.equals("ZZ")) {
            endTile = portalTile;
        }

        for (PortalTile ref : portalTiles) {
            if (ref.s.equals(s)) {
                log.info("Linking {}", s);
                ref.otherTile = portalTile;
                portalTile.otherTile = ref;
            }
        }

        portalTiles.add(portalTile);


    }


    public void addRow(char[] line, int rowNumber) {

        for (int i = 0; i < line.length; i++) {
            grid[rowNumber][i + 2] = line[i] == '#' ?
                    new WallTile(rowNumber, i + 2) :
                    new EmptyTile(rowNumber, i + 2);
        }

    }

    public void addRow(char[] line, char[] line2, int rowNumber) {
        int offset = donutWidth - ringWidth;
        for (int i = 0; i < line.length; i++) {
            grid[rowNumber][i + 2] = line[i] == '#' ? new WallTile(rowNumber, i + 2) : new EmptyTile(rowNumber, i + 2);
        }
        for (int i = 0; i < line2.length; i++) {
            grid[rowNumber][i + +offset - 2] = line2[i] == '#' ? new WallTile(rowNumber, i + 2) : new EmptyTile(rowNumber, i + +offset - 2);
        }

    }


    public List<DonutTile> getAdjacentTiles(DonutTile current, PortalTile currentPortal) {


        List<DonutTile> result = new ArrayList<>();
        int x = current.getX();
        int y = current.getY();
        int level = current.level;
        if (current instanceof PortalTile) {
            PortalTile portalTile = (PortalTile) current;
            if (portalTile.otherTile != null) {
                if (!portalTile.isInner && portalTile.level == 0) {
                    return result;
                }

                x = portalTile.otherTile.x;
                y = portalTile.otherTile.y;
                level = portalTile.otherTile.level;

            }
            if (level == 400) {
                return result;
            }
        }

        result.add(grid[y][x + 1].cloneTile(level));
        result.add(grid[y + 1][x].cloneTile(level));
        result.add(grid[y][x - 1].cloneTile(level));

        result.add(grid[y - 1][x].cloneTile(level));


        return result.stream()
                .filter(donutTile -> {
                    if (donutTile instanceof PortalTile) {

                        PortalTile temp = (PortalTile) donutTile;
                        if (current.level == 0) {
                            if (temp.isExit) {
                                return true;
                            }
                            if (!temp.isInner) {
                                return false;
                            }

                        }


                        String hash = ((PortalTile) donutTile).isInner + ((PortalTile) donutTile).s + donutTile.level;
                        if (visitedPortalTiles.contains(hash)) {
                            return false;
                        }
                        visitedPortalTiles.add(hash);
//                        log.info("Adding hash {}", hash);
                        return true;

                    }
                    return donutTile instanceof EmptyTile;
                })
                .collect(Collectors.toList());
    }

    public void printGrid(DonutTile current) {
        int row = 0;
        for (DonutTile[] chars : grid) {
            StringBuilder sb = new StringBuilder();
            for (DonutTile tile : chars) {
                if (tile.equals(current)) {
                    sb.append("X");
                } else {

                    sb.append(tile);
                }
            }


            log.info("{}: {}", String.format("%3s", row++), sb.toString());
        }
    }
}
