import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitwiseAss {
  public static void main(String[] args) throws IOException {

    // Data structures
    Map<Integer, Long> mem = new HashMap<>();
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
        int memoryAddress = Integer.parseInt(memMatcher.group(1));
        String valueBin =
            String.format("%1$36s", Long.toString(Long.parseLong(val), 2)).replace(' ', '0');
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 35; i++) {
          String maskBit = mask.substring(i, i + 1);
          if (maskBit.equals("1")) {
            sb.append("1");
          } else if (maskBit.equals("0")) {
            sb.append("0");
          } else {
            sb.append(valueBin.substring(i, i + 1));
          }
        }
        long dec = Long.parseLong(sb.toString(), 2);
        mem.put(memoryAddress, dec);
      }

    }
    // System.out.println(mem);
    long sum = mem.values().stream().reduce((v1, v2) -> v1 + v2).orElse(-1L);
    System.out.println(sum);
  }
}
