import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import java.util.function.Function;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
public class Editor extends Application
{
	public static final int headerBarHeight = 100;
	public static final int rightPaneWidth = 500;
	public static final int addColWidth = 50;
	private String sprite = null;
	private ScrollPane editPane = null;
	private GameObject addobject = null;
	private int layer = 4;
	private Ev last;
	//Launch application
	public static void main(String[] args)
	{
		launch(args);
	}
	//Create stage
	public void start(Stage stage)
	{
		String[] rows = new String[]{"Base", "Bottom Decoration", "Tile", "Interactable", "Top Decoration"};
		GameObject.LIVE = true;
		Pane editor = new Pane();
		editPane = new ScrollPane();
		editPane.layoutXProperty().bind(editor.widthProperty().subtract(rightPaneWidth));
		editPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		setEdit();
		editPane.setMinWidth(rightPaneWidth);
		editPane.setMaxWidth(rightPaneWidth);
		editPane.setPrefWidth(rightPaneWidth);
		editPane.minHeightProperty().bind(editor.heightProperty().subtract(editPane.layoutYProperty()));
		editPane.maxHeightProperty().bind(editor.heightProperty().subtract(editPane.layoutYProperty()));
		editPane.prefHeightProperty().bind(editor.heightProperty().subtract(editPane.layoutYProperty()));
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
		world.setOnMouseClicked(e->{if(!e.isShiftDown())addObject(e.getX() / world.scaleX(), e.getY() / world.scaleY(), world);});
		final ToggleGroup group = new ToggleGroup();
		ToggleButton tb1 = getToggleButton("Edit", ()->{setEdit();}, editor.widthProperty().subtract(rightPaneWidth), new SimpleDoubleProperty(50*rows.length), new SimpleDoubleProperty(rightPaneWidth/3), new SimpleDoubleProperty(50));
		tb1.setToggleGroup(group);
		editor.getChildren().add(tb1);
		tb1.setSelected(true);
		ToggleButton tb2 = getToggleButton("Add", ()->{setMake();}, editor.widthProperty().subtract(rightPaneWidth - rightPaneWidth/3), new SimpleDoubleProperty(50*rows.length), new SimpleDoubleProperty(rightPaneWidth/3), new SimpleDoubleProperty(50));
		tb2.setToggleGroup(group);
		editor.getChildren().add(tb2);
		ToggleButton tb3 = getToggleButton("Image", ()->{setDecorate();}, editor.widthProperty().subtract(rightPaneWidth - rightPaneWidth*2/3), new SimpleDoubleProperty(50*rows.length), new SimpleDoubleProperty(rightPaneWidth/3), new SimpleDoubleProperty(50));
		tb3.setToggleGroup(group);
		editor.getChildren().add(tb3);
		editor.getChildren().add(getButton("+", ()->{world.expandTop();}, new SimpleDoubleProperty(addColWidth), new SimpleDoubleProperty(headerBarHeight), world.widthProperty(), new SimpleDoubleProperty(addColWidth)));
		editor.getChildren().add(getButton("+", ()->{world.expandBottom();}, new SimpleDoubleProperty(addColWidth), editor.heightProperty().subtract(addColWidth), world.widthProperty(), new SimpleDoubleProperty(addColWidth)));
		editor.getChildren().add(getButton("+", ()->{world.expandLeft();}, new SimpleDoubleProperty(0), new SimpleDoubleProperty(headerBarHeight), new SimpleDoubleProperty(addColWidth), editor.heightProperty().subtract(headerBarHeight)));
		editor.getChildren().add(getButton("+", ()->{world.expandRight();}, editor.widthProperty().subtract(addColWidth + rightPaneWidth), new SimpleDoubleProperty(headerBarHeight), new SimpleDoubleProperty(addColWidth), editor.heightProperty().subtract(headerBarHeight)));
		TextField filename = getTextField("end", "filename", new SimpleDoubleProperty(0), new SimpleDoubleProperty(0), world.widthProperty().divide(2), new SimpleDoubleProperty(headerBarHeight/2));
		editor.getChildren().add(filename);
		editor.getChildren().add(getButton("Save",()->world.save(filename.getText()),world.widthProperty().divide(2),new SimpleDoubleProperty(0),world.widthProperty().divide(4).add(addColWidth),new SimpleDoubleProperty(headerBarHeight/2)));
		editor.getChildren().add(getButton("Load/New", ()->world.load(filename.getText()), world.widthProperty().multiply(3).divide(4).add(addColWidth), new SimpleDoubleProperty(0), world.widthProperty().divide(4).add(addColWidth), new SimpleDoubleProperty(headerBarHeight/2)));
		for(int i = 0; i < rows.length; i++)
		{
			int j = i;
			editor.getChildren().add(getButton(rows[i], ()->{layer = j; world.setLayer(j);last.act();}, editor.widthProperty().subtract(rightPaneWidth), new SimpleDoubleProperty(50*(rows.length-i-1)), new SimpleDoubleProperty(rightPaneWidth), new SimpleDoubleProperty(50)));
		}
		for(GameObject go: ObjectList.LIST)
			Tooltip.install(go.getSprite(), new Tooltip(go.getClassName()));
		setEdit();
		editPane.setLayoutY(50*(1+rows.length));
		editor.getChildren().add(editPane);
		stage.setTitle("A Hero Searching for a Past Editor");
		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();
	}
	public void setEdit()
	{
		last = ()->setEdit();
		addobject = null;
		editPane.setContent(null);
		GameObject.injectable = (o, b)->{
			Pane ed = o.getEditor();
			ed.setMinWidth(rightPaneWidth);
			ed.setMaxWidth(rightPaneWidth);
			ed.setPrefWidth(rightPaneWidth);
			editPane.setContent(ed);
		};
	}
	public void setMake()
	{
		last = ()->setMake();
		addobject = null;
		Function<GameObject, Boolean>[] conditions = new Function[5];
		conditions[0] = o->o.isBase();
		conditions[1] = o->o.isDecoration();
		conditions[2] = o->o.isTile();
		conditions[3] = o->o.isInteractable();
		conditions[4] = o->o.isDecoration();
		FlowPane fp = new FlowPane();
		fp.setMinWidth(rightPaneWidth);
		fp.setMaxWidth(rightPaneWidth);
		fp.setPrefWidth(rightPaneWidth);
		for(GameObject go: ObjectList.LIST)
		{
			if(conditions[layer].apply(go))
			{
				GameObject res = go;
				go.getSprite().setOnMouseClicked(e->{addobject = res;});
				fp.getChildren().add(go.getSprite());
			}
		}
		GameObject.injectable = (o, b)->{
			if(b)
			{
				GameWorld.getWorld().remove(o);
			}
		};
		editPane.setContent(fp);
	}
	public void setDecorate()
	{
		last = ()->setDecorate();
		addobject = null;
		GameObject.injectable = (o, b)->{
			if(sprite != null)
				o.setSprite(sprite);
		};
		
		DirectoryWatcher imsetter = new DirectoryWatcher("Images", 800, sprite==null?"":sprite, "Sprite", 16, s->sprite = s);
		imsetter.minWidthProperty().bind(editPane.minWidthProperty());
		imsetter.maxWidthProperty().bind(editPane.minWidthProperty());
		imsetter.prefWidthProperty().bind(editPane.minWidthProperty());
		editPane.setContent(imsetter);
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
	public static ToggleButton getToggleButton(String text, Ev action, ObservableDoubleValue xproperty, ObservableDoubleValue yproperty, ObservableDoubleValue width, ObservableDoubleValue height)
	{
		ToggleButton b = new ToggleButton(text);
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
	public static TextField getTextField(String text, String prompt, ObservableDoubleValue xproperty, ObservableDoubleValue yproperty, ObservableDoubleValue width, ObservableDoubleValue height)
	{
		TextField field = new TextField(text);
		field.setPromptText(prompt);
		field.layoutXProperty().bind(xproperty);
		field.layoutYProperty().bind(yproperty);
		field.minHeightProperty().bind(height);
		field.maxHeightProperty().bind(height);
		field.prefHeightProperty().bind(height);
		field.minWidthProperty().bind(width);
		field.maxWidthProperty().bind(width);
		field.prefWidthProperty().bind(width);
		return field;
	}
	public void addObject(double x, double y, GameWorld world)
	{
		if(addobject == null) return;
		GameObject copy = addobject.makeCopy();
		copy.setX((int)(x/GameWorld.TILEWIDTH) + world.getLeft());
		copy.setY((int)(y/GameWorld.TILEHEIGHT) + world.getTop());
		if(layer == 0)
			GameWorld.getWorld().addBase((Base)copy);
		else if(layer == 1)
			GameWorld.getWorld().addDecoration((Decoration)copy);
		else if(layer == 2)
			GameWorld.getWorld().addTile((Tile)copy);
		else if(layer == 3)
			GameWorld.getWorld().addInteractable((Interactable)copy);
		else if(layer == 4)
		{
			((Decoration)copy).setIsTopLayer(true);
			GameWorld.getWorld().addDecoration((Decoration)copy);
		}
	}
	public static interface Ev
	{
		public void act();
	}
}
 
