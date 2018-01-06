package de.saarbastler.ui;

import javax.xml.bind.annotation.XmlElement;

public class UIObject
{
  /** The label shown to the user. */
  @XmlElement
  protected String label;

  public String getLabel()
  {
    return label;
  }

}
