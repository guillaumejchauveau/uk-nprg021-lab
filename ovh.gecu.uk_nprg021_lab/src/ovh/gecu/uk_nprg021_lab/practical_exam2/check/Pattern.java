package ovh.gecu.uk_nprg021_lab.practical_exam2.check;

import ovh.gecu.uk_nprg021_lab.practical_exam2.Check;

import java.lang.annotation.*;
import java.lang.reflect.Field;

public class Pattern implements Check {
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @Documented
  public @interface Check {
    String message() default "";

    String pattern();
  }

  @Override
  public Class<? extends Annotation> getAnnotationType() {
    return Check.class;
  }

  @Override
  public boolean isValid(Field field, Object value) {
    if (!field.getType().equals(String.class)) {
      throw new IllegalArgumentException("Pattern is only applicable to strings");
    }
    if (!field.isAnnotationPresent(Check.class)) {
      throw new IllegalArgumentException("The field does not contain the annotation for this check");
    }
    var pattern = java.util.regex.Pattern.compile(field.getAnnotation(Check.class).pattern());
    return pattern.matcher((String) value).find();
  }

  @Override
  public String getMessage(Field field) {
    if (!field.isAnnotationPresent(Check.class)) {
      throw new IllegalArgumentException("The field does not contain the annotation for this check");
    }
    return field.getAnnotation(Check.class).message();
  }
}
