package ovh.gecu.nprg021_lab.p2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
  boolean enabled() default true;

  Class<? extends Throwable>[] expectedExceptions() default {};
}
