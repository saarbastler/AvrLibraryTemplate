package de.saarbastler.ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ButtonFileChooser extends Button
{

  public ButtonFileChooser(Window window, TextField textField)
  {
    super( "..." );

    setOnAction( new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent actionEvent)
      {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle( "Choose file" );
        File file = new File( textField.getText() );
        if (file.exists())
        {
          fileChooser.setInitialDirectory( file.getParentFile() );
          fileChooser.setInitialFileName( file.getName() );
        }
        file = fileChooser.showOpenDialog( window );

        if (file != null)
          textField.setText( file.getPath() );
      }
    } );
  }

}
