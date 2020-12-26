package trew.stefan.aoc2019.day25;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
@Slf4j
public class Day25 implements Day {

    List<String> inventory = new ArrayList<>();
    List<String> droppedItems = new ArrayList<>();

    public void run() {
        List<String> lines = InputReader2019.readStrings(25, "");

        try {
            List<String> avoidItems = new ArrayList<>();
            avoidItems.add("escape pod");
            avoidItems.add("infinite loop");
            avoidItems.add("molten lava");
            avoidItems.add("photons");
            avoidItems.add("giant electromagnet");

            DayProcessor25 processor = new DayProcessor25(lines);

            readLine(processor);

//            sendString(processor, "inv");
            sendString(processor, "south");  // To Hallway [1]
            sendString(processor, "east");  // To Hot Chocolate Fountain[18]
            sendString(processor, "north");  // Storage [19]
            takeItem(processor, "candy cane");
            sendString(processor, "south");  // To Hot Chocolate Fountain[18]
            sendString(processor, "west");  // To Hallway [1]
            sendString(processor, "north");  // To Hull Breach [0]
            sendString(processor, "west");  // To Gift Wrapping [3]
            takeItem(processor, "mug");
            sendString(processor, "north"); // To warp drive [5]
            takeItem(processor, "easter egg");
            sendString(processor, "south"); // To Gift Wrapping [3]
            sendString(processor, "east"); // To Hull Breach [0]
            sendString(processor, "east"); // To passage [6]
            takeItem(processor, "coin");
            sendString(processor, "north"); // To Corridor [7]
            sendString(processor, "north"); // To Arcade [8]
            takeItem(processor, "hypercube");
            sendString(processor, "south"); // To Corridor [7]
            sendString(processor, "east"); // To Crew Quarters [9]
            takeItem(processor, "manifold");
            sendString(processor, "west"); // To Corridor [7]
            sendString(processor, "south"); // To passages [6]
            sendString(processor, "south"); // To science lab[10]
            sendString(processor, "east"); //   to Sick Bay[17]
            takeItem(processor, "pointer");
            sendString(processor, "west"); // To science lab[10]
            sendString(processor, "west"); // To Engineering [11]
            takeItem(processor, "astrolabe");
            sendString(processor, "north"); // To Kitchen [12]
            sendString(processor, "east"); // To    Holodeck [13]
//            sendString(processor, "drop pointer");
//            sendString(processor, "drop candy cane");
//            sendString(processor, "drop manifold");
//            sendString(processor, "drop mug");
//            sendString(processor, "drop easter egg");
//            sendString(processor, "drop coin");
//            sendString(processor, "drop astrolabe");
            getThroughSecurity(processor);
//            sendString(processor, "inv");


//            sendString(processor, "take escape pod");
//            sendString(processor, "south");
//            sendString(processor, "take infinite loop");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getThroughSecurity(DayProcessor25 processor) throws Exception {
        takeAll(processor);
        List<String> items = new ArrayList<>(inventory);

        sendString(processor, "north"); // Security Checkpoint [14]

        for (int i = 0; i < Math.pow(2, items.size()); i++) {
            String mask = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            dropAll(processor);

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < items.size(); j++) {
                if (mask.charAt(j) == '1') {
                    takeItem(processor, items.get(j));
                    sb.append(items.get(j) + ", ");
                }
            }
            log.info("{} {}", mask, sb.toString());

//
            String result = sendString(processor, "east"); // To  Pressure-Sensitive Floor [15]
            if (!result.contains("and you are ejected back to the checkpoint")) {
                takeAll(processor);
                return;
            }
        }
    }

    private void takeAll(DayProcessor25 processor) throws Exception {
        List<String> items = new ArrayList<>(droppedItems);
        for (String item : items) {
            takeItem(processor, item);


        }
    }

    public void takeItem(DayProcessor25 processor, String item) throws Exception {
        String result = sendString(processor, "take " + item);
        if (!result.startsWith("You take")) {
            throw new Exception("You don't see that item here.");
        }

        inventory.add(item);
        droppedItems.remove(item);

    }

    public void dropItem(DayProcessor25 processor, String item) throws Exception {
        String result = sendString(processor, "drop " + item);
        if (!result.startsWith("You drop")) {
            throw new Exception("You don't have that item.");
        }

        inventory.remove(item);
        droppedItems.add(item);

    }

    public void dropAll(DayProcessor25 processor) throws Exception {

        List<String> items = new ArrayList<>(inventory);
        for (String item : items) {
            dropItem(processor, item);


        }
    }

    public String sendString(DayProcessor25 processor, String str) throws Exception {
        log.info(str);
        for (char c : str.toCharArray()) {
//            log.info("{} {}", c, (byte) c);
            processor.run((int) c);
        }
//        log.info("{}", 10);
//        log.info("After input {}", );

        processor.run(10);
        return readLine(processor);

    }

    private String readLine(DayProcessor25 processor) {
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                Long output = processor.run(null);
                if (output == null) {
                    break;
                }
                if (output < 255)
                    sb.append((char) output.intValue());
                else sb.append(output);
            }
            log.info("{}", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
