package main.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdaptedArray2 {

  private long[] sortedInput;

  public AdaptedArray2(long[] sortedInput) {
    this.sortedInput = sortedInput;
  }

  public long calculatePathsToAdapter() {
    Map<Long, Long> paths = new HashMap<>();
    paths.put(0L, 1L);
    for (int i = 1; i < sortedInput.length; i++) {
      long value = paths.getOrDefault(sortedInput[i] - 3, 0L)
          + paths.getOrDefault(sortedInput[i] - 2, 0L) + paths.getOrDefault(sortedInput[i] - 1, 0L);
      paths.put(sortedInput[i], value);
    }
    return paths.values().stream().reduce(Math::max).orElseThrow();
  }

  public void print() {
    Arrays.stream(this.sortedInput).forEach(i -> System.out.println(i));
  }

  public static void main(String[] args) throws IOException {
    long[] input = Files.lines(Paths.get("src/main/day10/input.txt"))
        .mapToLong(i -> Long.parseLong(i)).sorted().toArray();
    long[] realInputArray = new long[input.length + 2];
    realInputArray[0] = 0;
    for (int j = 0; j < input.length; j++) {
      realInputArray[j + 1] = input[j];
    }
    realInputArray[realInputArray.length - 1] = realInputArray[realInputArray.length - 2] + 3;
    AdaptedArray2 aa = new AdaptedArray2(realInputArray);
    System.out.println(aa.calculatePathsToAdapter());
  }
}
