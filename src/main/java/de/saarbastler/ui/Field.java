package de.saarbastler.ui;

import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public abstract class Field
{
  private final String key;

  protected final String label;

  public abstract String getValue();
  
  public abstract void setValue(String value);
  
  public abstract Field addToGrid(Window window, GridPane gridPane, int row);
  
  public Field(String label, String key)
  {
    super();
    this.label = label;
    this.key = key;
  }

  public String getKey()
  {
    return key;
  }

}