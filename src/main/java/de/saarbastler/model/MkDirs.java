package de.saarbastler.model;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@XmlType(name = "mkdirs")
// @XmlRootElement
public class MkDirs extends TemplateExecuter
{
  @XmlElement
  private String path;

  public MkDirs(String path)
  {
    super();
    this.path = path;
  }

  public MkDirs()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(Configuration cfg, Map<String, String> values) throws TemplateException, IOException
  {
    File solutionDir = new File( evaluateString( cfg, values, path ) );
    solutionDir.mkdirs();
  }

}
