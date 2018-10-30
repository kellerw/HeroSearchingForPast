import javafx.scene.layout.Pane;
public class MoveLava extends Base
{
	private int dx = 0;
	private int dy = 0;
	public MoveLava()
	{
		setSprite("lava.png");
	}
	public MoveLava makeCopy()
	{
		return new MoveLava();
	}
	public String getClassName()
	{
		return "MoveLava";
	}
	public Action onWalk(Interactor i2)
	{
		Platform i = new Platform();
		Action a = new Action(o->
		{
			i.setSprite(i2.getImageName());
			i.setX(i2.getX());
			i.setY(i2.getY());
			GameWorld.getWorld().addTile(i);
			GameWorld.getWorld().remove(i2);
		});
		for(int j = 0; j < dx; j++)
			a.then((o)->{if(GameWorld.getWorld().getTile(i.getX(),i.getY())==i)i.move(1, 0, o, GameWorld.getWorld().getBase(i.getX(),i.getY()));}).then(o->{i.setX(i.getX());i.setY(i.getY());o.start();});
		for(int j = 0; j > dx; j--)
			a.then((o)->{if(GameWorld.getWorld().getTile(i.getX(),i.getY())==i)i.move(-1, 0, o, GameWorld.getWorld().getBase(i.getX(),i.getY()));i.setY(i.getY());o.start();});
		for(int j = 0; j < dy; j++)
			a.then((o)->{if(GameWorld.getWorld().getTile(i.getX(),i.getY())==i)i.move(0, 1, o, GameWorld.getWorld().getBase(i.getX(),i.getY()));}).then(o->{i.setX(i.getX());i.setY(i.getY());o.start();});
		for(int j = 0; j > dy; j--)
			a.then((o)->{if(GameWorld.getWorld().getTile(i.getX(),i.getY())==i)i.move(0, -1, o, GameWorld.getWorld().getBase(i.getX(),i.getY()));}).then(o->{i.setX(i.getX());i.setY(i.getY());o.start();});
		return a;
	}
	public String parse(String saved)
	{
		saved = super.parse(saved);
		int index = saved.indexOf("\t\t");
		String[] parts = saved.substring(0, index).split("\t");
		setDx(Integer.parseInt(parts[0]));
		setDy(Integer.parseInt(parts[1]));
		return saved.substring(index+2);
	}
	public String toString()
	{
		return super.toString() + getDx() +"\t"+getDy()+ "\t\t";
	}
	public int getDx()
	{
		return dx;
	}
	public int getDy()
	{
		return dy;
	}
	public void setDx(int x)
	{
		dx = x;
		updateImage();
	}
	public void setDy(int y)
	{
		dy = y;
		updateImage();
	}
	public void updateImage()
	{
		if(getDx() > 0)
			setSprite("lavaright.png");
		else if(getDx() < 0)
			setSprite("lavaleft.png");
		else if(getDy() > 0)
			setSprite("lavadown.png");
		else if(getDy() < 0)
			setSprite("lavaup.png");
		else
			setSprite("lava.png");
	}
	public Pane getEditor()
	{
		Pane p = super.getEditor();
		addTextField("dx", ""+getDx(), p, t->{try{setDx(Integer.parseInt(t));} catch(Exception e){}});
		addTextField("dy", ""+getDy(), p, t->{try{setDy(Integer.parseInt(t));} catch(Exception e){}});
		return p;
	}
	public void rotate()
	{
		int t = getDx();
		setDx(-1 *getDy());
		setDy(t);
	}
	public boolean isWalkable(Interactor i)
	{
		return !i.getClassName().equals("Player");
	}
}
 
