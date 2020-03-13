package ovh.gecu.uk_nprg021_lab.p3;

import ovh.gecu.uk_nprg021_lab.p1.TextProcessor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ServiceLoader;

public class E1 {
  public static void test(String[] args) throws Exception {
    var processors = new ArrayList<TextProcessor>();
    for (var plugin : ServiceLoader.load(TextProcessor.class)) {
      processors.add(plugin);
    }
    var inputReader = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder input = new StringBuilder();
    String line;
    while ((line = inputReader.readLine()) != null) {
      input.append(line).append('\n');
    }
    String output = input.toString();
    for (var processor : processors) {
      output = processor.process(output);
    }
    System.out.println(output);
  }
}
