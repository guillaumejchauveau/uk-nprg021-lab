import ovh.gecu.uk_nprg021_lab.p1.MyPlugin;
import ovh.gecu.uk_nprg021_lab.p1.MyPluginInterface;
import ovh.gecu.uk_nprg021_lab.p1.MyTextProc;
import ovh.gecu.uk_nprg021_lab.p1.TextProcessor;

module ovh.gecu.uk_nprg021_lab {
  requires java.xml;
  requires java.sql;
  requires jdom2;
  provides TextProcessor with MyTextProc;
  uses TextProcessor;
  provides MyPluginInterface with MyPlugin;
  uses MyPluginInterface;
  exports cz.cuni.mff.java.exam;
}
