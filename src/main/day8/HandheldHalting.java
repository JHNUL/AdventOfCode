package main.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HandheldHalting {
  public class Operation {
    private String type;
    private int arg;
    private boolean visited;
    private int index;

    public Operation(String type, int arg, boolean visited, int index) {
      this.type = type;
      this.arg = arg;
      this.visited = visited;
      this.index = index;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public int getArg() {
      return arg;
    }

    @Override
    public String toString() {
      return String.format("<Operation>{ type: %s, arg: %d, index: %d, visited: %s }", this.type,
          this.arg, this.index, this.visited);
    }

    public boolean getVisited() {
      return this.visited;
    }

    public int getIndex() {
      return index;
    }

    public void setVisited(boolean visited) {
      this.visited = visited;
    }
  }

  private List<Operation> operations;

  public HandheldHalting() {
    this.operations = new ArrayList<>();
  }

  public int getLastIndex() {
    return this.operations.size() - 1;
  }

  public void addOperation(String type, int arg) {
    int index = this.operations.size();
    this.operations.add(new Operation(type, arg, false, index));
  }

  public Operation getOperationAtIndex(int index) {
    return this.operations.get(index);
  }

  public void print() {
    this.operations.forEach(op -> {
      String s = String.format("%s %d %s", op.getType(), op.getArg(), op.getVisited());
      System.out.println(s);
    });
  }

  public void unvisitAll() {
    this.operations.forEach(op -> op.setVisited(false));
  }

  public int checkProgram() {
    int value = 0;
    int index = 0;
    try {
      while (true) {
        if (index == this.getLastIndex() + 1) {
          break;
        }
        Operation o = this.getOperationAtIndex(index);
        if (o.getVisited()) {
          throw new IllegalStateException("Should not visit an operation again");
        }
        o.setVisited(true);
        if (o.getType().equals("acc")) {
          value += o.getArg();
          index++;
        }
        if (o.getType().equals("nop")) {
          index++;
        }
        if (o.getType().equals("jmp")) {
          index = index + o.getArg();
        }
      }
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
    return value;
  }

  public static void main(String[] args) throws IOException {
    HandheldHalting hh = new HandheldHalting();
    Files.lines(Paths.get("src/main/day8/input.txt")).forEach(line -> {
      String[] pieces = line.split(" ");
      hh.addOperation(pieces[0].trim(), Integer.parseInt(pieces[1].trim()));
    });
    // Brute force it!
    for (int i = 1; i < hh.getLastIndex() + 1; i++) {
      Operation o = hh.getOperationAtIndex(i);
      String originalType = o.getType();
      if (originalType.equals("acc")) {
        continue;
      }
      if (originalType.equals("jmp")) {
        o.setType("nop");
      }
      if (originalType.equals("nop")) {
        o.setType("jmp");
      }
      int value = hh.checkProgram();
      if (value > 0) {
        String result = String.format("Program finished: %s, value: %d, changed: %s", value > 0,
            value, o.toString());
        System.out.println(result);
        break;
      }
      // switch type back to what it was
      o.setType(originalType);
      hh.unvisitAll();
    }
  }
}
