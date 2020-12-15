package main.day12;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Waypoint {

  private int x;
  private int y;
  private int shipX;
  private int shipY;

  public Waypoint(int x, int y, int shipX, int shipY) {
    this.x = x;
    this.y = y;

  }

  public int distanceTo() {
    return Math.abs(shipX) + Math.abs(shipY);
  }

  private void turnLeft(int turns) {
    for (int i = 0; i < turns; i++) {
      int temp = this.x;
      this.x = this.y * (-1);
      this.y = temp;
    }
  }

  private void turnRight(int turns) {
    for (int i = 0; i < turns; i++) {
      int temp = this.y;
      this.y = this.x * (-1);
      this.x = temp;
    }
  }

  public void printRelativeLocation() {
    String loc =
        String.format("shipX: %d, shipY: %d, x: %d, y: %d", this.shipX, this.shipY, this.x, this.y);
    System.out.println(loc);
  }

  public void processInstruction(String c, int value) {
    switch (c) {
      case "F": {
        this.shipX += (this.x * value);
        this.shipY += (this.y * value);
        break;
      }
      case "E": {
        this.x += value;
        break;
      }
      case "W": {
        this.x -= value;
        break;
      }
      case "N": {
        this.y += value;
        break;
      }
      case "S": {
        this.y -= value;
        break;
      }
      case "L": {
        if (value % 90 != 0) {
          System.out.println(value);
          throw new IllegalArgumentException("Invalid turn, yo!");
        }
        int turns = value / 90;
        this.turnLeft(turns);
        break;
      }
      case "R": {
        if (value % 90 != 0) {
          throw new IllegalArgumentException("Invalid turn, yo!");
        }
        int turns = value / 90;
        this.turnRight(turns);
        break;
      }
      default: {
        throw new IllegalArgumentException("Unsupported command");
      }
    }
  }

  public static void main(String[] args) throws Exception {
    // 10 units east and 1 unit north
    Waypoint waypoint = new Waypoint(10, 1, 0, 0);
    Files.lines(Paths.get("input.txt")).forEach(l -> {
      String command = l.substring(0, 1);
      int value = Integer.parseInt(l.substring(1));
      waypoint.processInstruction(command, value);
    });
    waypoint.printRelativeLocation();
    System.out.println(waypoint.distanceTo());
  }
}


