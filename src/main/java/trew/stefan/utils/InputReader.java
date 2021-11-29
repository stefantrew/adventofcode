package trew.stefan.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class InputReader {

    private static String s = "C:/code/aoc/2021/src/main/resources/inputs/";

    private static final String session = "***REMOVED***";

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
            con.addRequestProperty("Cookie", "session=" + session);
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

    public static List<String> readStrings(int day, String suffix) {


        File file = new File(s + day + suffix + ".txt");

        if (suffix.equals("") && !file.exists()) {
            downloadFile(new File(s + day + ".txt"), 2018, day);
        }

        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static List<Long> readCommaStringsLong(int day, String suffix) {
        List<String> lines = readStrings(day, suffix);
        String[] strs = lines.get(0).split(",");
        return Arrays.stream(strs).map(Long::parseLong).collect(Collectors.toList());
    }

    public static List<Integer> readCommaStrings(int day, String suffix) {
        List<String> lines = readStrings(day, suffix);
        String[] strs = lines.get(0).split(",");
        return Arrays.stream(strs).map(Integer::parseInt).collect(Collectors.toList());
    }

}

