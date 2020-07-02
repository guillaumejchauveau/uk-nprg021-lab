package ovh.gecu.uk_nprg021_lab.practical_exam2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface Check {
  Class<? extends Annotation> getAnnotationType();

  boolean isValid(Field field, Object value);

  String getMessage(Field field);
}
