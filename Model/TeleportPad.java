public class TeleportPad extends Base
{
	public TeleportPad()
	{
		setSprite("teleportpad.png");
	}
	public TeleportPad makeCopy()
	{
		return new TeleportPad();
	}
	public String getClassName()
	{
		return "TeleportPad";
	}
	public Action onWalk(Interactor i)
	{
		if(getImageName().equals("teleportpad.png"))
			GameWorld.getWorld().playSound("warp.mp3", new Action());
		String[] s = getNames().split(";")[0].split("_");
		Base t = s.length==2?GameWorld.getWorld().getBase(s[0]+"_"+(s[1].toLowerCase().equals("a")?"B":"A")):null;
		if(t == null || GameWorld.getWorld().getInteractable(t.getX(), t.getY()) != null)
			return new Action();
		return new Action(o->{i.setX(-5000);i.setY(t.getY());i.setX(t.getX()); o.start();});
	}
}
