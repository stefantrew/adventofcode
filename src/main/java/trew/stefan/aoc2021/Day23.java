package trew.stefan.aoc2021;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day23 extends AbstractAOC {

    Map<Integer, Integer> cache = new HashMap<>();
    private final ArrayList<Integer> validHallway;
    private final HashMap<Character, Integer> locations;

    public Day23() {
        validHallway = new ArrayList<Integer>();
        locations = new HashMap<Character, Integer>();
        locations.put('A', 3);
        locations.put('B', 5);
        locations.put('C', 7);
        locations.put('D', 9);
        validHallway.add(1);
        validHallway.add(2);
        validHallway.add(4);
        validHallway.add(6);
        validHallway.add(8);
        validHallway.add(10);
        validHallway.add(11);
    }

    @Override
    public String runPart1() {

        cache.clear();


        var list = getStringInput("");


        var map = new Game(locations, validHallway, 2);
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            map.grid[i] = s.toCharArray();

        }
        map.buildPods();

        return formatResult(doRound(map));
    }


    @Override
    public String runPart2() {

        cache.clear();


        var list = getStringInput("");

        list.add(3, "  #D#C#B#A#");
        list.add(4, "  #D#B#A#C#");

        var map = new Game(locations, validHallway, 4);
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            map.grid[i] = s.toCharArray();

        }
        map.buildPods();

        map.printGrid();
        return formatResult(doRound(map));
    }

    Integer doRound(Game map) {

        if (cache.containsKey(map.hashCode())) {

            var temp = cache.get(map.hashCode());
            if (temp == null || temp < map.currentCost) {
                return null;
            }
        }

        cache.put(map.hashCode(), map.currentCost);

        if (map.isWin) {
            return map.currentCost;
        }

        var validMoves = map.getValidMoves();

        Integer best = null;
        for (PodMove validMove : validMoves) {
            var newMap = map.applyMove(validMove);

            var result = doRound(newMap);
            if (result != null) {

                best = best == null ? result : Math.min(best, result);

            }
        }


        return best;
    }


    class Game {

        int currentCost = 0;
        char[][] grid;

        List<Integer> validHallway;
        List<Pod> pods = new ArrayList<>();

        Pod[][] rooms;
        String str = "";
        boolean isWin = false;
        Map<Character, Integer> locations;
        List<Pod> hallwayPods = new ArrayList<>();
        int size;

        public Game(Map<Character, Integer> locations, List<Integer> validHallway, int size) {
            this.locations = locations;
            this.validHallway = validHallway;
            this.size = size;

            grid = new char[3 + size][13];
            rooms = new Pod[4][size];

        }

        void buildMapString() {
            var sb = new StringBuilder();
            for (int i = 1; i < 2 + size; i++) {
                sb.append(new String(grid[i]).toCharArray());
            }

            str = sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Game game = (Game) o;
            return str.equals(game.str);
        }

        @Override
        public int hashCode() {
            return Objects.hash(str);
        }

        Game applyMove(PodMove move) {

            var map = new Game(this.locations, this.validHallway, size);
            for (int i = 1; i < 2 + size; i++) {
                map.grid[i] = new String(grid[i]).toCharArray();
            }

            map.grid[move.pod.y][move.pod.x] = '.';
            map.grid[move.ty][move.tx] = move.pod.type;
            map.currentCost += currentCost + move.cost;
            map.buildPods();
            return map;
        }

        List<Pod> getExitablePodsFromRooms() {
            var result = new ArrayList<Pod>();

            for (int i = 0; i < rooms.length; i++) {
                Pod topPod = null;
                var hasOther = false;

                for (Pod pod : rooms[i]) {
                    if (pod == null) {
                        continue;
                    }
                    if (topPod == null) {
                        topPod = pod;
                    } else if (pod.getCorrectRoom() != i) {
                        hasOther = true;
                    }
                }

                if (topPod == null) {
                    continue;
                }

                if (topPod.getCorrectRoom() != i || hasOther) {
                    result.add(topPod);
                }


            }

            return result;
        }


        void printGrid() {
            log.info("------------");
            for (int i = 0; i < 3 + size; i++) {
                log.info("{}", new String(grid[i]));
            }


        }

        void buildPods() {
            buildMapString();

            isWin = true;

            for (int i = 1; i < 2 + size; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    final var codePoint = grid[i][j];
                    if (Character.isAlphabetic(codePoint)) {
                        var pod = new Pod(codePoint, i, j);
                        pods.add(pod);


                        if (!pod.inCorrectRoom()) {
                            isWin = false;
                        }
                    }

                }

            }

            for (Pod pod : pods) {
                if (pod.inRoom) {
                    rooms[pod.room][pod.posInRoom] = pod;
                }
                if (pod.inHall) {
                    hallwayPods.add(pod);
                }
            }


        }


        List<PodMove> getValidMoves() {
            var result = new ArrayList<PodMove>();

            for (Pod hallwayPod : hallwayPods) {

                if (isCorrectRoomOpen(hallwayPod) && isPathToRoomOpen(hallwayPod, hallwayPods)) {

                    var count = 0;
                    for (Pod pod : rooms[hallwayPod.getCorrectRoom()]) {
                        if (pod != null && pod.type == hallwayPod.type) {
                            count++;
                        }
                    }

                    var ty = size + 1 - count;
                    result.add(new PodMove(hallwayPod, hallwayPod.getCorrectRoomPosition(), ty));
                }
            }

            var movablePods = getExitablePodsFromRooms();

            for (var movablePod : movablePods) {

                result.addAll(findValidHallwayMoves(hallwayPods, movablePod));
            }


            return result;
        }

        private boolean isPathToRoomOpen(Pod hallwayPod, List<Pod> hallwayPods) {
            var target = hallwayPod.getCorrectRoomPosition();
            if (hallwayPod.x < target) {
                for (int i = hallwayPod.x + 1; i < target; i++) {
                    for (Pod pod : hallwayPods) {
                        if (pod.x == i) {
                            return false;
                        }
                    }
                }

            } else {
                for (int i = hallwayPod.x - 1; i > target; i--) {
                    for (Pod pod : hallwayPods) {
                        if (pod.x == i) {
                            return false;
                        }
                    }
                }
            }


            return true;
        }

        private boolean isCorrectRoomOpen(Pod hallwayPod) {
            var room = rooms[hallwayPod.getCorrectRoom()];

            for (Pod pod : room) {
                if (pod != null && pod.type != hallwayPod.type) {
                    return false;
                }
            }

            return true;
        }

        private List<PodMove> findValidHallwayMoves(List<Pod> hallwayPods, Pod movablePod) {


            var result = new ArrayList<PodMove>();

            for (int i = movablePod.x; i < 13; i++) {
                if (!validHallway.contains(i)) {
                    continue;
                }
                var found = false;
                for (Pod hallwayPod : hallwayPods) {
                    if (hallwayPod.x == i) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
                result.add(new PodMove(movablePod, i, 1));

            }

            for (int i = movablePod.x; i > 0; i--) {
                if (!validHallway.contains(i)) {
                    continue;
                }
                var found = false;
                for (Pod hallwayPod : hallwayPods) {
                    if (hallwayPod.x == i) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
                result.add(new PodMove(movablePod, i, 1));

            }

            return result;
        }


    }

    @ToString(exclude = {"pod"})
    class PodMove {

        Pod pod;
        int tx;
        int ty;
        int cost;

        public PodMove(Pod pod, int tx, int ty) {
            this.pod = pod;
            this.tx = tx;
            this.ty = ty;

            var distance = Math.abs(pod.x - tx) + Math.abs(pod.y - ty);

            switch (pod.type) {
                case 'A':
                    cost = 1;
                    break;
                case 'B':
                    cost = 10;
                    break;
                case 'C':
                    cost = 100;
                    break;
                case 'D':
                    cost = 1000;
                    break;
            }
            cost = distance * cost;
        }
    }

    @ToString
    class Pod {

        char type;

        int y;
        int x;

        int cost;
        int room;
        int posInRoom;

        boolean inHall;
        boolean inRoom;
        Boolean isCorrect = null;

        boolean inCorrectRoom() {

            if (isCorrect != null) {
                return isCorrect;
            }

            isCorrect = x == getCorrectRoomPosition();
            return isCorrect;
        }

        int getCorrectRoom() {
            switch (type) {

                case 'A':
                    return 0;
                case 'B':
                    return 1;
                case 'C':
                    return 2;
                case 'D':
                    return 3;
            }
            return 0;

        }

        int getCorrectRoomPosition() {
            switch (type) {

                case 'A':
                    return 3;
                case 'B':
                    return 5;
                case 'C':
                    return 7;
                case 'D':
                    return 9;
            }
            return 0;

        }

        public Pod(char type, int y, int x) {
            this.type = type;
            this.y = y;
            this.x = x;
            inHall = y == 1;
            inRoom = y > 1;
            if (inRoom) {

                room = (x - 3) / 2;
                posInRoom = y - 2;
            }


        }


    }


    @Override
    public String getAnswerPart1() {
        return "14467";
    }

    @Override
    public String getAnswerPart2() {
        return "48759";
    }
}
