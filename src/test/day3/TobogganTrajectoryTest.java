package test.day3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import main.day3.TobogganTrajectory;

public class TobogganTrajectoryTest {

  @Test
  public void initializesWithSlope() {
    TobogganTrajectory tt = new TobogganTrajectory(3, 1);
    assertTrue("Slope x not correct", tt.getSlope().getDx() == 3);
    assertTrue("Slope y not correct", tt.getSlope().getDy() == 1);
  }

  @Test
  public void returnsCharacterBasedOnSlopedoesNotFlowOver() {
    TobogganTrajectory tt = new TobogganTrajectory(3, 1);
    // ..##.......
    // #...#...#..
    // .#....#..#.
    List<String> results = tt.getTraversed(Arrays.asList("..##.......", "#...#...#..", ".#....#..#."));
    assertEquals(Arrays.asList(".", ".", "#"), results);
  }

  @Test
  public void returnsCharacterBasedOnSlopeOverFlow1() {
    TobogganTrajectory tt = new TobogganTrajectory(3, 1);
    // abcd
    // efgh
    // ijkl
    // mnop
    // qrst
    List<String> results = tt.getTraversed(Arrays.asList("abcd", "efgh", "ijkl", "mnop", "qrst"));
    assertEquals(Arrays.asList("a", "h", "k", "n", "q"), results);
  }

  @Test
  public void returnsCharacterBasedOnSlopeOverFlowAndStepsOverLines1() {
    TobogganTrajectory tt = new TobogganTrajectory(3, 2);
    // abcd
    // efgh
    // ijkl
    // mnop
    // qrst
    List<String> results = tt.getTraversed(Arrays.asList("abcd", "efgh", "ijkl", "mnop", "qrst"));
    assertEquals(Arrays.asList("a", "l", "s"), results);
  }
  
  @Test
  public void returnsCharacterBasedOnSlopeOverFlowAndStepsOverLines2() {
    TobogganTrajectory tt = new TobogganTrajectory(3, 5);
    // abcd
    // efgh
    // ijkl
    // mnop
    // qrst
    List<String> results = tt.getTraversed(Arrays.asList("abcd", "efgh", "ijkl", "mnop", "qrst"));
    assertEquals(Arrays.asList("a"), results);
  }

  @Test
  public void returnsCharacterBasedOnSlopeOverFlow2() {
    TobogganTrajectory tt = new TobogganTrajectory(3, 1);
    // ..##.......
    // #...#...#..
    // .#....#..#.
    // ..#.#...#.#
    // .#...##..#.
    // ..#.##.....
    // .#.#.#....#
    // .#........#
    // #.##...#...
    // #...##....#
    // .#..#...#.#
    List<String> results = tt.getTraversed(Arrays.asList("..##.......", "#...#...#..", ".#....#..#.",
        "..#.#...#.#", ".#...##..#.", "..#.##.....", ".#.#.#....#", ".#........#", "#.##...#...",
        "#...##....#", ".#..#...#.#"));
    long trees = results.stream().filter(c -> c.equals("#")).count();
    assertTrue(String.format("Expected to encounter 7 trees, got %d", trees), trees == 7L);
  }

}
