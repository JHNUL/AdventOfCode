import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BinaryBoardingTest {

  @Test
  public void resolvesRowNumber1() {
    BinaryBoarding bb = new BinaryBoarding("FBFBBFFRLR");
    int i = bb.resolveRow();
    assertTrue(i == 44);
  }

  @Test
  public void resolvesRowNumber2() {
    BinaryBoarding bb = new BinaryBoarding("BFFFBBFRRR");
    int i = bb.resolveRow();
    assertTrue(i == 70);
  }

  @Test
  public void resolvesRowNumber3() {
    BinaryBoarding bb = new BinaryBoarding("FFFBBBFRRR");
    int i = bb.resolveRow();
    assertTrue(i == 14);
  }

  @Test
  public void resolvesRowNumber4() {
    BinaryBoarding bb = new BinaryBoarding("BBFFBBFRLL");
    int i = bb.resolveRow();
    assertTrue(i == 102);
  }

  @Test
  public void resolvesColumnNumber1() {
    BinaryBoarding bb = new BinaryBoarding("FBFBBFFRLR");
    int i = bb.resolveColumn();
    assertTrue(i == 5);
  }

  @Test
  public void resolvesColumnNumber2() {
    BinaryBoarding bb = new BinaryBoarding("BFFFBBFRRR");
    int i = bb.resolveColumn();
    assertTrue(i == 7);
  }

  @Test
  public void resolvesColumnNumber3() {
    BinaryBoarding bb = new BinaryBoarding("FFFBBBFRRR");
    int i = bb.resolveColumn();
    assertTrue(i == 7);
  }

  @Test
  public void resolvesColumnNumber4() {
    BinaryBoarding bb = new BinaryBoarding("BBFFBBFRLL");
    int i = bb.resolveColumn();
    assertTrue(i == 4);
  }

  @Test
  public void getSeatId() {
    BinaryBoarding bb = new BinaryBoarding("FBFBBFFRLR");
    int i = bb.getSeatId();
    assertTrue(i == 357);
  }

}
