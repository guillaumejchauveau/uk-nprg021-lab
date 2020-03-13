package ovh.gecu.uk_nprg021_lab.p1;

import java.util.ArrayList;
import java.util.List;

public class E1 {
  public static <T> List<T> loadPlugins(Class<T> pluginInterface, String... pluginNames) throws ReflectiveOperationException {
    var plugins = new ArrayList<T>();
    for (String pluginName : pluginNames) {
      Class<?> clazz = Class.forName(pluginName);

      if (!pluginInterface.isAssignableFrom(clazz)) {
        throw new IllegalArgumentException("Class '" + pluginName + "' does not implement interface '" + pluginInterface.getName() + "'");
      }
      plugins.add(pluginInterface.cast(clazz.getDeclaredConstructor().newInstance()));
    }
    return plugins;
  }

  public static void test() throws Exception {
    for (var plugin : E1.loadPlugins(MyPluginInterface.class, "ovh.gecu.nprg021_lab.P1.MyPlugin")) {
      System.out.println(plugin.myMethod());
    }
  }
}
