package amazon.maze;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MazeCpu implements Runnable {

    private final AmazonMaze amazonMaze;
    public boolean done = false;
    private int n;
    private int id;
    private long startSeed;
    private long endSeed;
    public long counter = 0L;
    public long skipCounter = 0L;
    private final long limit;

    public MazeCpu(AmazonMaze amazonMaze, int n, long startSeed, long endSeed, int id) {
        this.amazonMaze = amazonMaze;
        this.n = n;
        this.startSeed = startSeed;
        this.endSeed = endSeed;
        this.id = id;
        limit = (long) Math.pow(2, n * n);

//        log.info("=======================> {}: {} {} => {}", id, n, startSeed, endSeed);
    }

    @Override
    public void run() {

        Maze123 maze123 = new Maze123(n);
        for (long seed = startSeed; seed < endSeed; seed++) {
            if (seed % 1000000000 == 0) {
//                log.info("{}", seed);
            }

            if (n == 6) {

                boolean flag = isFlag(seed, n, 64, 21, 42);
                if (flag) {
                    skipCounter++;//7634432
                    continue;
                }

            } else if (n == 5) {

                boolean flag = isFlag(seed, n, 32, 21, 10);
                if (flag) {
                    skipCounter++;//7634432
                    continue;
                }
            } else if (n == 4) {
                boolean flag = isFlag(seed, n, 16, 10, 5);
                if (flag) {
                    skipCounter++;//7634432
                    continue;
                }
            }

            maze123.reseed(limit + seed);

            counter += maze123.isValid() ? 1 : 0;
//            log.info("{} {}", str, flag);
//        maze.printMap();
        }

        done = true;

//        log.info("{}", count);

    }

    private boolean isFlag(long seed, int n, int mod, int a1, int a2) {

        long a = seed;

        boolean flag = false;
        for (int x = 0; x < n; x++) {

            a = a >> n;
            int b = (int) (a % mod);
            if (b == a1 || b == a2) {
                flag = true;
            }
        }
        return flag;
    }
}
