package trew.stefan.aoc2020.day20;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.*;
import java.util.function.BinaryOperator;

@Slf4j
public class Day20 implements AOCDay {

    private int day = 20;

    class Map {

        char[][] image;
        int n = 10;

        public Map(char[][] image) {
            this.image = image;
            n = image.length;
        }

        void RotateMatrix() {


            char[][] ret = new char[n][n];

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    ret[i][j] = image[n - j - 1][i];
                }
            }

            image = ret;
        }

        void flipH() {

            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder(new String(image[i])).reverse();
                image[i] = sb.toString().toCharArray();
            }

        }

        void flipV() {
            for (int i = 0; i < n; i++) {

                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(image[j][i]);
                }
                char[] newString = sb.reverse().toString().toCharArray();
                for (int j = 0; j < n; j++) {
                    image[j][i] = newString[j];
                }
            }

        }

        private void rotate(int degrees) {
            switch (degrees) {

                case 270:
                    RotateMatrix();
                    RotateMatrix();
                    RotateMatrix();
                    break;
                case 180:

                    RotateMatrix();
                    RotateMatrix();
                    break;
                case 90:

                    RotateMatrix();
                    break;
            }
        }

    }

    class Tile {

        public Tile(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return id.equals(tile.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        String id;

        char[][] image = new char[10][10];

        List<String> edges = new ArrayList<>();

        Set<Tile> candidates = new HashSet<>();

        boolean placed = false;

        Tile topImage = null;
        Tile leftImage = null;
        Tile rightImage = null;
        Tile bottomImage = null;

        String topEdge;
        String bottomEdge;
        String leftEdge;
        String rightEdge;
        String topEdgeReverse;
        String bottomEdgeReverse;
        String leftEdgeReverse;
        String rightEdgeReverse;
        boolean isCorner = false;
        boolean isBorder = false;

        int x = 0;
        int y = 0;


        void RotateMatrix() {
            int n = 10;

            char[][] ret = new char[n][n];

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    ret[i][j] = image[n - j - 1][i];
                }
            }

            image = ret;
        }

        void buildTile() {
            edges.clear();

            topEdge = new String(image[0]);
            edges.add(topEdge);
            bottomEdge = new String(image[9]);
            edges.add(bottomEdge);

            StringBuilder left = new StringBuilder();
            StringBuilder right = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                left.append(image[i][0]);
                right.append(image[i][9]);
            }
            leftEdge = left.toString();
            rightEdge = right.toString();

            edges.add(left.toString());
            edges.add(right.toString());

            for (int i = 0; i < 4; i++) {
                edges.add((new StringBuilder(edges.get(i)).reverse()).toString());
            }
            topEdgeReverse = edges.get(4);
            bottomEdgeReverse = edges.get(5);
            leftEdgeReverse = edges.get(6);
            rightEdgeReverse = edges.get(7);
        }


        void flipH() {

            for (int i = 0; i < 10; i++) {
                StringBuilder sb = new StringBuilder(new String(image[i])).reverse();
                image[i] = sb.toString().toCharArray();
            }

            buildTile();
        }

        void flipV() {
            for (int i = 0; i < 10; i++) {

                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 10; j++) {
                    sb.append(image[j][i]);
                }
                char[] newString = sb.reverse().toString().toCharArray();
                for (int j = 0; j < 10; j++) {
                    image[j][i] = newString[j];
                }
            }

            buildTile();
        }

        private void rotate(int degrees) {
            switch (degrees) {

                case 270:
                    RotateMatrix();
                    RotateMatrix();
                    RotateMatrix();
                    break;
                case 180:

                    RotateMatrix();
                    RotateMatrix();
                    break;
                case 90:

                    RotateMatrix();
                    break;
            }
            buildTile();
        }

        public void rotateTileToTopLeft(List<Tile> tiles) {

            flipV();

            while (true) {
                int countTop = 0;
                int countLeft = 0;

                for (Tile tile : tiles) {
                    if (tile.id.equals(id)) continue;

                    if (tile.edges.contains(topEdge)) {
                        countTop++;
                    }

                    if (tile.edges.contains(leftEdge)) {
                        countLeft++;
                    }

                }
//                log.info("{} Top: {}, left: {}", id, countTop, countLeft);
                if (countLeft == 0 && countTop == 0) {
                    return;
                }

                rotate(90);

            }
        }

        int countCandidates(List<Tile> tiles) {

            int countTop = 0;
            int countBottom = 0;
            int countLeft = 0;
            int countRight = 0;

            for (Tile tile : tiles) {
                if (tile.id.equals(id)) continue;

                if (tile.edges.contains(topEdge)) {
                    candidates.add(tile);
                    countTop++;
                }

                if (tile.edges.contains(bottomEdge)) {
                    candidates.add(tile);
                    countBottom++;
                }

                if (tile.edges.contains(leftEdge)) {
                    candidates.add(tile);
                    countLeft++;
                }

                if (tile.edges.contains(rightEdge)) {
                    candidates.add(tile);
                    countRight++;
                }
            }

//            log.info("{} {} {} {} {}", id, countTop, countRight, countBottom, countLeft);
            int count = countTop + countBottom + countLeft + countRight;

            if (count == 2) {
                isCorner = true;
            } else if (count == 3) {
                isBorder = true;
            }

            return count;
        }

        public void printIdMap() {
            log.info("{} {} {}", leftImage.topImage.id, topImage.id, rightImage.topImage.id);
            log.info("{} {} {}", leftImage.id, id, rightImage.id);
            log.info("{} {} {}", leftImage.bottomImage.id, bottomImage.id, rightImage.bottomImage.id);

        }

        public void printTile() {

            log.info("");
            log.info("");
            log.info("{}", id);
            for (int i = 0; i < 10; i++) {

                log.info("{}", new String(image[i]));
            }

        }

        public void placeTiles(List<Tile> tiles) {


            for (Tile tile : tiles) {

                if (tile.id.equals(id)) continue;

                if (bottomImage == null && tile.edges.contains(bottomEdge)) {
                    rotateTileToMatchBottom(tile);
                    tile.topImage = this;
                    this.bottomImage = tile;
                    tile.placed = true;
                    tile.x = x;
                    tile.y = y + 1;
                    log.info("Placing {} under {}", tile.id, id);
                } else if (topImage == null && tile.edges.contains(topEdge)) {
                    rotateTileToMatchTop(tile);
                    tile.bottomImage = this;
                    this.topImage = tile;
                    tile.placed = true;
                    tile.x = x;
                    tile.y = y - 1;
                    log.info("Placing {} top of {}", tile.id, id);
                } else if (rightImage == null && tile.edges.contains(rightEdge)) {
                    rotateTileToMatchRight(tile);
                    tile.leftImage = this;
                    this.rightImage = tile;
                    tile.placed = true;
                    tile.x = x + 1;
                    tile.y = y;
                    log.info("Placing {} right of {}", tile.id, id);
                } else if (leftImage == null && tile.edges.contains(leftEdge)) {
                    rotateTileToMatchLeft(tile);
                    tile.rightImage = this;
                    this.leftImage = tile;
                    tile.placed = true;
                    tile.x = x - 1;
                    tile.y = y;
                    log.info("Placing {} left of {}", tile.id, id);
                }
            }

        }

        private void rotateTileToMatchBottom(Tile tile) {
            if (tile.topEdge.equals(bottomEdge)) {
                return;
            }

            if (tile.topEdgeReverse.equals(bottomEdge)) {
                tile.flipH();
            } else {
                tile.rotate(90);
                if (tile.topEdge.equals(bottomEdge)) {
                    return;
                } else if (tile.topEdgeReverse.equals(bottomEdge)) {
                    tile.flipH();
                    return;
                }
                tile.rotate(90);
                if (tile.topEdge.equals(bottomEdge)) {
                    return;
                } else if (tile.topEdgeReverse.equals(bottomEdge)) {
                    tile.flipH();
                    return;
                }
                tile.rotate(90);
                if (tile.topEdge.equals(bottomEdge)) {
                } else if (tile.topEdgeReverse.equals(bottomEdge)) {
                    tile.flipH();
                }
            }
        }

        private void rotateTileToMatchTop(Tile tile) {
            if (tile.bottomEdge.equals(topEdge)) {
                return;
            }

            if (tile.bottomEdgeReverse.equals(topEdge)) {
                tile.flipH();
            } else {
                tile.rotate(90);
                if (tile.bottomEdge.equals(topEdge)) {
                    return;
                } else if (tile.bottomEdgeReverse.equals(topEdge)) {
                    tile.flipH();
                    return;
                }
                tile.rotate(90);
                if (tile.bottomEdge.equals(topEdge)) {
                    return;
                } else if (tile.bottomEdgeReverse.equals(topEdge)) {
                    tile.flipH();
                    return;
                }
                tile.rotate(90);
                if (tile.bottomEdge.equals(topEdge)) {
                } else if (tile.bottomEdgeReverse.equals(topEdge)) {
                    tile.flipH();
                }
            }
        }

        private void rotateTileToMatchLeft(Tile tile) {

            if (tile.rightEdge.equals(leftEdge)) {
                return;
            }

            if (tile.rightEdgeReverse.equals(leftEdge)) {
                tile.flipV();
            } else {
                tile.rotate(90);
                if (tile.rightEdge.equals(leftEdge)) {
                    return;
                } else if (tile.rightEdgeReverse.equals(leftEdge)) {
                    tile.flipV();
                    return;
                }
                tile.rotate(90);
                if (tile.rightEdge.equals(leftEdge)) {
                    return;
                } else if (tile.rightEdgeReverse.equals(leftEdge)) {
                    tile.flipV();
                    return;
                }
                tile.rotate(90);
                if (tile.rightEdge.equals(leftEdge)) {
                } else if (tile.rightEdgeReverse.equals(leftEdge)) {
                    tile.flipV();
                }
            }
        }

        private void rotateTileToMatchRight(Tile tile) {
            if (tile.leftEdge.equals(rightEdge)) {
                return;
            }

            if (tile.leftEdgeReverse.equals(rightEdge)) {
                tile.flipV();
            } else {
                tile.rotate(90);
                if (tile.leftEdge.equals(rightEdge)) {
                    return;
                } else if (tile.leftEdgeReverse.equals(rightEdge)) {
                    tile.flipV();
                    return;
                }
                tile.rotate(90);
                if (tile.leftEdge.equals(rightEdge)) {
                    return;
                } else if (tile.leftEdgeReverse.equals(rightEdge)) {
                    tile.flipV();
                    return;
                }
                tile.rotate(90);
                if (tile.leftEdge.equals(rightEdge)) {
                } else if (tile.leftEdgeReverse.equals(rightEdge)) {
                    tile.flipV();
                }
            }
        }

    }

    //2287 3433 3083 3461

    public void buildRow(Tile topLeft) {

        Tile currentTile = topLeft;
        Tile currentTop = topLeft.topImage;
        int watchDog = 15;

        List<Tile> processed = new ArrayList<>();
        processed.add(currentTile);
        while (true) {
            boolean found = false;
            if (watchDog-- == 0) {
                return;
            }
            for (Tile tile : currentTile.candidates) {
                if (tile.id.equals(currentTile.id)) continue;
                if (processed.contains(tile)) continue;


                if (tile.edges.contains(currentTile.rightEdge)) {

                    currentTile.rotateTileToMatchRight(tile);
                    tile.leftImage = currentTile;

                    if (currentTop != null) {
                        currentTop = currentTop.rightImage;
                        tile.topImage = currentTop;
                        currentTop.bottomImage = tile;
                    }

                    currentTile.rightImage = tile;
                    currentTile = tile;
                    processed.add(currentTile);
                    found = true;
                    break;
                }


            }

            if (!found) {
                return;
            }
        }
    }

    public char[][] mergeTiles(Tile firstTile, int depth) {

        char[][] result = new char[depth * 8][depth * 8];

        int row = 0;
        while (firstTile != null) {
            Tile currentTile = firstTile;
            int col = 0;
            while (currentTile != null) {

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        result[row * 8 + i][col * 8 + j] = currentTile.image[i + 1][j + 1];
                    }
                }

                currentTile = currentTile.rightImage;
                col++;
            }


            firstTile = firstTile.bottomImage;
            row++;
        }


        return result;
    }

    public Tile addBottomTile(Tile currentTile) {



        for (Tile tile : currentTile.candidates) {
            if (tile.id.equals(currentTile.id)) continue;


            if (tile.edges.contains(currentTile.bottomEdge)) {

                currentTile.rotateTileToMatchBottom(tile);
                tile.topImage = currentTile;
                currentTile.bottomImage = tile;
                currentTile = tile;
                return currentTile;
            }


        }
        return null;

    }

    @Override
    public String runPart1() {
        List<Tile> tiles = new ArrayList<>();
        setupTiles(tiles);

        long result = tiles.stream()
                .filter(tile -> tile.isCorner)
                .map(tile -> Long.parseLong(tile.id))
                .reduce(1L, (subtotal, element) -> subtotal * element);


        return String.valueOf(result);


    }

    @Override
    public String runPart2() {
        return String.valueOf(run());
    }

    public int run() {
        List<Tile> tiles = new ArrayList<>();
        setupTiles(tiles);

        Tile start = tiles.stream().filter(tile -> tile.isCorner).findFirst().orElse(new Tile("aa"));
        start.rotateTileToTopLeft(tiles);


        Tile currentTile = start;
        while (true) {
            buildRow(currentTile);
            currentTile = addBottomTile(currentTile);
            if (currentTile == null) {
                break;
            }
        }


        Map map = new Map(mergeTiles(start, (int) Math.sqrt(tiles.size())));
        Map resultMap = new Map(mergeTiles(start, (int) Math.sqrt(tiles.size())));

        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);
        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);
        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);
        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);

        map.flipH();
        resultMap.flipH();
        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);
        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);
        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);
        map.rotate(90);
        resultMap.rotate(90);
        processMap(map, resultMap);
        return countHashes(resultMap);
    }

    private int countHashes(Map map) {

        int counter = 0;
        for (int row = 0; row < map.image.length; row++) {
            for (int col = 0; col < map.image.length; col++) {

                if (map.image[row][col] == '#') {
                    counter++;
                }
            }
        }

        return counter;
    }

    boolean isMatch(Map map, char[][] monster, int row, int col) {


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                if (monster[i][j] != '#') {
                    continue;
                }

                if (map.image[row + i][col + j] != '#') {
                    return false;
                }

            }
        }

        return true;
    }

    void edgeMatch(Map map, char[][] monster, int row, int col) {


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                if (monster[i][j] != '#') {
                    continue;
                }


                map.image[row + i][col + j] = 'O';
            }
        }

    }

    void processMap(Map map, Map result) {
        char[][] monster = new char[3][20];
        monster[0] = "                  # ".toCharArray();
        monster[1] = "#    ##    ##    ###".toCharArray();
        monster[2] = " #  #  #  #  #  #   ".toCharArray();


        for (int row = 0; row < map.image.length - 3; row++) {
            for (int col = 0; col < map.image.length - 20; col++) {
                boolean isMatch = isMatch(map, monster, row, col);
                if (isMatch) {
                    edgeMatch(result, monster, row, col);
                }

            }
        }

    }

    public void printMap(Map map) {
        log.info("");
        int n = map.image.length;
        for (int i = 0; i < n; i++) {

            log.info("{}", new String(map.image[i]));
        }

    }

    private void setupTiles(List<Tile> tiles) {
        List<String> lines = InputReader2020.readStrings(day, "");


        int i = 0;
        while (true) {

            String id = lines.get(i++);
            Tile tile = new Tile(id);
            for (int a = 0; a < 10; a++) {
                tile.image[a] = lines.get(i + a).toCharArray();
            }
            tiles.add(tile);
            i += 11;
            if (i >= lines.size()) {
                break;
            }


        }

        for (Tile tile : tiles) {
            tile.buildTile();
        }

        for (Tile tile : tiles) {
            tile.countCandidates(tiles);
        }
    }


}
