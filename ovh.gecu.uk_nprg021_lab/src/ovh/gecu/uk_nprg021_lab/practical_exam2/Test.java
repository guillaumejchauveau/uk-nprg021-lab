package ovh.gecu.uk_nprg021_lab.practical_exam2;

import ovh.gecu.uk_nprg021_lab.practical_exam2.check.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

public class Test {
  @AssertFalse.Check(message = "Should be false")
  boolean f1;

  @AssertTrue.Check
  boolean f2;

  @Future.Check
  LocalDateTime f3;

  @Future.Check(message = "Should have failed")
  @Ignored
  LocalDateTime f4;

  @NotBlank.Check
  String f5;

  @NotNull.Check
  Object f6;

  @Null.Check
  Object f7;

  @Pattern.Check(pattern = "^h")
  String f8;

  @Size.Check(min = 2, max = 10)
  String f9;

  @Size.Check(min = 2, max = 10)
  Collection<Character> f10;

  @Size.Check(min = 2, max = 10)
  Character[] f11;

  public Test() {
    f1 = false;
    f2 = true;
    f3 = LocalDateTime.of(2020, Month.AUGUST, 1, 0, 0);
    f4 = LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0);
    f5 = "   a  ";
    f6 = "not null";
    f7 = null;
    f8 = "hello";
    f9 = "hello";
    f10 = Arrays.asList('h', 'e', 'l');
    f11 = new Character[]{'h', 'e', 'l'};
  }


  public static void main(String[] args) {
    Validator.addChecks(Arrays.asList(
      new AssertFalse(),
      new AssertTrue(),
      new Future(),
      new NotBlank(),
      new NotNull(),
      new Null(),
      new Pattern(),
      new Size()
    ));
    var a = new Test();
    Validator.validate(a);
  }
}
