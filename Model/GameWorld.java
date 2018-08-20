import javafx.scene.layout.Pane;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javafx.scene.Group;
import javafx.beans.property.SimpleDoubleProperty;
public class GameWorld extends Pane
{
	//width and height of each tile
	public static final int TILEWIDTH = 100;
	public static final int TILEHEIGHT = 100;
	//number of tiles wide/tall world is
	public static final int TILESWIDE = 25;
	public static final int TILESHIGH = 19;
	private static GameWorld world = new GameWorld();
	private Player hero;
	private Group group;
	private Pane pane;
	private Pane decorationPaneBottom;
	private Pane decorationPaneTop;
	public static GameWorld getWorld()
	{
		return world;
	}
	private GameWorld()
	{
		//group = new Group();
		//this.getChildren().add(group);
		//group.setScaleX(0.5);
		//group.setScaleY(0.5);
		pane = new Pane();
		decorationPaneBottom = new Pane();
		decorationPaneTop = new Pane();
		//this.setScaleX(0.5);
		//this.setScaleY(0.5);
		//group.getChildren().add(pane);
		this.getChildren().add(pane);
		//this.scaleXProperty().bind(new SimpleDoubleProperty(TILEWIDTH*TILESWIDE).divide(this.widthProperty()));
		//this.scaleYProperty().bind(new SimpleDoubleProperty(TILEHEIGHT*TILESHIGH).divide(this.heightProperty()));
		this.scaleXProperty().bind(this.widthProperty().divide(TILEWIDTH*TILESWIDE));
		this.scaleYProperty().bind(this.heightProperty().divide(TILEHEIGHT*TILESHIGH));
		hero = new Player();
		pane.getChildren().add(decorationPaneBottom);
		pane.getChildren().add(hero.getSprite());
		pane.getChildren().add(decorationPaneTop);
		pane.layoutXProperty().bind(hero.getSprite().layoutXProperty().multiply(-1).add(new SimpleDoubleProperty(TILEWIDTH*(TILESWIDE-1)/2.0).multiply(this.scaleXProperty())));
		pane.layoutYProperty().bind(hero.getSprite().layoutYProperty().multiply(-1).add(new SimpleDoubleProperty(TILEHEIGHT*(TILESHIGH-1)/2.0).multiply(this.scaleYProperty())));
		load("end");
		
	}
	public Player getPlayer()
	{
		return hero;
	}
	public void addDecoration(Decoration decoration)
	{
		(decoration.isTopLayer()?decorationPaneTop:decorationPaneBottom).getChildren().add(decoration.getSprite());
	}
	public void load(String file)
	{
		Scanner scan = null;
		try
		{
			if(GameObject.LIVE)
				scan = new Scanner(new File("Datafiles/"+file+".data"));
			else
				scan = new Scanner(getClass().getResource(file+".data").openStream());
			String s = null;
			while(!"".equals(s = scan.nextLine()))
			{
				addDecoration((Decoration)parseObject(s));
			}
		} catch(IOException e){e.printStackTrace();}
		finally {if(scan != null) scan.close();}
	}
	public GameObject parseObject(String object)
	{
		String name = object.substring(0, object.indexOf('\t'));
		for(GameObject go : ObjectList.LIST)
			if(name.equals(go.getClassName()))
			{
				GameObject res = go.makeCopy();
				res.parse(object);
				return res;
			}
		throw new IllegalArgumentException("Could not parse type: "+name);
	}
	public Tile getTile(double x, double y)
	{
		return null;
	}
}
