package trew.stefan.aoc2020.day22;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.*;

@Slf4j
public class Day22 implements AOCDay {

    private int day = 22;


    @Override
    public String runPart1() {
        return String.valueOf(doRound(getPlayer1(), getPlayer2()));
    }

    @Override
    public String runPart2() {

        List<String> lines = InputReader2020.readStrings(day, "");

        Queue<Integer> player1 = getPlayer1();
        Queue<Integer> player2 = getPlayer2();


        return String.valueOf(doRound(player1, player2, 1));


    }

    String computeHash(Queue<Integer> queue) {
        List<Integer> array = new ArrayList<>(queue);
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i).append(",");
        }
        return sb.toString();

    }

    long calcScore(Queue<Integer> input) {
        int x = input.size();
        long result = 0;
        while (input.size() > 0) {
            result += input.poll() * x;
            x--;
        }

        return result;
    }

    Queue<Integer> buildSub(Queue<Integer> input, int n) {
        Queue<Integer> result = new LinkedList<>();
        for (Integer i : input) {
            if (result.size() == n) {
                return result;
            }
            result.add(i);
        }

        return result;
    }

    private long doRound(Queue<Integer> player1, Queue<Integer> player2) {

        while (player1.size() > 0 && player2.size() > 0) {
            int card1 = player1.poll();
            int card2 = player2.poll();

            if (card1 > card2) {
//                log.info("Player 1 wins the round!");
                player1.add(card1);
                player1.add(card2);
            } else {
//                log.info("Player 2 wins the round!");
                player2.add(card2);
                player2.add(card1);
            }
        }

        return player1.isEmpty() ? calcScore(player2) : calcScore(player1);
    }

    private long doRound(Queue<Integer> player1, Queue<Integer> player2, int game) {

//        log.info("======= GAME {} ===============", game++);

        HashSet<String> player1Hash = new HashSet<>();
        HashSet<String> player2Hash = new HashSet<>();

        boolean isFirstGame = game == 1;
        while (player1.size() > 0 && player2.size() > 0) {

            String hash1 = computeHash(player1);
            String hash2 = computeHash(player2);
            if (player1Hash.contains(hash1)) {
                return 1;
            } else if (player2Hash.contains(hash2)) {
                return 1;
            }

            player1Hash.add(hash1);
            player2Hash.add(hash2);


            int card1 = player1.poll();
            int card2 = player2.poll();


            if (card1 <= player1.size() && card2 <= player2.size()) {
                Queue<Integer> player1Sub = buildSub(player1, card1);
                Queue<Integer> player2Sub = buildSub(player2, card2);


                long result = doRound(player1Sub, player2Sub, ++game);
                if (result == 1) {
                    player1.add(card1);
                    player1.add(card2);
                } else {
                    player2.add(card2);
                    player2.add(card1);
                }
            } else if (card1 > card2) {
                player1.add(card1);
                player1.add(card2);
            } else {
                player2.add(card2);
                player2.add(card1);
            }

            if (player1.size() == 0) {
                if (isFirstGame) {
                    return calcScore(player2);
                }
                return 2;
            }

            if (player2.size() == 0) {
                if (isFirstGame) {
                    return calcScore(player1);
                }
                return 1;
            }

        }
        return 0;
    }

    private Queue<Integer> getPlayer2() {
        Queue<Integer> player2 = new LinkedList<>();
//        player2.add(5);
//        player2.add(8);
//        player2.add(4);
//        player2.add(7);
//        player2.add(10);
        player2.add(17);
        player2.add(4);
        player2.add(44);
        player2.add(9);
        player2.add(27);
        player2.add(18);
        player2.add(30);
        player2.add(42);
        player2.add(21);
        player2.add(26);
        player2.add(16);
        player2.add(48);
        player2.add(8);
        player2.add(15);
        player2.add(34);
        player2.add(50);
        player2.add(19);
        player2.add(43);
        player2.add(25);
        player2.add(1);
        player2.add(13);
        player2.add(31);
        player2.add(3);
        player2.add(5);
        player2.add(40);

        return player2;
    }

    private Queue<Integer> getPlayer1() {
        Queue<Integer> player1 = new LinkedList<>();
        player1.add(41);
        player1.add(33);
        player1.add(20);
        player1.add(32);
        player1.add(7);
        player1.add(45);
        player1.add(2);
        player1.add(12);
        player1.add(14);
        player1.add(29);
        player1.add(49);
        player1.add(37);
        player1.add(6);
        player1.add(11);
        player1.add(39);
        player1.add(46);
        player1.add(47);
        player1.add(38);
        player1.add(23);
        player1.add(22);
        player1.add(28);
        player1.add(10);
        player1.add(36);
        player1.add(35);
        player1.add(24);
//        player1.add(9);
//        player1.add(2);
//        player1.add(6);
//        player1.add(3);
//        player1.add(1);
        return player1;
    }
}
