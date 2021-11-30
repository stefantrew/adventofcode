package trew.stefan.aoc2018;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day10 extends AbstractAOC {
    @ToString
    class Light {

        int x;
        int dx;
        int y;
        int dy;

        public Light(int x, int y, int dx, int dy) {
            this.x = x;
            this.dx = dx;
            this.y = y;
            this.dy = dy;
        }

        void tick() {
            x += dx;
            y += dy;

        }
    }
//position=<-3,  6> velocity=< 2, -1>

    Light mapper(String input) {

        var p = Pattern.compile("position=<([-\\s]?\\w*), ([-\\s]?\\w*)> velocity=<([-\\s]?\\w*), ([-\\s]?\\w*)>");
        var m = p.matcher(input);

        if (m.find()) {

            return new Light(Integer.parseInt(m.group(1).trim()),
                    Integer.parseInt(m.group(2).trim()),
                    Integer.parseInt(m.group(3).trim()),
                    Integer.parseInt(m.group(4).trim()));
        }
        return null;
    }

    void print(List<Light> list) {

        var maxX = list.get(0).x;
        var maxY = list.get(0).y;
        var minX = list.get(0).x;
        var minY = list.get(0).y;

        for (Light light : list) {
            maxX = Math.max(maxX, light.x);
            maxY = Math.max(maxY, light.y);

            minX = Math.min(minX, light.x);
            minY = Math.min(minY, light.y);
        }

        var xOffset = -minX;
        var yOffset = -minY;
        log.info("{} {} {} {}", yOffset, maxY, xOffset, maxX);

        var strings = new char[yOffset + maxY + 2][xOffset + maxX + 2];

        var sb = new StringBuilder();
        for (int j = 0; j < maxX + xOffset + 1; j++) {
            sb.append(" ");
        }
        for (int i = 0; i < strings.length; i++) {
            strings[i] = sb.toString().toCharArray();
        }

        for (Light light : list) {
            strings[light.y + yOffset][light.x + xOffset] = '#';
        }

        log.info("---------------------------------------------------------");
        for (int i = 0; i < strings.length; i++) {
            log.info("{}", new String(strings[i]));
        }
    }

    @Override
    public String runPart1() {


        List<Light> list = getLights();

        while (!tick(list)) ;

        return "HJBJXRAZ";
    }

    private List<Light> getLights() {
        var list = getInput("").stream().map(this::mapper).collect(Collectors.toList());
        return list;
    }

    public boolean tick(List<Light> list) {
        for (Light light : list) {
            light.tick();
        }

        var maxX = list.get(0).x;
        var maxY = list.get(0).y;
        var minX = list.get(0).x;
        var minY = list.get(0).y;

        for (Light light : list) {
            maxX = Math.max(maxX, light.x);
            maxY = Math.max(maxY, light.y);

            minX = Math.min(minX, light.x);
            minY = Math.min(minY, light.y);
        }

        return maxY - minY < 10;

    }

    @Override
    public String runPart2() {


        var list = getLights();
        var count = 1;
        while (!tick(list)) {
            count++;
        }
        return String.valueOf(count);
    }

    @Override
    public String getAnswerPart1() {
        return "HJBJXRAZ";
    }

    @Override
    public String getAnswerPart2() {
        return "10641";
    }
}
