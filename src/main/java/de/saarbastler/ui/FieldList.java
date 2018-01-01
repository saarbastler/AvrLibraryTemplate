package de.saarbastler.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

/**
 * The Combobox control
 */
@XmlType(name = "list")
public class FieldList extends Field
{

  /** The items, the user can choose one. */
  @XmlElementWrapper(name = "items")
  @XmlElement(name = "item")
  private List<String> items;

  /** The combo control itself. */
  @XmlTransient
  private ComboBox<String> combo;

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

    combo = new ComboBox<>( FXCollections.observableList( items ) );
    gridPane.add( combo, 1, row, 2, 1 );

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.ui.Field#getValue()
   */
  @Override
  public String getValue()
  {
    return combo.getSelectionModel().getSelectedItem();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.ui.Field#setValue(java.lang.String)
   */
  @Override
  public void setValue(String value)
  {
    combo.getSelectionModel().select( value );
  }
}
