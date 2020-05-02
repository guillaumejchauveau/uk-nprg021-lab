package ovh.gecu.uk_nprg021_lab.annotations_assignment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationReader {
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new RuntimeException("Expected a single class name");
    }
    Class<?> clazz;
    try {
      clazz = Class.forName(args[0]);
    } catch (ReflectiveOperationException e) {
      System.out.println("\"" + args[0] + "\" does not exists");
      //System.exit(1);
      return;
    }
    var className = clazz.getName();

    var methods = new ArrayList<Method>();
    var isSuper = false;
    while (clazz != Object.class) {
      var declaredMethods = Arrays.stream(clazz.getDeclaredMethods()).filter(m -> !Modifier.isAbstract(m.getModifiers()));
      if (isSuper) {
        //declaredMethods = declaredMethods.filter(m -> !Modifier.isPrivate(m.getModifiers()));
      }
      declaredMethods.collect(Collectors.toCollection(() -> methods));
      clazz = clazz.getSuperclass();
      isSuper = true;
    }

    var methods_annotations = new HashMap<Method, Collection<Annotation>>();
    for (var method : methods) {
      for (var annotation : method.getDeclaredAnnotations()) {
        methods_annotations.putIfAbsent(method, new ArrayList<>());
        methods_annotations.get(method).add(annotation);
      }
    }

    if (methods_annotations.size() == 0) {
      System.out.println("No annotations in \"" + className + "\"");
      return;
    }

    System.out.println("Annotations in \"" + className + "\":");
    var sortedMethods = new ArrayList<>(methods_annotations.keySet());
    sortedMethods.sort(Comparator.comparing(Method::getName));
    for (var method : sortedMethods) {
      for (var annotation : methods_annotations.get(method)) {
        System.out.print("@" + annotation.annotationType().getName() + " ");
      }
      System.out.println(method.getName() + "()");
    }
  }
}
