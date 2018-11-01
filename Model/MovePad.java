import javafx.scene.layout.Pane;
public class MovePad extends Base
{
	private int dx = 0;
	private int dy = 0;
	public MovePad()
	{
		setSprite("movepadicon.png");
	}
	public MovePad makeCopy()
	{
		return new MovePad();
	}
	public String getClassName()
	{
		return "MovePad";
	}
	public Action onWalk(Interactor i)
	{
		Action a = new Action();
		for(int j = 0; j < dx; j++)
			a.then((o)->{if(GameWorld.getWorld().getInteractable(i.getX(),i.getY())==i)i.tryMoveRight(o);}).then(o->{i.setX(i.getX());i.setY(i.getY());o.start();});
		for(int j = 0; j > dx; j--)
			a.then((o)->{if(GameWorld.getWorld().getInteractable(i.getX(),i.getY())==i)i.tryMoveLeft(o);}).then(o->{i.setX(i.getX());i.setY(i.getY());o.start();});
		for(int j = 0; j < dy; j++)
			a.then((o)->{if(GameWorld.getWorld().getInteractable(i.getX(),i.getY())==i)i.tryMoveDown(o);}).then(o->{i.setX(i.getX());i.setY(i.getY());o.start();});
		for(int j = 0; j > dy; j--)
			a.then((o)->{if(GameWorld.getWorld().getInteractable(i.getX(),i.getY())==i)i.tryMoveUp(o);}).then(o->{i.setX(i.getX());i.setY(i.getY());o.start();});
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
		String name = getImageName().replaceAll("down\\.png","").replaceAll("up\\.png","").replaceAll("left\\.png","").replaceAll("right\\.png","").replaceAll("icon\\.png","");
		if(getDx() > 0)
			setSprite(name+"right.png");
		else if(getDx() < 0)
			setSprite(name+"left.png");
		else if(getDy() > 0)
			setSprite(name+"down.png");
		else if(getDy() < 0)
			setSprite(name+"up.png");
		else
			setSprite(name+"icon.png");
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
}
