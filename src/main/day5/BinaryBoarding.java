package main.day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinaryBoarding {

  private final String encoded;

  public BinaryBoarding(String encoded) {
    if (encoded.length() != 10) {
      throw new IllegalArgumentException("Input must be exactly 10 characters");
    }
    this.encoded = encoded;
  }

  public int getSeatId() {
    return this.resolveRow() * 8 + this.resolveColumn();
  }

  public int resolveRow() {
    String rowpart = this.encoded.substring(0, 7);
    Integer[] rows = Stream.iterate(0, (e) -> e + 1).limit(127).toArray(Integer[]::new);
    for (int i = 0; i < rowpart.length(); i++) {
      char code = rowpart.charAt(i);
      int halfWay = (int) ((rows.length - 1) / 2);
      if (code == 'B') { // take the latter part
        rows = Arrays.copyOfRange(rows, halfWay + 1, rows.length); // end exclusive
      } else if (code == 'F') {
        rows = Arrays.copyOfRange(rows, 0, halfWay + 1);
      }
    }
    return rows[0];
  }

  public int resolveColumn() {
    String colpart = this.encoded.substring(7);
    int[] cols = {0, 1, 2, 3, 4, 5, 6, 7};
    for (int i = 0; i < colpart.length(); i++) {
      char code = colpart.charAt(i);
      int halfWay = (int) ((cols.length - 1) / 2);
      if (code == 'R') { // take the latter part
        cols = Arrays.copyOfRange(cols, halfWay + 1, cols.length); // end exclusive
      } else if (code == 'L') {
        cols = Arrays.copyOfRange(cols, 0, halfWay + 1);
      }
    }
    return cols[0];
  }

  public static void main(String[] args) {
    try (Stream<String> s = Files.lines(Paths.get("src/main/day5/input.txt"))) {
      List<Integer> list = s.map(line -> {
        BinaryBoarding bb = new BinaryBoarding(line);
        return bb.getSeatId();
      }).sorted().collect(Collectors.toList());
      for (int i = 1; i < list.size(); i++) {
        if (list.get(i) - list.get(i - 1) == 2) {
          System.out.println(list.get(i));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
