package trew.stefan.aoc2016;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day16 implements Day {


    enum ItemType {
        MICROCHIP, GENERATOR
    }


    class Item {

        ItemType itemType;
        char label;
        int id = 0;

        public Item(ItemType itemType, char label) {
            this.itemType = itemType;
            this.label = label;
        }

        @Override
        public String toString() {
            return label + (itemType == ItemType.MICROCHIP ? "M" : "G");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return id == item.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        boolean isGreater(Item o2) {
            return id > o2.id;
        }
    }

    @AllArgsConstructor
    class Move {

        int fromFloor;
        int toFloor;
        Item item1;
        Item item2;

    }

    class Building {

        Map<Integer, ArrayList<Item>> floors = new HashMap<>();

        Item eleItem1;
        Item eleItem2;

        int ele = 0;
        int ele2 = 0;
        boolean wentUp = false;
        List<Item> allItems = new ArrayList<>();

        List<Item> getFloor(int i) throws Exception {
            if (i < 0 || i > 3) {
                throw new Exception("Floor out of range");
            }
            if (floors.size() > 4) {
                throw new Exception("Floor count out of range");

            }
            return floors.computeIfAbsent(i, integer -> new ArrayList<>());
        }

        List<Building> getValidMoves() throws Exception {
            List<Item> movableItems = getFloor(ele);
            Set<Building> result = new HashSet<>();

            if (ele < 3) {

                for (Item item1 : movableItems) {
                    for (Item item2 : movableItems) {

                        Building clone = cloneBuilding();
                        clone.wentUp = true;
                        if (item1 == item2) {
                            clone.moveItem(item1, null, ele + 1);
                        } else {
                            if (item1.isGreater(item2)) {
                                continue;
                            }


                            clone.moveItem(item1, item2, ele + 1);
                        }
                        if (!clone.isInverse(lastMove) && clone.isValidState()) {
                            result.add(clone);
                        }
                    }
                }

            }

            if (ele > 0) {

                for (Item item1 : movableItems) {
                    for (Item item2 : movableItems) {


                        Building clone = cloneBuilding();
                        clone.wentUp = false;
                        if (item1 == item2) {
                            clone.moveItem(item1, null, ele - 1);
                        } else {
                            if (item1.isGreater(item2)) {
                                continue;
                            }

                            clone.moveItem(item1, item2, ele - 1);
                        }
                        if (!clone.isInverse(lastMove) && clone.isValidState()) {
                            result.add(clone);
                        }
                    }
                }

            }

            return new ArrayList<>(result);
        }

        private boolean isInverse(Move move) {
            if (move == null) {
                return false;
            }

            boolean b = move.item1 == lastMove.item1 &&
                    move.item2 == lastMove.item2 &&
                    move.fromFloor == lastMove.toFloor &&
                    move.toFloor == lastMove.fromFloor;


            return b;
        }

        void printBuilding() throws Exception {
            log.info(" ");
            log.info("Is valid: {}", isValidState());

            for (int i = 3; i >= 0; i--) {
                List<Item> floor = getFloor(i);
                StringBuilder sb = new StringBuilder();

                for (Item item : allItems) {
                    sb.append(floor.contains(item) ? item.toString() : ". ");
                    sb.append(" ");
                }

                log.info("F{} {} {}", i + 1, ele == i ? 'E' : '.', sb.toString());

            }
        }

        private Move lastMove = null;

        private void moveItem(Item item1, Item item2, int targetFloor) throws Exception {

            lastMove = new Move(ele, targetFloor, item1, item2);

            ele2 = ele;
            ele = targetFloor;
            eleItem1 = item1;
            eleItem2 = item2;
            for (int i = 0; i < 4; i++) {
                getFloor(i).remove(item1);
                if (item2 != null) {
                    getFloor(i).remove(item2);
                }
            }
            getFloor(targetFloor).add(item1);
            if (item2 != null) {

                getFloor(targetFloor).add(item2);
            }
        }

        private Building cloneBuilding() throws Exception {
            Building building = new Building();
            building.allItems = allItems;
            building.ele = this.ele;

            for (int i = 0; i < 4; i++) {
                for (Item item : getFloor(i)) {
                    building.getFloor(i).add(item);
                }
            }
            return building;
        }

        boolean isEndState() throws Exception {

            return ele == 3 && getFloor(3).size() == allItems.size();
        }

        boolean isValidState() throws Exception {

            int itemCount = 0;

            for (int i = 0; i < 4; i++) {
                List<Item> chips = new ArrayList<>();
                List<Item> gens = new ArrayList<>();
                itemCount += getFloor(i).size();
                for (Item item : getFloor(i)) {
                    if (item.itemType == ItemType.MICROCHIP) {
                        chips.add(item);
                    } else {
                        gens.add(item);
                    }

                }

                // No gens so safe floor
                if (gens.size() == 0) {
                    continue;
                }

                for (Item chip : chips) {
                    boolean found = false;
                    for (Item gen : gens) {
                        if (chip.label == gen.label) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        return false;
                    }
                }

            }

            if (itemCount != allItems.size()) {
                throw new Exception("Invalid Item Count " + itemCount + " " + allItems.size());
            }

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Building building = (Building) o;
            return building.hashCode() == o.hashCode();
        }

        String floorHash(int i) throws Exception {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            List<Item> floor = getFloor(i);
            floor.sort(Comparator.comparingInt(o -> o.id));

            for (Item item : floor) {
                sb.append(item.itemType);
                sb.append(item.label);
                sb.append(" ");
            }

            return sb.toString();
        }

        @Override
        public int hashCode() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                try {
                    sb.append(floorHash(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sb.append(ele);
            sb.append(wentUp);

            return Objects.hash(sb.toString());
        }

        public void addItem(int i, Item h) throws Exception {
            getFloor(i).add(h);
            allItems.add(h);
            h.id = allItems.size();
        }
    }

    HashMap<Integer, Integer> visited = new HashMap<>();
    HashMap<Integer, Integer> resultCache = new HashMap<>();

    @Override
    public void run() {

        try {
            int i = 0;
            log.info("=====> {}", loop(100));
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }
//    The first floor contains a polonium generator, a thulium generator, a thulium-compatible microchip, a promethium generator, a ruthenium generator, a ruthenium-compatible microchip, a cobalt generator, and a cobalt-compatible microchip.
//    The second floor contains a polonium-compatible microchip and a promethium-compatible microchip.
//    The third floor contains nothing relevant.
//    The fourth floor contains nothing relevant.

    //The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
    //The second floor contains a hydrogen generator.
    //The third floor contains a lithium generator.
    //The fourth floor contains nothing relevant.
    public Integer loop(int timeout) throws Exception {

        Building building = new Building();
        visited.clear();
        resultCache.clear();
        if (false) {


            building.addItem(0, new Item(ItemType.MICROCHIP, 'R'));
            building.addItem(0, new Item(ItemType.MICROCHIP, 'T'));
            building.addItem(0, new Item(ItemType.MICROCHIP, 'C'));
            building.addItem(1, new Item(ItemType.MICROCHIP, 'P'));
            building.addItem(1, new Item(ItemType.MICROCHIP, 'A'));

            building.addItem(0, new Item(ItemType.GENERATOR, 'P'));
            building.addItem(0, new Item(ItemType.GENERATOR, 'T'));
            building.addItem(0, new Item(ItemType.GENERATOR, 'R'));
            building.addItem(0, new Item(ItemType.GENERATOR, 'C'));
            building.addItem(0, new Item(ItemType.GENERATOR, 'A'));
        } else {
            building.addItem(1, new Item(ItemType.GENERATOR, 'H'));
            building.addItem(0, new Item(ItemType.MICROCHIP, 'H'));
            building.addItem(2, new Item(ItemType.GENERATOR, 'L'));
            building.addItem(0, new Item(ItemType.MICROCHIP, 'L'));
        }

        Integer result = iterate(building, 1, timeout, null);
        log.info("{}", result);
        return result;
    }

    public Integer iterate(Building building, int depth, int timeout, Building last) throws Exception {


        if (building.isEndState()) {
            return 0;
        }
        if (depth == timeout) {
            return null;
        }

        List<Building> validMoves = building.getValidMoves();
//        validMoves.clear();
        int hashCode = building.hashCode();
        if (resultCache.containsKey(hashCode) && resultCache.get(hashCode) != null) {
            int counter = visited.get(hashCode);
            return resultCache.get(hashCode);
        }
        visited.put(hashCode, depth);
        Integer min = null;

//        Collections.shuffle(validMoves);

        for (Building validMove : validMoves) {


//            if (last != null && validMove.hashCode() == last.hashCode()) {
//                continue;
//            }


            Integer result = iterate(validMove, depth + 1, timeout, building);
            if (result != null) {
//                return result + 1;
                if (min == null) {
                    min = result + 1;
                } else {
                    min = Math.min(min, result + 1);
                }
            }

        }

        resultCache.put(hashCode, min);

        return min;

    }


}
