package trew.stefan.aoc2020.day04;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day04 implements AOCDay {

    private int day = 4;
    private int lowerLimit;

    public boolean checkHeight(String value) {
        int result = 0;
        Pattern p = Pattern.compile("(\\d*)(in|cm)");

        Matcher matcher = p.matcher(value);
        while (matcher.find()) {

            int num1 = Integer.parseInt(matcher.group(1));
            String type = (matcher.group(2));

            if (type.equals("cm")) {
                return num1 >= 150 && num1 <= 193;
            }

            if (type.equals("in")) {
                return num1 >= 59 && num1 <= 76;
            }

        }
        return false;
    }

    public boolean checkHair(String value) {
        int result = 0;
        Pattern p = Pattern.compile("#[0-9a-f]{6}");

        Matcher matcher = p.matcher(value);
        while (matcher.find()) {

            return true;

        }
        return false;
    }

    public boolean checkPassport(String value) {
        if (value.length() != 9) {
            return false;
        }
        Pattern p = Pattern.compile("[0-9a-f]{9}");

        Matcher matcher = p.matcher(value);
        while (matcher.find()) {

            return true;

        }
        return false;
    }

    @Override
    public String runPart1() {

        List<String> lines = InputReader2020.readStrings(day, "");


        boolean flag_byr = false;
        boolean flag_iyr = false;
        boolean flag_eyr = false;
        boolean flag_hgt = false;
        boolean flag_hcl = false;
        boolean flag_ecl = false;
        boolean flag_pid = false;
        boolean flag_cid = false;

        int count = 0;
        for (String line : lines) {
            String[] arrOfStr = line.split(" ");
            for (String a : arrOfStr) {

                String[] b = a.split(":");
                switch (b[0]) {

                    case "byr":
                        flag_byr = true;
                        break;
                    case "iyr":
                        flag_iyr = true;
                        break;
                    case "eyr":
                        flag_eyr = true;
                        break;
                    case "hgt":
                        flag_hgt = true;
                        break;
                    case "ecl":
                        flag_ecl = true;
                        break;
                    case "hcl":
                        flag_hcl = true;
                        break;
                    case "pid":
                        flag_pid = true;
                        break;
                    case "cid":
                        flag_cid = true;
                        break;
                }
            }

            if (line.equals("")) {
                count = checkFlags(flag_byr, flag_iyr, flag_eyr, flag_hgt, flag_hcl, flag_ecl, flag_pid, flag_cid, count);

                flag_byr = false;
                flag_iyr = false;
                flag_eyr = false;
                flag_hgt = false;
                flag_hcl = false;
                flag_ecl = false;
                flag_pid = false;
                flag_cid = false;
            }
        }
        count = checkFlags(flag_byr, flag_iyr, flag_eyr, flag_hgt, flag_hcl, flag_ecl, flag_pid, flag_cid, count);

        return String.valueOf(count);
    }

    @Override
    public String runPart2() {

        List<String> lines = InputReader2020.readStrings(day, "");

        boolean flag_byr = false;
        boolean flag_iyr = false;
        boolean flag_eyr = false;
        boolean flag_hgt = false;
        boolean flag_hcl = false;
        boolean flag_ecl = false;
        boolean flag_pid = false;
        boolean flag_cid = false;

        int count = 0;
        for (String line : lines) {
            String[] arrOfStr = line.split(" ");
            for (String a : arrOfStr) {

                String[] b = a.split(":");
                switch (b[0]) {

                    case "byr":
                        flag_byr = checkRange(b[1], 1920, 2002);
                        break;
                    case "iyr":
                        flag_iyr = checkRange(b[1], 2010, 2020);
                        break;
                    case "eyr":
                        flag_eyr = checkRange(b[1], 2020, 2030);
                        break;
                    case "hgt":
                        flag_hgt = checkHeight(b[1]);
                        break;
                    case "ecl":
                        flag_ecl = checkEyeColor(b[1]);
                        break;
                    case "hcl":
                        flag_hcl = checkHair(b[1]);
                        break;
                    case "pid":
                        flag_pid = checkPassport(b[1]);
                        break;
                    case "cid":
                        flag_cid = true;
                        break;
                }
            }

            if (line.equals("")) {
                count = checkFlags(flag_byr, flag_iyr, flag_eyr, flag_hgt, flag_hcl, flag_ecl, flag_pid, flag_cid, count);

                flag_byr = false;
                flag_iyr = false;
                flag_eyr = false;
                flag_hgt = false;
                flag_hcl = false;
                flag_ecl = false;
                flag_pid = false;
                flag_cid = false;
            }
        }
        count = checkFlags(flag_byr, flag_iyr, flag_eyr, flag_hgt, flag_hcl, flag_ecl, flag_pid, flag_cid, count);

        return String.valueOf(count);

    }

    private int checkFlags(boolean flag_byr, boolean flag_iyr, boolean flag_eyr, boolean flag_hgt, boolean flag_hcl, boolean flag_ecl, boolean flag_pid, boolean flag_cid, int count) {
        if (
                flag_byr &&
                        flag_iyr &&
                        flag_eyr &&
                        flag_hgt &&
                        flag_hcl &&
                        flag_ecl &&
                        flag_pid) {
            count++;
//            log.info("valid");
        } else {
//            log.info("flag_byr {}", flag_byr);
//            log.info("flag_iyr {}", flag_iyr);
//            log.info("flag_eyr {}", flag_eyr);
//            log.info("flag_hgt {}", flag_hgt);
//            log.info("flag_hcl {}", flag_hcl);
//            log.info("flag_ecl {}", flag_ecl);
//            log.info("flag_pid {}", flag_pid);
//            log.info("flag_cid {}", flag_cid);
//            log.info("invalid");
        }
        return count;
    }

    private boolean checkEyeColor(String value) {
        switch (value) {
            case "amb":
            case "blu":
            case "brn":
            case "gry":
            case "grn":
            case "hzl":
            case "oth":
                return true;
        }
        return false;
    }

    private boolean checkRange(String s, int lowerLimit, int upperLimit) {
        try {

            int value = Integer.parseInt(s);
            return value >= lowerLimit && value <= upperLimit;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


}
