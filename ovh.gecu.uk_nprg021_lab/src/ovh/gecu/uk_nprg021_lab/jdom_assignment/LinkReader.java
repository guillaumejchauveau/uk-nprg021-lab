package ovh.gecu.uk_nprg021_lab.jdom_assignment;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.filter.ContentFilter;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LinkReader {
  protected Collection<Section> sections;
  protected Map<String, Section> ids;

  public static void main(String[] args) throws IOException, JDOMException {
    var builder = new SAXBuilder();
    var reader = new LinkReader(builder.build(System.in));
    reader.printSectionsLinks();
  }

  public LinkReader(Document document) {
    this.sections = new ArrayList<>();
    this.ids = new HashMap<>();

    this.parseSection(document.getRootElement(), null);
    for (var section : this.sections) {
      for (var link : section.getLinks()) {
        link.mapTarget(this.ids);
      }
    }
  }

  protected void parseSection(Element element, Section parentSection) {
    var currentSection = parentSection;
    if (element.getName().equals("section")) {
      var title = element.getChild("title");
      if (title == null) {
        throw new RuntimeException("Section without title");
      }
      currentSection = new Section(this.getText(title));
      this.sections.add(currentSection);
    } else if (element.getName().equals("link")) {
      if (currentSection == null) {
        throw new RuntimeException("Link outside section");
      }
      var targetId = element.getAttribute("linkend");
      if (targetId == null) {
        throw new RuntimeException("Invalid link");
      }
      currentSection.addLink(new Link(this.getText(element), targetId.getValue()));
    }

    var id = element.getAttribute("id");
    if (id != null && currentSection != null) {
      if (this.ids.containsKey(id.getValue())) {
        throw new RuntimeException("Duplicated ID '" + id.getValue() + "'");
      }
      this.ids.put(id.getValue(), currentSection);
    }

    for (var child : element.getChildren()) {
      this.parseSection(child, currentSection);
    }
  }

  protected String getText(Element element) {
    var text = new StringBuilder();
    for (var piece : element.getDescendants(new ContentFilter(ContentFilter.TEXT))) {
      text.append(((Text) piece).getText());
    }
    return text.toString();
  }

  public void printSectionsLinks() {
    for (var section : this.sections) {
      if (section.hasLinks()) {
        System.out.print(section);
        System.out.println(':');
      }
      for (var link : section.getLinks()) {
        System.out.println("    " + link);
      }
    }
  }
}
