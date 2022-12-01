package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day22 extends AbstractAOC {

    @Override
    public String runPart1() {

        var total = 0L;
        return formatResult(total);
    }

    public class Coordinate {
        int x, y, z;

        Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (this.getClass() != obj.getClass()) {
                return false;
            }

            Coordinate other = (Coordinate) obj;

            if (this.x == other.x && this.y == other.y && this.z == other.z) {
                return true;
            }

            return false;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            result = prime * result + z;
            return result;
        }
    }

    public class Cuboid {
        int x1, x2, y1, y2, z1, z2;

        Cuboid(int x1, int x2, int y1, int y2, int z1, int z2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
        }

        Cuboid(Pair<Integer, Integer> xRange, Pair<Integer, Integer> yRange, Pair<Integer, Integer> zRange) {
            this(xRange.first, xRange.second, yRange.first, yRange.second, zRange.first, zRange.second);
        }

        long getVolume() {
            long length = x2 - x1 + 1;
            long width = y2 - y1 + 1;
            long height = z2 - z1 + 1;
            return length * width * height;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append("(" + x1 + "," + x2 + ")");
            sb.append(",");
            sb.append("(" + y1 + "," + y2 + ")");
            sb.append(",");
            sb.append("(" + z1 + "," + z2 + ")");
            sb.append("]");
            return sb.toString();
        }
    }

    public class Instruction {
        boolean toggle;
        Pair<Integer, Integer> xRange, yRange, zRange;

        Instruction(boolean toggle, Pair<Integer, Integer> xRange, Pair<Integer, Integer> yRange, Pair<Integer, Integer> zRange) {
            this.toggle = toggle;
            this.xRange = new Pair<>(xRange);
            this.yRange = new Pair<>(yRange);
            this.zRange = new Pair<>(zRange);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(toggle ? "on" : "off");
            sb.append(" ");
            sb.append("x=" + xRange.first + ".." + xRange.second);
            sb.append(",");
            sb.append("y=" + yRange.first + ".." + yRange.second);
            sb.append(",");
            sb.append("z=" + zRange.first + ".." + zRange.second);

            return sb.toString();
        }
    }

    public class Pair<T, V> {
        T first;
        V second;

        Pair(T first, V second) {
            this.first = first;
            this.second = second;
        }

        Pair(Pair<T, V> other) {
            this.first = other.first;
            this.second = other.second;
        }

        @Override
        public String toString() {
            return "(" + this.first + "," + this.second + ")";
        }
    }


    @Override
    public String runPart2() {

        List<Instruction> instructions = readInstructions();

        List<Pair<Integer, Cuboid>> reactorCore = new ArrayList<>();

        // Below solution calculates the answer using the formula for union of sets (see: https://en.wikipedia.org/wiki/Inclusion%E2%80%93exclusion_principle#Statement)
        for (Instruction instruction : instructions) {
            List<Pair<Integer, Cuboid>> cuboidsToAddInCore = new ArrayList<>();
            Cuboid cuboidToProcess = new Cuboid(instruction.xRange, instruction.yRange, instruction.zRange);

            if (instruction.toggle) {
                cuboidsToAddInCore.add(new Pair<>(1, cuboidToProcess));
            }

            for (Pair<Integer, Cuboid> reactorCuboid : reactorCore) {
                Cuboid intersection = getIntersection(cuboidToProcess, reactorCuboid.second);
                if (intersection != null) {
                    cuboidsToAddInCore.add(new Pair<>(-reactorCuboid.first, intersection));
                }
            }

            reactorCore.addAll(cuboidsToAddInCore);
        }

        long answer = reactorCore.stream().mapToLong(x -> x.first * x.second.getVolume()).sum();
        return formatResult(answer);
    }


    private Cuboid getIntersection(Cuboid A, Cuboid B) {
        Cuboid intersection = new Cuboid(Math.max(A.x1, B.x1), Math.min(A.x2, B.x2), Math.max(A.y1, B.y1), Math.min(A.y2, B.y2), Math.max(A.z1, B.z1), Math.min(A.z2, B.z2));
        if ((intersection.x1 > intersection.x2) || (intersection.y1 > intersection.y2) || (intersection.z1 > intersection.z2)) {
            return null;
        } else {
            return intersection;
        }
    }


    private List<Instruction> readInstructions() {
        var list = getStringInput("");
        List<Instruction> instructions = new ArrayList<>();

        Pattern instructionPattern = Pattern.compile("(on|off) (x=-?\\d+..-?\\d+),(y=-?\\d+..-?\\d+),(z=-?\\d+..-?\\d+)");
        Pattern rangePattern = Pattern.compile("[xyz]=(-?\\d+)..(-?\\d+)");
        for (String line : list) {
            Matcher mat = instructionPattern.matcher(line);
            mat.matches();
            String toggleStr = mat.group(1);
            boolean toggle = toggleStr.equals("on") ? true : false;

            List<Pair<Integer, Integer>> ranges = new ArrayList<>();
            for (int i = 2; i <= 4; i++) {
                Matcher rangeMat = rangePattern.matcher(mat.group(i));
                rangeMat.matches();
                ranges.add(new Pair<>(Integer.decode(rangeMat.group(1)), Integer.decode(rangeMat.group(2))));
            }

            Instruction instruction = new Instruction(toggle, ranges.get(0), ranges.get(1), ranges.get(2));
            instructions.add(instruction);
        }

        return instructions;


    }

    @Override
    public String getAnswerPart1() {
        return "611378";
    }

    @Override
    public String getAnswerPart2() {
//        return "2758514936282235";
        return "474140";
    }
}
