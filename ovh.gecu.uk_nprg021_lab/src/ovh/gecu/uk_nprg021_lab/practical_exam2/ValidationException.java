package ovh.gecu.uk_nprg021_lab.practical_exam2;

import java.lang.reflect.Field;

public class ValidationException extends RuntimeException {
  public ValidationException(Check check, Field field) {
    super("Field '" + field.getName() + "' is invalid: " + check.getMessage(field));
  }
}
