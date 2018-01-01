package de.saarbastler.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class TemplateExecuter
{
  abstract void execute(Configuration cfg, Map<String, String> values) throws Exception;

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
