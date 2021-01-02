package trew.stefan.aoc2019.day16;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.utils.NumberUtil;

import java.util.HashSet;

@Slf4j
public class Day16 implements AOCDay {

    String input1 = "5976963863863522779287383960061929616183024341182656262080375535764140970294244138198279929788165928888" +
            "82437933211542931027433259047571986688202138853076129009722733114991859299011176643875596577061100349927864890" +
            "02400852438961738219627639830515185618184324995881914532256988843436511730932141380017180796681870256240757580" +
            "45450509623061052043099753614534107458563710545640123820918711839704637358976640808012098481703569922842236695" +
            "26283442355428498507091813637031723347887445373576074463229037436446738001407709822832900685029723979707993282" +
            "49132774293609700245065522290562319955768092155530250003587007804302344866598232236645453817273744027537630";

    String input = "03036732577212944063491565474664";
    int[] basePattern = new int[]{0, 1, 0, -1};
    int[][] patterns;

    @Override
    public String runPart1() {

        patterns = new int[input.length()][];
        for (int b = 0; b < input.length(); b++) {
            patterns[b] = computePattern(input.length(), b + 1);

//            log.info("Pattern {}", patterns[b]);
        }

        byte[] values = NumberUtil.BurstDigits(input);
        for (int i = 0; i < 100; i++) {

            values = doRound(values);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(values[i]);
        }
        return sb.toString();
    }

    private byte[] doRound(byte[] values) {

        byte[] result = new byte[values.length];

        for (int i = 0; i < values.length; i++) {
            int sum = 0;
            for (int j = 0; j < values.length; j++) {
                int val = patterns[i][j + 1] * values[j];
                sum = (sum + val);
            }
            result[i] = (byte) (Math.abs(sum) % 10);
        }

        return result;
    }

    private int[] computePattern(int length, int position) {

        int[] result = new int[length + 1];

        int i = 0;
        while (i < length) {

            for (int value : basePattern) {
                for (int b = 0; b < position; b++) {
                    result[i++] = value;
                    if (i == length + 1) {
                        return result;
                    }
                }
            }

        }

        return result;

    }

    @Override
    public String runPart2() {
        int n = 100;
        patterns = new int[n][];
        for (int b = 0; b < n; b++) {
            patterns[b] = computePattern(n, b + 1);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < patterns[b].length; i++) {
                sb.append(patterns[b][i]);
            }
            log.info("Pattern {}", sb.toString());
        }
        return "";
    }

    public String run(int n) {
        StringBuilder sb1 = new StringBuilder();
        while (n-- > 0) {
            sb1.append(input);
        }
        String input2 = sb1.toString();
        patterns = new int[input2.length()][];
        for (int b = 0; b < input2.length(); b++) {
            patterns[b] = computePattern(input2.length(), b + 1);

//            log.info("Pattern {}", patterns[b]);
        }

        byte[] values = NumberUtil.BurstDigits(input2);
        for (int i = 0; i < 100; i++) {

            values = doRound(values);
        }
//        log.info("{}", values);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(values[i]);
        }
        return sb.toString();
    }
}
