import javafx.scene.layout.Pane;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javafx.scene.Group;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Scale; 
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	private Interactable[][] interactables = new Interactable[1][1];
	private Tile[][] tiles = new Tile[1][1];
	private Base[][] bases = new Base[1][1];
	private ArrayList<Decoration> decorations = new ArrayList<>();
	private SimpleIntegerProperty startx = new SimpleIntegerProperty(0);
	private SimpleIntegerProperty starty = new SimpleIntegerProperty(0);
	private Scale scale;
	public static GameWorld getWorld()
	{
		return world;
	}
	public double scaleX()
	{
		return scale.getX();
	}
	public double scaleY()
	{
		return scale.getY();
	}
	public int getWide()
	{
		return tiles.length + (int)getLeft();
	}
	public int getHigh()
	{
		return tiles[0].length + (int)getTop();
	}
	private GameWorld()
	{
		scale = new Scale();
		pane = new Pane();
		pane.minWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		pane.maxWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		pane.prefWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		pane.minHeightProperty().bind(this.heightProperty().divide(scale.yProperty()));
		pane.maxHeightProperty().bind(this.heightProperty().divide(scale.yProperty()));
		pane.prefHeightProperty().bind(this.heightProperty().divide(scale.yProperty()));
		decorationPaneBottom = getPane();
		decorationPaneTop = getPane();
		interactablePane = getPane();
		tilePane = getPane();
		basePane = getPane();
		this.getChildren().add(pane);
		if(!GameObject.LIVE)
		{
			this.scaleXProperty().bind(this.widthProperty().divide(TILEWIDTH*TILESWIDE));
			this.scaleYProperty().bind(this.heightProperty().divide(TILEHEIGHT*TILESHIGH));
			hero = new Player();
		}
		pane.getChildren().add(basePane);
		pane.getChildren().add(decorationPaneBottom);
		pane.getChildren().add(tilePane);
		pane.getChildren().add(interactablePane);
		if(hero != null)
			interactablePane.getChildren().add(hero.getSprite());
		pane.getChildren().add(decorationPaneTop);
		if(hero != null)
		{
			pane.layoutXProperty().bind(hero.getSprite().layoutXProperty().multiply(-1).add(new SimpleDoubleProperty(TILEWIDTH*(TILESWIDE-1)/2.0).multiply(this.scaleXProperty())));
			pane.layoutYProperty().bind(hero.getSprite().layoutYProperty().multiply(-1).add(new SimpleDoubleProperty(TILEHEIGHT*(TILESHIGH-1)/2.0).multiply(this.scaleYProperty())));
		}
		pane.getTransforms().addAll(scale); 
		load("end");
	}
	public void setWidth(int w1, int w2)
	{
		int v = Math.max(1, w2 - w1);
		if(hero == null)
		{
			scale.xProperty().bind(this.widthProperty().divide(v*TILEWIDTH));
			//this.scaleXProperty().bind(this.widthProperty().divide(v*TILEWIDTH));
			pane.layoutXProperty().bind(new SimpleDoubleProperty(-TILEWIDTH*w1).multiply(scale.xProperty()));//.multiply(this.scaleXProperty()));
		}
		Interactable[][] ints = new Interactable[v][interactables[0].length];
		for(int i = Math.max(startx.getValue(), w1); i < Math.min(w2, startx.getValue() - interactables.length); i++)
			for(int j = 0; j < ints[i].length; j++)
				ints[i-w1][j] = interactables[i-startx.getValue()][j];
		Tile[][] ts = new Tile[v][interactables[0].length];
		for(int i = Math.max(startx.getValue(), w1); i < Math.min(w2, startx.getValue() - interactables.length); i++)
			for(int j = 0; j < ints[i].length; j++)
				ts[i-w1][j] = tiles[i-startx.getValue()][j];
		Base[][] bs = new Base[v][interactables[0].length];
		for(int i = Math.max(startx.getValue(), w1); i < Math.min(w2, startx.getValue() - interactables.length); i++)
			for(int j = 0; j < ints[i].length; j++)
				bs[i-w1][j] = bases[i-startx.getValue()][j];
		interactables = ints;
		tiles = ts;
		bases = bs;
		startx.setValue(w1);
	}
	public double getLeft()
	{
		return startx.getValue();
	}
	public double getTop()
	{
		return starty.getValue();
	}
	public Pane getPane()
	{
		Pane p = new Pane();
		p.minWidthProperty().bind(pane.widthProperty());
		p.maxWidthProperty().bind(pane.widthProperty());
		p.prefWidthProperty().bind(pane.widthProperty());
		p.minHeightProperty().bind(pane.heightProperty());
		p.maxHeightProperty().bind(pane.heightProperty());
		p.prefHeightProperty().bind(pane.heightProperty());
		/*Rectangle r = new Rectangle(0, 0, 0, 0);
		r.layoutXProperty().bind(startx.multiply(TILEWIDTH));
		r.layoutYProperty().bind(starty.multiply(TILEHEIGHT));
		r.widthProperty().bind(p.widthProperty().subtract(r.layoutXProperty()));
		r.heightProperty().bind(p.heightProperty().subtract(r.layoutYProperty()));
		r.setFill(Color.TRANSPARENT);
		p.getChildren().add(r);*/
		return p;
	}
	public void remove(GameObject o)
	{
		if(o.isBase())
		{
			for(int i = 0; i < tiles.length; i++)
				for(int j = 0; j < tiles[i].length; j++)
					if(bases[i][j] == o)
						bases[i][j] = null;
			basePane.getChildren().remove(o.getSprite());
		}
		if(o.isTile())
		{
			for(int i = 0; i < tiles.length; i++)
				for(int j = 0; j < tiles[i].length; j++)
					if(tiles[i][j] == o)
						tiles[i][j] = null;
			tilePane.getChildren().remove(o.getSprite());
		}
		if(o.isInteractable())
		{
			for(int i = 0; i < tiles.length; i++)
				for(int j = 0; j < tiles[i].length; j++)
					if(interactables[i][j] == o)
						interactables[i][j] = null;
			interactablePane.getChildren().remove(o.getSprite());
		}
		if(o.isDecoration())
		{
			decorations.remove(o);
			decorationPaneTop.getChildren().remove(o.getSprite());
			decorationPaneBottom.getChildren().remove(o.getSprite());
		}
	}
	public void setHeight(int h1, int h2)
	{
		int v = Math.max(1, h2 - h1);
		if(hero == null)
		{
			scale.yProperty().bind(this.heightProperty().divide(v*TILEHEIGHT));
			//this.scaleYProperty().bind(this.heightProperty().divide(v*TILEHEIGHT));
			pane.layoutYProperty().bind(new SimpleDoubleProperty(-TILEHEIGHT*h1).multiply(scale.yProperty()));//.multiply(this.scaleYProperty()));
		}
		Interactable[][] ints = new Interactable[interactables.length][v];
		for(int i = 0; i < ints.length; i++)
			for(int j = Math.max(starty.getValue(), h1); j < Math.min(h2, starty.getValue() - interactables[i].length); j++)
			{
// 				System.out.println("ints[i][j-h1] = interactables[i][j-starty.getValue()]");
// 				System.out.println("ints["+i+"]["+j+"-"+h1+"] = interactables["+i+"]["+j+"-"+starty.getValue()+"]");
				ints[i][j-h1] = interactables[i][j-starty.getValue()];
			}
		Tile[][] ts = new Tile[interactables.length][v];
		for(int i = 0; i < ints.length; i++)
			for(int j = Math.max(starty.getValue(), h1); j < Math.min(h2, starty.getValue() - interactables[i].length); j++)
				ts[i][j-h1] = tiles[i][j-starty.getValue()];
		Base[][] bs = new Base[interactables.length][v];
		for(int i = 0; i < ints.length; i++)
			for(int j = Math.max(starty.getValue(), h1); j < Math.min(h2, starty.getValue() - interactables[i].length); j++)
				bs[i][j-h1] = bases[i][j-starty.getValue()];
		interactables = ints;
		tiles = ts;
		bases = bs;
		starty.setValue(h1);
	}
	public void expandTop()
	{
		setHeight(starty.getValue() - 1, starty.getValue() + tiles[0].length);
	}
	public void expandBottom()
	{
		setHeight(starty.getValue(), starty.getValue() + tiles[0].length + 1);
	}
	public void expandLeft()
	{
		setWidth(startx.getValue() - 1, startx.getValue() + tiles.length);
	}
	public void expandRight()
	{
		setWidth(startx.getValue(), startx.getValue() + tiles.length + 1);
	}
	public void addBase(Base b)
	{
		if(bases[(int)b.getX() - startx.getValue()][(int)b.getY() - starty.getValue()] != null)
			remove(bases[(int)b.getX() - startx.getValue()][(int)b.getY() - starty.getValue()]);
		bases[(int)b.getX() - startx.getValue()][(int)b.getY() - starty.getValue()] = b;
		basePane.getChildren().add(b.getSprite());
	}
	public void addTile(Tile t)
	{
		if(tiles[(int)t.getX() - startx.getValue()][(int)t.getY() - starty.getValue()] != null)
			remove(tiles[(int)t.getX() - startx.getValue()][(int)t.getY() - starty.getValue()]);
		tiles[(int)t.getX() - startx.getValue()][(int)t.getY() - starty.getValue()] = t;
		tilePane.getChildren().add(t.getSprite());
	}
	public void addInteractable(Interactable i)
	{
		if(interactables[(int)i.getX() - startx.getValue()][(int)i.getY() - starty.getValue()] != null)
			remove(interactables[(int)i.getX() - startx.getValue()][(int)i.getY() - starty.getValue()]);
		interactables[(int)i.getX() - startx.getValue()][(int)i.getY() - starty.getValue()] = i;
		interactablePane.getChildren().add(i.getSprite());
	}
	public Player getPlayer()
	{
		return hero;
	}
	public void addDecoration(Decoration decoration)
	{
		decorations.add(decoration);
		(decoration.isTopLayer()?decorationPaneTop:decorationPaneBottom).getChildren().add(decoration.getSprite());
	}
	public void load(String file)
	{
		Scanner scan = null;
		setWidth(0, 1);
		setHeight(0, 1);
		bases = new Base[bases.length][bases[0].length];
		tiles = new Tile[bases.length][bases[0].length];
		interactables = new Interactable[bases.length][bases[0].length];
		decorations.clear();
		decorationPaneBottom.getChildren().clear();
		decorationPaneTop.getChildren().clear();
		tilePane.getChildren().clear();
		interactablePane.getChildren().clear();
		basePane.getChildren().clear();
		if(hero != null)
			interactablePane.getChildren().add(hero.getSprite());
		try
		{
			if(GameObject.LIVE)
				scan = new Scanner(new File("Datafiles/"+file+".data"));
			else
				scan = new Scanner(getClass().getResource(file+".data").openStream());
			setWidth(Integer.parseInt(scan.nextLine()),Integer.parseInt(scan.nextLine()));
			setHeight(Integer.parseInt(scan.nextLine()),Integer.parseInt(scan.nextLine()));
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
				bases = new Base[bases.length][bases[0].length];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					addBase((Base)parseObject(s));
				}
				count++;
				tiles = new Tile[bases.length][bases[0].length];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					addTile((Tile)parseObject(s));
				}
				count++;
				interactables = new Interactable[bases.length][bases[0].length];
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
		} catch(IOException e){if(hero != null) e.printStackTrace();}
		finally {if(scan != null) scan.close();}
	}
	public void save(String file)
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(new File("Datafiles/"+file+".data"));
			out.println((int)getLeft());
			out.println((int)getWide());
			out.println((int)getTop());
			out.println((int)getHigh());
			for(Decoration d : decorations)
				out.println(d);
			out.println();
			for(int i = 0; i < bases.length; i++)
				for(int j = 0; j < bases[i].length; j++)
					if(bases[i][j] != null)
						out.println(bases[i][j]);
			out.println();
			for(int i = 0; i < bases.length; i++)
				for(int j = 0; j < bases[i].length; j++)
					if(tiles[i][j] != null)
						out.println(tiles[i][j]);
			out.println();
			for(int i = 0; i < bases.length; i++)
				for(int j = 0; j < bases[i].length; j++)
					if(interactables[i][j] != null)
						out.println(interactables[i][j]);
			out.println();
		} catch(IOException e){e.printStackTrace();}
		finally {if(out != null) out.close();}
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
	public void setLayer(int layer)
	{
		pane.getChildren().clear();
		pane.getChildren().add(basePane);
		if(layer > 0) pane.getChildren().add(decorationPaneBottom);
		if(layer > 1) pane.getChildren().add(tilePane);
		if(layer > 2) pane.getChildren().add(interactablePane);
		if(layer > 3) pane.getChildren().add(decorationPaneTop);
	}
	public <T> T fetch(T[][] arr, double xv, double yv)
	{
		int x = (int) xv - startx.getValue();
		int y = (int) yv - starty.getValue();
		if( x < 0 || y < 0 || x >= arr.length || y >= arr[x].length)
			return null;
		return arr[x][y];
	}
	public void moveInteractable(Interactable i, int ox, int oy, int nx, int ny)
	{
		ox -= startx.getValue();
		nx -= startx.getValue();
		oy -= starty.getValue();
		ny -= starty.getValue();
		System.out.println(interactables[ox][oy]+"/"+ i);
		System.out.println(interactables[ox][oy]==i);
		System.out.println(ox+" "+oy+" "+nx+" "+ny);
		if(interactables[ox][oy] == i)
			interactables[ox][oy] = null;
		interactables[nx][ny] = i;
		for(int k = 0; k < interactables.length; k++)
		{
			for(int j = 0; j < interactables[k].length; j++)
				System.out.print(interactables[k][j]==null?".":"o");
			System.out.println();
		}
	}
}
