package de.saarbastler;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.saarbastler.model.ConfigurationData;
import de.saarbastler.ui.Form;
import freemarker.cache.TemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * The Main Class showing the form and executing the templates.
 */
public class AvrLibTemplate extends Application
{
  private static final Logger log = LogManager.getLogger( AvrLibTemplate.class );

  private ConfigurationData configurationData;

  private Stage mainStage;

  private TemplateLoader templateLoader = new AvrLibTemplateLoader();

  public static void main(String[] args)
  {
    log.debug( "Avr Library Template generator started" );

    launch( args );

    log.debug( "Avr Library Template generator terminated" );
  }

  @Override
  public void start(Stage primaryStage) throws PropertyException, JAXBException
  {
    mainStage = primaryStage;
    mainStage.setTitle( "SaarBastler AVR Library Project" );

    try
    {
      configurationData = ConfigurationData.loadConfig();
    }
    catch (Exception e)
    {
      e.printStackTrace();

      Alert alert = new Alert( AlertType.ERROR, "Unable to load resource 'config.xml':" + e.getMessage(),
          ButtonType.OK );
      alert.showAndWait();
      System.exit( -1 );
    }

    Form form = new Form( configurationData );
    form.setOnAction( new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        saveData( form.getValues() );

        generateProject( form.getValues() );
      }
    } );

    Scene scene = new Scene( form.initialize( mainStage ) );

    readData().ifPresent( map -> form.setValues( map ) );

    mainStage.setScene( scene );

    mainStage.show();
  }

  protected void saveData(Map<String, String> values)
  {
    try (XMLEncoder xmlEncoder = new XMLEncoder( new FileOutputStream( "data.xml" ) ))
    {
      xmlEncoder.writeObject( values );
    }
    catch (FileNotFoundException e)
    {
      Alert alert = new Alert( AlertType.ERROR );
      alert.setTitle( "Error saving File" );
      alert.setHeaderText( "File: data.xml" );
      alert.setContentText( e.getMessage() );
      alert.showAndWait();

      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  protected Optional<Map<String, String>> readData()
  {
    File file = new File( "data.xml" );
    if (file.exists())
      try (XMLDecoder xmlDecoder = new XMLDecoder( new FileInputStream( file ) ))
      {
        Map<String, String> data = (Map<String, String>) xmlDecoder.readObject();

        return Optional.of( data );
      }
      catch (FileNotFoundException e)
      {
        Alert alert = new Alert( AlertType.ERROR );
        alert.setTitle( "Error reading File" );
        alert.setHeaderText( "File: data.xml" );
        alert.setContentText( e.getMessage() );
        alert.showAndWait();

        e.printStackTrace();
      }

    return Optional.empty();
  }

  protected void processTemplate(Configuration cfg, String templateName, File destFile, Map<String, String> values)
      throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
  {
    Template template = cfg.getTemplate( templateName );
    try (Writer out = new FileWriter( destFile ))
    {
      template.process( values, out );
    }
  }

  protected void generateProject(Map<String, String> values)
  {
    configurationData.preprocessData( values );

    try
    {
      Configuration cfg = new Configuration( Configuration.VERSION_2_3_25 );
      cfg.setTemplateLoader( templateLoader );
      cfg.setDefaultEncoding( "UTF-8" );
      cfg.setTemplateExceptionHandler( TemplateExceptionHandler.RETHROW_HANDLER );
      cfg.setLogTemplateExceptions( false );

      configurationData.generateProject( cfg, values );
    }
    catch (Exception e)
    {
      Alert alert = new Alert( AlertType.ERROR );
      alert.setTitle( "IO Exception" );
      alert.setHeaderText( "Template generation" );
      alert.setContentText( e.getMessage() );
      alert.showAndWait();
    }
  }

}
