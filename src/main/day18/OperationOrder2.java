package main.day18;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Scanner;

public class OperationOrder2 {
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
          StringBuilder b = new StringBuilder();
          while (!pino.peek().equals("(")) {
            b.insert(0, pino.pop());
          }
          pino.pop();
          String lauseke = b.toString();
          // splittaa kertomerkkien mukaan
          // 2 * 2 + 3 -> 2, 2 + 3
          // käy läpi listan siten, että kaikki joissa on + merkki
          // arvioidaan ja laitetaan takaisin listaan, sen jälkeen
          // kaikki kerrotaan keskenään
          String[] kertolaskut = lauseke.split("\\*");
          for (int j = 0; j < kertolaskut.length; j++) {
            String pala = kertolaskut[j];
            if (pala.indexOf("+") >= 0) {
              String[] plusoperandit = pala.split("\\+");
              long arvo = Long.parseLong(plusoperandit[0].trim());
              for (int k = 1; k < plusoperandit.length; k++) {
                arvo += Long.parseLong(plusoperandit[k].trim());
              }
              kertolaskut[j] = String.valueOf(arvo);
            }
          }
          long luku = 1;
          for (int j = 0; j < kertolaskut.length; j++) {
            luku *= Long.parseLong(kertolaskut[j]);
          }
          pino.push(String.valueOf(luku));
        }
      }
      totlamad += Long.parseLong(pino.pop());
    }
    System.out.println(totlamad);
  }
}
