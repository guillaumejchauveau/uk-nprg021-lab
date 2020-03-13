package ovh.gecu.uk_nprg021_lab.p2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TesterInfo {
  String createdBy();

  String lastModified();

  Priority priority();
}
