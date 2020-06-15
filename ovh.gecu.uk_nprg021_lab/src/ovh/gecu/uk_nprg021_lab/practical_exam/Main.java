package ovh.gecu.uk_nprg021_lab.practical_exam;

import cz.cuni.mff.java.exam.Examlet;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

import static java.nio.file.StandardWatchEventKinds.*;

public class Main {
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new RuntimeException("Directory path required");
    }
    var app = new Main(args[0]);
    app.run();
  }

  protected FileSystem fileSystem;
  protected Path jarDirectory;
  protected Map<Path, Class<? extends Examlet>> examlets;

  public Main(String jarDirectoryStr) {
    this.fileSystem = FileSystems.getDefault();
    this.jarDirectory = fileSystem.getPath(jarDirectoryStr);
    this.examlets = new HashMap<>();
  }

  public void run() {
    var jars = this.jarDirectory.toFile().listFiles((file) -> file.getName().endsWith(".jar"));
    if (jars == null) {
      throw new RuntimeException("Invalid directory");
    }
    WatchService watchService;
    try {
      for (var jarFile : jars) {
        this.addPath(jarFile.toPath());
      }
      watchService = this.fileSystem.newWatchService();
      this.jarDirectory.register(watchService, ENTRY_CREATE, ENTRY_DELETE);
    } catch (IOException | ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }

    while (true) {
      WatchKey key;
      try {
        key = watchService.take();
      } catch (InterruptedException e) {
        return;
      }

      for (var event : key.pollEvents()) {
        if (OVERFLOW.equals(event.kind())) {
          throw new RuntimeException("OVERFLOW");
        }
        var path = this.jarDirectory.resolve((Path) event.context());
        if (!path.toString().endsWith(".jar")) {
          continue;
        }
        if (ENTRY_CREATE.equals(event.kind())) {
          try {
            this.addPath(path);
          } catch (IOException | ReflectiveOperationException e) {
            throw new RuntimeException(e);
          }
        } else {
          if (this.examlets.containsKey(path)) {
            System.out.println("Removed Examlet " + this.examlets.get(path).getName());
            this.examlets.remove(path);
          }
        }
      }
      key.reset();
    }
  }

  protected void addPath(Path path) throws IOException, ReflectiveOperationException {
    var clazz = this.loadPath(path);
    if (clazz != null) {
      this.examlets.put(path, clazz);
      System.out.println("Added Examlet " + clazz.getName());
      clazz.getConstructor().newInstance().service();
    }
  }

  protected Class<? extends Examlet> loadPath(Path path) throws IOException {
    var jarFile = new JarFile(path.toFile());
    var clazzName = jarFile.getManifest().getMainAttributes().getValue("Examlet");
    jarFile.close();
    if (clazzName == null) {
      return null;
    }
    Class<?> clazz;
    var loader = new URLClassLoader(new URL[]{new URL("jar:file:" + path.toString() + "!/")});
    try {
      clazz = loader.loadClass(clazzName);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    loader.close();

    if (!Arrays.asList(clazz.getInterfaces()).contains(Examlet.class)) {
      return null;
    }
    return clazz.asSubclass(Examlet.class);
  }
}
