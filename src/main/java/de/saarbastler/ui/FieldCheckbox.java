package de.saarbastler.ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class FieldCheckbox extends Field
{
  protected CheckBox checkBox;

  @Override
  public String getValue()
  {
    return checkBox.isSelected() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
  }

  @Override
  public void setValue(String value)
  {
    checkBox.setSelected( Boolean.valueOf( value ) );
  }

  @Override
  public Field addToGrid(Window window, GridPane gridPane, int row)
  {
    Label label = new Label( this.label );
    gridPane.add( label, 0, row );

    checkBox = new CheckBox();

    gridPane.add( checkBox, 1, row, 2, 1 );

    return this;
  }

}
