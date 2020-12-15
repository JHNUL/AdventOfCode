package main.day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Ship {

  public enum Direction {
    EAST, WEST, NORTH, SOUTH
  }

  private Direction direction;
  private Map<Direction, Direction> leftTurns;
  private Map<Direction, Direction> rightTurns;
  private int x;
  private int y;

  public Ship(Direction d, int x, int y) {
    this.direction = d;
    this.x = x;
    this.y = y;
    this.leftTurns = new HashMap<>();
    this.leftTurns.put(Direction.NORTH, Direction.WEST);
    this.leftTurns.put(Direction.WEST, Direction.SOUTH);
    this.leftTurns.put(Direction.SOUTH, Direction.EAST);
    this.leftTurns.put(Direction.EAST, Direction.NORTH);
    this.rightTurns = new HashMap<>();
    this.rightTurns.put(Direction.NORTH, Direction.EAST);
    this.rightTurns.put(Direction.EAST, Direction.SOUTH);
    this.rightTurns.put(Direction.SOUTH, Direction.WEST);
    this.rightTurns.put(Direction.WEST, Direction.NORTH);
  }

  public int distanceTo() {
    return Math.abs(x) + Math.abs(y);
  }

  private void turnLeft(int turns) {
    for (int i = 0; i < turns; i++) {
      this.direction = this.leftTurns.get(this.direction);
    }
  }

  private void turnRight(int turns) {
    for (int i = 0; i < turns; i++) {
      this.direction = this.rightTurns.get(this.direction);
    }
  }

  public void processInstruction(String c, int value) {
    switch (c) {
      case "F": {
        if (this.direction == Direction.EAST) {
          this.x -= value;
        }
        if (this.direction == Direction.WEST) {
          this.x += value;
        }
        if (this.direction == Direction.NORTH) {
          this.y -= value;
        }
        if (this.direction == Direction.SOUTH) {
          this.y += value;
        }
        break;
      }
      case "E": {
        this.x -= value;
        break;
      }
      case "W": {
        this.x += value;
        break;
      }
      case "N": {
        this.y -= value;
        break;
      }
      case "S": {
        this.y += value;
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
    Ship ship = new Ship(Direction.EAST, 0, 0);
    Files.lines(Paths.get("testinput.txt")).forEach(l -> {
      String command = l.substring(0, 1);
      int value = Integer.parseInt(l.substring(1));
      ship.processInstruction(command, value);
    });
    System.out.println(ship.distanceTo());
  }
}


