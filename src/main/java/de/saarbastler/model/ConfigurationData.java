package de.saarbastler.model;

import static de.saarbastler.StringConstants.AVRDEVICE;
import static de.saarbastler.StringConstants.AVR_LIB_PATH_ABSOLUTE;
import static de.saarbastler.StringConstants.AVR_LIB_PATH_RELATIVE;
import static de.saarbastler.StringConstants.CPU;
import static de.saarbastler.StringConstants.MCU;
import static de.saarbastler.StringConstants.WORKSPACE_DIR;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.saarbastler.ui.UITab;
import freemarker.template.Configuration;

/**
 * The Class ConfigurationData is loaded from XML during startup. It defines the
 * UI and the template execution.
 */
@XmlRootElement
@XmlSeeAlso({ MkDirs.class, ProcessTemplate.class })
public class ConfigurationData
{

  /** The Constant log. */
  private static final Logger log = LogManager.getLogger( ConfigurationData.class );

  /** The config file name. */
  public static final String RESOURCE_CONFIG = "config.xml";

  /** Workaround to run it in eclipse. */
  public static final String ECLIPSE_RESOURCE_PATH = "src/main/resources/";

  /** Workaround to run it in eclipse. */
  public static final String ECLIPSE_RESOURCE_CONFIG = ECLIPSE_RESOURCE_PATH + RESOURCE_CONFIG;

  /** Special treatment for devices macros. */
  @XmlElementWrapper
  @XmlElement(name = "device")
  private List<Device> devices = new ArrayList<>();

  /** The template executers. */
  @XmlElementWrapper(name = "templateExecuters")
  @XmlElements({ @XmlElement(name = "mkdirs", type = MkDirs.class, required = false),
      @XmlElement(name = "processTemplate", type = ProcessTemplate.class, required = false),
      @XmlElement(name = "configWriter", type = ConfigurationWriter.class, required = false) })
  private List<TemplateExecuter> templateExecuters = new ArrayList<>();

  /** The tabs containing the fields. */
  @XmlElementWrapper
  @XmlElement(name = "tab")
  private List<UITab> tabs = new ArrayList<>();

  /**
   * Load the XML config.
   *
   * @return the configuration data
   * @throws JAXBException
   *           the JAXB exception
   */
  public static ConfigurationData loadConfig() throws JAXBException
  {
    JAXBContext jaxbContext = JAXBContext.newInstance( ConfigurationData.class );
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    File file = new File( ECLIPSE_RESOURCE_CONFIG );
    if (file.exists())
      return (ConfigurationData) unmarshaller.unmarshal( file );

    InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream( RESOURCE_CONFIG );
    if (resource == null)
      throw new IllegalArgumentException( "unable to find resource " + RESOURCE_CONFIG );

    return (ConfigurationData) unmarshaller.unmarshal( resource );
  }

  /**
   * Gets the devices.
   *
   * @return the devices
   */
  public List<String> getDevices()
  {
    List<String> result = new ArrayList<>( devices.size() );
    devices.forEach( d -> result.add( d.getAvrdevice() ) );

    return result;
  }

  /**
   * Gets the fields.
   *
   * @return the fields
   */
  // public List<Field> getFields()
  // {
  // return fields;
  // }
  public List<UITab> getTabs()
  {
    return tabs;
  }

  /**
   * Some special treatment, the Macros CPU and MCU are derived from AVRDEVICE.
   *
   * @param values
   *          the values
   */
  public void preprocessData(Map<String, String> values)
  {
    String avrDevice = values.get( AVRDEVICE );
    Optional<Device> device = devices.stream().filter( d -> d.getAvrdevice().equals( avrDevice ) ).findFirst();
    if (!device.isPresent())
      throw new IllegalArgumentException( "undefined AVRDEVICE: " + avrDevice );

    values.put( CPU, device.get().getCpu() );
    values.put( MCU, device.get().getMcu() );

    Path workspace = Paths.get( values.get( WORKSPACE_DIR ) );
    Path library = Paths.get( values.get( AVR_LIB_PATH_ABSOLUTE ) );

    Path relative = workspace.relativize( library );

    values.put( AVR_LIB_PATH_RELATIVE, relative.toString() );
  }

  /**
   * Generate the project.
   *
   * @param cfg
   *          the cfg
   * @param values
   *          the values
   * @throws Exception
   *           the exception
   */
  public void generateProject(Configuration cfg, Map<String, String> values) throws Exception
  {
    for (TemplateExecuter executer : templateExecuters)
      try
      {
        executer.execute( cfg, values );
      }
      catch (Exception e)
      {
        log.error( "Exception {} in TemplateExecuter {}", e.getMessage(), executer );
        log.catching( e );
        throw e;
      }
  }
}
