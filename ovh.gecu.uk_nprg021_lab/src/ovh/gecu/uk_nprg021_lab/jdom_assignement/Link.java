package ovh.gecu.uk_nprg021_lab.jdom_assignement;

import java.util.Map;

public class Link {
  protected String text;
  protected Section target;
  protected String targetId;

  public Link(String text, Section target) {
    this.text = text;
    this.target = target;
    this.targetId = null;
  }

  public Link(String text, String targetId) {
    this.text = text;
    this.target = null;
    this.targetId = targetId;
  }

  public String getText() {
    return this.text;
  }

  public Section getTarget() {
    return this.target;
  }

  public void mapTarget(Map<String, Section> sectionMap) {
    if (this.targetId == null || !sectionMap.containsKey(this.targetId)) {
      throw new RuntimeException("Invalid target id '" + this.targetId + "'");
    }
    this.target = sectionMap.get(this.targetId);
  }

  public String toString() {
    return this.getText() + " (" + this.target + ')';
  }
}
