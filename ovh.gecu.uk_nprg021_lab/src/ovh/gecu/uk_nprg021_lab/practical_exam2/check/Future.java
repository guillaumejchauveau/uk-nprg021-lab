package ovh.gecu.uk_nprg021_lab.practical_exam2.check;

import ovh.gecu.uk_nprg021_lab.practical_exam2.Check;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class Future implements Check {
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @Documented
  public @interface Check {
    String message() default "";
  }

  @Override
  public Class<? extends Annotation> getAnnotationType() {
    return Check.class;
  }

  @Override
  public boolean isValid(Field field, Object value) {
    if (!field.getType().equals(LocalDateTime.class)) {
      throw new IllegalArgumentException("Future is only applicable to LocalDateTime");
    }
    return ((LocalDateTime) value).isAfter(LocalDateTime.now());
  }

  @Override
  public String getMessage(Field field) {
    if (!field.isAnnotationPresent(Check.class)) {
      throw new IllegalArgumentException("The field does not contain the annotation for this check");
    }
    return field.getAnnotation(Check.class).message();
  }
}
