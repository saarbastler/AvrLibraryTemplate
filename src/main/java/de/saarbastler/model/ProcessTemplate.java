package de.saarbastler.model;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import freemarker.template.Configuration;
import freemarker.template.Template;

@XmlType(name = "processTemplate")
// @XmlRootElement
public class ProcessTemplate extends TemplateExecuter
{
  @XmlElement
  private String templateName;

  @XmlElement
  private String file;

  public ProcessTemplate()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  public ProcessTemplate(String templateName, String file)
  {
    super();
    this.templateName = templateName;
    this.file = file;
  }

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
