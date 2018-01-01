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

public class AvrLibTemplate extends Application
{
  private ConfigurationData configurationData;

  private Stage mainStage;

  private TemplateLoader templateLoader = new AvrLibTemplateLoader();

  public static void main(String[] args)
  {
    launch( args );
  }

  @Override
  public void start(Stage primaryStage) throws PropertyException, JAXBException
  {
    mainStage = primaryStage;
    mainStage.setTitle( "SaarBastler AVR Library Project" );

    // configurationData= new ConfigurationData();
    // configurationData.initAndSave();
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
      // cfg.setDirectoryForTemplateLoading( new File( "templates" ) );
      cfg.setDefaultEncoding( "UTF-8" );
      cfg.setTemplateExceptionHandler( TemplateExceptionHandler.RETHROW_HANDLER );
      cfg.setLogTemplateExceptions( false );

      configurationData.generateProject( cfg, values );
      // File solutionDir = new File( values.get( WORKSPACE_DIR ).toString() );
      // solutionDir.mkdirs();
      //
      // processTemplate( cfg, "atsln.ftlh", new File( solutionDir, values.get(
      // ASSEMBLY_NAME ).toString() + ".atsln" ),
      // values );
      //
      // processTemplate( cfg, "program.ftlh", new File( solutionDir,
      // "program.cmd" ), values );
      //
      // processTemplate( cfg, "terminal.ftlh", new File( solutionDir,
      // "terminal.cmd" ), values );
      //
      // File workspaceDir = new File( solutionDir, values.get( ASSEMBLY_NAME
      // ).toString() );
      // workspaceDir.mkdir();
      //
      // processTemplate( cfg, "cppproj.ftlh", new File( workspaceDir,
      // values.get( ASSEMBLY_NAME ) + ".cppproj" ),
      // values );
      //
      // processTemplate( cfg, "Device.h.ftlh", new File( workspaceDir,
      // "Device.h" ), values );
      //
      // processTemplate( cfg, "io_config.h.ftlh", new File( workspaceDir,
      // "io_config.h" ), values );
      //
      // processTemplate( cfg, "saba.h.ftlh", new File( workspaceDir, "saba.h"
      // ), values );
      //
      // processTemplate( cfg, "saba.cpp.ftlh", new File( workspaceDir,
      // "saba.cpp" ), values );
      //
      // processTemplate( cfg, "main.cpp.ftlh", new File( workspaceDir,
      // "main.cpp" ), values );
    }
    catch (Exception e)
    {
      Alert alert = new Alert( AlertType.ERROR );
      alert.setTitle( "IO Exception" );
      alert.setHeaderText( "Template generation" );
      alert.setContentText( e.getMessage() );
      alert.showAndWait();

      e.printStackTrace();
    }
    // catch (TemplateException e)
    // {
    // Alert alert = new Alert( AlertType.ERROR );
    // alert.setTitle( "TemplateException" );
    // alert.setHeaderText( "Template generation" );
    // alert.setContentText( e.getMessage() );
    // alert.showAndWait();
    //
    // e.printStackTrace();
    // }

  }

}
