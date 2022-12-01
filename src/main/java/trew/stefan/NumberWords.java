package trew.stefan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberWords {

    public NumberWords() {

    }

    public String print(int n) {

        if (n == 1000) {
            return "one thousand";
        }
        var sb = new StringBuilder();
        if (n < 20) {
            return printOnes(n);
        }

        if (n >= 100) {
            var hundreds = n / 100;
            n = n % 100;
            sb.append(printOnes(hundreds) + " hundred");
            if (n > 0) {
                sb.append(" and ");
            }
        }

        var tens = n / 10;
        var ones = n > 20 ? n % 10 : n;
        sb.append(printTens(tens));
        var str = printOnes(ones);
        if (str.length() > 0) {

            sb.append(" " + str);
        }

        return sb.toString();

    }

    private String printTens(int n) {
        if (n < 2) {
            return "";
        }

        switch (n) {
            case 2:
                return "twenty";
            case 3:
                return "thirty";
            case 4:
                return "forty";
            case 5:
                return "fifty";
            case 6:
                return "sixty";
            case 7:
                return "seventy";
            case 8:
                return "eighty";
            case 9:
                return "ninety";
            default:
                return "";
        }

    }

    private String printOnes(int n) {

        switch (n) {
            case 0:
                return "";
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            case 9:
                return "nine";
            case 10:
                return "ten";
            case 11:
                return "eleven";
            case 12:
                return "twelve";
            case 13:
                return "thirteen";
            case 14:
                return "fourteen";
            case 15:
                return "fifteen";
            case 16:
                return "sixteen";
            case 17:
                return "seventeen";
            case 18:
                return "eighteen";
            case 19:
                return "nineteen";
            default:
                return "";
        }

    }

    public static void main(String[] args) {

        var euler = new NumberWords();
        int count = 0;
        for (int i = 0; i <= 1000; i++) {
            var length = euler.print(i).replaceAll(" ", "").length();
            log.info("{} {}", i, length);
            count += length;
        }
        log.info("total: {}", count);

    }
}
