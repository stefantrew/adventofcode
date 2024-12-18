package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.HashMap;


@Slf4j
public class Day09 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("").get(0);
        var total = 0L;

        var digits = list.chars().mapToObj(c -> (int) c - 48).toList();
        var space = digits.stream().reduce(0, Integer::sum);

        var disk = new ArrayList<Integer>(space);
        var fileId = 0;
        for (var i = 0; i < digits.size(); i++) {
            var isFile = i % 2 == 0;

            if (isFile) {
                for (int j = 0; j < digits.get(i); j++) {
                    disk.add(fileId);
                }
                fileId++;
            } else {

                for (int j = 0; j < digits.get(i); j++) {
                    disk.add(null);
                }
            }


        }

        while (disk.contains(null)) {

            var last = disk.get(disk.size() - 1);

            var index = disk.indexOf(null);
            disk.set(index, last);
            disk.remove(disk.size() - 1);

        }

        for (var i = 0; i < disk.size(); i++) {
            total += disk.get(i) * i;
        }

        return String.valueOf(total);
    }


    @Override
    public String runPart2() {

        var list = getStringInput("").get(0);
        var total = 0L;
        var fileSizeMap = new HashMap<Integer, Integer>();
        var fileStart = new HashMap<Integer, Integer>();

        var digits = list.chars().mapToObj(c -> (int) c - 48).toList();
        var space = digits.stream().reduce(0, Integer::sum);

        var disk = new ArrayList<Integer>(space);
        var fileId = 0;
        for (var i = 0; i < digits.size(); i++) {
            var isFile = i % 2 == 0;

            if (isFile) {
                fileSizeMap.put(fileId, digits.get(i));
                fileStart.put(fileId, disk.size());
                for (int j = 0; j < digits.get(i); j++) {
                    disk.add(fileId);
                }
                fileId++;
            } else {

                for (int j = 0; j < digits.get(i); j++) {
                    disk.add(null);
                }
            }


        }

        for (int i = fileId - 1; i >= 0; i--) {
            var size = fileSizeMap.get(i);
            var start = fileStart.get(i);
            Integer currentStart = null;
            var currentSize = 0;

            for (int j = 0; j < start; j++) {
                if (currentStart == null && disk.get(j) == null) {
                    currentStart = j;
                    currentSize = 1;
                    if (currentSize == size) {
                        for (int k = 0; k < size; k++) {
                            disk.set(currentStart + k, i);
                            disk.set(start + k, null);
                        }
                        break;
                    }
                } else if (currentStart != null && disk.get(j) == null) {
                    currentSize++;
                    if (currentSize == size) {
                        for (int k = 0; k < size; k++) {
                            disk.set(currentStart + k, i);
                            disk.set(start + k, null);
                        }
                        break;
                    }
                } else if (currentStart != null && disk.get(j) != null) {
                    currentStart = null;
                    currentSize = 0;

                }


            }


        }

        for (var i = 0; i < disk.size(); i++) {
            var i1 = disk.get(i);
            if (i1 == null) {
                i1 = 0;
            }
            total += i1 * i;
        }

        return String.valueOf(total);
    }

    @Override
    public String getAnswerPart1() {
        return "6216544403458";
    }

    @Override
    public String getAnswerPart2() {
        return "6237075041489";
    }
}
