package de.saarbastler.ui;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class FieldList extends Field
{
  private ObservableList<String> items;
  private ComboBox<String> combo;

  public FieldList(String label, String key, ObservableList<String> items)
  {
    super( label, key );
    this.items = items;
  }

  @Override
  public Field addToGrid(Window window, GridPane gridPane, int row)
  {
    Label label = new Label( this.label );
    gridPane.add( label, 0, row );

    combo = new ComboBox<>( items );
    gridPane.add( combo, 1, row, 2, 1 );

    return this;
  }

  @Override
  public String getValue()
  {
    return combo.getSelectionModel().getSelectedItem();
  }

  @Override
  public void setValue(String value)
  {
    combo.getSelectionModel().select( value );
  }
}
