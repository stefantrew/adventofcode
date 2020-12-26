package trew.stefan.aoc2019;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day12 implements Day {

    @Getter
    class Moon {

        SimplePoint position;
        SimplePoint velocity = new SimplePoint(0, 0, 0);
        String name;
        SimplePoint initialState;

        Moon(SimplePoint position, String name) {
            this.position = position;
            this.name = name;
            this.initialState = new SimplePoint(position.X, position.Y, position.Z);
        }

        public boolean isSameAsInitial() {
            return initialState.X == position.X && initialState.Y == position.Y && initialState.Z == position.Z;
        }

        public boolean isSameAsInitialX() {
            return initialState.X == position.X && velocity.X == 0;
        }

        public boolean isSameAsInitialY() {
            return initialState.Y == position.Y && velocity.Y == 0;
        }

        public boolean isSameAsInitialZ() {
            return initialState.Z == position.Z && velocity.Z == 0;
        }

        public void updateVelocity(Moon b) {
            SimplePoint positionB = b.position;

            if (positionB.X == position.X) {
                //skip
            } else if (positionB.X > position.X) {
                velocity.X++;
                b.velocity.X--;
            } else {
                velocity.X--;
                b.velocity.X++;
            }

            if (positionB.Y == position.Y) {
                //skip
            } else if (positionB.Y > position.Y) {
                velocity.Y++;
                b.velocity.Y--;
            } else {
                velocity.Y--;
                b.velocity.Y++;
            }

            if (positionB.Z == position.Z) {
                //skip
            } else if (positionB.Z > position.Z) {
                velocity.Z++;
                b.velocity.Z--;
            } else {
                velocity.Z--;
                b.velocity.Z++;
            }
        }

        @Override
        public String toString() {
            return String.format("Moon{name='%10s', pos=%s, vel=%s energy=%d}", name, position, velocity, isSameAsInitial());
        }

        public void applyVelocity() {
            position.X += velocity.X;
            position.Y += velocity.Y;
            position.Z += velocity.Z;
        }
    }

    Moon moon1;
    Moon moon2;
    Moon moon3;
    Moon moon4;

    Long firstX = null;
    Long firstY = null;
    Long firstZ = null;

    public void run() {
        List<Moon> moons = new ArrayList<>();
/*
7562
 */
//        moon1 =  new Moon(new SimplePoint(-1, 0, 2), "Io");
//        moon2 =  new Moon(new SimplePoint(2, -10, -7), "Europa");
//        moon3 =  new Moon(new SimplePoint(4, -8, 8), "Ganymede");
//        moon4 =  new Moon(new SimplePoint(3, 5, -1), "Callisto");

        moon1 = new Moon(new SimplePoint(-1, 7, 3), "Io");
        moon2 = new Moon(new SimplePoint(12, 2, -13), "Europa");
        moon3 = new Moon(new SimplePoint(14, 18, -8), "Ganymede");
        moon4 = new Moon(new SimplePoint(17, 4, -4), "Callisto");

//        moon1 = new Moon(new SimplePoint(-8, -10, 0), "Io");
//        moon2 = new Moon(new SimplePoint(5, 5, 10), "Europa");
//        moon3 = new Moon(new SimplePoint(2, -7, 3), "Ganymede");
//        moon4 = new Moon(new SimplePoint(9, -8, -3), "Callisto");
        int counter = 0;

        long i = 0;
        while (true) {
            i++;

            if (i % 100000000L == 0) {
                log.info("--------------- {} ---------------", i + 1);
            }
            doStep();

            boolean flag = true;
            if (!testX(i)) flag = false;
            if (!testY(i)) flag = false;
            if (!testZ(i)) flag = false;
            if (firstX != null && firstZ != null && firstY != null) {
                log.info("XY {}", PrimeUtil.GetGCDByModulus(firstX, firstY));
                long xy = firstX * firstY / PrimeUtil.GetGCDByModulus(firstX, firstY);

                log.info("ZY {}", PrimeUtil.GetGCDByModulus(firstZ, xy));
                long product = firstZ * xy / PrimeUtil.GetGCDByModulus(firstZ, xy);
                log.info("{} X={} Y={} Z={} M={}", i, firstX, firstY, firstZ, product);
                return;
            }
            if (flag) {
                log.info("{} {} {} {}", counter++, 2028 * counter, i, 4686774924L % (2028));
                return;
            }
//            return;
        }
    }

    private boolean testX(long i) {
        if (firstX != null) return true;
        if (!moon1.isSameAsInitialX()) return false;
        if (!moon2.isSameAsInitialX()) return false;
        if (!moon3.isSameAsInitialX()) return false;
        if (!moon4.isSameAsInitialX()) return false;
        if (firstX == null) firstX = i;
        log.info("X {} {}", i, NumberUtil.GetDivisors((int) i));
        return true;
    }

    private boolean testY(long i) {
        if (firstY != null) return true;
        if (!moon1.isSameAsInitialY()) return false;
        if (!moon2.isSameAsInitialY()) return false;
        if (!moon3.isSameAsInitialY()) return false;
        if (!moon4.isSameAsInitialY()) return false;
        if (firstY == null) firstY = i;
        log.info("Y {} {}", i, NumberUtil.GetDivisors((int) i));

        return true;
    }

    private boolean testZ(long i) {
        if (firstZ != null) return true;
        if (!moon1.isSameAsInitialZ()) return false;
        if (!moon2.isSameAsInitialZ()) return false;
        if (!moon3.isSameAsInitialZ()) return false;
        if (!moon4.isSameAsInitialZ()) return false;
        log.info("Z {} {}", i, NumberUtil.GetDivisors((int) i));

        if (firstZ == null) firstZ = i;
        return true;
    }

    private void doStep() {
        moon1.updateVelocity(moon2);
        moon1.updateVelocity(moon3);
        moon1.updateVelocity(moon4);

        moon2.updateVelocity(moon3);
        moon2.updateVelocity(moon4);

        moon3.updateVelocity(moon4);

        moon1.applyVelocity();
        moon2.applyVelocity();
        moon3.applyVelocity();
        moon4.applyVelocity();
    }

}
