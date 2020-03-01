package ovh.gecu.nprg021_lab.p2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestLauncher {
  protected List<Class<?>> testClasses;

  public TestLauncher(String path) throws IOException, ClassNotFoundException {
    this.testClasses = new ArrayList<>();
    var reader = new BufferedReader(new FileReader(path));
    String line;
    while ((line = reader.readLine()) != null) {
      var testClass = Class.forName(line);
      var info = testClass.getAnnotation(TesterInfo.class);
      if (info == null) {
        throw new RuntimeException("Missing TesterInfo annotation on test class");
      }
      this.testClasses.add(testClass);
    }
    this.testClasses.sort((x, y) -> {
      var xAnnotation = x.getAnnotation(TesterInfo.class);
      var yAnnotation = y.getAnnotation(TesterInfo.class);
      return yAnnotation.priority().ordinal() - xAnnotation.priority().ordinal();
    });
  }

  public void runTests() throws ReflectiveOperationException {
    var passed = 0;
    var failed = 0;
    var ignored = 0;
    for (var testClass : this.testClasses) {
      var info = testClass.getAnnotation(TesterInfo.class);
      System.out.format("Running test class '%s' created by '%s'\n", testClass.getName(), info.createdBy());

      var instance = testClass.getConstructor().newInstance();

      var befores = new ArrayList<Method>();
      var tests = new ArrayList<Method>();
      var afters = new ArrayList<Method>();
      for (var method : testClass.getDeclaredMethods()) {
        if (method.isAnnotationPresent(Test.class)) {
          tests.add(method);
        } else if (method.isAnnotationPresent(Before.class)) {
          befores.add(0, method);
        } else if (method.isAnnotationPresent(After.class)) {
          afters.add(method);
        }
      }

      for (var before : befores) {
        before.invoke(instance);
      }

      for (var testMethod : tests) {
        var testAnnotation = testMethod.getAnnotation(Test.class);
        if (!testAnnotation.enabled()) {
          System.out.format("Ignoring test '%s'\n", testMethod.getName());
          ignored++;
          continue;
        }

        System.out.format("Running test '%s'...", testMethod.getName());
        var testFailed = true;
        try {
          testMethod.invoke(instance);
          testFailed = false;
        } catch (InvocationTargetException e) {
          var thrownException = e.getTargetException();
          for (var expectedException : testAnnotation.expectedExceptions()) {
            if (expectedException.isInstance(thrownException)) {
              testFailed = false;
              break;
            }
          }
        }
        if (testFailed) {
          failed++;
          System.out.println("failed");
          continue;
        }
        passed++;
        System.out.println("passed");
      }

      for (var after : afters) {
        after.invoke(instance);
      }
    }
    System.out.format("Result: Total %d, Passed %d, Failed %d, Ignored %d\n", passed + failed + ignored, passed, failed, ignored);
  }
}
