package trew.stefan.aoc2019.day10;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.Point;

@Getter
@Slf4j
class StraightLine {

    private double m;
    private double c;
    private double c2;
    private int ref;
    private boolean isRight;
    private boolean isUp;
    private LineType lineType = LineType.NORMAL;
    private Point origin;
    private Point reference;
    private int distance;


    public StraightLine(Point pointA, Point pointB) throws Exception {

        if (pointA.equals(pointB)) {
            throw new Exception("Points cannot be the same");
        }

        origin = pointA;
        reference = pointB;
        distance = pointA.getDistance(pointB);

        double dx = pointA.getX() - pointB.getX();
        double dy = pointA.getY() - pointB.getY();

        if (dx == 0) {
            lineType = LineType.VERTICAL;
            ref = pointA.getX();
            isUp = pointB.getY() > pointA.getY();
        } else if (dy == 0) {
            lineType = LineType.HORIZONTAL;
            ref = pointA.getY();
            isRight = pointB.getX() > pointA.getX();

        } else {
            isRight = pointB.getX() > pointA.getX();

            m = dy / dx;
            c = pointA.getY() - m * pointA.getX();
            c2 = pointB.getY() - m * pointB.getX();
        }
    }

    public boolean isOnLine(Point point) {
        switch (lineType) {

            case NORMAL:
                return Math.abs(point.getY() - m * point.getX() - c) < 0.00001;
            case HORIZONTAL:
                return point.getY() == ref;
            case VERTICAL:
                return point.getX() == ref;
        }

        return false;
    }

    public boolean canSeePoint(Point point) {
        if (!isOnLine(point)) {
            return true;
        }

        switch (lineType) {

            case NORMAL:
            case HORIZONTAL:

                if (isRight) {
                    return point.getX() < reference.getX();
                }
                return point.getX() > reference.getX();

            case VERTICAL:
                if (point.getDistance(origin) < distance) {
                    return true;
                }

                if (isUp) {
                    return point.getY() < reference.getY();
                }
                return point.getY() > reference.getY();
        }

        return point.getDistance(origin) < distance || point.getDistance(reference) > distance;
    }


    @Override
    public String toString() {
        return "StraightLine{" +
                "m=" + m +
                ", c=" + c +
                ", c2=" + c2 +
                ", ref=" + ref +
                ", lineType=" + lineType +
                '}';
    }
}
