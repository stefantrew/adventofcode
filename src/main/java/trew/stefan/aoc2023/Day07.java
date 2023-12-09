package trew.stefan.aoc2023;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day07 extends AbstractAOC {

    enum HandType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIRS,
        ONE_PAIR,
        HIGH_CARD

    }


    @AllArgsConstructor
    class Hand {

        String cards;
        HandType type;
        Integer bid;

    }

    @Override
    public String runPart1() {

        var total = 0L;

        var cards = new HashMap<Character, Integer>();

        cards.put('A', 14);
        cards.put('K', 13);
        cards.put('Q', 12);
        cards.put('J', 11);
        cards.put('T', 10);
        cards.put('9', 9);
        cards.put('8', 8);
        cards.put('7', 7);
        cards.put('6', 6);
        cards.put('5', 5);
        cards.put('4', 4);
        cards.put('3', 3);
        cards.put('2', 2);

        var list = getStringInput("");

        var unsorted = new ArrayList<Hand>();

        for (var s : list) {
            var hand = s.substring(0, 5);
            var bid = Integer.parseInt(s.substring(6));
            unsorted.add(new Hand(hand, compute(hand), bid));
        }

        List<Hand> sorted = sortHands(cards, unsorted);

        for (int i = 0; i < sorted.size(); i++) {
            Hand hand = sorted.get(i);

            total += (long) (i + 1) * hand.bid;
        }

        return formatResult(total);
    }

    private static List<Hand> sortHands(HashMap<Character, Integer> cards, ArrayList<Hand> unsorted) {
        return unsorted.stream().sorted((o1, o2) -> {
            if (o1.type == o2.type) {
                for (int i = 0; i < 5; i++) {
                    var c1 = cards.get(o1.cards.charAt(i));
                    var c2 = cards.get(o2.cards.charAt(i));


                    if (c1 > c2) {
                        return 1;
                    }
                    if (c1 < c2) {
                        return -1;
                    }

                }

            }
            return o2.type.compareTo(o1.type);
        }).toList();
    }


    @Override
    public String runPart2() {


        var total = 0L;

        var cards = new HashMap<Character, Integer>();

        cards.put('A', 14);
        cards.put('K', 13);
        cards.put('Q', 12);
        cards.put('T', 10);
        cards.put('9', 9);
        cards.put('8', 8);
        cards.put('7', 7);
        cards.put('6', 6);
        cards.put('5', 5);
        cards.put('4', 4);
        cards.put('3', 3);
        cards.put('2', 2);
        cards.put('J', 1);

        var list = getStringInput("");

        var unsorted = new ArrayList<Hand>();

        for (var s : list) {
            var hand = s.substring(0, 5);
            var bid = Integer.parseInt(s.substring(6));

            var type = hand.contains("J") ? compute2(hand) : compute(hand);
            if (hand.contains("J")) {

//                log.info("Hand: {} Type: {} Bid: {}", hand, type, bid);
            }
            unsorted.add(new Hand(hand, type, bid));
        }

        List<Hand> sorted = sortHands(cards, unsorted);

        for (int i = 0; i < sorted.size(); i++) {
            Hand hand = sorted.get(i);

            total += (long) (i + 1) * hand.bid;
        }

        return formatResult(total);
    }

    private HandType compute2(String hand) {

        var map = new HashMap<Character, Integer>();


        for (var c : hand.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        var jokerCount = map.get('J');
        map.remove('J');

        if (jokerCount == 5) {
            return HandType.FIVE_OF_A_KIND;
        }
        var max = map.values().stream().max(Integer::compareTo).get();

        if (max + jokerCount == 5) {
            return HandType.FIVE_OF_A_KIND;
        }

        if (max + jokerCount == 4) {
            return HandType.FOUR_OF_A_KIND;
        }

        if (map.size() == 2) {
            return HandType.FULL_HOUSE;
        }

        if (max + jokerCount == 3) {
            return HandType.THREE_OF_A_KIND;
        }

        return HandType.ONE_PAIR;
    }

    private HandType compute(String hand) {

        var map = new HashMap<Character, Integer>();

        for (var c : hand.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        var max = map.values().stream().max(Integer::compareTo).get();
        if (max == 5) {
            return HandType.FIVE_OF_A_KIND;
        }
        if (max == 4) {
            return HandType.FOUR_OF_A_KIND;
        }
        if (max == 3 && map.size() == 2) {
            return HandType.FULL_HOUSE;
        }
        if (max == 3) {
            return HandType.THREE_OF_A_KIND;
        }

        if (max == 2 && map.size() == 3) {
            return HandType.TWO_PAIRS;
        }

        if (max == 2) {
            return HandType.ONE_PAIR;
        }

        if (map.size() == 5) {
            return HandType.HIGH_CARD;
        }

        throw new IllegalArgumentException("Unknown hand: " + hand);
    }

    @Override
    public String getAnswerPart1() {
        return "";
    }

    @Override
    public String getAnswerPart2() {
        //251156055
        return "";
    }
}
