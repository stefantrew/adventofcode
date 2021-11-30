package trew.stefan.aoc2021;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day06 extends AbstractAOC {

    @NoArgsConstructor
    class Planet {

        int x;
        int y;
        Integer id = null;
        int count = 1;
        boolean isEdge = false;

        public Planet(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }

        public int distanceFrom(Planet planet) {
            return Math.abs(planet.x - x) + Math.abs(planet.y - y);
        }
    }

    @Override
    public String runPart1() {

        List<Planet> list = getPlanets();

        var maxX = 0;
        var maxY = 0;
        for (int i = 0; i < list.size(); i++) {
            Planet planet = list.get(i);
            maxX = Math.max(maxX, planet.x);
            maxY = Math.max(maxX, planet.y);
            planet.id = i;
        }

        var matrix = new Matrix<Planet>(maxX + 5, maxY + 5, Planet.class, null);

        for (Planet planet : list) {
            matrix.set(planet.y, planet.x, planet);
        }

        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                var planet = new Planet(j, i);

                if (planet.id != null) {
                    continue;
                }


                Integer closestDistance = null;
                var count = 0;
                Planet first = null;

                for (Planet temp : list) {
                    var distance = planet.distanceFrom(temp);

                    if (closestDistance == null || closestDistance > distance) {
                        closestDistance = distance;
                        count = 1;
                        first = temp;
                    } else if (closestDistance == distance) {
                        count++;
                    }


                }

                if (count == 1 && closestDistance > 0) {
                    planet.id = first.id;
                    matrix.set(i, j, planet);
                    first.count++;

                    if (i == 0 || j == 0 || i == matrix.getHeight() - 1 || j == matrix.getWidth() - 1) {
                        first.isEdge = true;
                    }
                }


            }
        }


        Integer maxCount = 0;
        for (Planet planet : list) {
            if (planet.count > maxCount && !planet.isEdge) {
                maxCount = planet.count;
            }
        }

        return String.valueOf(maxCount);
    }

    private List<Planet> getPlanets() {
        var p = Pattern.compile("(\\d*), (\\d*)");

        return getInput("").stream().map(str -> {
            var m = p.matcher(str);
            if (m.find()) {

                return new Planet(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            }

            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public String runPart2() {


        var list = getPlanets();
        var maxX = 0;
        var maxY = 0;
        for (int i = 0; i < list.size(); i++) {
            Planet planet = list.get(i);
            maxX = Math.max(maxX, planet.x);
            maxY = Math.max(maxX, planet.y);
            planet.id = i;
        }
        var count = 0;

        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                var planet = new Planet(j, i);

                var sum = 0;

                for (Planet temp : list) {
                    sum += planet.distanceFrom(temp);
                    if (sum > 10000) {
                        break;
                    }
                }
                if (sum < 10000) {
                    count++;
                }


            }
        }


        return String.valueOf(count);
    }

    @Override
    public String getAnswerPart1() {
        return "3890";
    }

    @Override
    public String getAnswerPart2() {
        return "40284";
    }
}
