package de.saarbastler.ui;

import javafx.scene.control.TextField;

public abstract class FieldText extends Field
{
  protected TextField textField;

  public FieldText(String label, String key)
  {
    super( label, key );
  }

  @Override
  public String getValue()
  {
    return textField.getText(); 
  }

  @Override
  public void setValue(String value)
  {
    textField.setText( value );
  }

}