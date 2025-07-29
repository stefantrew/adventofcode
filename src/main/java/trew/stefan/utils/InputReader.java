package trew.stefan.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class InputReader {

    private InputReader() {
    }

    public static void downloadFile(File result, int year, int day) {

        if (result.exists()) {
            log.info("File exists: {}", result.getAbsolutePath());
            return;
        }
        log.info("File does not exist: {}", result.getAbsolutePath());

        var url = "https://adventofcode.com/" + year + "/day/" + day + "/input";
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("Cookie", "session=" + System.getenv("aoc_session"));
            try (var in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                try (var out = new BufferedWriter(new FileWriter(result))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {

                        out.write(inputLine);
                        out.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> readIntegers(int year, int day, String suffix) {
        return readStrings(year, day, suffix).stream().map(Integer::parseInt).toList();
    }

    public static List<Double> readDoubles(int year, int day, String suffix) {
        return readStrings(year, day, suffix).stream().map(Double::parseDouble).toList();
    }

    public static List<Long> readLongs(int year, int day, String suffix) {
        return readStrings(year, day, suffix).stream().map(Long::parseLong).toList();
    }

    public static List<String> readStrings(int year, int day, String suffix) {

        var folder = Objects.requireNonNull(InputReader.class.getResource("/inputs/")).getFile();
        String pathname = folder + year + "/" + day + suffix + ".txt";
        String pathname2 = folder + year + "/" + day + "_sample.txt";
        File file = new File(pathname);
        File file2 = new File(pathname2);

        if (suffix.equals("") && !file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (suffix.isEmpty() && !file.exists()) {
            downloadFile(new File(pathname), year, day);
        }


        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }


    public static List<Long> readCommaStringsLong(int year, int day, String suffix) {
        List<String> lines = readStrings(year, day, suffix);
        String[] strs = lines.get(0).split(",");
        return Arrays.stream(strs).map(Long::parseLong).toList();
    }

    public static List<Integer> readCommaStrings(int year, int day, String suffix) {
        List<String> lines = readStrings(year, day, suffix);
        String[] strs = lines.get(0).split(",");
        return Arrays.stream(strs).map(Integer::parseInt).toList();
    }

    public static List<String> readSplitStrings(String separator, int year, int day, String suffix) {
        List<String> lines = readStrings(year, day, suffix);
        return Arrays.asList(lines.get(0).split(separator));
    }

    public static List<Integer> readSplitIntegers(String separator, int year, int day, String suffix) {
        return readSplitStrings(separator, year, day, suffix).stream().map(Integer::parseInt).toList();
    }

    public static List<Double> readSplitDoubles(String separator, int year, int day, String suffix) {
        return readSplitStrings(separator, year, day, suffix).stream().map(Double::parseDouble).toList();
    }

    public static List<Long> readSplitLongs(String separator, int year, int day, String suffix) {
        return readSplitStrings(separator, year, day, suffix).stream().map(Long::parseLong).toList();
    }


}

