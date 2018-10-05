public class CastleGate extends Base
{
	private boolean stepped = false;
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
		for(int i = 0; i < GameWorld.getWorld().getWide(); i++)
			for(int j = 0; j < GameWorld.getWorld().getHigh(); j++)
			{
				double x = GameWorld.getWorld().getLeft() + i;
				double y = GameWorld.getWorld().getTop() + j;
				Base b = GameWorld.getWorld().getBase(x, y);
				if(b != null && b.getClassName().equals("CastleStone"))
					if(!((CastleStone)b).getStepped())
						return false;
			}
		setSprite("GateOpen.png");
		return true;
	}
}
