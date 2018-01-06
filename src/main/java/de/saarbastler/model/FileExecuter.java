package de.saarbastler.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * The Class FileExecuter is the base class for all file executers
 */
public abstract class FileExecuter extends TemplateExecuter
{
  /** The destination file. */
  @XmlElement
  protected String file;
}
