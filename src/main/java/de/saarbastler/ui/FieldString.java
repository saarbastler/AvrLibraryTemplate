package de.saarbastler.ui;

import javax.xml.bind.annotation.XmlType;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

/**
 * The simple text input control.
 */
@XmlType(name = "string")
public class FieldString extends FieldText
{

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.ui.Field#addToGrid(javafx.stage.Window,
   * javafx.scene.layout.GridPane, int)
   */
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
