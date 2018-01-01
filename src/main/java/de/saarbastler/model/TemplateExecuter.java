package de.saarbastler.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * The abstract super class of an executer. An Executer is able to create a
 * directory or a file.
 */
public abstract class TemplateExecuter
{

  /**
   * Execute it.
   *
   * @param cfg
   *          the feemarker configuration
   * @param values
   *          the values
   * @throws Exception
   *           the exception
   */
  abstract void execute(Configuration cfg, Map<String, String> values) throws Exception;

  /**
   * Evaluate a parameter string.
   *
   * @param cfg
   *          the cfg
   * @param cfg
   *          the feemarker configuration
   * @param values
   *          the values
   * @return the string
   * @throws TemplateException
   *           the template exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  String evaluateString(Configuration cfg, Map<String, String> values, String text)
      throws TemplateException, IOException
  {
    Template template = new Template( "noName", text, cfg );

    StringWriter out = new StringWriter();

    template.process( values, out );

    cfg.removeTemplateFromCache( "noName" );

    return out.toString();
  }
}
