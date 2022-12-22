package trew.stefan.aoc2022;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;


@Slf4j
public class Day14 extends AbstractAOC {

    enum Material {

        ROCK("#"), SAND("o"), AIR("."), VISIT("x");

        String name;

        Material(String s) {
            this.name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Data
    public class SimplePoint {

        int row;
        int col;

        public SimplePoint(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    @Override
    public String runPart1() {
        var offset = 0;
        Matrix<Material> matrix = getMaterialMatrix(offset, "", false);
//        matrix.printMatrix(false);

        while (addSand(offset, matrix)) {

        }


        var ascore = "";
        return String.valueOf(matrix.count(material -> material == Material.SAND) - 1);
    }

    private static boolean addSand(int offset, Matrix<Material> matrix) {
        matrix.set(0, 500 - offset, Material.SAND);
        var sand = matrix.getPoint(0, 500 - offset);
        while (true) {

            if (!matrix.checkDimensions(sand.getRow() + 1, sand.getCol())) {
                return false;
            }
            var next = matrix.getPoint(sand.getRow() + 1, sand.getCol());
            if (next.getValue() == Material.AIR) {
                matrix.set(sand, Material.AIR);
                matrix.set(next, Material.SAND);
                sand = next;
                continue;
            }

            next = matrix.getPoint(sand.getRow() + 1, sand.getCol() - 1);
            if (next.getValue() == Material.AIR) {
                matrix.set(sand, Material.AIR);
                matrix.set(next, Material.SAND);
                sand = next;
                continue;
            }

            next = matrix.getPoint(sand.getRow() + 1, sand.getCol() + 1);
            if (next.getValue() == Material.AIR) {
                matrix.set(sand, Material.AIR);
                matrix.set(next, Material.SAND);
                sand = next;
                continue;
            }
            if (sand.getRow() == 0) {
                return false;
            }
            return true;
        }
    }

    private Matrix<Material> getMaterialMatrix(int offset, String sample, boolean addFloor) {
        var list = getStringInput(sample);
        var n = 500 - offset;
        var matrix = new Matrix<Material>(n, n * 2, Material.class, Material.AIR);
        var superMaxRow = 0;
        for (var item : list) {


            var nodes = item.split(" -> ");
            SimplePoint current = null;
            for (String node : nodes) {
                var temp = node.split(",");
                var point = new SimplePoint(Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));

                if (current == null) {
                    current = point;
                    continue;
                }

                var maxRow = Math.max(current.row, point.row);
                var maxCol = Math.max(current.col, point.col);
                var minRow = Math.min(current.row, point.row);
                var minCol = Math.min(current.col, point.col);

                superMaxRow = Math.max(superMaxRow, maxRow);

                for (int row = minRow; row <= maxRow; row++) {
                    for (int col = minCol; col <= maxCol; col++) {

                        matrix.set(row, col - offset, Material.ROCK);
                    }
                }
                current = point;
            }

        }
        if (addFloor) {

            for (int col = 0; col < matrix.getWidth(); col++) {

                matrix.set(superMaxRow + 2, col - offset, Material.ROCK);
            }
        }

        return matrix;
    }

    @Override
    public String runPart2() {
        var offset = 0;
        Matrix<Material> matrix = getMaterialMatrix(offset, "", true);

        while (addSand(offset, matrix)) ;


        return String.valueOf(matrix.count(material -> material == Material.SAND));
    }

    @Override
    public String getAnswerPart1() {
        return "838";
    }

    @Override
    public String getAnswerPart2() {
        return "27539";
    }
}
