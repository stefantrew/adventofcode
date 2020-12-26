package amazon.maze;

class AmazonMazePoint {

    public int x;
    public int y;
    public AmazonMaze.Direction direction;
    public AmazonMaze.EntryDirection entryDirection;

    public AmazonMazePoint() {

    }

    public AmazonMazePoint(int x, int y, AmazonMaze.Direction direction, AmazonMaze.EntryDirection entryDirection) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.entryDirection = entryDirection;
    }

}
