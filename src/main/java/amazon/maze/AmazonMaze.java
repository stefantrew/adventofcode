package amazon.maze;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class AmazonMaze implements Day {

    enum Direction {
        NE, SW, NW, SE
    }

    enum EntryDirection {
        N, S, E, W
    }

    @Override
    public void run() {

//        for (int i = 0; i < 1000; i++) {
//            int j = i % 64;
//            log.info("{} {} {} {}", i, Integer.toString(i, 2) , j, Integer.toString(j,2));
//        }


        long time = (new Date()).getTime();
        for (int n = 2; n < 7; n++) {
            long result = runLevel(n);

            long answer = 0;
            switch (n) {
                case 2:
                    answer = 2;
                    break;
                case 3:
                    answer = 78;
                    break;
                case 4:
                    answer = 10814;
                    break;
                case 5:
                    answer = 5695680;
                    break;
                case 6:
                    //1H 14m
                    //1H 7m
                    answer = 11870648744L;
                    break;
            }
            long now = (new Date()).getTime();

            log.info("{} {} => {} == {} | {}", new Date(), n, result, answer, now - time);
            time = now;

        }

    }

    public long runLevel(int n) {

        long count = 0L;
        long skip = 0;
        long limit = (long) Math.pow(2, n * n);
        List<MazeCpu> cpuses = new ArrayList<>();
        int cores = 30;
        long increments = limit / cores;
        for (int i = 0; i < cores; i++) {

            long endSeed = increments * (i + 1);
            if (i == cores - 1) {
                endSeed = limit;
            }
            MazeCpu mazeCpu = new MazeCpu(this, n, increments * i, endSeed, i);
            Thread thread = new Thread(mazeCpu);


            thread.start();


            cpuses.add(mazeCpu);
        }

        while (true) {
            try {
                Thread.sleep(1000);
                boolean flag = true;
                for (MazeCpu mazeCpu1 : cpuses) {

                    if (!mazeCpu1.done) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (MazeCpu mazeCpu1 : cpuses) {

            count += mazeCpu1.counter;
            skip += mazeCpu1.skipCounter;
        }

        log.info("SKIP {} {}", skip, limit);
        return count;

    }


}
