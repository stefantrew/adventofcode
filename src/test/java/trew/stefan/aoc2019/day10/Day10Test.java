package trew.stefan.aoc2019.day10;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import trew.stefan.utils.Point;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class Day10Test {


    @Test
    public void testStraightLine() throws Exception {

        Point pointA = new Point(1, 1);
        Point pointB = new Point(2, 2);
        Point pointC = new Point(5, 2);

        StraightLine line = new StraightLine(pointA, pointB);
        log.info("Line {}", line);
        assert line.getC() == 0;
        assert line.getM() == 1;
        assert line.getRef() == 0;
        assert line.isRight();
        assert line.getLineType() == LineType.NORMAL;
        assert line.getDistance() == 2;

        StraightLine line2 = new StraightLine(pointA, pointC);
        log.info("Line {}", line2);
        assert line2.getC() == 0.75;
        assert line2.isRight();
        assert line2.getM() == 0.25;
        assert line2.getRef() == 0;
        assert line2.getLineType() == LineType.NORMAL;
        assert line2.getDistance() == 5;
    }

    @Test
    public void testStraightLine2() throws Exception {

        Point pointA = new Point(5, 11);
        Point pointB = new Point(3, 7);

        StraightLine line = new StraightLine(pointA, pointB);
        log.info("Line {}", line);
        assert line.getC() == 1;
        assert line.getM() == 2;
        assert !line.isRight();
        assert line.getRef() == 0;
        assert line.getLineType() == LineType.NORMAL;

    }

    @Test
    public void testHorizontalStraightLine() throws Exception {

        Point pointA = new Point(5, 11);
        Point pointB = new Point(3, 11);

        StraightLine line = new StraightLine(pointA, pointB);
        log.info("Line {}", line);
        assert line.getC() == 0;
        assert line.getM() == 0;
        assert !line.isRight();
        assert line.getRef() == 11;
        assert line.getLineType() == LineType.HORIZONTAL;

    }

    @Test
    public void testVerticalStraightLine() throws Exception {

        Point pointA = new Point(3, 2);
        Point pointB = new Point(3, 11);

        StraightLine line = new StraightLine(pointA, pointB);
        log.info("Line {}", line);
        assert line.getC() == 0;
        assert line.isUp();
        assert line.getM() == 0;
        assert line.getRef() == 3;
        assert line.getLineType() == LineType.VERTICAL;

    }

    @Test
    public void testOnLine() throws Exception {
        Point pointA = new Point(5, 11);
        Point pointB = new Point(3, 7);

        StraightLine line = new StraightLine(pointA, pointB);

        assert line.isOnLine(pointA);
        assert line.isOnLine(pointB);
        assert line.isOnLine(new Point(5, 11));
        assert !line.isOnLine(new Point(6, 11));

    }

    @Test
    public void testOnVerticalLine() throws Exception {
        Point pointA = new Point(3, 2);
        Point pointB = new Point(3, 11);
        StraightLine line = new StraightLine(pointA, pointB);

        assert line.isOnLine(pointA);
        assert line.isOnLine(pointB);
        assert line.isOnLine(new Point(3, 33));
        assert !line.isOnLine(new Point(6, 11));

    }

    @Test
    public void testOnHorizontalLine() throws Exception {
        Point pointA = new Point(5, 11);
        Point pointB = new Point(3, 11);
        StraightLine line = new StraightLine(pointA, pointB);

        assert line.isOnLine(pointA);
        assert line.isOnLine(pointB);
        assert line.isOnLine(new Point(311, 11));
        assert !line.isOnLine(new Point(6, 111));

    }

    @Test
    public void testCanSeeHorizontalLine() throws Exception {
        Point pointA = new Point(3, 11);
        Point pointB = new Point(5, 11);
        StraightLine line = new StraightLine(pointA, pointB);
// x   x  O  x  R x
        assert line.isOnLine(pointA);
        assert line.isOnLine(pointB);
        assert line.canSeePoint(new Point(-11, 11));
        assert line.canSeePoint(new Point(1, 11));
        assert line.canSeePoint(new Point(1, 121));
        assert line.canSeePoint(new Point(4, 11));
        assert !line.canSeePoint(new Point(6, 11));
        assert !line.canSeePoint(new Point(111, 11));

    }

    @Test
    public void testGetDegrees() {
        Point station = new Point(3, 11);
        Point pointN = new Point(3, 10);
        Point pointE = new Point(4, 11);
        Point pointS = new Point(3, 12);
        Point pointW = new Point(2, 11);


        log.info("N {}", station.getDegrees(pointN));
        log.info("E {}", station.getDegrees(pointE));
        log.info("S {}", station.getDegrees(pointS));
        log.info("W {}", station.getDegrees(pointW));

    }

    @Test
    public void testGetDegrees2() {
        Point station = new Point(8, 3);
        List<Point> points = new ArrayList<>();
        points.add(new Point(8, 1));
        points.add(new Point(9, 0));
        points.add(new Point(9, 1));
        points.add(new Point(10, 0));
        points.add(new Point(9, 2));
        points.add(new Point(11, 1));

        for (Point point : points) {

            log.info("N {}", station.getDegrees(point));
        }

    }
}
    



