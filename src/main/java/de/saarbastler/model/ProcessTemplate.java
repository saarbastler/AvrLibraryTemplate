package de.saarbastler.model;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * The Class executes a freemarker template.
 */
@XmlType(name = "processTemplate")
public class ProcessTemplate extends TemplateExecuter
{

  /** The template name. */
  @XmlElement
  private String templateName;

  /** The file. */
  @XmlElement
  private String file;

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.model.TemplateExecuter#execute(freemarker.template.
   * Configuration, java.util.Map)
   */
  @Override
  public void execute(Configuration cfg, Map<String, String> values) throws Exception
  {
    Template template = cfg.getTemplate( evaluateString( cfg, values, templateName ) );
    String outFile = evaluateString( cfg, values, file );
    try (Writer out = new FileWriter( outFile ))
    {
      template.process( values, out );
    }
  }

}
