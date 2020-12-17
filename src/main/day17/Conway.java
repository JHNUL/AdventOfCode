package main.day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Conway {
  int[][][] grid;

  public Conway(int[][][] grid) {
    this.grid = grid;
  }

  public void printGridByLevels() {
    for (int z = 0; z < grid.length; z++) {
      System.out.println("level " + z);
      for (int y = 0; y < grid[z].length; y++) {
        for (int x = 0; x < grid[z][y].length; x++) {
          System.out.print(grid[z][y][x]);
          System.out.print(" ");
        }
        System.out.println("");
      }
    }
  }

  public boolean shouldChangeState(int z, int y, int x) {
    // If a cube is active and exactly 2 or 3 of its
    // neighbors are also active, the cube remains active.
    // Otherwise, the cube becomes inactive.
    // If a cube is inactive but exactly 3 of its neighbors
    // are active, the cube becomes active. Otherwise, the cube remains inactive.
    int adjacentActive = 0;
    int state = grid[z][y][x];
    for (int i = z - 1; i <= z + 1; i++) {
      for (int j = y - 1; j <= y + 1; j++) {
        for (int k = x - 1; k <= x + 1; k++) {
          if (!(i == z && j == y && k == x)) {
            try {
              if (this.grid[i][j][k] == 1) {
                adjacentActive++;
              }
            } catch (Exception e) {
              // System.out.println(e);
            }
          }
        }
      }
    }
    if (state == 1) {
      return adjacentActive < 2 || adjacentActive > 3;
    }
    return adjacentActive == 3;

  }

  public void runCycle() {
    boolean[][][] state = new boolean[grid.length][grid[0].length][grid[0][0].length];
    for (int z = 0; z < grid.length; z++) {
      for (int y = 0; y < grid[0].length; y++) {
        for (int x = 0; x < grid[0][0].length; x++) {
          boolean shouldChange = this.shouldChangeState(z, y, x);
          if (shouldChange) {
            state[z][y][x] = true;
          }
        }
      }
    }
    for (int z = 0; z < grid.length; z++) {
      for (int y = 0; y < grid[0].length; y++) {
        for (int x = 0; x < grid[0][0].length; x++) {
          if (state[z][y][x]) {
            int curr = grid[z][y][x];
            grid[z][y][x] = curr == 1 ? 0 : 1;
          }
        }
      }
    }
  }

  public int countActives() {
    int count = 0;
    for (int z = 0; z < grid.length; z++) {
      for (int y = 0; y < grid[0].length; y++) {
        for (int x = 0; x < grid[0][0].length; x++) {
          if (grid[z][y][x] == 1) {
            count++;
          }
        }
      }
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    List<String> input =
        Files.lines(Paths.get("src/main/day17/input.txt")).collect(Collectors.toList());
    int[][] grid2D = new int[input.size() + 20][input.size() + 20];
    for (int i = 0; i < input.size(); i++) {
      String[] pcs = input.get(i).split("");
      int[] k = Arrays.stream(pcs).map(p -> {
        if (p.equals("#")) {
          return 1;
        }
        return 0;
      }).mapToInt(j -> j).toArray();
      int[] newK = new int[k.length + 20];
      for (int l = 0; l < k.length; l++) {
        newK[l + 10] = k[l];
      }
      grid2D[i + 10] = newK;
    }
    int[][][] finalInput = new int[21][grid2D.length][grid2D.length];
    finalInput[11] = grid2D;
    Conway c = new Conway(finalInput);
    c.runCycle();
    c.runCycle();
    c.runCycle();
    c.runCycle();
    c.runCycle();
    c.runCycle();
    c.printGridByLevels();
    int active = c.countActives();
    System.out.println(active);
  }
}
