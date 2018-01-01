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
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import freemarker.template.Configuration;

@XmlRootElement
@XmlSeeAlso({ MkDirs.class, ProcessTemplate.class })
public class ConfigurationData
{
  public static final String RESOURCE_CONFIG = "config.xml";
  public static final String ECLIPSE_RESOURCE_PATH = "src/main/resources/";
  public static final String ECLIPSE_RESOURCE_CONFIG = ECLIPSE_RESOURCE_PATH + RESOURCE_CONFIG;

  @XmlElementWrapper
  @XmlElement(name = "device")
  private List<Device> devices = new ArrayList<>();

  @XmlElementWrapper(name = "programAlgorithms")
  @XmlElement(name = "programAlgorithm")
  private List<String> programAlgorithms = new ArrayList<>();

  @XmlElementWrapper(name = "baudrates")
  @XmlElement(name = "baudrate")
  private List<String> baudrates = new ArrayList<>();

  @XmlElementWrapper(name = "templateExecuters")
  @XmlElements({ @XmlElement(name = "mkdirs", type = MkDirs.class, required = false),
      @XmlElement(name = "processTemplate", type = ProcessTemplate.class, required = false) })
  private List<TemplateExecuter> templateExecuters = new ArrayList<>();

  public ConfigurationData()
  {
  }

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
   * Initialzes data the and save. Only used to create an initial config.xml
   * file.
   *
   * @throws JAXBException
   *           the JAXB exception
   * @throws PropertyException
   *           the property exception
   */
  // public void initAndSave() throws JAXBException, PropertyException
  // {
  // devices.add( new Device( "m8", "ATmega8", "atmega8" ) );
  // devices.add( new Device( "m328p", "ATmega328P", "atmega328p" ) );
  // devices.add( new Device( "m32u4", "ATmega32u4", "atmega32u4" ) );
  // devices.add( new Device( "m2560", "ATmega2560", "atmega2560" ) );
  //
  // programAlgorithms.add( "wiring" );
  // programAlgorithms.add( "arduino" );
  // programAlgorithms.add( "stk500" );
  // programAlgorithms.add( "AVR109" );
  //
  // baudrates.add( "19200" );
  // baudrates.add( "38400" );
  // baudrates.add( "57600" );
  // baudrates.add( "115200" );
  //
  // templateExecuters.add( new MkDirs( "${WORKSPACE_DIR}" ) );
  // templateExecuters.add( new ProcessTemplate( "atsln.ftlh",
  // "${ASSEMBLY_NAME}.atsln" ) );
  //
  // JAXBContext jaxbContext = JAXBContext.newInstance( ConfigurationData.class
  // );
  // Marshaller marshaller = jaxbContext.createMarshaller();
  //
  // marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
  //
  // marshaller.marshal( this, new File( "config.xml" ) );
  // }

  public List<String> getDevices()
  {
    List<String> result = new ArrayList<>( devices.size() );
    devices.forEach( d -> result.add( d.getAvrdevice() ) );

    return result;
  }

  public List<String> getProgramAlgorithms()
  {
    return programAlgorithms;
  }

  public List<String> getBaudrates()
  {
    return baudrates;
  }

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

  public void generateProject(Configuration cfg, Map<String, String> values) throws Exception
  {
    for (TemplateExecuter executer : templateExecuters)
    {
      executer.execute( cfg, values );
    }

  }
}
