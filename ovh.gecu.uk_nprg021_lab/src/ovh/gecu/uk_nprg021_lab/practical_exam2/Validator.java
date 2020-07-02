package ovh.gecu.uk_nprg021_lab.practical_exam2;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Validator {
  protected static Map<Class<? extends Annotation>, Check> checks;

  static {
    Validator.checks = new HashMap<>();
  }

  public static void addChecks(Collection<Check> checks) {
    for (var check : checks) {
      Validator.checks.put(check.getAnnotationType(), check);
    }
  }

  public static void validate(Object o) throws ValidationException {
    var oType = o.getClass();
    for (var field : oType.getDeclaredFields()) {
      if (field.isAnnotationPresent(Ignored.class)) {
        continue;
      }

      Object value;
      try {
        value = field.get(o);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
      Arrays.stream(field.getAnnotations())
        .map(Annotation::annotationType)
        .filter(Validator.checks::containsKey)
        .map(Validator.checks::get)
        .filter(check -> !check.isValid(field, value))
        .findAny()
        .ifPresent(check -> {
          throw new ValidationException(check, field);
        });
    }
  }

  public static boolean isValid(Object o) {
    try {
      Validator.validate(o);
    } catch (ValidationException e) {
      return false;
    }
    return true;
  }
}
