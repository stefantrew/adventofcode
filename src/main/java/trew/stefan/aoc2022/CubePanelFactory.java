package trew.stefan.aoc2022;

import java.util.HashMap;
import java.util.Map;

import static trew.stefan.aoc2022.TileColour.*;

public class CubePanelFactory {


    public static Map<TileColour, CubePanel> buildCubeSamplePanels() {

        var map = new HashMap<TileColour, CubePanel>();

        var white = new CubePanel(WHITE, 0, 3, 8, 11);
        white.upTile = ORANGE;
        white.downTile = RED;
        white.rightTile = BLUE;
        white.leftTile = GREEN;

        map.put(white.colour, white);

        var red = new CubePanel(RED, 4, 7, 8, 11);
        red.upTile = WHITE;
        red.downTile = YELLOW;
        red.rightTile = BLUE;
        red.leftTile = GREEN;

        map.put(red.colour, red);

        var yellow = new CubePanel(YELLOW, 7, 11, 8, 11);
        yellow.upTile = RED;
        yellow.downTile = ORANGE;
        yellow.rightTile = BLUE;
        yellow.leftTile = GREEN;

        map.put(yellow.colour, yellow);

        var orange = new CubePanel(ORANGE, 4, 7, 0, 3);
        orange.upTile = WHITE;
        orange.downTile = YELLOW;
        orange.rightTile = GREEN;
        orange.leftTile = BLUE;

        map.put(orange.colour, orange);

        var blue = new CubePanel(BLUE, 7, 11, 7, 11);
        blue.upTile = RED;
        blue.downTile = ORANGE;
        blue.rightTile = WHITE;
        blue.leftTile = YELLOW;

        map.put(blue.colour, blue);

        var green = new CubePanel(GREEN, 4, 7, 4, 7);
        green.upTile = WHITE;
        green.downTile = YELLOW;
        green.rightTile = RED;
        green.leftTile = ORANGE;

        map.put(green.colour, green);

        return map;
    }

}
