import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tiles {

  private List<Tile> tiles;

  public Tiles() {
    this.tiles = new ArrayList<>();
  }

  public void addTile(int id, int[][] content) {
    this.tiles.add(new Tile(id, content));
  }

  public Tile getTile(int index) {
    return this.tiles.get(index);
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  public void matchTiles() {
    for (int i = 0; i < tiles.size() - 1; i++) {
      Tile og = tiles.get(i);
      boolean res = false;
      for (int j = i + 1; j < tiles.size(); j++) {
        Tile compared = tiles.get(j);
        if (og.matchTo(compared)) {
          res = true;
        }
      }
      if (!res) {
        throw new IllegalStateException("No match for tile " + og.id);
      }
    }
  }

  public class Tile {
    private int id;
    private int[][] content;
    private String[] sides;
    private boolean[] matched;

    public Tile(int id, int[][] content) {
      this.id = id;
      this.content = content;
      this.sides = new String[4];
      this.matched = new boolean[4];
      this.initSides();
    }

    private void initSides() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < content.length; i++) {
        sb.append(content[0][i]);
      }
      String upper = sb.toString();
      sb = new StringBuilder();
      for (int i = 0; i < content.length; i++) {
        sb.append(content[content.length - 1][i]);
      }
      String lower = sb.toString();
      sb = new StringBuilder();
      for (int i = 0; i < content.length; i++) {
        sb.append(content[i][0]);
      }
      String left = sb.toString();
      sb = new StringBuilder();
      for (int i = 0; i < content.length; i++) {
        sb.append(content[i][content.length - 1]);
      }
      String right = sb.toString();
      this.sides[0] = upper;
      this.sides[1] = lower;
      this.sides[2] = left;
      this.sides[3] = right;
    }

    public int getMatchCount() {
      int count = 0;
      for (int i = 0; i < matched.length; i++) {
        if (matched[i]) {
          count++;
        }
      }
      return count;
    }


    public boolean matchTo(Tile t) {
      boolean anyMatch = false;
      for (int i = 0; i < t.getSides().length; i++) {
        if (t.getMatched()[i]) {
          anyMatch = true;
          continue;
        }
        StringBuilder sb = new StringBuilder();
        String otherSide = t.getSides()[i];
        String otherSideReversed = sb.append(otherSide).reverse().toString();
        if (this.sides[0].equals(otherSide)) { // upper
          t.getMatched()[i] = true;
          this.matched[0] = true;
          return true;
        }
        if (this.sides[0].equals(otherSideReversed)) { // upper
          t.getMatched()[i] = true;
          this.matched[0] = true;
          return true;
        }
        if (this.sides[1].equals(otherSide)) { // lower
          t.getMatched()[i] = true;
          this.matched[1] = true;
          return true;
        }
        if (this.sides[1].equals(otherSideReversed)) { // lower
          t.getMatched()[i] = true;
          this.matched[1] = true;
          return true;
        }
        if (this.sides[2].equals(otherSide)) { // left
          t.getMatched()[i] = true;
          this.matched[2] = true;
          return true;
        }
        if (this.sides[2].equals(otherSideReversed)) { // left
          t.getMatched()[i] = true;
          this.matched[2] = true;
          return true;
        }
        if (this.sides[3].equals(otherSide)) { // right
          t.getMatched()[i] = true;
          this.matched[3] = true;
          return true;
        }
        if (this.sides[3].equals(otherSideReversed)) { // right
          t.getMatched()[i] = true;
          this.matched[3] = true;
          return true;
        }
      }
      return anyMatch;
    }

    public String[] getSides() {
      return sides;
    }

    public void setMatched(int index) {
      this.matched[index] = true;
    }

    public boolean[] getMatched() {
      return matched;
    }

    public void printMatched() {
      System.out.println("Matched:");
      if (this.matched[0]) {
        System.out.println("Upper");
      }
      if (this.matched[1]) {
        System.out.println("Lower");
      }
      if (this.matched[2]) {
        System.out.println("Left");
      }
      if (this.matched[3]) {
        System.out.println("Right");
      }
    }

    public void printTile() {
      System.out.println(this.id);
      for (int i = 0; i < content.length; i++) {
        for (int j = 0; j < content.length; j++) {
          System.out.print(content[i][j]);
          System.out.print(" ");
        }
        System.out.println("");
      }
    }

  }


  public static void main(String[] args) throws IOException {
    BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
    StringBuilder sb = new StringBuilder();
    reader.lines().forEach(l -> sb.append(l).append("\n"));
    List<String> palat = Arrays.asList(sb.toString().split("\n"));
    Pattern pattern = Pattern.compile("([0-9]+):");

    Tiles t = new Tiles();

    for (int i = 0; i < palat.size(); i++) {
      Matcher matcher = pattern.matcher(palat.get(i));
      if (matcher.find()) {
        int id = Integer.parseInt(matcher.group(1));
        // next 10 rows are the tile
        List<String> sublist = palat.subList(i + 1, i + 11);
        int[][] tile = new int[10][10];
        for (int j = 0; j < sublist.size(); j++) {
          String[] row = sublist.get(j).split("");
          for (int k = 0; k < row.length; k++) {
            tile[j][k] = row[k].equals("#") ? 1 : 0;
          }
        }
        t.addTile(id, tile);
      }
    }
    t.getTile(0).printTile();
    t.matchTiles();
    long res = 1;
    for (Tile tt : t.getTiles()) {
      if (tt.getMatchCount() == 2) {
        res *= tt.id;
      }
    }
    System.out.println(res);
  }
}
