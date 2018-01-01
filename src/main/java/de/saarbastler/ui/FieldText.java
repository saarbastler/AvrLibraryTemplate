package de.saarbastler.ui;

import javafx.scene.control.TextField;

/**
 * The super class of all text input controls
 */
public abstract class FieldText extends Field
{

  /** The text field. */
  protected TextField textField;

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.ui.Field#getValue()
   */
  @Override
  public String getValue()
  {
    return textField.getText();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.saarbastler.ui.Field#setValue(java.lang.String)
   */
  @Override
  public void setValue(String value)
  {
    textField.setText( value );
  }

}