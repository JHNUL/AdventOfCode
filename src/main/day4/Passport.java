package main.day4;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Passport {

  public enum PassportField {
    ISSUEYEAR("iyr"), EXPIRATIONYEAR("eyr"), HEIGHT("hgt"), HAIRCOLOR("hcl"), EYECOLOR(
        "ecl"), PASSPORTID("pid"), COUNTRYID("cid"), BIRTHYEAR("byr");

    private final String name;

    private PassportField(String name) {
      this.name = name;
    }
  }

  private Map<String, String> fields;
  private static final String[] fieldNames =
      new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"};

  public Passport(String input) {
    this.fields = Arrays.asList(input.split(" ")).stream().map(str -> str.split(":"))
        .collect(Collectors.toMap(str -> str[0], str -> str[1]));
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public boolean isValid() {
    for (int i = 0; i < fieldNames.length; i++) {
      if (fieldNames[i].equals(PassportField.COUNTRYID.name)) {
        continue;
      }
      if (!this.fields.containsKey(fieldNames[i])) {
        return false;
      }
    }
    return true;
  }

  public static boolean birthYearIsValid(int birthYear) {
    return birthYear >= 1920 && birthYear <= 2002;
  }

  public static boolean issueYearIsValid(int issueYear) {
    return issueYear >= 2010 && issueYear <= 2020;
  }

  public static boolean expirationYearIsValid(int expirationYear) {
    return expirationYear >= 2020 && expirationYear <= 2030;
  }

  public static boolean heightIsValid(String height) {
    boolean heightIsInInches = height.contains("in");
    boolean heightIsMetric = height.contains("cm");
    if (!heightIsInInches && !heightIsMetric) {
      return false;
    }
    height = height.replace("in", "");
    height = height.replace("cm", "");
    int heightNumeric = Integer.parseInt(height);
    if (heightIsInInches && (heightNumeric < 59 || heightNumeric > 76)) {
      return false;
    }
    if (heightIsMetric && (heightNumeric < 150 || heightNumeric > 193)) {
      return false;
    }
    return true;
  }

  public static boolean hairColorIsValid(String hairColor) {
    Pattern hairColorPattern = Pattern.compile("^#[0-9a-f]{6}$");
    Matcher hairColorMatcher = hairColorPattern.matcher(hairColor);
    return hairColorMatcher.matches();
  }

  public static boolean eyeColorIsValid(String eyeColor) {
    Pattern eyeColorPattern = Pattern.compile("^amb$|^blu$|^brn$|^gry$|^grn$|^hzl$|^oth$");
    Matcher eyeColorMatcher = eyeColorPattern.matcher(eyeColor);
    return eyeColorMatcher.matches();
  }

  public static boolean passportIdIsValid(String passportId) {
    Pattern passportIdPattern = Pattern.compile("^[0-9]{9}$");
    Matcher passportIdMatcher = passportIdPattern.matcher(passportId);
    return passportIdMatcher.matches();
  }

  public boolean isStrictlyValid() {
    try {
      int birthYear = Integer.parseInt(fields.get(PassportField.BIRTHYEAR.name));
      int issueYear = Integer.parseInt(fields.get(PassportField.ISSUEYEAR.name));
      int expirationYear = Integer.parseInt(fields.get(PassportField.EXPIRATIONYEAR.name));
      String height = fields.get(PassportField.HEIGHT.name);
      String hairColor = fields.get(PassportField.HAIRCOLOR.name);
      String eyeColor = fields.get(PassportField.EYECOLOR.name);
      String passportId = fields.get(PassportField.PASSPORTID.name);
      if (!birthYearIsValid(birthYear)) {
        throw new IllegalArgumentException("Invalid birthyear " + birthYear);
      }
      if (!issueYearIsValid(issueYear)) {
        throw new IllegalArgumentException("Invalid issueyear " + issueYear);
      }
      if (!expirationYearIsValid(expirationYear)) {
        throw new IllegalArgumentException("Invalid expirationyear " + expirationYear);
      }
      if (!heightIsValid(height)) {
        throw new IllegalArgumentException("Invalid height " + height);
      }
      if (!hairColorIsValid(hairColor)) {
        throw new IllegalArgumentException("Invalid haircolor " + hairColor);
      }
      if (!eyeColorIsValid(eyeColor)) {
        throw new IllegalArgumentException("Invalid eyeColor " + eyeColor);
      }
      if (!passportIdIsValid(passportId)) {
        throw new IllegalArgumentException("Invalid passportId " + passportId);
      }
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
    return true;
  }

  public boolean isValidWithCountryId() {
    for (int i = 0; i < fieldNames.length; i++) {
      if (!this.fields.containsKey(fieldNames[i])) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    try {
      File file = new File("src/main/day4/input.txt");
      Scanner scanner = new Scanner(file);
      StringBuilder sb = new StringBuilder();
      List<String> passportInputs = new ArrayList<>();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        sb.append(line.trim() + " ");
        if (line.isEmpty() || !scanner.hasNextLine()) {
          // we have the necessary data for 1 passport in a single string
          String passportLine = sb.toString().trim();
          passportInputs.add(passportLine);
          sb = new StringBuilder();
        }
      }
      // do the actual things
      long valids = passportInputs.stream().map(input -> new Passport(input))
          .filter(passport -> passport.isValid())
          .filter(looselyValid -> looselyValid.isStrictlyValid()).count();
      System.out.println(valids);
      scanner.close();
    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
