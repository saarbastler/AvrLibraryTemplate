package de.saarbastler.model;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;

/**
 * The Class ConfigurationWriter writes the user entered configuration
 */
@XmlType(name = "configWriter")
public class ConfigurationWriter extends FileExecuter
{
  private static final Logger log = LogManager.getLogger( ConfigurationWriter.class );

  /**
   * Write the values in a configuration file.
   *
   * @param values
   *          the configuration values
   * @param filename
   *          the filename
   * @throws FileNotFoundException
   *           the file not found exception
   */
  public static void writeConfigurationFile(Map<String, String> values, String filename) throws FileNotFoundException
  {
    try (XMLEncoder xmlEncoder = new XMLEncoder( new FileOutputStream( filename ) ))
    {
      xmlEncoder.writeObject( values );
    }
  }

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
   * @see de.saarbastler.model.TemplateExecuter#executeConditional(freemarker.
   * template.Configuration, java.util.Map)
   */
  @Override
  protected void executeConditional(Configuration cfg, Map<String, String> values) throws Exception
  {
    String outFile = evaluateString( cfg, values, file );

    log.info( "writing configration to file '{}'", outFile );

    writeConfigurationFile( values, outFile );
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append( "configWriter if=\"" ).append( getIfCondition() ).append( "\" file=\"" ).append( file )
        .append( "\"" );

    return builder.toString();
  }

}
