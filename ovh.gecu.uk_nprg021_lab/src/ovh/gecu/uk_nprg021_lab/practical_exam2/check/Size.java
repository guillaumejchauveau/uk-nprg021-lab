package ovh.gecu.uk_nprg021_lab.practical_exam2.check;

import ovh.gecu.uk_nprg021_lab.practical_exam2.Check;

import java.lang.annotation.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class Size implements Check {
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @Documented
  public @interface Check {
    String message() default "";

    int min();

    int max();
  }

  @Override
  public Class<? extends Annotation> getAnnotationType() {
    return Check.class;
  }

  @Override
  public boolean isValid(Field field, Object value) {
    int size;
    if (String.class.equals(field.getType())) {
      size = ((String) value).length();
    } else if (Collection.class.equals(field.getType())) {
      size = ((Collection<?>) value).size();
    } else if (Map.class.equals(field.getType())) {
      size = ((Map<?, ?>) value).size();
    } else if (field.getType().isArray()) {
      size = ((Object[]) value).length;
    } else {
      throw new IllegalArgumentException("Size is only applicable to strings, collections, maps and arrays");
    }
    var min = field.getAnnotation(Check.class).min();
    var max = field.getAnnotation(Check.class).max();
    return min <= size && size <= max;
  }

  @Override
  public String getMessage(Field field) {
    if (!field.isAnnotationPresent(Check.class)) {
      throw new IllegalArgumentException("The field does not contain the annotation for this check");
    }
    return field.getAnnotation(Check.class).message();
  }
}
