package main.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class GameOfSeats2 {

  private int[][] grid;

  public GameOfSeats2(int[][] grid) {
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

  public boolean changeState(int y, int x) {
    int seat = this.grid[y][x];
    int visibleOccupied = 0;
    // check upper left diagonal
    int row = y - 1;
    int col = x - 1;
    while (row >= 0 && col >= 0) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      row--;
      col--;
    }
    // check lower left diagonal
    row = y + 1;
    col = x - 1;
    while (row < this.grid.length && col >= 0) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      row++;
      col--;
    }
    // check left horizontal
    row = y;
    col = x - 1;
    while (col >= 0) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      col--;
    }
    // check right horizontal
    row = y;
    col = x + 1;
    while (col < this.grid[0].length) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      col++;
    }
    // check lower right diagonal
    row = y + 1;
    col = x + 1;
    while (row < this.grid.length && col < this.grid[0].length) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      row++;
      col++;
    }
    // check upper right diagonal
    row = y - 1;
    col = x + 1;
    while (row >= 0 && col < this.grid[0].length) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      row--;
      col++;
    }
    // check upper vertical
    row = y - 1;
    col = x;
    while (row >= 0) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      row--;
    }
    // check lower vertical
    row = y + 1;
    col = x;
    while (row < this.grid.length) {
      if (this.grid[row][col] > 0) {
        if (this.grid[row][col] == 2) {
          visibleOccupied++;
        }
        break;
      }
      row++;
    }
    if (seat == 1) {
      return visibleOccupied == 0;
    }
    return visibleOccupied >= 5;
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
    GameOfSeats2 gs = new GameOfSeats2(grid);
    int changes = gs.runGame();
    int rounds = 0;
    while (changes > 0) {
    rounds++;
    changes = gs.runGame();
    }
    System.out.println(gs.countOccupied());
  }
}
