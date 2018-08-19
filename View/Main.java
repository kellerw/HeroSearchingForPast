import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
public class Main extends Application
{
	//Launch application
	public static void main(String[] args)
	{
		launch(args);
	}
	//Create stage
	public void start(Stage stage)
	{
		GameWorld world = GameWorld.getWorld();
		stage.setScene(new Scene(world));
		stage.setTitle("A Hero Searching for a Past");
		stage.setMaximized(true);
		stage.setFullScreen(true);
		stage.show();
	}
}
