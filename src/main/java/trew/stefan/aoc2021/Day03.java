package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day03 extends AbstractAOC {


    @ToString
    @AllArgsConstructor
    class Claim {
        //#1 @ 1,3: 4x4
        int id;
        int x;
        int y;
        int w;
        int h;


    }


    public Claim map(String input) {

        var p = Pattern.compile("#(\\d*) @ (\\d*),(\\d*): (\\d*)x(\\d*)");


        var matcher = p.matcher(input);
        if (!matcher.find()) {
            return null;
        }
        return new Claim(Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4)),
                Integer.parseInt(matcher.group(5)));
    }

    @Override
    public String runPart1() {

        List<Claim> list = getClaims();

        var map = buildMap(list);

        var sum = map.count(value -> value > 1);


        return String.valueOf(sum);
    }

    private Matrix<Integer> buildMap(List<Claim> list) {

        var map = new Matrix<>(1000, 1000, Integer.class, 0);

        for (var claim : list) {
            for (var y = claim.y; y < claim.y + claim.h; y++) {
                for (var x = claim.x; x < claim.x + claim.w; x++) {
                    map.apply(y, x, value -> value + 1);
                }
            }
        }
        return map;
    }


    private List<Claim> getClaims() {
        return getInput().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public String runPart2() {


        List<Claim> list = getClaims();

        var map = buildMap(list);
        for (var claim : list) {
            if (testClaim(map, claim)) {
                return String.valueOf(claim.id);
            }
        }

        return "";
    }

    private boolean testClaim(Matrix<Integer> map, Claim claim) {
        for (var y = claim.y; y < claim.y + claim.h; y++) {
            for (var x = claim.x; x < claim.x + claim.w; x++) {
                if (map.get(y, x) > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String getAnswerPart1() {
        return "101469";
    }

    @Override
    public String getAnswerPart2() {
        return "1067";
    }
}
