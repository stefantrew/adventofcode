package trew.stefan.aoc2019;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;

@Slf4j
public class Day24 implements Day {

    class BugMap {

        char[][] map;
        char[][] temp;
        int level;

        @Getter
        BugMap parent;
        @Getter
        BugMap child;

        public BugMap(int level, BugMap parent, BugMap child) {
            map = new char[5][5];
            for (int i = 0; i < 25; i++) {
                map[i / 5][i % 5] = '.';
            }
            map[2][2] = '?';
            this.level = level;
            this.child = child;
            this.parent = parent;
        }

        public BugMap(char[][] map, int level) {
            this.map = map;
            this.level = level;
            this.parent = new BugMap(level - 1, null, this);
            this.child = new BugMap(level + 1, this, null);

        }

        public BugMap getTop() {
            if (parent == null) {
                return this;
            }
            return parent.getTop();
        }

        public char get(int x, int y) {
            if (x == -1) {
                return parent == null ? '.' : parent.get(1, 2);
            }
            if (y == -1) {
                return parent == null ? '.' : parent.get(2, 1);
            }
            if (x == 5) {
                return parent == null ? '.' : parent.get(3, 2);
            }
            if (y == 5) {
                return parent == null ? '.' : parent.get(2, 3);
            }

            return map[y][x];
        }

        public void setMap(char[][] temp) {
            map = temp;
        }

        public int bugCount() {
            int count = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (map[i][j] == '#') {
                        count++;
                    }
                }
            }
            return count;
        }

        public void printMap() {

            log.info("---------");
            log.info("level {} ", level);
            for (int i = 0; i < 5; i++) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 5; j++) {
                    builder.append(map[i][j]);
                }
                log.info(builder.toString());
            }
            log.info("---------");
        }

        void save() {
            map = temp;
            temp = new char[5][5];
        }

        void addParent() {
            if (bugCount() > 0) {

                this.parent = new BugMap(level - 1, null, this);
            }
        }

        void addChild() {
            if (bugCount() > 0) {
                this.child = new BugMap(level + 1, this, null);
            }
        }

        void process() {
            temp = new char[5][5];

            int[][] directions = {{0, 1,}, {1, 0}, {0, -1,}, {-1, 0}};

            for (int i = 0; i < 25; i++) {
                int x = i % 5;
                int y = i / 5;
                char c = this.get(x, y);

                int count = 0;
                for (int[] direction : directions) {
                    char t = this.get(x + direction[0], y + direction[1]);
                    if (t == '#') {
                        count++;
                    }
                }

                if (child != null) {

                    switch (i) {
                        case 11:
                            for (int j = 0; j < 5; j++) {
                                if (child.get(0, j) == '#') count++;
                            }
                            break;
                        case 7:
                            for (int j = 0; j < 5; j++) {
                                if (child.get(j, 0) == '#') count++;
                            }
                            break;
                        case 17:
                            for (int j = 0; j < 5; j++) {
                                if (child.get(j, 4) == '#') count++;
                            }
                            break;
                        case 13:
                            for (int j = 0; j < 5; j++) {
                                if (child.get(4, j) == '#') count++;
                            }
                            break;
                    }
                }

                if (c == '#') {
                    c = count == 1 ? '#' : '.';
                } else if (c == '.') {
                    c = count == 1 || count == 2 ? '#' : '.';
                }

                temp[y][x] = c;
            }

        }

    }


    public void processStack(BugMap parent) {

        parent.process();
        while (true) {
            BugMap child = parent.getChild();
            if (child == null) {
                break;
            }
            child.process();
            parent = child;
        }
    }

    public void saveStack(BugMap parent) {

        parent.addParent();
        parent.save();
        while (true) {
            BugMap child = parent.getChild();
            if (child == null) {
                break;
            }
            child.save();
            parent = child;
        }
        parent.addChild();
    }


    public void printStack(BugMap parent) {
        int count = parent.bugCount();
        parent.printMap();

        while (true) {
            BugMap child = parent.getChild();
            if (child == null) {
                break;
            }
            count += child.bugCount();
            child.printMap();
            parent = child;
        }
        log.info("{}", count);
    }

    public void run() {

        char[][] map = new char[][]{
                {'.', '.', '.', '.', '#'},
                {'#', '.', '.', '#', '.'},
                {'#', '.', '?', '#', '#'},
                {'.', '.', '#', '.', '.'},
                {'#', '.', '.', '.', '.'}
        };
        char[][] map2 = new char[][]{
                {'#', '#', '.', '#', '.'},
                {'#', '.', '.', '#', '.'},
                {'.', '.', '?', '.', '.'},
                {'.', '.', '.', '.', '#'},
                {'#', '.', '#', '#', '#'}
        };

        BugMap bugMap = new BugMap(map2, 0);
        for (int i = 0; i < 200; i++) {
            iterate(bugMap);
        }
        printStack(bugMap.getTop());

//        bugMap.process();
//        bugMap.printMap();

//        process(bugMap);
//        process(bugMap);
//        bugMap.printMap();
//        process(bugMap);
//        bugMap.printMap();
//        process(bugMap);
//        bugMap.printMap();

    }

    private void iterate(BugMap bugMap) {
        processStack(bugMap.getTop());
        saveStack(bugMap.getTop());
    }

    /*
    #..#.
    ####.
    ###.#
    ##.##
    .##..
     */


}

