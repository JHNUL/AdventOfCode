package main.day22;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class CrabCombat {

  private Deque<Integer> firstPlayer;
  private Deque<Integer> secondPlayer;

  public CrabCombat(Deque<Integer> firstPlayer, Deque<Integer> secondPlayer) {
    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
  }

  public Deque<Integer> getSecondPlayer() {
    return secondPlayer;
  }

  public Deque<Integer> getFirstPlayer() {
    return firstPlayer;
  }

  public Deque<Integer> getWinner() {
    if (!firstPlayer.isEmpty() && !secondPlayer.isEmpty()) {
      throw new IllegalAccessError("Cannot determine winner");
    }
    return firstPlayer.isEmpty() ? secondPlayer : firstPlayer;
  }

  public boolean playRound() {
    // if one collection is already empty, game is over
    if (firstPlayer.isEmpty() || secondPlayer.isEmpty()) {
      return false;
    }
    int firstPlayerValue = firstPlayer.poll();
    int secondPlayerValue = secondPlayer.poll();
    // System.out.println("first player playing " + firstPlayerValue);
    // System.out.println("second player playing " + secondPlayerValue);
    if (firstPlayerValue > secondPlayerValue) {
      firstPlayer.addLast(firstPlayerValue);
      firstPlayer.addLast(secondPlayerValue);
    } else { // draw not possible
      secondPlayer.addLast(secondPlayerValue);
      secondPlayer.addLast(firstPlayerValue);
    }
    return true;
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
    CrabCombat cc = new CrabCombat(firstPlayer, secondPlayer);
    boolean gameOn = true;
    int count = 0;
    while (gameOn) {
      gameOn = cc.playRound();
      if (gameOn) {
        count++;
      }
    }
    Deque<Integer> winner = cc.getWinner();
    int multiplier = 1;
    int total = 0;
    while (!winner.isEmpty()) {
      total += multiplier * winner.pollLast();
      multiplier++;
    }
    System.out.println("Game finished in " + count + " rounds");
    System.out.println("Winning player's score is " + total);
  }
}
