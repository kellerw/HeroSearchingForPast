import javafx.scene.layout.Pane;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
public class GameWorld extends Pane
{
	//width and height of each tile
	public static final int TILEWIDTH = 100;
	public static final int TILEHEIGHT = 100;
	//number of tiles wide/tall world is
	public static final int TILESWIDE = 25;
	public static final int TILESHIGH = 19;
	private static GameWorld world = new GameWorld();
	public static GameWorld getWorld()
	{
		return world;
	}
	private GameWorld()
	{
		load("end");
	}
	public void addDecoration(Decoration decoration)
	{
		this.getChildren().add(decoration.getSprite());
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
}
