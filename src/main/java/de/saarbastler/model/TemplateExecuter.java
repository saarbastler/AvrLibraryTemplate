package de.saarbastler.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * The abstract super class of an executer. An Executer is able to create a
 * directory or a file.
 */
public abstract class TemplateExecuter
{

  /** The if condition. */
  @XmlAttribute(name = "if")
  private String ifCondition;

  /**
   * Execute conditional has to be implented in derived classes.
   *
   * @param cfg
   *          the feemarker configuration
   * @param values
   *          the macro values defined in the UI
   * @throws Exception
   *           the exception in case of any error
   */
  protected abstract void executeConditional(Configuration cfg, Map<String, String> values) throws Exception;

  /**
   * Gets the logger.
   *
   * @return the logger
   */
  protected abstract Logger getLogger();

  /**
   * Execute it. This method will test the attribut "if", if it is missing, or
   * it evaluates to "true" the executeConditional will be called.
   *
   * @param cfg
   *          the feemarker configuration
   * @param values
   *          the macro values defined in the UI
   * @throws Exception
   *           the exception in case of any error
   */
  public void execute(Configuration cfg, Map<String, String> values) throws Exception
  {
    boolean doExecute = true;
    if (ifCondition != null && !ifCondition.isEmpty())
    {
      String textCondition = evaluateString( cfg, values, ifCondition );
      getLogger().debug( "if='{}' evaluates to '{}'", ifCondition, textCondition );

      doExecute = Boolean.valueOf( textCondition );
    }

    if (doExecute)
      executeConditional( cfg, values );
  }

  /**
   * Evaluate a parameter string.
   *
   * @param cfg
   *          the feemarker configuration
   * @param values
   *          the values
   * @param text
   *          the text
   * @return the string
   * @throws TemplateException
   *           the template exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected String evaluateString(Configuration cfg, Map<String, String> values, String text)
      throws TemplateException, IOException
  {
    Template template = new Template( "noName", text.trim(), cfg );

    StringWriter out = new StringWriter();

    template.process( values, out );

    cfg.removeTemplateFromCache( "noName" );

    getLogger().trace( "Evaluating '{}' to {}'", text, out.toString() );

    return out.toString();
  }

  protected String getIfCondition()
  {
    return ifCondition;
  }

}
