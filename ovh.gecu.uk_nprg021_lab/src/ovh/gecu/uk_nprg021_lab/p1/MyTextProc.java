package ovh.gecu.uk_nprg021_lab.p1;

public class MyTextProc implements TextProcessor {
  @Override
  public String process(String text) {
    return text.toUpperCase();
  }
}
