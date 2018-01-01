package de.saarbastler.ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class ButtonDirectoryChooser extends Button
{
  //private TextField textField;
  
  public ButtonDirectoryChooser(Window window, TextField textField)
  {
	  super("...");
	  //this.textField= textField;
	  
	  setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent actionEvent)
      {
        DirectoryChooser directoryChooser= new DirectoryChooser();
        
        directoryChooser.setTitle("Choose directory");
        File file= new File(textField.getText());
        if( file.exists() )
          directoryChooser.setInitialDirectory(file);
        
        file= directoryChooser.showDialog(window);
      
        if( file != null)
          textField.setText(file.getPath());
      }
    });
  }
  
}
