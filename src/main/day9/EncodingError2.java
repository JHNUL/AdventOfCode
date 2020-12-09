package main.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class EncodingError2 {

  long[] input;
  int preambleSize;

  public EncodingError2(long[] input, int preambleSize) {
    this.input = input;
    this.preambleSize = preambleSize;
  }

  public void print() {
    for (int i = 0; i < this.input.length; i++) {
      System.out.println(this.input[i]);
    }
  }

  private boolean checkForSum(int lowerIndex, int upperIndex, long sum) {
    for (int i = lowerIndex; i < upperIndex; i++) {
      for (int j = lowerIndex; j <= upperIndex; j++) {
        // System.out.println("" + this.input[i] + " " + this.input[j]);
        long first = this.input[i];
        long second = this.input[j];
        if (first != second && first + second == sum) {
          return true;
        }
      }
    }
    return false;
  }

  public long firstNonConfirmingNumber() {
    for (int i = this.preambleSize; i < this.input.length; i++) {
      long numToCheck = this.input[i];
      boolean canBeFormed = this.checkForSum(i - this.preambleSize, i - 1, numToCheck);
      if (!canBeFormed) {
        // String result = String.format("Checking number %d, result: %s", numToCheck, canBeFormed);
        // System.out.println(result);
        return numToCheck;
      }
    }
    return -1L;
  }

  public long[] contiguousSet(long sum) {
    for (int i = 0; i < this.input.length; i++) {
      long acc = this.input[i];
      for (int j = i + 1; j < this.input.length; j++) {
        acc += this.input[j];
        if (acc > sum) {
          break;
        }
        if (acc == sum) {
          String s = String.format("The range containing the sum is between %d and %d", i, j);
          System.out.println(s);
          return Arrays.copyOfRange(this.input, i, j + 1);
        }
      }
    }
    return null;
  }

  public static void main(String[] args) throws IOException {
    long[] lista =
        Files.lines(Paths.get("src/main/day9/input.txt")).mapToLong(Long::parseLong).toArray();
    EncodingError2 ee = new EncodingError2(lista, 25);
    long[] res = ee.contiguousSet(69316178);
    if (res != null) {
      long min = Arrays.stream(res).min().orElse(-1L);
      long max = Arrays.stream(res).max().orElse(-1L);
      String result = String.format("Min value %d, max value %d, sum %d", min, max, (min+max));
      System.out.println(result);
    }
    // 69316178 <-- real
    // 127 <-- test
  }
}
