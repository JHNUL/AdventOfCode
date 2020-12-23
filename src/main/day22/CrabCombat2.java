package main.day22;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CrabCombat2 {

  public Deque<Integer> copyOf(Deque<Integer> original, int size) {
    Deque<Integer> copied = new ArrayDeque<>();
    Deque<Integer> temp = new ArrayDeque<>();
    int limit = original.size() - size;
    while (original.size() > limit) {
      int top = original.poll();
      copied.add(top);
      temp.addFirst(top);
    }
    while (!temp.isEmpty()) {
      int top = temp.poll();
      original.addFirst(top);
    }
    return copied;
  }

  public int recursiveCombat(Deque<Integer> firstDeck, Deque<Integer> secondDeck,
      Set<String> memory) {
    while (!(firstDeck.isEmpty() || secondDeck.isEmpty())) {
      String state = Arrays.toString(firstDeck.toArray(Integer[]::new))
          + Arrays.toString(secondDeck.toArray(Integer[]::new));
      if (memory.contains(state)) {
        return 1;
      } else {
        memory.add(state);
      }
      int firstDeckTopCard = firstDeck.poll();
      int secondDeckTopCard = secondDeck.poll();
      int winningDeck = firstDeckTopCard > secondDeckTopCard ? 1 : 2;
      if (firstDeck.size() >= firstDeckTopCard && secondDeck.size() >= secondDeckTopCard) {
        winningDeck = this.recursiveCombat(this.copyOf(firstDeck, firstDeckTopCard),
            this.copyOf(secondDeck, secondDeckTopCard), new HashSet<>());
      }
      if (winningDeck == 1) {
        firstDeck.add(firstDeckTopCard);
        firstDeck.add(secondDeckTopCard);
      } else {
        secondDeck.add(secondDeckTopCard);
        secondDeck.add(firstDeckTopCard);
      }
    }
    if (firstDeck.isEmpty()) {
      this.winner = secondDeck;
      return 2;
    } else {
      this.winner = firstDeck;
      return 1;
    }
  }

  private Deque<Integer> winner;

  public CrabCombat2() {
    this.winner = null;
  }

  public Deque<Integer> getWinner() {
    return winner;
  }

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(new File("src/main/day22/input.txt"));
    Deque<Integer> firstPlayer = new ArrayDeque<>();
    Deque<Integer> secondPlayer = new ArrayDeque<>();
    boolean collectingSecondPlayer = false;
    scanner.nextLine(); // skip first line
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.indexOf("Player 2") >= 0) {
        collectingSecondPlayer = true;
      }
      try {
        int cardValue = Integer.parseInt(line);
        if (collectingSecondPlayer) {
          secondPlayer.add(cardValue);
        } else {
          firstPlayer.add(cardValue);
        }
      } catch (Exception e) {
        // not a number
      }
    }
    Set<String> memory = new HashSet<>();
    CrabCombat2 cc = new CrabCombat2();
    int i = cc.recursiveCombat(firstPlayer, secondPlayer, memory);
    Deque<Integer> winner = cc.getWinner();
    System.out.println(winner);
    int multiplier = 1;
    int total = 0;
    while (!winner.isEmpty()) {
      total += multiplier * winner.pollLast();
      multiplier++;
    }
    System.out.println("Winner is supposedly player " + i);
    System.out.println("Winning player's score is " + total);
  }
}
