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
	private Pane interactablePane;
	private Pane tilePane;
	private Pane basePane;
	private Interactable[][] interactables;
	private Tile[][] tiles;
	private Base[][] bases;
	public static GameWorld getWorld()
	{
		return world;
	}
	private GameWorld()
	{
		pane = new Pane();
		decorationPaneBottom = new Pane();
		decorationPaneTop = new Pane();
		interactablePane = new Pane();
		tilePane = new Pane();
		basePane = new Pane();
		this.getChildren().add(pane);
		this.scaleXProperty().bind(this.widthProperty().divide(TILEWIDTH*TILESWIDE));
		this.scaleYProperty().bind(this.heightProperty().divide(TILEHEIGHT*TILESHIGH));
		hero = new Player();
		pane.getChildren().add(decorationPaneBottom);
		pane.getChildren().add(basePane);
		pane.getChildren().add(tilePane);
		pane.getChildren().add(interactablePane);
		interactablePane.getChildren().add(hero.getSprite());
		pane.getChildren().add(decorationPaneTop);
		pane.layoutXProperty().bind(hero.getSprite().layoutXProperty().multiply(-1).add(new SimpleDoubleProperty(TILEWIDTH*(TILESWIDE-1)/2.0).multiply(this.scaleXProperty())));
		pane.layoutYProperty().bind(hero.getSprite().layoutYProperty().multiply(-1).add(new SimpleDoubleProperty(TILEHEIGHT*(TILESHIGH-1)/2.0).multiply(this.scaleYProperty())));
		load("end");
		
	}
	public void addBase(Base b)
	{
		bases[(int)b.getX()][(int)b.getY()] = b;
		basePane.getChildren().add(b.getSprite());
	}
	public void addTile(Tile t)
	{
		tiles[(int)t.getX()][(int)t.getY()] = t;
		tilePane.getChildren().add(t.getSprite());
	}
	public void addInteractable(Interactable i)
	{
		interactables[(int)i.getX()][(int)i.getY()] = i;
		interactablePane.getChildren().add(i.getSprite());
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
			int w = Integer.parseInt(scan.nextLine());
			int h = Integer.parseInt(scan.nextLine());
			int count = 2;
			String s = null;
			try
			{
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					addDecoration((Decoration)parseObject(s));
				}
				count++;
				bases = new Base[w][h];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					addBase((Base)parseObject(s));
				}
				count++;
				tiles = new Tile[w][h];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					addTile((Tile)parseObject(s));
				}
				count++;
				interactables = new Interactable[w][h];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					addInteractable((Interactable)parseObject(s));
				}
				count++;
			}
			catch(RuntimeException e)
			{
				System.out.println("On line "+count);
				System.out.println(s);
				throw e;
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
		return fetch(tiles, x, y);
	}
	public Base getBase(double x, double y)
	{
		return fetch(bases, x, y);
	}
	public Walkable getWalkable(double x, double y)
	{
		Tile tile = getTile(x, y);
		return tile==null?getBase(x,y):tile;
	}
	public Interactable getInteractable(double x, double y)
	{
		return fetch(interactables, x, y);
	}
	public static <T> T fetch(T[][] arr, double xv, double yv)
	{
		int x = (int) xv;
		int y = (int) yv;
		if( x < 0 || y < 0 || x >= arr.length || y >= arr[x].length)
			return null;
		return arr[x][y];
	}
}
