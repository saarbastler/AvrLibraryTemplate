package de.saarbastler.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.saarbastler.model.ConfigurationData;
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

  private Map<String, String> values;

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

    fields = configurationData.getFields();
    int index = 0;
    for (Field field : fields)
      field.addToGrid( window, grid, ++index );

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

        Form.this.values = values;
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
      if (this.values.containsKey( field.getKey() ))
        field.setValue( this.values.get( field.getKey() ) );
    }
  }

}
