package de.saarbastler.model;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Create direcories
 */
@XmlType(name = "mkdirs")
public class MkDirs extends TemplateExecuter
{

  /** The path. */
  @XmlElement
  private String path;

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.model.TemplateExecuter#execute(freemarker.template.
   * Configuration, java.util.Map)
   */
  @Override
  public void execute(Configuration cfg, Map<String, String> values) throws TemplateException, IOException
  {
    File solutionDir = new File( evaluateString( cfg, values, path ) );
    solutionDir.mkdirs();
  }

}
