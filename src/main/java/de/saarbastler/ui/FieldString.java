package de.saarbastler.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class FieldString extends FieldText
{

  public FieldString(String label, String key)
  {
    super( label, key );
  }

  @Override
  public Field addToGrid(Window window, GridPane gridPane, int row)
  {
    Label label = new Label( this.label );
    gridPane.add( label, 0, row );

    textField = new TextField();

    gridPane.add( textField, 1, row, 2, 1 );

    return this;
  }

}
