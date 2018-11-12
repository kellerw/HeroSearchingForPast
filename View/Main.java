import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
public class Main extends Application
{
	static String startlevel = "end";
	public static boolean enablesaves = false;
	//Launch application
	public static void main(String[] args)
	{
		if(args.length > 0)
			startlevel = args[0];
		launch(args);
	}
	private static Stage astage;
	public static void setWorld()
	{
		GameWorld world = GameWorld.getWorld();
		world.load(startlevel);
		Scene scene = new Scene(world, Color.rgb(9,5,15));
		world.minWidthProperty().bind(scene.widthProperty());
		world.maxWidthProperty().bind(scene.widthProperty());
		world.prefWidthProperty().bind(scene.widthProperty());
		world.minHeightProperty().bind(scene.heightProperty());
		world.maxHeightProperty().bind(scene.heightProperty());
		world.prefHeightProperty().bind(scene.heightProperty());
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			world.getPlayer().handleKey(key.getCode());
		});
		scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
			world.getPlayer().handleKeyRelease(key.getCode());
		});
		astage.setScene(scene);
		astage.setFullScreen(true);
	}
	//Create stage
	public void start(Stage stage)
	{
		astage = stage;
		setWorld();
		GameWorld.getWorld().loadsave();
		enablesaves = true;
		GameWorld.getWorld().showMenu();
		GameWorld.getWorld().showMainMenu();
		stage.setTitle("Searching for a Past");
		//astage.setMaximized(true);
		//astage.setFullScreen(true);
		stage.show();
	}
}
