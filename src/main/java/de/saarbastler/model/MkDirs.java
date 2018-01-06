package de.saarbastler.model;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Create direcories
 */
@XmlType(name = "mkdirs")
public class MkDirs extends TemplateExecuter
{
  private static final Logger log = LogManager.getLogger( ProcessTemplate.class );

  /** The path. */
  @XmlElement
  private String path;

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
  public void executeConditional(Configuration cfg, Map<String, String> values) throws TemplateException, IOException
  {
    File solutionDir = new File( evaluateString( cfg, values, path ) );

    log.info( "Creating directory '{}'", solutionDir );

    solutionDir.mkdirs();
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append( "mkdirs if=\"" ).append( getIfCondition() ).append( "\" path=\"" ).append( path ).append( "\"" );

    return builder.toString();
  }

}
