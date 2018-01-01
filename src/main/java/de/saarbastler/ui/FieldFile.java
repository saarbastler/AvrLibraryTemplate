package de.saarbastler.ui;

import javax.xml.bind.annotation.XmlType;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

/**
 * The file chooser control.
 */
@XmlType(name = "file")
public class FieldFile extends FieldText
{

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.ui.Field#addToGrid(javafx.stage.Window,
   * javafx.scene.layout.GridPane, int)
   */
  @Override
  public FieldFile addToGrid(Window window, GridPane gridPane, int row)
  {
    Label label = new Label( this.label );
    gridPane.add( label, 0, row );

    textField = new TextField();
    gridPane.add( textField, 1, row );

    Button button = new ButtonFileChooser( window, textField );
    gridPane.add( button, 2, row );

    return this;
  }

}
