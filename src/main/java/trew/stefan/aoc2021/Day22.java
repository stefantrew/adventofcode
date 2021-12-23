package trew.stefan.aoc2021;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day22 extends AbstractAOC {

    int order = 1;

    @Override
    public String runPart1() {

        var total = 0L;
        order = 1;
        var list = getStringInput("").stream().map(this::mapper).collect(Collectors.toList());


        total = getTotal(list, -50, -50, -50, 101);

        return formatResult(total);
    }

    private long getTotal(java.util.List<Step> list, int lowerX, int lowerY, int lowerZ, int size) {
        var h = size;
        long total = 0L;
        var grid = new boolean[h][h][h];

        for (Step step : list) {
            boolean target = step.task.equals("on");

            if (step.startX > lowerX + size - 1) continue;
            if (step.endX < lowerX) continue;
            if (step.startY > lowerY + size - 1) continue;
            if (step.endY < lowerY) continue;
            if (step.startZ > lowerZ + size - 1) continue;
            if (step.endZ < lowerZ) continue;


            var startX = Math.max(step.startX, lowerX);
            var endX = Math.min(step.endX, lowerX + size - 1);
            var startY = Math.max(step.startY, lowerY);
            var endY = Math.min(step.endY, lowerY + size - 1);
            var startZ = Math.max(step.startZ, lowerZ);
            var endZ = Math.min(step.endZ, lowerZ + size - 1);

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
    @NoArgsConstructor
    class Step {

        String task;
        int startX;
        int endX;
        int startY;
        int endY;
        int startZ;
        int endZ;
        int order;
        Box box = new Box();
        List<Step> intersecting = new ArrayList<>();

        public Step(String task, int startX, int endX, int startY, int endY, int startZ, int endZ) {
            this.task = task;
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
            this.startZ = startZ;
            this.endZ = endZ;

            box.setWidth(endX - startX);
            box.setDepth(endY - startY);
            box.setHeight(endZ - startZ);

            //Instantiating the Translate class
            Translate translate = new Translate();

            //setting the properties of the translate object
            translate.setX(startX);
            translate.setY(startY);
            translate.setZ(startZ);

            box.getTransforms().add(translate);
        }

        public void explode(Step test) {


        }

        public boolean intersects(Step test) {

            return box.intersects(test.box.getBoundsInParent());
//            return inRange(test.startX, test.endX, startX, endX) && inRange(test.startY, test.endY, startY, endY) && inRange(test.startZ, test.endZ, startZ, endZ);
        }
    }


    Step mapper(String input) {

        var p = Pattern.compile("(on|off) x=(-?\\d*)\\.\\.(-?\\d*),y=(-?\\d*)\\.\\.(-?\\d*),z=(-?\\d*)\\.\\.(-?\\d*)");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Step(m.group(1), m.getInt(2), m.getInt(3), m.getInt(4), m.getInt(5), m.getInt(6), m.getInt(7));
        }
        return null;
    }


    @Override
    public String runPart2() {


        var total = 0L;

        var steps = getStringInput("_sample").stream().map(this::mapper).collect(Collectors.toList());

        Group root = new Group();
        for (Step step : steps) {
            step.order = order++;
            log.info("Step {}", step);
            root.getChildren().add(step.box);
        }

//        determineIntersects(steps);


        var res = findFirstIntersect(steps);

        log.info("Found {} {}", res.step.order, res.step2.order);

//        root.get
        return formatResult(total);
    }

    @AllArgsConstructor
    class Result {
        Step step;
        Step step2;
    }

    public Result findFirstIntersect(List<Step> steps) {

        for (int i = 0; i < steps.size(); i++) {
            var subject = steps.get(i);
            log.info("Finding for {}", subject.order);
            for (int j = i + 1; j < steps.size(); j++) {

                var test = steps.get(j);
                log.info("  testing {}", test.order);

                if (subject.intersects(test)) {

                    return new Result(subject, test);
                }

            }
        }

        return null;

    }


    public void determineIntersects(List<Step> steps) {

        for (int i = 0; i < steps.size(); i++) {
            var subject = steps.get(i);
            log.info("Finding for {}", subject.order);
            for (int j = i + 1; j < steps.size(); j++) {

                var test = steps.get(j);
                log.info("  testing {}", test.order);

                if (subject.intersects(test)) {
                    subject.intersecting.add(test);
                }

            }
        }

        for (Step step : steps) {
            log.info("{} {}", step.order, step.intersecting.size());
        }

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
