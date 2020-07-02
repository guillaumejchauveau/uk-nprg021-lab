package ovh.gecu.uk_nprg021_lab.practical_exam2.check;

import ovh.gecu.uk_nprg021_lab.practical_exam2.Check;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public class AssertTrue implements Check {
  protected static final Collection<Class<?>> validTypes = Arrays.asList(Boolean.class, boolean.class);

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
    if (!validTypes.contains(field.getType())) {
      throw new IllegalArgumentException("AssertTrue is only applicable to booleans");
    }
    return (Boolean) value;
  }

  @Override
  public String getMessage(Field field) {
    if (!field.isAnnotationPresent(Check.class)) {
      throw new IllegalArgumentException("The field does not contain the annotation for this check");
    }
    return field.getAnnotation(Check.class).message();
  }
}
