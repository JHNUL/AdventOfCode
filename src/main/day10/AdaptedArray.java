package main.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdaptedArray {

  private int[] sortedInput;
  private int builtInAdapterRating;

  public AdaptedArray(int[] sortedInput) {
    this.sortedInput = sortedInput;
    this.builtInAdapterRating = this.sortedInput[this.sortedInput.length - 1] + 3;
  }

  public int getBuiltInAdapterRating() {
    return this.builtInAdapterRating;
  }

  public Map<Integer, Integer> getIntervals() {
    Map<Integer, Integer> intervalMap = new HashMap<>();
    for (int i = 1; i < sortedInput.length; i++) {
      int interval = sortedInput[i] - sortedInput[i - 1];
      if (intervalMap.putIfAbsent(interval, 1) != null) {
        intervalMap.put(interval, intervalMap.get(interval) + 1);
      }
    }
    return intervalMap;
  }

  public void print() {
    Arrays.stream(this.sortedInput).forEach(i -> System.out.println(i));
  }

  public static void main(String[] args) throws IOException {
    int[] input = Files.lines(Paths.get("src/main/day10/input.txt"))
        .mapToInt(i -> Integer.parseInt(i)).sorted().toArray();
    int[] realInputArray = new int[input.length + 2];
    realInputArray[0] = 0;
    for (int j = 0; j < input.length; j++) {
      realInputArray[j + 1] = input[j];
    }
    realInputArray[realInputArray.length - 1] = realInputArray[realInputArray.length - 2] + 3;
    AdaptedArray aa = new AdaptedArray(realInputArray);
    aa.print();
    Map<Integer, Integer> results = aa.getIntervals();
    System.out.println(results);
    System.out.println(results.get(1) * results.get(3));
    System.out.println(results.get(1) * results.get(3));
  }
}
