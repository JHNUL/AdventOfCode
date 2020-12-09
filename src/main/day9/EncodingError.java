package main.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncodingError {

  long[] input;
  int preambleSize;

  public EncodingError(long[] input, int preambleSize) {
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
        // System.out.println("" + this.input[i] + " " +  this.input[j]);
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

  public static void main(String[] args) throws IOException {
    long[] lista =
        Files.lines(Paths.get("src/main/day9/input.txt")).mapToLong(Long::parseLong).toArray();
    EncodingError ee = new EncodingError(lista, 25);
    long i = ee.firstNonConfirmingNumber();
    if (i > 0) {
      String result = String.format("Found nonconfirming number: %d", i);
      System.out.println(result);
    }
    // 69316178
  }
}
