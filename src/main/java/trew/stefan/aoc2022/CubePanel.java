package trew.stefan.aoc2022;

import trew.stefan.utils.Direction;

public class CubePanel {

    TileColour colour;

    int topRow;
    int bottomRow;
    int leftCol;
    int rightCol;

    TileColour upTile;
    TileColour rightTile;
    TileColour downTile;
    TileColour leftTile;

    Direction newUpDirection;
    Direction newRightDirection;
    Direction newDownDirection;
    Direction newLeftDirection;

    public CubePanel(TileColour colour, int topRow, int bottomRow, int leftCol, int rightCol) {
        this.colour = colour;
        this.topRow = topRow;
        this.bottomRow = bottomRow;
        this.leftCol = leftCol;
        this.rightCol = rightCol;
    }

}

