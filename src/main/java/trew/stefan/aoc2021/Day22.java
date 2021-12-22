package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day22 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0L;

        var list = getStringInput("").stream().map(this::mapper).collect(Collectors.toList());


        total = getTotal(list, -50, -50, -50, 101);

        return formatResult(total);
    }

    private long getTotal(java.util.List<Item> list, int lowerX, int lowerY, int lowerZ, int size) {
        var h = size;
        long total = 0L;
        var grid = new boolean[h][h][h];

        for (Item item : list) {
            boolean target = item.task.equals("on");

            if (item.startX > lowerX + size - 1) continue;
            if (item.endX < lowerX) continue;
            if (item.startY > lowerY + size - 1) continue;
            if (item.endY < lowerY) continue;
            if (item.startZ > lowerZ + size - 1) continue;
            if (item.endZ < lowerZ) continue;


            var startX = Math.max(item.startX, lowerX);
            var endX = Math.min(item.endX, lowerX + size - 1);
            var startY = Math.max(item.startY, lowerY);
            var endY = Math.min(item.endY, lowerY + size - 1);
            var startZ = Math.max(item.startZ, lowerZ);
            var endZ = Math.min(item.endZ, lowerZ + size - 1);

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    for (int z = startZ; z <= endZ; z++) {
                        grid[x + lowerX + size - 1][y + lowerY + size - 1][z + lowerZ + size - 1] = target;
                    }
                }
            }

        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (grid[i][j][k]) {
                        total++;
                    }

                }
            }
        }
        return total;
    }

    @ToString
    @AllArgsConstructor
    class Item {

        String task;
        int startX;
        int endX;
        int startY;
        int endY;
        int startZ;
        int endZ;

    }

    Item mapper(String input) {

        var p = Pattern.compile("(on|off) x=(-?\\d*)\\.\\.(-?\\d*),y=(-?\\d*)\\.\\.(-?\\d*),z=(-?\\d*)\\.\\.(-?\\d*)");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item(m.group(1), m.getInt(2), m.getInt(3), m.getInt(4), m.getInt(5), m.getInt(6), m.getInt(7));
        }
        return null;
    }


    @Override
    public String runPart2() {


        var total = 0L;

        var list = getStringInput("_sample").stream().map(this::mapper).collect(Collectors.toList());


        int maxX = 0;
        int minX = 0;
        int maxY = 0;
        int minY = 0;
        int maxZ = 0;
        int minZ = 0;
        for (var s : list) {


            maxX = Math.max(maxX, s.endX);
            minX = Math.min(minX, s.startX);
            maxY = Math.max(maxY, s.endY);
            minY = Math.min(minY, s.startY);
            maxZ = Math.max(maxZ, s.endZ);
            minZ = Math.min(minZ, s.startZ);


        }
        log.info("X {} {} {} {}", maxX, minX, maxX - minX, (maxX - minX) / 100);
        log.info("Y {} {} {} {}", maxY, minY, maxY - minY, (maxY - minY) / 100);
        log.info("Z {} {} {} {}", maxZ, minZ, maxZ - minZ, (maxZ - minZ) / 100);

        log.info("{}", ((maxX - minX) * (maxY - minY) * (maxZ - minZ)) / (10000));
        for (int x = minX; x < maxX; x += 100) {
            log.info("{}", x);
            for (int y = minY; y < maxY; y += 100) {
                for (int z = minZ; z < maxZ; z += 100) {

                    total =+ getTotal(list, x, y, z, 100);
                }
            }
        }
//2758514936282235
        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "611378";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
