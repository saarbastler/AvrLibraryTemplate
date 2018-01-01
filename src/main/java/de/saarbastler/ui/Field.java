package de.saarbastler.ui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import javafx.scene.layout.GridPane;
import javafx.stage.Window;

/**
 * The super Class of all Fields.
 */
public abstract class Field
{

  /** The entered value will be stored using this key */
  @XmlElement
  private String key;

  /** The label shown to the user. */
  @XmlElement
  protected String label;

  /**
   * Gets the entered value.
   *
   * @return the value
   */
  @XmlTransient
  public abstract String getValue();

  /**
   * Sets the value.
   *
   * @param value
   *          the new value
   */
  public abstract void setValue(String value);

  /**
   * Adds the control to the grid.
   *
   * @param window
   *          the window
   * @param gridPane
   *          the grid pane
   * @param row
   *          the row
   * @return the field
   */
  public abstract Field addToGrid(Window window, GridPane gridPane, int row);

  /**
   * Gets the key.
   *
   * @return the key
   */
  public String getKey()
  {
    return key;
  }

}