package main.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class GameOfSeats {

  private int[][] grid;

  public GameOfSeats(int[][] grid) {
    this.grid = grid;
  }

  public void printGrid() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        System.out.print(grid[i][j]);
        System.out.print(" ");
      }
      System.out.println("");
    }
  }

  private boolean changeState(int y, int x) {
    int seat = this.grid[y][x];
    int adjacentOccupied = 0;
    for (int i = y - 1; i <= y + 1; i++) {
      for (int j = x - 1; j <= x + 1; j++) {
        if (!(i == y && j == x)) {
          try {
            if (this.grid[i][j] == 2) {
              adjacentOccupied++;
            }
          } catch (Exception e) {
            // System.out.println(e);
          }
        }
      }
    }
    if (seat == 1) { // vacant
      return adjacentOccupied == 0;
    }
    return adjacentOccupied >= 4;
  }

  public int countOccupied() {
    int sum = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == 2) {
          sum++;
        }
      }
    }
    return sum;
  }

  public int runGame() {
    boolean[][] stateChanges = new boolean[this.grid.length][this.grid[0].length];
    int changes = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        int val = this.grid[i][j];
        if (val != 0 && this.changeState(i, j)) {
          stateChanges[i][j] = true;
          changes++;
        }
      }
    }
    if (changes > 0) {
      for (int i = 0; i < stateChanges.length; i++) {
        for (int j = 0; j < stateChanges[0].length; j++) {
          if (stateChanges[i][j]) {
            int val = this.grid[i][j];
            this.grid[i][j] = val == 1 ? 2 : 1;
          }
        }
      }
    }
    return changes;
  }

  public static void main(String[] args) throws IOException {
    List<String> input =
        Files.lines(Paths.get("src/main/day11/input.txt")).collect(Collectors.toList());
    int[][] grid = new int[input.size()][input.get(0).length()];
    for (int i = 0; i < input.size(); i++) {
      String[] linePieces = input.get(i).split("");
      for (int j = 0; j < linePieces.length; j++) {
        grid[i][j] = linePieces[j].equals("L") ? 1 : 0;
      }
    }
    GameOfSeats gs = new GameOfSeats(grid);
    int changes = gs.runGame();
    int rounds = 0;
    while (changes > 0) {
      rounds++;
      changes = gs.runGame();
    }
    // gs.printGrid();
    System.out.println(gs.countOccupied());
  }
}
