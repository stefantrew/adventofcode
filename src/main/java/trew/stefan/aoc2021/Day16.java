package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.math.BigInteger;
import java.util.*;

@Slf4j
public class Day16 extends AbstractAOC {

    class Packet {
        int version;
        int type;
        long value;
        List<Packet> subPackets = new ArrayList<>();

        public Packet(int version, int type) {
            this.type = type;
            this.version = version;
        }

        public int sumVersion() {
            return subPackets.stream().mapToInt(Packet::sumVersion).sum() + version;
        }

        public long process() {
            switch (type) {
                case 0:

                    return subPackets.stream().mapToLong(Packet::process).sum();
                case 1:
                    var prod = 1;
                    for (Packet subPacket : subPackets) {
                        prod *= subPacket.process();
                    }
                    return prod;
                case 2:
                    return subPackets.stream().mapToLong(Packet::process).min().orElse(0L);
                case 3:
                    return subPackets.stream().mapToLong(Packet::process).max().orElse(0L);
                case 4:
                    return value;
                case 5:
                    return subPackets.get(0).process() > subPackets.get(1).process() ? 1 : 0;
                case 6:
                    return subPackets.get(0).process() < subPackets.get(1).process() ? 1 : 0;
                case 7:
                    return subPackets.get(0).process() == subPackets.get(1).process() ? 1 : 0;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }

        }
    }

    static int binToDec(String s) {
        return Integer.parseInt(s, 2);
    }

    static String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }

    static LinkedList<Character> toLinkedList(String s) {
        var characters = new LinkedList<Character>();
        for (var c : s.toCharArray()) {
            characters.add(c);
        }
        return characters;
    }

    @Override
    public String runPart1() {
        var characters = toLinkedList(hexToBin(getStringInput().get(0)));
        var result = processPacket(characters);
        return formatResult(result.sumVersion());
    }

    @Override
    public String runPart2() {
        var characters = toLinkedList(hexToBin(getStringInput().get(0)));
        var result = processPacket(characters);
        return formatResult(result.process());
    }

    private String take(LinkedList<Character> vector, int n) {
        var sb = new StringBuilder();
        while (n-- > 0) {
            sb.append(vector.pop());
        }
        return sb.toString();
    }

    private Packet processPacket(LinkedList<Character> vector) {

        var version = binToDec(take(vector, 3));
        var type = binToDec(take(vector, 3));
        var result = new Packet(version, type);
        if (type == 4) {
            result.value = extractLiteral(vector);
        } else if (vector.pop() == '0') {
            var temp = toLinkedList(take(vector, binToDec(take(vector, 15))));

            while (!temp.isEmpty()) {

                result.subPackets.add(processPacket(temp));

            }
        } else {

            var counter = binToDec(take(vector, 11));
            while (counter-- > 0) {
                result.subPackets.add(processPacket(vector));
            }

        }
        return result;
    }

    private long extractLiteral(LinkedList<Character> vector) {
        var message = new StringBuilder();
        while (!vector.isEmpty()) {
            var sub = take(vector, 5);
            message.append(sub.substring(1));

            if (sub.charAt(0) == '0') {
                break;
            }

        }
        return Long.parseLong(message.toString(), 2);
    }


    @Override
    public String getAnswerPart1() {
        return "879";
    }

    @Override
    public String getAnswerPart2() {
        return "539051801941";
    }
}
