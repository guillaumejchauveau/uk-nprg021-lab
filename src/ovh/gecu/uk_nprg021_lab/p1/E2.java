package ovh.gecu.uk_nprg021_lab.p1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class E2 {
  public static void test(String[] args) throws Exception {
    if (args.length != 2) {
      throw new RuntimeException("One argument expected");
    }
    var reader = new BufferedReader(new FileReader(args[1]));
    String line;
    var processors = new ArrayList<TextProcessor>();
    while ((line = reader.readLine()) != null) {
      processors.add(E1.loadPlugins(TextProcessor.class, line).get(0));
    }
    var inputReader = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder input = new StringBuilder();
    while ((line = inputReader.readLine()) != null) {
      input.append(line);
    }
    String output = input.toString();
    for (var processor : processors) {
      output = processor.process(output);
    }
    System.out.println(output);
  }
}
