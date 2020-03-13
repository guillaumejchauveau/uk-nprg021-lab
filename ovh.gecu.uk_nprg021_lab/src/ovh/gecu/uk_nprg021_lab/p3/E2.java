package ovh.gecu.uk_nprg021_lab.p3;

import ovh.gecu.uk_nprg021_lab.p1.MyPluginInterface;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.ServiceLoader;

public class E2 {
  public static void test(String[] args) throws Exception {
    var urls = new ArrayList<URL>();
    for (var path : args) {
      urls.add(new URL(path));
    }
    var providers = ServiceLoader.load(MyPluginInterface.class, new URLClassLoader(urls.toArray(new URL[urls.size()])));
    for (var provider : providers) {
      System.out.println(provider.getClass().getName());
    }
  }
}
