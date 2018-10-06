import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.io.File;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
public abstract class GameObject
{
	public static boolean LIVE = false;
	public static BiConsumer<GameObject, Boolean> injectable;
	//Semicolon separated list of names this element has
	private String names = "";
	//Image of the object
	private ImageView sprite;
	//x coordinate in tiles to display on screen
	private double x = -5000;
	//y coordinate in tiles to display on screen
	private double y = -5000;
	//name of image
	private String imagename;
	
	public GameObject()
	{
		sprite = new ImageView();
		//Used for editor
		if(LIVE)
		{
			sprite.setOnMouseClicked((e)->{injectable.accept(this, e.isShiftDown());});
			sprite.setOnMouseDragged((e)->{injectable.accept(this, e.isShiftDown());});
		}
	}
	
	//call the constructor - used in the editor
	public abstract GameObject makeCopy();
	
	public String getNames()
	{
		return names;
	}
	public void setNames(String names)
	{
		this.names = names;
	}
	
	public ImageView getSprite()
	{
		return sprite;
	}
	public String getImageName()
	{
		return imagename;
	}
	public void setSprite(String image)
	{
		try
		{
			this.imagename = image;
			if(LIVE)
				//special method for the editor so don't have to rebuild to get new Images
				sprite.setImage(new Image(new File("Images/"+image).toURI().toString()));
			else
				sprite.setImage(new Image(getClass().getResource(image).openStream()));
		}
		catch(IOException ioe) {ioe.printStackTrace();}
		catch(RuntimeException e)
		{
			System.out.println("While loading: "+(LIVE?"./Images/":"")+image);
			throw e;
		}
	}
	
	public double getX()
	{
		return this.x;
	}
	public void setX(double x)
	{
		setX(x, true);
	}
	public void setX(double x, boolean set)
	{
		this.x = x;
		if(set)
			sprite.setLayoutX(x*GameWorld.TILEWIDTH);
	}
	public double getY()
	{
		return this.y;
	}
	public void setY(double y)
	{
		setY(y, true);
	}
	public void setY(double y, boolean set)
	{
		this.y = y;
		if(set)
			sprite.setLayoutY((1+y)*GameWorld.TILEHEIGHT-sprite.getImage().getHeight());
	}
	public abstract String getClassName();
	public String parse(String saved)
	{
		int index = saved.indexOf("\t\t");
		String s = saved.substring(0, index);
		String[] parts = s.split("\t");
		setNames(parts[1]);
		setSprite(parts[2]);
		setX(Double.parseDouble(parts[3]));
		setY(Double.parseDouble(parts[4]));
		return saved.substring(index+2);
	}
	public boolean isDecoration()
	{
		return false;
	}
	public boolean isBase()
	{
		return false;
	}
	public boolean isTile()
	{
		return false;
	}
	public boolean isInteractable()
	{
		return false;
	}
	public String toString()
	{
		return getClassName() + "\t" + (getNames().equals("")?"-":getNames())+"\t"+getImageName()+"\t"+getX()+"\t"+getY() + "\t\t";
	}
	public Pane getEditor()
	{
		VBox editor = new VBox();
		addFile("Image", getImageName(), "Images", editor, (s)->{setSprite(s);});
		addTextField("Name(s)", getNames(), editor, (s)->setNames(s));
		addTextField("X", ""+getX(), editor, (s)->setX(Double.parseDouble(s)));
		addTextField("Y", ""+getY(), editor, (s)->setY(Double.parseDouble(s)));
		return editor;
	}
	public static void addFile(String prompt, String text, String directory, Pane pane, OnTextSet set)
	{
		DirectoryWatcher imsetter = new DirectoryWatcher(directory, 400, text, prompt, s->set.set(s));
		imsetter.minWidthProperty().bind(pane.minWidthProperty());
		imsetter.maxWidthProperty().bind(pane.minWidthProperty());
		imsetter.prefWidthProperty().bind(pane.minWidthProperty());
		pane.getChildren().add(imsetter);
	}
	public static void addTextField(String prompt, String text, Pane pane, OnTextSet set)
	{
		TextField field = new TextField(text);
		field.setPromptText(prompt);
		field.focusedProperty().addListener((obs, oldVal, newVal) -> set.set(field.getText()));
		field.setOnAction(e->{set.set(field.getText());});
		field.minWidthProperty().bind(pane.widthProperty());
		field.maxWidthProperty().bind(pane.widthProperty());
		field.prefWidthProperty().bind(pane.widthProperty());
		addRow(pane, prompt).getChildren().add(field);
	}
	public static Pane addRow(Pane pane, String description)
	{
		HBox h = new HBox();
		h.minWidthProperty().bind(pane.widthProperty());
		h.maxWidthProperty().bind(pane.widthProperty());
		h.prefWidthProperty().bind(pane.widthProperty());
		h.getChildren().add(getText(description, 100));
		pane.getChildren().add(h);
		return h;
	}
	public static Label getText(String text, double width)
	{
		Label l = new Label(text);
		l.setMinWidth(width);
		l.setMaxWidth(width);
		l.setPrefWidth(width);
		l.setAlignment(javafx.geometry.Pos.CENTER);
		return l;
	}
	public static interface OnTextSet
	{
		public void set(String text);
	}
}
