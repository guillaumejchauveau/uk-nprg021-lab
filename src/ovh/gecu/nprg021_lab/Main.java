package ovh.gecu.nprg021_lab;

import ovh.gecu.nprg021_lab.p2.TestLauncher;

public class Main {
  public static void main(String[] args) throws Exception {
    var a = new TestLauncher("tests");
    a.runTests();
  }
}
