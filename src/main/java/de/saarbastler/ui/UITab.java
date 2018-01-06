package de.saarbastler.ui;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

public class UITab extends UIObject
{
  /** The UI fields. */
  @XmlElementWrapper(name = "fields")
  @XmlElements({ @XmlElement(name = "list", type = FieldList.class, required = false),
      @XmlElement(name = "directory", type = FieldDirectory.class, required = false),
      @XmlElement(name = "file", type = FieldFile.class, required = false),
      @XmlElement(name = "integer", type = FieldInteger.class, required = false),
      @XmlElement(name = "string", type = FieldString.class, required = false),
      @XmlElement(name = "boolean", type = FieldCheckbox.class, required = false) })
  private List<Field> fields = new ArrayList<>();

  public List<Field> getFields()
  {
    return fields;
  }

}
