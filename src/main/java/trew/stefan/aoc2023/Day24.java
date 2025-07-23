package trew.stefan.aoc2023;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day24 extends AbstractAOC {

    private static final double EPSILON = 1e-10;

    @NoArgsConstructor
    @ToString
    static class HailStone {
        int id;
        long x;
        long y;
        long z;
        BigDecimal cXY;
        BigDecimal cXZ;
        BigDecimal mXY;
        BigDecimal mXZ;
        long dx;
        long dy;
        long dz;


        public HailStone(int id, long x, long y, long z, long dx, long dy, long dz) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            mXY = BigDecimal.valueOf(dy).divide(BigDecimal.valueOf(dx), 10, RoundingMode.FLOOR);
            cXY = BigDecimal.valueOf(y).subtract(mXY.multiply(BigDecimal.valueOf(x)));

            mXZ = BigDecimal.valueOf(dz).divide(BigDecimal.valueOf(dx), 10, RoundingMode.FLOOR);
            cXZ = BigDecimal.valueOf(z).subtract(mXZ.multiply(BigDecimal.valueOf(x)));


        }


    }

    private boolean doesSimpleIntercept(HailStone stone1, HailStone stone2, long min, long max) {

        if (stone1.mXZ.equals(stone2.mXZ)) {
            return false;
        }
        if (stone1.mXY.equals(stone2.mXY)) {
            return false;
        }

        var temp = stone1.cXY.subtract(stone2.cXY);
        var temp2 = stone1.mXY.subtract(stone2.mXY);
        var x = temp.divide(temp2, 10, RoundingMode.FLOOR).negate();


        var y = stone1.mXY.multiply(x).add(stone1.cXY);


        if (x.longValue() < min || x.longValue() > max) {
            return false;
        }

        if (y.longValue() < min || y.longValue() > max) {
            return false;
        }

        if (x.subtract(BigDecimal.valueOf(stone1.x)).signum() * Math.signum(stone1.dx) < 0) {
            return false;
        }

        return !(x.subtract(BigDecimal.valueOf(stone2.x)).signum() * Math.signum(stone2.dx) < 0);
    }

    public String runPart1() {

        var total = 0;

        final String regex = "(\\d*), (\\d*), (\\d*) @ (-?\\d*), (-?\\d*), (-?\\d*)";

        var pattern = Pattern.compile(regex);

        var stones = new ArrayList<HailStone>();
        var list = getStringInput("");

        for (var s : list) {
            var matcher = pattern.matcher(s);
            if (matcher.find()) {
                HailStone stone = new HailStone(stones.size(),
                        Long.parseLong(matcher.group(1)),
                        Long.parseLong(matcher.group(2)),
                        Long.parseLong(matcher.group(3)),
                        Long.parseLong(matcher.group(4)),
                        Long.parseLong(matcher.group(5)),
                        Long.parseLong(matcher.group(6))

                );
                stones.add(stone);

            }
        }
        long min = 200000000000000L;
        long max = 400000000000000L;


        for (int i = 0; i < stones.size(); i++) {
            for (int j = i + 1; j < stones.size(); j++) {

                if (this.doesSimpleIntercept(stones.get(i), stones.get(j), min, max)) {
                    total++;
                }
            }

        }

        return formatResult(total);
    }

    @Override
    public String runPart2() {

        final String regex = "(\\d*), (\\d*), (\\d*) @ (-?\\d*), (-?\\d*), (-?\\d*)";

        var pattern = Pattern.compile(regex);

        var stones = new ArrayList<HailStone>();
        var list = getStringInput("");

        for (var s : list) {

            var matcher = pattern.matcher(s);
            if (matcher.find()) {
                HailStone stone = new HailStone(stones.size(),
                        Long.parseLong(matcher.group(1)),
                        Long.parseLong(matcher.group(2)),
                        Long.parseLong(matcher.group(3)),
                        Long.parseLong(matcher.group(4)),
                        Long.parseLong(matcher.group(5)),
                        Long.parseLong(matcher.group(6))

                );
                stones.add(stone);

            }
        }

        var s0 = stones.get(0);
        int n = 4;
        var xy = computeXY(n, stones, s0);
        var xz = computeXZ(n, stones, s0);


        var result = BigDecimal.valueOf(xy[0]).add(BigDecimal.valueOf(xy[1])).add(BigDecimal.valueOf(xz[1]));
        return formatResult(result.round(MathContext.DECIMAL64).longValue());
    }

    private static double[] computeXY(int n, List<HailStone> stones, HailStone s0) {
        //(dy'-dy) X + (dx-dx') Y + (y-y') DX + (x'-x) DY = x' dy' - y' dx' - x dy + y dx

        double[] b = new double[n];
        double[][] A = new double[n][n];

        for (int i = 0; i < n; i++) {
            var s1 = stones.get(i + 1);
            b[i] = s1.x * s1.dy - s1.y * s1.dx - s0.x * s0.dy + s0.y * s0.dx;

            A[i][0] = s1.dy - s0.dy;
            A[i][1] = s0.dx - s1.dx;
            A[i][2] = s0.y - s1.y;
            A[i][3] = s1.x - s0.x;

        }

        return lsolve(A, b);

    }

    private static double[] computeXZ(int n, List<HailStone> stones, HailStone s0) {
        //(dz'-dz) X + (dx-dx') Z + (z-z') DX + (x'-x) DZ = x' dz' - z' dx' - x dz + z dx
        double[] b = new double[n];
        double[][] A = new double[n][n];

        for (int i = 0; i < n; i++) {
            var s1 = stones.get(i + 1);
            b[i] = s1.x * s1.dz - s1.z * s1.dx - s0.x * s0.dz + s0.z * s0.dx;

            A[i][0] = s1.dz - s0.dz;
            A[i][1] = s0.dx - s1.dx;
            A[i][2] = s0.z - s1.z;
            A[i][3] = s1.x - s0.x;

        }

        return lsolve(A, b);

    }

    public static double[] lsolve(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }


    @Override
    public String getAnswerPart1() {
        return "16018";
    }

    @Override
    public String getAnswerPart2() {
        return "1004774995964534";
    }
}
