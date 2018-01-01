package de.saarbastler.ui;

import static de.saarbastler.StringConstants.ASSEMBLY_NAME;
import static de.saarbastler.StringConstants.AVRDEVICE;
import static de.saarbastler.StringConstants.AVRDUDE_CONFIG_FILE;
import static de.saarbastler.StringConstants.AVRDUDE_PATH;
import static de.saarbastler.StringConstants.AVR_LIB_PATH_ABSOLUTE;
import static de.saarbastler.StringConstants.BAUDRATE;
import static de.saarbastler.StringConstants.COMPORT;
import static de.saarbastler.StringConstants.F_CPU;
import static de.saarbastler.StringConstants.PROGRAM_ALGO;
import static de.saarbastler.StringConstants.WORKSPACE_DIR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.saarbastler.model.ConfigurationData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class Form
{
  private List<Field> fields;

  private Optional<EventHandler<ActionEvent>> eventHandler = Optional.empty();

  private Map<String,String> values;
  
  private ConfigurationData configurationData;
  
  public Form(ConfigurationData configurationData)
  {
    super();
    this.configurationData = configurationData;
  }

  public GridPane initialize(Window window)
  {
    GridPane grid = new GridPane();

    grid.setAlignment( Pos.CENTER );
    grid.setHgap( 10 );
    grid.setVgap( 10 );
    grid.setPadding( new Insets( 25, 25, 25, 25 ) );

    fields = new ArrayList<>();

    fields.add( new FieldDirectory( "AvrDude Path", AVRDUDE_PATH ).addToGrid( window, grid, fields.size() + 1 ) );

    fields.add(
        new FieldFile( "AvrDude Config file", AVRDUDE_CONFIG_FILE ).addToGrid( window, grid, fields.size() + 1 ) );

    fields.add( new FieldList( "CPU", AVRDEVICE, FXCollections.observableList( configurationData.getDevices() ) ).addToGrid( window, grid,
        fields.size() + 1 ) );

    fields
        .add( new FieldList( "Programming algorithm", PROGRAM_ALGO, FXCollections.observableList( configurationData.getProgramAlgorithms() ) )
            .addToGrid( window, grid, fields.size() + 1 ) );

    fields.add( new FieldInteger( "Com Port", COMPORT ).addToGrid( window, grid, fields.size() + 1 ) );

    fields.add( new FieldList( "Baudrate", BAUDRATE, FXCollections.observableList( configurationData.getBaudrates() ) ).addToGrid( window,
        grid, fields.size() + 1 ) );

    fields
        .add( new FieldDirectory( "Workspace Directory", WORKSPACE_DIR ).addToGrid( window, grid, fields.size() + 1 ) );

    fields.add( new FieldDirectory( "Library Path", AVR_LIB_PATH_ABSOLUTE ).addToGrid( window, grid, fields.size() + 1 ) );

    fields.add( new FieldString( "Assembly name:", ASSEMBLY_NAME ).addToGrid( window, grid, fields.size() + 1 ) );

    fields.add( new FieldInteger( "CPU Frequency", F_CPU ).addToGrid( window, grid, fields.size() + 1 ) );

    Button start = new Button( "Start" );
    HBox hbBtn = new HBox( 10 );
    hbBtn.setAlignment( Pos.BOTTOM_RIGHT );
    hbBtn.getChildren().add( start );
    grid.add( hbBtn, 1, fields.size() + 1 );

    start.setOnAction( new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        Map<String, String> values = new HashMap<>();
        for (Field field : fields)
        {
          values.put( field.getKey(), field.getValue() );
        }

        Form.this.values= values;
        eventHandler.ifPresent( eh -> eh.handle( new ActionEvent() ) );
      }
    } );
    return grid;
  }

  public void setOnAction(EventHandler<ActionEvent> eventHandler)
  {
    this.eventHandler = Optional.ofNullable( eventHandler );
  }

  public Map<String, String> getValues()
  {
    return values;
  }

  public void setValues(Map<String, String> values)
  {
    this.values = values;
    for (Field field : fields)
    {
      if( this.values.containsKey( field.getKey() ))
        field.setValue( this.values.get( field.getKey() ));
    }
  }
  
  
}
