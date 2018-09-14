import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
public class Main extends Application
{
	static String startlevel = "end";
	//Launch application
	public static void main(String[] args)
	{
		if(args.length > 0)
			startlevel = args[0];
		launch(args);
	}
	//Create stage
	public void start(Stage stage)
	{
		GameWorld world = GameWorld.getWorld();
		world.load(startlevel);
		Scene scene = new Scene(world, Color.BLACK);
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
