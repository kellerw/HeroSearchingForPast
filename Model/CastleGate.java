public class CastleGate extends Base
{
	private boolean stepped = false;
	private boolean open = false;
	public CastleGate()
	{
		setSprite("Gate.png");
	}
	public CastleGate makeCopy()
	{
		return new CastleGate();
	}
	public String getClassName()
	{
		return "CastleGate";
	}
	public boolean isWalkable(Interactor t)
	{
		if(open)
			return true;
		for(int i = (int)GameWorld.getWorld().getLeft(); i < GameWorld.getWorld().getWide(); i++)
		{
			for(int j = (int)GameWorld.getWorld().getTop(); j < GameWorld.getWorld().getHigh(); j++)
			{
				double x = i;
				double y = j;
				Base b = GameWorld.getWorld().getBase(x, y);
				if(b != null && b.getClassName().equals("CastleStone"))
					if(!((CastleStone)b).getStepped())
						return false;
			}
		}
		setSprite("GateOpen.png");
		open = true;
		return true;
	}
	public boolean isWalkableDown(Interactor i)
	{
		setSprite("GateOpen.png");
		open = true;
		return true;
	}
}
