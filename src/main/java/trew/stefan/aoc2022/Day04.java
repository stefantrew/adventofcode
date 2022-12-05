package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Slf4j
public class Day04 extends AbstractAOC {


    @Override
    public String runPart1() {
        return String.valueOf(getRecords().stream().filter(WorkRecord::contains).count());
    }

    record WorkRecord(int as, int ae, int bs, int be) {

        boolean contains() {
            return (as >= bs && ae <= be) || (as <= bs && ae >= be);
        }

        boolean overlaps() {
            return contains() || (as >= bs && as <= be) || (ae >= bs && ae <= be);
        }
    }


    private List<WorkRecord> getRecords() {
        var result = new ArrayList<WorkRecord>();
        var list = getStringInput("");
        var p = Pattern.compile("(\\d*)-(\\d*),(\\d*)-(\\d*)");
        for (var s : list) {
            var m = new AOCMatcher(p.matcher(s));
            if (m.find()) {
                result.add(new WorkRecord(m.getInt(1), m.getInt(2), m.getInt(3), m.getInt(4)));
            }
        }

        return result;
    }


    @Override
    public String runPart2() {
        return String.valueOf(getRecords().stream().filter(WorkRecord::overlaps).count());
    }

    @Override
    public String getAnswerPart1() {
        return "448";
    }

    @Override
    public String getAnswerPart2() {
        return "794";
    }
}
