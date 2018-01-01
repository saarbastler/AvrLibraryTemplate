package de.saarbastler.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class FieldDirectory extends FieldText
{
  
  public FieldDirectory(String label, String key)
  {
    super(label,key);
  }
  
  @Override
  public FieldDirectory addToGrid(Window window, GridPane gridPane, int row)
  {
    Label label = new Label(this.label);
    gridPane.add(label, 0, row);

    textField = new TextField();
    gridPane.add(textField, 1, row);

    Button button = new ButtonDirectoryChooser(window, textField);
    gridPane.add( button, 2, row );
    
    return this;
  }
}