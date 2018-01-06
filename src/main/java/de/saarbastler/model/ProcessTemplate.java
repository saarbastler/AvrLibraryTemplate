package de.saarbastler.model;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * The Class executes a freemarker template.
 */
@XmlType(name = "processTemplate")
public class ProcessTemplate extends TemplateExecuter
{
  private static final Logger log = LogManager.getLogger( ProcessTemplate.class );

  /** The template name. */
  @XmlElement
  private String templateName;

  /** The file. */
  @XmlElement
  private String file;

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.model.TemplateExecuter#getLogger()
   */
  @Override
  protected Logger getLogger()
  {
    return log;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.model.TemplateExecuter#execute(freemarker.template.
   * Configuration, java.util.Map)
   */
  @Override
  public void executeConditional(Configuration cfg, Map<String, String> values) throws Exception
  {
    String templateText = evaluateString( cfg, values, templateName );
    Template template = cfg.getTemplate( templateText );
    String outFile = evaluateString( cfg, values, file );

    log.info( "writing template '{}' to file '{}'", templateText, outFile );
    try (Writer out = new FileWriter( outFile ))
    {
      template.process( values, out );
    }
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append( "processTemplate if=\"" ).append( getIfCondition() ).append( "\" templateName=\"" )
        .append( templateName ).append( "\" file=\"" ).append( file ).append( "\"" );

    return builder.toString();
  }

}
