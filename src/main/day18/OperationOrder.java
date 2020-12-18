package main.day18;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Scanner;

public class OperationOrder {
  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(new File("src/main/day18/input.txt"));
    long totlamad = 0;
    while (scanner.hasNextLine()) {
      ArrayDeque<String> pino = new ArrayDeque<>();
      String line = scanner.nextLine().replace(" ", "");
      StringBuilder sb = new StringBuilder();
      sb.append(line);
      sb.insert(0, "(");
      sb.insert(sb.toString().length(), ")");
      line = sb.toString();
      for (int i = 0; i < line.length(); i++) {
        String merkki = line.substring(i, i + 1);
        pino.push(merkki);
        if (merkki.equals(")")) {
          pino.pop();
          ArrayDeque<String> lauseke = new ArrayDeque<>();
          while (!pino.peek().equals("(")) {
            lauseke.addFirst(pino.pop());
          }
          pino.pop();
          long operandi = Long.parseLong(lauseke.pop());
          while (!lauseke.isEmpty()) {
            // operaattorien järjestys on se miten ne tulee vastaan
            String edellinen = lauseke.pop();
            if (edellinen.equals("*")) {
              operandi *= Long.parseLong(String.valueOf(lauseke.pop()));
            }
            if (edellinen.equals("+")) {
              operandi += Long.parseLong(String.valueOf(lauseke.pop()));
            }
          }
          pino.push(String.valueOf(operandi));
        }
      }
      totlamad += Long.parseLong(pino.pop());
    }
    System.out.println(totlamad);
  }
}
