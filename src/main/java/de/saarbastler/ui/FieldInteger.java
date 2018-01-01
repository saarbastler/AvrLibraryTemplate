package de.saarbastler.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import javafx.util.converter.NumberStringConverter;

public class FieldInteger extends FieldText
{

  public FieldInteger(String label, String key)
  {
    super( label, key );
  }

  @Override
  public Field addToGrid(Window window, GridPane gridPane, int row)
  {
    Label label = new Label( this.label );
    gridPane.add( label, 0, row );

    textField = new TextField();
    textField.setTextFormatter( new TextFormatter<>( new NumberStringConverter() ) );
    textField.setAlignment( Pos.CENTER_RIGHT );

    gridPane.add( textField, 1, row, 2, 1 );

    return this;
  }

  @Override
  public String getValue()
  {
    String value = super.getValue();
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < value.length(); i++)
      if (Character.isDigit( value.charAt( i ) ))
        result.append( value.charAt( i ) );

    return result.toString();
  }

}
