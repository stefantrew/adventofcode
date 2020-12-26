package trew.stefan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@Slf4j
@Accessors(chain = true)
@NoArgsConstructor
public class Point {

    private Integer X;
    private Integer Y;
    private Character c;
    private double degrees;
    public Point parent;
    private byte quadrant;

    public String getHash() {
        return X.toString() + " " + Y.toString() + " " + c + " "+ quadrant;
    }

    public Point(Integer x, Integer y, byte quadrant) {
        X = x;
        Y = y;
        this.quadrant = quadrant;
    }

    public Point(Integer x, Integer y, double degrees) {
        X = x;
        Y = y;
        this.degrees = degrees;
    }

    public Point(Integer x, Integer y, Character c) {
        X = x;
        Y = y;
        this.c = c;
    }

    public Point(Integer x, Integer y, Point parent, byte quadrant) {
        X = x;
        Y = y;
        this.parent = parent;
        this.quadrant = quadrant;
    }

    public Point(Integer x, Integer y, Point parent) {
        X = x;
        Y = y;
        this.parent = parent;
    }

    public Point(Integer x, Integer y) {
        X = x;
        Y = y;
        c = ' ';
    }

    public void addX(int dist) {
        X += dist;
    }

    public void addY(int dist) {
        Y += dist;
    }

    public Point getClone() {
        return new Point(X, Y, c);
    }

    public int getDistance() {
        return Math.abs(X) + Math.abs(Y);
    }

    public double getDegrees(Point point) {
        double degrees = Math.toDegrees(Math.atan2(Y - point.getY(), X - point.getX()));
        degrees -= 90;
//        degrees = (degrees + ( Math.PI / 2)) ;
        if (degrees < 0) {
            degrees += 360;
        }
        point.setDegrees(degrees);
        return degrees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(X, point.X) &&
                Objects.equals(Y, point.Y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    public int getDistance(Point point) {
        return Math.abs(X - point.getX()) + Math.abs(Y - point.getY());
    }

    public void addDegrees(int i) {
        log.info("Current degrees {}, change {}", degrees, i);
        degrees += i + 360;
        degrees %= 360;
        log.info("Final  degrees {}", degrees);
    }

    public void moveForward() throws Exception {
        switch ((int) degrees) {
            case 0:
                log.info("Moving Left");
                X--;
                break;
            case 90:
                log.info("Moving Up");
                Y--;
                break;
            case 180:
                log.info("Moving Right");
                X++;
                break;
            case 270:
                log.info("Moving Down");
                Y++;
                break;
            default:
                throw new Exception("Cannot resolve " + (int) degrees + " " + degrees);
        }

    }
}
