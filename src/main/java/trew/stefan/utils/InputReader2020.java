package trew.stefan.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class InputReader2020 {

    public static List<String> readStrings(int day, String suffix) {
        try {
            return Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/2020/" + day + suffix + ".txt").toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static List<String> readStrings(int year, int day, String suffix) {
        try {
            return Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/" + year + "/" + day + suffix + ".txt").toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Long> readCommaStringsLone(int day, String suffix) {
        try {
            List<String> lines = Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/2020/" + day + suffix + ".txt").toPath());
            String[] strs = lines.get(0).split(",");
            return Arrays.stream(strs).map(Long::parseLong).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Integer> readCommaStrings(int day, String suffix) {
        try {
            List<String> lines = Files.readAllLines(new File("C:/code/aoc/2019/src/main/resources/2020/" + day + suffix + ".txt").toPath());
            String[] strs = lines.get(0).split(",");
            return Arrays.stream(strs).map(Integer::parseInt).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}

