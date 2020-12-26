package amazon.maze;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Maze123 {


    private int N;
    private int offset;
    private long seed;
    private final AmazonMazePoint point;

    public boolean isEndPoint(AmazonMazePoint amazonMazePoint) {
        if (amazonMazePoint.direction == AmazonMaze.Direction.NW || amazonMazePoint.direction == AmazonMaze.Direction.NE) {
            return false;
        }

        return amazonMazePoint.y == N - 1;
    }

    public Maze123(int N) {
        this.N = N;
        point = new AmazonMazePoint();
    }

    public void reseed(long seed) {
        offset = (N * N) - 1;
        this.seed = seed;

    }

    public char getSymbol(int x, int y) {
        int index = y * N + x;
        return (seed >> offset - index & 1) == 1 ? '/' : '\\';
    }

    public boolean isValid() {
        for (int x = 0; x < N; x++) {
            point.direction = getSymbol(x, 0) == '/' ? AmazonMaze.Direction.NW : AmazonMaze.Direction.NE;
            point.x = x;
            point.y = 0;
            point.entryDirection = AmazonMaze.EntryDirection.N;
            boolean flag = checkStart(point);
            if (flag) {
                return true;
            }
        }

        return false;
    }

    public AmazonMazePoint getNext(AmazonMazePoint current) {

        AmazonMaze.Direction direction = current.direction;
        AmazonMaze.EntryDirection entryDirection = current.entryDirection;
        AmazonMaze.EntryDirection exitDirection = null;
        switch (direction) {

            case NE:
                exitDirection = entryDirection == AmazonMaze.EntryDirection.E ? AmazonMaze.EntryDirection.N : AmazonMaze.EntryDirection.E;
                break;
            case SW:
                exitDirection = entryDirection == AmazonMaze.EntryDirection.S ? AmazonMaze.EntryDirection.W : AmazonMaze.EntryDirection.S;
                break;
            case NW:
                exitDirection = entryDirection == AmazonMaze.EntryDirection.W ? AmazonMaze.EntryDirection.N : AmazonMaze.EntryDirection.W;
                break;
            case SE:
                exitDirection = entryDirection == AmazonMaze.EntryDirection.E ? AmazonMaze.EntryDirection.S : AmazonMaze.EntryDirection.E;
                break;
        }

        int x = current.x;
        int y = current.y;

        switch (exitDirection) {

            case N:
                y--;
                break;
            case S:
                y++;
                break;
            case E:
                x++;
                break;
            case W:
                x--;
                break;
        }

        if (x < 0 || x == N || y < 0 || y == N) {
            return null;
        }

        char nextMapPoint = getSymbol(x, y);

        AmazonMaze.Direction newDirection = null;
        AmazonMaze.EntryDirection newEntryDirection = null;
        switch (exitDirection) {

            case N:
                newEntryDirection = AmazonMaze.EntryDirection.S;
                newDirection = nextMapPoint == '/' ? AmazonMaze.Direction.SE : AmazonMaze.Direction.SW;
                break;
            case S:
                newEntryDirection = AmazonMaze.EntryDirection.N;
                newDirection = nextMapPoint == '/' ? AmazonMaze.Direction.NW : AmazonMaze.Direction.NE;
                break;
            case E:
                newEntryDirection = AmazonMaze.EntryDirection.W;
                newDirection = nextMapPoint == '/' ? AmazonMaze.Direction.NW : AmazonMaze.Direction.SW;
                break;
            case W:
                newEntryDirection = AmazonMaze.EntryDirection.E;
                newDirection = nextMapPoint == '/' ? AmazonMaze.Direction.SE : AmazonMaze.Direction.NE;
                break;
        }


        return new AmazonMazePoint(x, y, newDirection, newEntryDirection);
    }

    private boolean checkStart(AmazonMazePoint start) {

//            log.info("Start {}", start);
        AmazonMazePoint next = getNext(start);
        while (true) {

            if (next == null) {
//                    log.info("Fail");
                return false;
            } else if (isEndPoint(next)) {
//                    log.info("End");
                return true;

            }
//                log.info("{}", next);
            next = getNext(next);
        }
    }

    public void printMap() {

        log.info("---------------------------------------------------------------");
        for (int i = 0; i < N; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < N; j++) {
                builder.append(getSymbol(j, i));
            }
            log.info(builder.toString());
        }
        log.info("---------------------------------------------------------------");
    }
}
