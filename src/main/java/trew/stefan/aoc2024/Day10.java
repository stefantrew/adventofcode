package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
public class Day10 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        var total = 0L;


        var map = new Matrix<>(list, Integer.class, null);

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> c == '.' ? null : c - 48)
                    .toList());

        }


        var zeroes = map.find(0);

        for (var zero : zeroes) {
            total += new HashSet<>(getRoute(map, zero)).size();
        }

        return String.valueOf(total);
    }

    private List<RCPoint> getRoute(Matrix<Integer> map, Matrix<Integer>.MatrixPoint zero) {

        var directions = new int[][]{
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0}
        };

        var next = zero.getValue() + 1;
        var result = new ArrayList<RCPoint>();
        if (next == 10) {
            result.add(zero.getRcPoint());
            return result;
        }

        for (var d : directions) {
            var r = zero.getRow() + d[0];
            var c = zero.getCol() + d[1];

            if (r >= 0 && r < map.getRows() && c >= 0 && c < map.getCols()) {
                var point = map.getPoint(r, c);
                if (point != null && point.getValue() != null && point.getValue() == next) {
                    result.addAll(getRoute(map, point));
                }
            }
        }


        return result;
    }


    @Override
    public String runPart2() {
        var list = getStringInput("");
        var total = 0L;


        var map = new Matrix<>(list, Integer.class, null);

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> c == '.' ? null : c - 48)
                    .toList());

        }


        var zeroes = map.find(0);

        for (var zero : zeroes) {
            total += getRoute(map, zero).size();
        }

        return String.valueOf(total);
    }

    @Override
    public String getAnswerPart1() {
        return "709";
    }

    @Override
    public String getAnswerPart2() {
        return "1326";
    }
}
