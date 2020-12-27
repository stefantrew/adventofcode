package trew.stefan.aoc2019.day20Take3;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
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

        int x;
        int y;

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
    }

    class VoidTile extends DonutTile {
        public VoidTile(int y, int x) {
            super(y, x);
        }

        @Override
        char getPrintableChar() {
            return ' ';
        }
    }

    class PortalTile extends DonutTile {

        PortalType type;
        String s;
        boolean isEntry = false;
        boolean isExit = false;
        PortalTile otherTile;

        public PortalTile(PortalType type, String s, int y, int x) {
            super(y, x);
            this.type = type;
            this.s = s;
            this.isEntry = s.equals("AA");
            this.isExit = s.equals("ZZ");
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

    class WallTile extends DonutTile {
        public WallTile(int y, int x) {
            super(y, x);
        }

        @Override
        char getPrintableChar() {
            return '#';
        }
    }

    class EmptyTile extends DonutTile {
        public EmptyTile(int y, int x) {
            super(y, x);
        }

        @Override
        char getPrintableChar() {
            return '.';
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

        if (current instanceof PortalTile && ((PortalTile) current).otherTile != null) {


            x = ((PortalTile) current).otherTile.x;
            y = ((PortalTile) current).otherTile.y;
//            log.info("Portal Jump {}", ((PortalTile) current).s);

        }

        result.add(grid[y + 1][x]);
        result.add(grid[y - 1][x]);
        result.add(grid[y][x + 1]);
        result.add(grid[y][x - 1]);

        return result.stream()
                .filter(donutTile -> {
                    if (donutTile instanceof PortalTile) {
                        String hash = currentPortal.s + ((PortalTile) donutTile).s;
                        if (visitedPortalTiles.contains(hash)) {
                            return false;
                        }
                        visitedPortalTiles.add(hash);
                        log.info("Adding hash {}", hash);

                    }
                    return donutTile instanceof EmptyTile || donutTile instanceof PortalTile;
                })
                .collect(Collectors.toList());
    }

    public void printGrid() {
        for (DonutTile[] chars : grid) {
            StringBuilder sb = new StringBuilder();
            for (DonutTile tile : chars) {
                sb.append(tile);
            }


            log.info("[{}]", sb.toString());
        }
    }
}
