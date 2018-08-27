import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
public class Editor extends Application
{
	public static final int headerBarHeight = 100;
	public static final int rightPaneWidth = 500;
	public static final int addColWidth = 50;
	//Launch application
	public static void main(String[] args)
	{
		launch(args);
	}
	//Create stage
	public void start(Stage stage)
	{
		GameObject.LIVE = true;
		Pane editor = new Pane();
		Pane editPane = new Pane();
		editPane.layoutXProperty().bind(editor.widthProperty().subtract(rightPaneWidth));
		GameObject.injectable = o->{
			Pane ed = o.getEditor();
			ed.setMinWidth(rightPaneWidth);
			ed.setMaxWidth(rightPaneWidth);
			ed.setPrefWidth(rightPaneWidth);
			ed.minHeightProperty().bind(editor.heightProperty());
			ed.maxHeightProperty().bind(editor.heightProperty());
			ed.prefHeightProperty().bind(editor.heightProperty());
			editPane.getChildren().clear();
			editPane.getChildren().add(ed);
		};
		GameWorld world = GameWorld.getWorld();
		editor.getChildren().add(world);
		Scene scene = new Scene(editor, Color.BLACK);
		world.minWidthProperty().bind(editor.widthProperty().subtract(rightPaneWidth + 2*addColWidth));
		world.maxWidthProperty().bind(editor.widthProperty().subtract(rightPaneWidth + 2*addColWidth));
		world.prefWidthProperty().bind(editor.widthProperty().subtract(rightPaneWidth + 2*addColWidth));
		world.minHeightProperty().bind(editor.heightProperty().subtract(headerBarHeight + 2*addColWidth));
		world.maxHeightProperty().bind(editor.heightProperty().subtract(headerBarHeight + 2*addColWidth));
		world.prefHeightProperty().bind(editor.heightProperty().subtract(headerBarHeight + 2*addColWidth));
		world.setLayoutX(addColWidth);
		world.setLayoutY(headerBarHeight + addColWidth);
		editor.getChildren().add(getButton("+", ()->{world.expandTop();}, new SimpleDoubleProperty(addColWidth), new SimpleDoubleProperty(headerBarHeight), world.widthProperty(), new SimpleDoubleProperty(addColWidth)));
		editor.getChildren().add(getButton("+", ()->{world.expandBottom();}, new SimpleDoubleProperty(addColWidth), editor.heightProperty().subtract(addColWidth), world.widthProperty(), new SimpleDoubleProperty(addColWidth)));
		editor.getChildren().add(getButton("+", ()->{world.expandLeft();}, new SimpleDoubleProperty(0), new SimpleDoubleProperty(headerBarHeight), new SimpleDoubleProperty(addColWidth), editor.heightProperty().subtract(headerBarHeight)));
		editor.getChildren().add(getButton("+", ()->{world.expandRight();}, editor.widthProperty().subtract(addColWidth + rightPaneWidth), new SimpleDoubleProperty(headerBarHeight), new SimpleDoubleProperty(addColWidth), editor.heightProperty().subtract(headerBarHeight)));
		editor.getChildren().add(editPane);
		stage.setTitle("A Hero Searching for a Past Editor");
		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();
	}
	
	public static Button getButton(String text, Ev action, ObservableDoubleValue xproperty, ObservableDoubleValue yproperty, ObservableDoubleValue width, ObservableDoubleValue height)
	{
		Button b = new Button(text);
		b.setOnAction(e->action.act());
		b.layoutXProperty().bind(xproperty);
		b.layoutYProperty().bind(yproperty);
		b.minHeightProperty().bind(height);
		b.maxHeightProperty().bind(height);
		b.prefHeightProperty().bind(height);
		b.minWidthProperty().bind(width);
		b.maxWidthProperty().bind(width);
		b.prefWidthProperty().bind(width);
		return b;
	}
	public static interface Ev
	{
		public void act();
	}
}
 
