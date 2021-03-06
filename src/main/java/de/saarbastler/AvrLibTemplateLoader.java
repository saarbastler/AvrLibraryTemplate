package de.saarbastler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.saarbastler.model.ConfigurationData;
import freemarker.cache.TemplateLoader;

/**
 * The Class Freemarker TemplateLoader to load the resource from the jar file
 */
public class AvrLibTemplateLoader implements TemplateLoader
{
  private static final Logger log = LogManager.getLogger( AvrLibTemplateLoader.class );

  @Override
  public Object findTemplateSource(String name) throws IOException
  {
    File file = new File( ConfigurationData.ECLIPSE_RESOURCE_PATH + name );

    if (file.exists())
    {
      log.trace( "Found resource {} as {}", name, file );
      return new FileReader( file );
    }

    URL resource = Thread.currentThread().getContextClassLoader().getResource( "/" + name );
    if (resource != null)
    {
      log.trace( "Found resource {} as {}", name, resource );
      return resource;
    }

    log.trace( "resource {} not found", name );

    return null;
  }

  @Override
  public long getLastModified(Object templateSource)
  {
    return 0;
  }

  @Override
  public Reader getReader(Object templateSource, String encoding) throws IOException
  {
    if (templateSource instanceof FileReader)
      return (FileReader) templateSource;
    else if (templateSource instanceof URL)
      return new InputStreamReader( ((URL) templateSource).openStream(), encoding );

    log.error( "undefined template source type: {}", templateSource );

    throw new IllegalArgumentException( "undefined type: " + templateSource.getClass() );
  }

  @Override
  public void closeTemplateSource(Object templateSource) throws IOException
  {
    ((Reader) templateSource).close();
  }

}
