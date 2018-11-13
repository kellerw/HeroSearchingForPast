public class End extends LoadNew
{
	public End()
	{
		if(Memory.found.size() == 6)
		{
			Decoration d = new Decoration("cave.png");
			d.setX(1);
			d.setY(3);
			GameWorld.getWorld().addDecoration(d);
		}
	}
	public Action onWalk(Interactor i)
	{
		Action a = new Action();
		if(Memory.found.size() == 6)
			a.then(super.onWalk(i));
		return a;
	}
	public End makeCopy()
	{
		return new End();
	}
	public String getClassName()
	{
		return "End";
	}
}
