import javafx.application.Platform;
public class CastleStone extends Base
{
	private boolean stepped = false;
	public CastleStone()
	{
		setSprite("IceStoneButton.png");
	}
	public CastleStone makeCopy()
	{
		return new CastleStone();
	}
	public String getClassName()
	{
		return "CastleStone";
	}
	public boolean getStepped()
	{
		return stepped;
	}
	public Action onWalk(Interactor i)
	{
		return new Action((o)->{if(stepped) GameWorld.getWorld().restartLevel(); else stepped = true; o.start();});
	}
	public Action onWalkOff(Interactor i)
	{
		return new Action((o)-> {
		new Thread() { public void run() {
						try {
							Thread.sleep((int)(200));
							Platform.runLater(()->
							{
								setSprite("Pit.png");
								 o.start();
							});
						} catch(InterruptedException v) {
							System.out.println(v);
						} 
					}
		}.start();});
	}
}
