package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day19 extends AbstractAOC {


    @Override
    public String runPart1() {


        List<Scanner> scanners = getScanners();

        doWork(scanners);


        return formatResult(scanners.get(0).beacons.stream().distinct().count());
    }


    @Override
    public String runPart2() {


        List<Scanner> scanners = getScanners();

        doWork(scanners);


        int max = 0;
        var scanner = scanners.get(0);
        var set = new HashSet<Vector>();
        for (Beacon beacon : scanner.beacons) {
            set.add(beacon.scannerPos);
        }
        for (var vector : set) {
            for (var vector1 : set) {
                max = Math.max(max, vector.distanceTo(vector1));
            }
        }

        return formatResult(max);

    }


    private List<Scanner> getScanners() {
        List<Scanner> scanners = new ArrayList<>();

        var list = getStringInput("");

        Scanner current = null;
        var p1 = Pattern.compile("--- scanner (\\d*) ---");
        for (var s : list) {
            if (s.isEmpty()) {
                continue;
            }

            var m1 = new AOCMatcher(p1.matcher(s));
            if (m1.find()) {

                current = new Scanner(m1.group(1));
                scanners.add(current);
            } else {

                Objects.requireNonNull(current).beacons.add(new Beacon(split(s), current));
            }
        }


        for (Scanner scanner : scanners) {
            scanner.computeDistances();
        }
        return scanners;
    }

    private void doWork(Scanner scanner, List<Scanner> scanners) {
        for (Scanner scanner1 : scanners) {
            if (scanner.id.equals(scanner1.id)) {
                continue;
            }
            scanner.compareTo(scanner1);
        }
    }

    private void doWork(List<Scanner> scanners) {
        while (scanners.size() > 1) {

            for (Scanner scanner : scanners) {

                doWork(scanner, scanners);
            }

            scanners = scanners.stream().filter(scanner -> !scanner.beacons.isEmpty()).collect(Collectors.toList());
        }
    }

    public static List<Integer> split(String input) {
        String[] strs = input.split(",");
        return Arrays.stream(strs).map(Integer::parseInt).collect(Collectors.toList());
    }

    @AllArgsConstructor
    class Scanner {

        String id;
        List<Beacon> beacons = new ArrayList<>();

        public Scanner(String id) {
            this.id = id;
        }

        public void computeDistances() {
            for (Beacon beacon : beacons) {
                beacon.distances.clear();
                for (Beacon beacon1 : beacons) {
                    if (beacon == beacon1) {
                        continue;
                    }
                    var distance = beacon.distanceTo(beacon1);
                    var distance2 = String.valueOf(distance);
                    while (beacon.distances2.contains(distance2)) {
                        distance2 += "_";
                    }
                    beacon.distances.add(distance);
                    beacon.distances2.add(distance2);
                }

            }

        }

        @Override
        public String toString() {
            return String.format("%s: [%s]", id, beacons);
        }

        public boolean compareTo(Scanner scanner) {
//            log.info("Comparing {} {}", id, scanner.id);
            var count = 0;
            List<Beacon> positionalBeacons = new ArrayList<>();
            List<Beacon> positionalBeacons1 = new ArrayList<>();
            for (Beacon beacon : beacons) {
                for (Beacon beacon1 : scanner.beacons) {

                    Set<String> result = new HashSet<>(beacon.distances2);
                    result.retainAll(beacon1.distances2);
                    int same = result.size();
                    if (same >= 11) {
                        count++;
                        positionalBeacons.add(beacon);
                        positionalBeacons1.add(beacon1);
                        break;
                    }
                }
            }
            if (count >= 12) {


                var vector = new Vector();
                var vector1 = new Vector();

                var refBeacon = positionalBeacons.get(0);
                for (Beacon positionalBeacon : positionalBeacons) {
                    vector.add(refBeacon.getVector(positionalBeacon));
                }


                var refBeacon1 = positionalBeacons1.get(0);
                for (Beacon positionalBeacon : positionalBeacons1) {

                    vector1.add(refBeacon1.getVector(positionalBeacon));
                }


                var mappingMatrix = new HashMap<String, String>();
                mappingMatrix.put("x", vector.getMapping(vector1, "x"));
                mappingMatrix.put("y", vector.getMapping(vector1, "y"));
                mappingMatrix.put("z", vector.getMapping(vector1, "z"));


                scanner.applyTransform(mappingMatrix);


                scanner.applyTranslation(refBeacon.getVector(refBeacon1));

                beacons.addAll(scanner.beacons);
                scanner.beacons.clear();

                return true;
            }
            return false;
        }

        private void applyTranslation(Vector vector) {
            for (Beacon beacon : beacons) {
                beacon.applyTranslation(vector);
            }
        }


        private void applyTransform(HashMap<String, String> mappingMatrix) {
            for (Beacon beacon : beacons) {
                beacon.applyTransform(mappingMatrix);
            }
        }
    }

    @NoArgsConstructor
    class Vector {
        int x = 0;
        int y = 0;
        int z = 0;

        public Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        void add(Vector vector) {
            x += vector.x;
            y += vector.y;
            z += vector.z;
        }

        public Integer distanceTo(Vector vector) {

            return Math.abs(vector.x - x) + Math.abs(vector.y - y) + Math.abs(vector.z - z);

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector vector = (Vector) o;
            return x == vector.x && y == vector.y && z == vector.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }


        public int get(String field) {
            switch (field) {
                case "x":
                    return x;
                case "-x":
                    return -x;
                case "y":
                    return y;
                case "-y":
                    return -y;
                case "z":
                    return z;
                case "-z":
                    return -z;
            }

            return 0;
        }

        @Override
        public String toString() {
            return String.format("<x= %3d, y=%3d, z=%3d>", x, y, z);
        }

        public String getMapping(Vector vector1, String x) {
            var ref = get(x);

            if (ref == vector1.get("x")) {
                return "x";
            } else if (ref == vector1.get("-x")) {
                return "-x";
            } else if (ref == vector1.get("y")) {
                return "y";
            } else if (ref == vector1.get("-y")) {
                return "-y";
            } else if (ref == vector1.get("z")) {
                return "z";
            } else if (ref == vector1.get("-z")) {
                return "-z";
            }
            return "";
        }

        public Vector getVector(Vector pos) {
            return new Vector(x - pos.x, y - pos.y, z - pos.z);

        }
    }

    class Beacon {


        Vector pos;
        Vector scannerPos = new Vector(0, 0, 0);

        Scanner scanner;

        Set<Integer> distances = new HashSet<>();
        Set<String> distances2 = new HashSet<>();

        public Beacon(List<Integer> input, Scanner scanner) {

            pos = new Vector(input.get(0), input.get(1), input.get(2));
            this.scanner = scanner;
        }

        private void applyTransform(HashMap<String, String> mappingMatrix) {

            var xTransform = mappingMatrix.get("x");
            var yTransform = mappingMatrix.get("y");
            var zTransform = mappingMatrix.get("z");
            var newX = get(xTransform);
            var newY = get(yTransform);
            var newZ = get(zTransform);

            pos = new Vector(newX, newY, newZ);

            var newX2 = get2(xTransform);
            var newY2 = get2(yTransform);
            var newZ2 = get2(zTransform);

            scannerPos = new Vector(newX2, newY2, newZ2);
        }

        @Override
        public String toString() {
            return pos.toString();
        }

        public Integer distanceTo(Beacon beacon1) {

            return pos.distanceTo(beacon1.pos);

        }

        public Integer scannerDistanceTo(Beacon beacon1) {

            return scannerPos.distanceTo(beacon1.scannerPos);

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Beacon beacon = (Beacon) o;
            return pos.equals(beacon.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos);
        }

        public Integer get2(String transform) {
            return scannerPos.get(transform);
        }

        public Integer get(String transform) {
            return pos.get(transform);
        }

        public void applyTranslation(Vector vector) {
            pos.add(vector);
            scannerPos.add(vector);
        }

        public Vector getVector(Beacon positionalBeacon) {

            return pos.getVector(positionalBeacon.pos);
        }
    }


    @Override
    public String getAnswerPart1() {
        return "355";
    }

    @Override
    public String getAnswerPart2() {
        return "10842";
    }
}
