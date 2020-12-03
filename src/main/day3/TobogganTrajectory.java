package main.day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TobogganTrajectory {

  public class Slope {
    private int dx;
    private int dy;

    public Slope(int dx, int dy) {
      this.dx = dx;
      this.dy = dy;
    }

    public int getDx() {
      return dx;
    }

    public int getDy() {
      return dy;
    }

    public void setDx(int dx) {
      this.dx = dx;
    }

    public void setDy(int dy) {
      this.dy = dy;
    }
  }

  private Slope slope;

  public TobogganTrajectory(int dx, int dy) {
    this.slope = new Slope(dx, dy);
  }

  public Slope getSlope() {
    return slope;
  }

  public List<String> getTraversed(List<String> l) {
    List<String> r = new ArrayList<>();
    int x = 0; // starts with the first position
    for (int i = 0; i < l.size(); i += this.slope.getDy()) {
      String line = l.get(i);
      String character = String.valueOf(line.charAt(x));
      r.add(character);
      // resolve the next x-position
      int maxIndex = line.length() - 1;
      x = x + this.slope.getDx() > maxIndex ? x + this.slope.getDx() - maxIndex - 1
          : x + this.slope.getDx();
    }
    return r;
  }

  public static void main(String[] args) {
    try (Stream<String> s = Files.lines(Paths.get("src/main/day3/input.txt"))) {
      List<String> input = s.collect(Collectors.toList());
      TobogganTrajectory tt = new TobogganTrajectory(0, 0);
      tt.getSlope().setDx(1);
      tt.getSlope().setDy(1);
      long res1 = tt.getTraversed(input).stream().filter(c -> c.equals("#")).count();
      tt.getSlope().setDx(3);
      tt.getSlope().setDy(1);
      long res2 = tt.getTraversed(input).stream().filter(c -> c.equals("#")).count();
      tt.getSlope().setDx(5);
      tt.getSlope().setDy(1);
      long res3 = tt.getTraversed(input).stream().filter(c -> c.equals("#")).count();
      tt.getSlope().setDx(7);
      tt.getSlope().setDy(1);
      long res4 = tt.getTraversed(input).stream().filter(c -> c.equals("#")).count();
      tt.getSlope().setDx(1);
      tt.getSlope().setDy(2);
      long res5 = tt.getTraversed(input).stream().filter(c -> c.equals("#")).count();
      String message = String.format("%d, %d, %d, %d, %d multiplied together %d", res1, res2, res3,
          res4, res5, (res1 * res2 * res3 * res4 * res5));
      System.out.println(message);
    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
