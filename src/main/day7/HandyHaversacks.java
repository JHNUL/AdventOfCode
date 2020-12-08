package main.day7;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandyHaversacks {
  public HandyHaversacks() {
    // foo
  }

  public void findValues(Map<String, List<String>> struct, String bag, List<String> visited) {
    List<String> containedBags = struct.get(bag);
    for (int i = 0; i < containedBags.size(); i++) {
      String containedBag = containedBags.get(i);
      visited.add(containedBag);
      findValues(struct, containedBag, visited);
    }
  }

  public static void main(String[] args) throws Exception {
    File file = new File("src/main/day7/input.txt");
    // build an adjacency-list representation
    Map<String, List<String>> adjacencyStructure = new HashMap<>();
    Map<String, Integer> containedBagCount = new HashMap<>();
    Scanner s = new Scanner(file);
    while (s.hasNextLine()) {
      String line = s.nextLine();
      // parsing rules: always the word contain
      Pattern r = Pattern.compile("^(.*)(bags contain)(.*)$");
      Matcher m = r.matcher(line);
      if (m.find()) {
        String outerBag = m.group(1).trim(); // the containing bag
        adjacencyStructure.put(outerBag, new ArrayList<>());
        containedBagCount.put(outerBag, 0);
        String otherBagsPart = m.group(3);
        if (!otherBagsPart.contains("no other bags")) {
          String[] other = otherBagsPart.split(",");
          Pattern p = Pattern.compile("(\\d+)(.*)(bag)");
          for (int i = 0; i < other.length; i++) {
            Matcher matcher = p.matcher(other[i]);
            if (matcher.find()) {
              int innerBagCount = Integer.parseInt(matcher.group(1).trim());
              containedBagCount.put(outerBag, containedBagCount.get(outerBag) + innerBagCount);
              String innerBag = matcher.group(2).trim(); // the contained bag
              adjacencyStructure.get(outerBag).add(innerBag);
            }
          }
        }
      }
    }
    HandyHaversacks hh = new HandyHaversacks();
    List<String> list = new ArrayList<>();
    list.add("shiny gold");
    hh.findValues(adjacencyStructure, "shiny gold", list);
    System.out.println(containedBagCount);
    System.out.println(list);
    long current = containedBagCount.get(list.get(0));
    long total = containedBagCount.get(list.get(0));
    for (int i = 1; i < list.size(); i++) {
      int count = containedBagCount.get(list.get(i));
      if (count > 0) {
        current = current * count;
        total += current;
      }

    }
    System.out.println(total);
    s.close();
  }
}
