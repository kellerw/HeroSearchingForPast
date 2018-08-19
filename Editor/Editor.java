import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
public class Editor extends Application
{
	//Launch application
	public static void main(String[] args)
	{
		launch(args);
	}
	//Create stage
	public void start(Stage stage)
	{
		GameObject.LIVE = true;
		stage.setScene(new Scene(new Pane()));
		stage.setTitle("A Hero Searching for a Past Editor");
		stage.setMaximized(true);
		stage.show();
	}
}
 
