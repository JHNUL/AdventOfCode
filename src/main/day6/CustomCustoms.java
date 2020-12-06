import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CustomCustoms {

  List<String> answers;

  public CustomCustoms() {
    this.answers = new ArrayList<>();
  }

  public void addAnswer(String a) {
    this.answers.add(a);
  }

  public void clear() {
    this.answers = new ArrayList<>();
  }

  public int getAnswerIntersections() {
    if (this.answers.isEmpty()) {
      return 0;
    }
    if (this.answers.size() == 1) {
      return this.answers.get(0).length();
    }
    Set<String> set = new HashSet<>();
    for (int i = 0; i < this.answers.get(0).length(); i++) {
      set.add(String.valueOf(this.answers.get(0).charAt(i)));
    }
    Set<String> intersecting = new HashSet<>();
    for (int i = 1; i < this.answers.size(); i++) {
      intersecting = new HashSet<>();
      for (int j = 0; j < this.answers.get(i).length(); j++) {
        String s = String.valueOf(this.answers.get(i).charAt(j));
        if (set.contains(s)) {
          // is already in set
          intersecting.add(s);
        }
      }
      set = Set.copyOf(intersecting);
    }
    return intersecting.size();
  }

  public static void main(String[] args) throws FileNotFoundException {
    File file = new File("src/main/day6/input.txt");
    Scanner scanner = new Scanner(file);
    CustomCustoms cc = new CustomCustoms();
    int totla = 0;
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      // must take into account each person's answer
      if (!line.isEmpty()) {
        cc.addAnswer(line);
      }
      if (line.isEmpty() || !scanner.hasNextLine()) {
        // end of group
        totla += cc.getAnswerIntersections();
        cc.clear();
      }
    }
    System.out.println(totla);
    scanner.close();
  }
}
