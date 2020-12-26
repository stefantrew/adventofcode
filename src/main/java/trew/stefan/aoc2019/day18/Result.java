package trew.stefan.aoc2019.day18;

class Result {
    String path = "";
    Integer distance = null;

    Result getClone() {
        Result cp = new Result();
        cp.path = path;
        cp.distance = distance;
        return cp;
    }
}
