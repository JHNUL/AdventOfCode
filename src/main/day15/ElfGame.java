package main.day15;

import java.util.stream.Stream;

public class ElfGame {
  public static void main(String[] args) {
    int[] startingNumbers = new int[] {1, 20, 8, 12, 0, 14};
    int arrayLen = 30000000;
    int[] numbers = Stream.iterate(-1, n -> n).limit(arrayLen).mapToInt(i -> i).toArray();
    int[] encountered = new int[arrayLen];
    int[] lastPositions = Stream.iterate(-1, n -> n).limit(arrayLen).mapToInt(i -> i).toArray();
    for (int i = 0; i < startingNumbers.length; i++) {

      // add the starting numbers
      numbers[i] = startingNumbers[i];

      // add starting numbers to encountered, each number value will be
      // index and the value in the index will be the times encountered
      encountered[startingNumbers[i]] = 1;

      // add the indices for the starting numbers, each number value will be
      // index and the value in the index will be the last position
      lastPositions[startingNumbers[i]] = i;

    }
    int startingPosition = startingNumbers.length;
    for (int j = startingPosition; j < numbers.length; j++) {
      int previous = numbers[j - 1];
      int timesEncountered = encountered[previous];
      if (timesEncountered == 1) {
        // if previous number is new:
        // - add zero to current position
        // - add one to encountered list for zero
        // - add last seen index to the current if it was new
        numbers[j] = 0;
        if (encountered[0] < 1) {
          lastPositions[0] = j;
        }
        encountered[0] = encountered[0] + 1;
      } else if (timesEncountered > 1) {
        // if number is not new
        // - count the difference between the last seen index
        //   and the current index and place that as the current value
        // - change the last seen index to the new position (previous index)
        // - add one to encountered list for the new number
        // - add last seen index to the current if it was new
        int diff = j - 1 - lastPositions[previous];
        numbers[j] = diff;
        lastPositions[previous] = j - 1;
        if (encountered[diff] < 1) {
          lastPositions[diff] = j;
        }
        encountered[diff] = encountered[diff] + 1;
      }
    }
    System.out.println(numbers[arrayLen - 1]);
  }
}
