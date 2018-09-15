public class Lava extends Base
{
	public Lava()
	{
		setSprite("lava.png");
	}
	public Lava makeCopy()
	{
		return new Lava();
	}
	public Action onWalk(Interactor i)
	{
		return new Action(o->
		{
			Platform p = new Platform();
			p.setSprite(i.getImageName());
			p.setX(i.getX());
			p.setY(i.getY());
			GameWorld.getWorld().addTile(p);
			GameWorld.getWorld().remove(i);
		});
	}
	public String getClassName()
	{
		return "Lava";
	}
	public boolean isWalkable(Interactor i)
	{
		return !i.getClassName().equals("Player");
	}
}
