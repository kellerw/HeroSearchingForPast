import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
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
		Scene scene = new Scene(world);
		world.minWidthProperty().bind(scene.widthProperty());
		world.maxWidthProperty().bind(scene.widthProperty());
		world.prefWidthProperty().bind(scene.widthProperty());
		world.minHeightProperty().bind(scene.heightProperty());
		world.maxHeightProperty().bind(scene.heightProperty());
		world.prefHeightProperty().bind(scene.heightProperty());
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			world.getPlayer().handleKey(key.getCode());
		});
		stage.setScene(scene);
		stage.setTitle("A Hero Searching for a Past");
		stage.setMaximized(true);
		stage.setFullScreen(true);
		stage.show();
	}
}
