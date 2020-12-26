package trew.stefan.aoc2019.day22;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day22 implements Day {


    public void run() {

        List<String> lines = InputReader2019.readStrings(22, "_2");
        long size = 23;
        int counter = 0;
        int hits = 0;
        long hugeSize = 119315717514047L;
        long hugeCycle = 101741582076661L;
        Long first = null;
        List<Long> deck = newDeck((int) size);

        int index = 2;
        deck = shuffle(deck, lines);

        log.info("2020 => {} {} {}", deck.get(index), deck, shuffle(lines, index, size));

        int inc = 5;
        for (int i = 0; i < size; i++) {
            int a = i % inc;
            int b = i / inc;
            int c = (int) size / inc + 1;
            log.info("{} => {} {} <{},{},{}>", i, deck.get(i), a * inc + b, a ,b, c);
        }

    }

    public long shuffle(List<String> lines, int index, long size) {

        long offset = 0;

        for (String line : lines) {
            if (line.startsWith("deal with increment")) {
                int increment = Integer.parseInt(line.substring(20));

            } else if (line.equals("deal into new stack")) {
//                deck = dealIntoNewStack(deck);
            } else if (line.startsWith("cut")) {

                offset += Integer.parseInt(line.substring(4));
            } else if (line.startsWith("Result")) {
//                log.info("Deck {}", deck);
//                log.info("Result {}", line);
//                deck = newDeck(deck.size());
            }
        }
        log.info("offset {}", offset);
        return (index + offset) % size;
    }

    public List<Long> shuffle(List<Long> deck, List<String> lines) {
//        long size = 119315717514047L;

        for (String line : lines) {
            if (line.startsWith("deal with increment")) {
                deck = dealWithIncrement(deck, Integer.parseInt(line.substring(20)));
            } else if (line.equals("deal into new stack")) {
                deck = dealIntoNewStack(deck);
            } else if (line.startsWith("cut")) {
                deck = cutDeck(deck, Integer.parseInt(line.substring(4)));
            } else if (line.startsWith("Result")) {
//                log.info("Deck {}", deck);
//                log.info("Result {}", line);
                deck = newDeck(deck.size());
            }
        }
//        if (deck.size() > 10) {
//            log.info("Result {}", deck.get(2019));
//        }
        return deck;
    }

    private List<Long> cutDeck(List<Long> deck, int position) {

        position += deck.size();
        position %= deck.size();
        List<Long> newDeck = deck.subList(position, deck.size());
        newDeck.addAll(deck.subList(0, position));

        return newDeck;
    }

    private List<Long> dealIntoNewStack(List<Long> deck) {
        Collections.reverse(deck);
        return deck;
    }

    private List<Long> dealWithIncrement(List<Long> deck, int increment) {
        List<Long> newDeck = newDeck(deck.size());

        int i = 0;
        for (long card : deck) {
            newDeck.set(i, card);
            i += increment;
            i %= deck.size();
        }

        return newDeck;
    }

    private List<Long> newDeck(int size) {
        List<Long> deck = new ArrayList<>();
        for (long i = 0; i < size; i++) {
            deck.add(i);
        }
        return deck;
    }

}
