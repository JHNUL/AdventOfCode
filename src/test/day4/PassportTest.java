package test.day4;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Map;
import org.junit.Test;
import main.day4.Passport;

public class PassportTest {

  @Test
  public void returnsFieldsAsMap1() {
    Passport p = new Passport("a:1 b:2 c:3");
    assertTrue(p.getFields() instanceof Map);
  }

  @Test
  public void returnsFieldsAsMap2() {
    Passport p = new Passport("a:1 b:2 c:3");
    assertEquals("1", p.getFields().get("a"));
    assertEquals("2", p.getFields().get("b"));
    assertEquals("3", p.getFields().get("c"));
  }

  @Test
  public void isValid_SuccessWithCountryId() {
    Passport p = new Passport(
        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm");
    assertTrue(p.isValid());
  }
  
  @Test
  public void isValidWithCountryId_SuccessWithCountryId() {
    Passport p = new Passport(
        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm");
    assertTrue(p.isValidWithCountryId());
  }

  @Test
  public void isValid_SuccessWithoutCountryId() {
    Passport p =
        new Passport("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 hgt:183cm");
    assertTrue(p.isValid());
  }

  @Test
  public void isValidWithCountryId_FailureWithoutCountryId() {
    Passport p = new Passport(
        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 hgt:183cm");
    assertFalse(p.isValidWithCountryId());
  }

  @Test
  public void isValid_FailureMissingAnyOtherFieldThanCountryId() {
    Passport p1 = new Passport("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017");
    assertFalse(p1.isValid());
    Passport p2 = new Passport("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 hgt:183cm");
    assertFalse(p2.isValid());
    Passport p3 = new Passport("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd iyr:2017 hgt:183cm");
    assertFalse(p3.isValid());
    Passport p4 = new Passport("ecl:gry pid:860033327 eyr:2020 byr:1937 iyr:2017 hgt:183cm");
    assertFalse(p4.isValid());
    Passport p5 = new Passport("ecl:gry pid:860033327 hcl:#fffffd byr:1937 iyr:2017 hgt:183cm");
    assertFalse(p5.isValid());
    Passport p6 = new Passport("ecl:gry eyr:2020 hcl:#fffffd byr:1937 iyr:2017 hgt:183cm");
    assertFalse(p6.isValid());
    Passport p7 = new Passport("pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 hgt:183cm");
    assertFalse(p7.isValid());
  }

  @Test
  public void heightIsValid_Metric() {
    assertTrue(Passport.heightIsValid("150cm"));
    assertTrue(Passport.heightIsValid("193cm"));
    assertTrue(Passport.heightIsValid("156cm"));
    assertTrue(Passport.heightIsValid("180cm"));
    assertTrue(Passport.heightIsValid("166cm"));
  }
  
  @Test
  public void heightIsInvalid_Metric() {
    assertFalse(Passport.heightIsValid("149cm"));
    assertFalse(Passport.heightIsValid("194cm"));
    assertFalse(Passport.heightIsValid("-170cm"));
  }
  
  @Test
  public void heightIsValid_Inches() {
    assertTrue(Passport.heightIsValid("59in"));
    assertTrue(Passport.heightIsValid("76in"));
    assertTrue(Passport.heightIsValid("60in"));
    assertTrue(Passport.heightIsValid("74in"));
    assertTrue(Passport.heightIsValid("71in"));
  }
  
  @Test
  public void heightIsinValid_Inches() {
    assertFalse(Passport.heightIsValid("58in"));
    assertFalse(Passport.heightIsValid("77in"));
    assertFalse(Passport.heightIsValid("-70in"));
  }
  
  @Test
  public void heightIsInvalid_WithoutUnit() {
    assertFalse(Passport.heightIsValid("64"));
    assertFalse(Passport.heightIsValid("160"));
    assertFalse(Passport.heightIsValid("70"));
    assertFalse(Passport.heightIsValid("166"));
  }
  
  @Test
  public void heightIsInvalid_WithArbitraryUnit() {
    assertFalse(Passport.heightIsValid("64foo"));
    assertFalse(Passport.heightIsValid("160m"));
    assertFalse(Passport.heightIsValid("70c"));
    assertFalse(Passport.heightIsValid("166im"));
  }

  @Test
  public void hairColorIsValid() { 
    assertTrue(Passport.hairColorIsValid("#000000"));
    assertTrue(Passport.hairColorIsValid("#18171d"));
    assertTrue(Passport.hairColorIsValid("#aaafff"));
    assertTrue(Passport.hairColorIsValid("#123eff"));
  }
  
  @Test
  public void hairColorIsInvalid() { 
    assertFalse(Passport.hairColorIsValid("000000"));
    assertFalse(Passport.hairColorIsValid("#18171d1"));
    assertFalse(Passport.hairColorIsValid("#12345"));
    assertFalse(Passport.hairColorIsValid("#aaffgg"));
    assertFalse(Passport.hairColorIsValid("#09832u"));
    assertFalse(Passport.hairColorIsValid("z"));
    assertFalse(Passport.hairColorIsValid("juhanir"));
    assertFalse(Passport.hairColorIsValid("#000000 "));
    assertFalse(Passport.hairColorIsValid(" #000000"));
  }

  @Test
  public void eyeColorIsValid() {
    // valids amb blu brn gry grn hzl oth
    assertTrue(Passport.eyeColorIsValid("amb"));
    assertTrue(Passport.eyeColorIsValid("blu"));
    assertTrue(Passport.eyeColorIsValid("brn"));
    assertTrue(Passport.eyeColorIsValid("gry"));
    assertTrue(Passport.eyeColorIsValid("grn"));
    assertTrue(Passport.eyeColorIsValid("hzl"));
    assertTrue(Passport.eyeColorIsValid("oth"));
  }
  
  @Test
  public void eyeColorIsInvalid() {
    // valids amb blu brn gry grn hzl oth
    assertFalse(Passport.eyeColorIsValid("abm"));
    assertFalse(Passport.eyeColorIsValid("ulb"));
    assertFalse(Passport.eyeColorIsValid("ambb"));
    assertFalse(Passport.eyeColorIsValid("bblu"));
    assertFalse(Passport.eyeColorIsValid(" amb"));
    assertFalse(Passport.eyeColorIsValid("amb "));
    assertFalse(Passport.eyeColorIsValid("zlh"));
    assertFalse(Passport.eyeColorIsValid("othoth"));
    assertFalse(Passport.eyeColorIsValid("dididiamb"));
  }

  @Test
  public void passportIdIsValid() {
    // nine digits 0-9
    assertTrue(Passport.passportIdIsValid("123456789"));
    assertTrue(Passport.passportIdIsValid("000456789"));
    assertTrue(Passport.passportIdIsValid("123456000"));
  }
  
  @Test
  public void passportIdIsInvalid() {
    // nine digits 0-9
    assertFalse(Passport.passportIdIsValid("12345678a"));
    assertFalse(Passport.passportIdIsValid("12345678"));
    assertFalse(Passport.passportIdIsValid("32"));
    assertFalse(Passport.passportIdIsValid("asdfghjkl"));
    assertFalse(Passport.passportIdIsValid("123456789 "));
    assertFalse(Passport.passportIdIsValid(" 123456789"));
    assertFalse(Passport.passportIdIsValid("#23456789"));
  }

}
