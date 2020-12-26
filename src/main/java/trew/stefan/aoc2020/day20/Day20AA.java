package trew.stefan.aoc2020.day20;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Day20AA implements Day {

    private int day = 20;

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

        int rotation = 0;
        boolean locked = false;

        Tile imageTop = null;
        Tile imageLeft = null;
        Tile imageRight = null;
        Tile imageBottom = null;

        String topEdge;
        String bottomEdge;
        String leftEdge;
        String rightEdge;
        String topEdgeReverse;
        String bottomEdgeReverse;
        String leftEdgeReverse;
        String rightEdgeReverse;


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

        boolean isTopLeft() {
            return imageTop == null && imageLeft == null;
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

        boolean checkAlignment() {

            boolean flag = true;

            if (imageTop != null && this != imageTop.imageBottom) {

                if (imageTop.imageLeft == this) {
                    imageTop.rotate(270);
                } else if (imageTop.imageRight == this) {
                    imageTop.rotate(90);
                } else {
                    imageTop.rotate(180);
                }


                flag = false;
            }

            if (imageBottom != null && this != imageBottom.imageTop) {


                if (imageBottom.imageRight == this) {
                    imageBottom.rotate(270);
                } else if (imageBottom.imageLeft == this) {
                    imageBottom.rotate(90);
                } else {
                    imageBottom.rotate(180);
                }

                flag = false;
            }

            if (imageLeft != null && this != imageLeft.imageRight) {

                if (imageLeft.imageTop == this) {
                    imageLeft.rotate(90);
                } else if (imageLeft.imageBottom == this) {
                    imageLeft.rotate(270);
                } else {
                    imageLeft.rotate(180);
                }

                flag = false;
            }

            if (imageRight != null && this != imageRight.imageLeft) {
                if (imageRight.imageTop == this) {
                    imageRight.rotate(270);
                } else if (imageRight.imageBottom == this) {
                    imageRight.rotate(90);
                } else {
                    imageRight.rotate(180);
                }
                flag = false;
            }

            if (imageBottom != null && imageBottom.topEdge.equals(bottomEdgeReverse)) {
                flipH();
                flag = false;
            }
            if (imageTop != null && imageTop.bottomEdge.equals(topEdgeReverse)) {
                flipH();
                flag = false;
            }
            if (imageLeft != null && imageLeft.rightEdge.equals(leftEdgeReverse)) {
                flipV();
                flag = false;
            }
            if (imageRight != null && imageRight.leftEdge.equals(rightEdgeReverse)) {
                flipV();
                flag = false;
            }
            return flag;
        }

        void flipH() {

            Tile temp = imageLeft;
            imageLeft = imageRight;
            imageRight = temp;

            for (int i = 0; i < 10; i++) {
                StringBuilder sb = new StringBuilder(new String(image[i])).reverse();
                image[i] = sb.toString().toCharArray();
            }

            buildTile();
        }

        void flipV() {

            Tile temp = imageBottom;
            imageBottom = imageTop;
            imageTop = temp;

            for (int i = 0; i < 10; i++) {

                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 10; j++) {
                    sb.append(image[j][i]);
                }
                char[] newString = sb.toString().toCharArray();
                for (int j = 0; j < 10; j++) {
                    image[j][i] = newString[j];
                }
            }

            buildTile();
        }

        private void rotate(int degrees) {
//            log.info("Rotate {} {}", id, degrees);

            Tile temp;
            switch (degrees) {

                case 270:
                    temp = imageBottom;
                    imageBottom = imageRight;
                    imageRight = imageTop;
                    imageTop = imageLeft;
                    imageLeft = temp;

                    RotateMatrix();
                    RotateMatrix();
                    RotateMatrix();
                    break;
                case 180:
                    temp = imageBottom;
                    imageBottom = imageTop;
                    imageTop = temp;

                    temp = imageRight;
                    imageRight = imageLeft;
                    imageLeft = temp;
                    RotateMatrix();
                    RotateMatrix();
                    break;
                case 90:
                    temp = imageBottom;
                    imageBottom = imageLeft;
                    imageLeft = imageTop;
                    imageTop = imageRight;
                    imageRight = temp;
                    RotateMatrix();
                    break;
            }
            buildTile();
        }

        int countCandidates(List<Tile> tiles) {

            int countTop = 0;
            int countBottom = 0;
            int countLeft = 0;
            int countRight = 0;

            for (Tile tile : tiles) {
                if (tile.id.equals(id)) continue;

                if (tile.edges.contains(topEdge)) {
                    imageTop = tile;
                    countTop++;
                }

                if (tile.edges.contains(bottomEdge)) {
                    imageBottom = tile;
                    countBottom++;
                }

                if (tile.edges.contains(leftEdge)) {
                    imageLeft = tile;
                    countLeft++;
                }

                if (tile.edges.contains(rightEdge)) {
                    imageRight = tile;
                    countRight++;
                }
            }

            log.info("{} {} {} {} {}", id, countTop, countRight, countBottom, countLeft);
            return countTop + countBottom + countLeft + countRight;
        }

        public void printIdMap() {
            log.info("{} {} {}", imageLeft.imageTop.id, imageTop.id, imageRight.imageTop.id);
            log.info("{} {} {}", imageLeft.id, id, imageRight.id);
            log.info("{} {} {}", imageLeft.imageBottom.id, imageBottom.id, imageRight.imageBottom.id);

        }

        public void printTile(boolean printSiblings) {

            log.info("");
            log.info("");
            log.info("{}", id);
            for (int i = 0; i < 10; i++) {

                log.info("{}", new String(image[i]));
            }

        }
    }


    public void run() {
        List<String> lines = InputReader2020.readStrings(day, "");

        List<Tile> tiles = new ArrayList<>();

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
                log.info("{}", i);
                break;
            }


        }

        for (Tile tile : tiles) {
            tile.buildTile();
        }

        long result = 1;
        for (Tile tile : tiles) {
            if (tile.countCandidates(tiles) == 2) {
                log.info("{}", tile.id);
                result *= Long.parseLong(tile.id);
            }
        }
        log.info("{}", result);

//        if (topLeft != null) {
//            topLeft.printTile(true);
//        }
        int x = 0;

    }

    boolean align(List<Tile> tiles) {
        int count = 0;
        for (Tile tile : tiles) {
            boolean b = tile.checkAlignment();
            if (b) count++;
//            log.info("{} {}", tile.id, b);
        }

//        log.info("{}", count);
//        log.info("");
//        log.info("");
        Collections.shuffle(tiles);
        return count == tiles.size();
    }
}
