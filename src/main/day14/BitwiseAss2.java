import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitwiseAss2 {

  public static Set<Long> setNthBits(Set<Long> numbers, int n) {
    Set<Long> newNumbers = new HashSet<>();
    long num = 1L << n;
    for (Long l : numbers) {
      // because X will have unset the nth bit, do
      // an XOR to get the number with the nth bit set
      newNumbers.add(l ^ num);
    }
    return newNumbers;
  }

  public static void main(String[] args) throws IOException {
    Map<Long, Long> mem = new HashMap<>();
    Scanner scanner = new Scanner(new File("input.txt"));
    Pattern pattern = Pattern.compile("^(.*)=(.*)$");
    Pattern memPattern = Pattern.compile("mem\\[(.*)\\]");
    String mask = "";
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      Matcher matcher = pattern.matcher(line);
      matcher.find();
      String key = matcher.group(1).trim();
      String val = matcher.group(2).trim();
      if (key.equals("mask")) {
        mask = val;
      } else {
        Matcher memMatcher = memPattern.matcher(key);
        memMatcher.find();
        String memoryAddress = memMatcher.group(1);
        String valueBin = String.format("%1$36s", Long.toString(Long.parseLong(memoryAddress), 2))
            .replace(' ', '0');
        StringBuilder sb = new StringBuilder();

        // first unset all "X" bits and set all "1" bits
        for (int i = 0; i <= 35; i++) {
          String maskBit = mask.substring(i, i + 1);
          if (maskBit.equals("1")) {
            sb.append("1");
          } else if (maskBit.equals("X")) {
            sb.append("0");
          } else {
            sb.append(valueBin.substring(i, i + 1));
          }
        }
        Set<Long> addressSet = new HashSet<>();
        // now we have the initialized memory address
        long dec = Long.parseLong(sb.toString(), 2);
        addressSet.add(dec);

        // go through the mask again, this time at every
        // "X" we go through the entire set and toggle the
        // nth bit, putting the results back into the set
        for (int i = 35; i >= 0; i--) {
          if (mask.substring(i, i + 1).equals("X")) {
            addressSet.addAll(setNthBits(addressSet, 35 - i));
          }
        }
        addressSet.forEach(addr -> {
          mem.put(addr, Long.parseLong(val));
        });
      }

    }
    long sum = mem.values().stream().reduce((v1, v2) -> v1 + v2).orElse(-1L);
    System.out.println(sum);
  }
}
