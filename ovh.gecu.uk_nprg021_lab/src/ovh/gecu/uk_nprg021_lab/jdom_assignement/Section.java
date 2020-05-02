package ovh.gecu.uk_nprg021_lab.jdom_assignement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Section {
  protected String title;
  protected Collection<Link> links;

  public Section(String title) {
    this.title = title;
    this.links = new ArrayList<>();
  }

  public String getTitle() {
    return this.title;
  }

  public boolean hasLinks() {
    return !this.links.isEmpty();
  }

  public Iterable<Link> getLinks() {
    return this.links;
  }

  public void addLink(Link link) {
    this.links.add(link);
  }

  public String toString() {
    return this.getTitle();
  }
}
